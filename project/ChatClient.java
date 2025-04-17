// ChatClient.java
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame {
    TextArea chatArea;
    TextField inputField;
    Button sendButton;
    Socket socket;
    PrintWriter out;

    public ChatClient() {
        setTitle("Simple Chat Client");
        setSize(400, 400);
        setLayout(new BorderLayout());

        chatArea = new TextArea();
        chatArea.setEditable(false);
        add(chatArea, BorderLayout.CENTER);

        Panel inputPanel = new Panel(new BorderLayout());
        inputField = new TextField();
        sendButton = new Button("Send");

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        addWindowListener(new WindowAdapter() {
            @SuppressWarnings("override")
            public void windowClosing(WindowEvent we) {
                try {
                    if (out != null) out.close();
                    if (socket != null) socket.close();
                } catch (IOException ignored) {}
                System.exit(0);
            }
        });

        setVisible(true);
        connectToServer();
    }

    private void sendMessage() {
        String msg = inputField.getText();
        if (!msg.isEmpty()) {
            out.println(msg);
            chatArea.append("Me: " + msg + "\n");
            inputField.setText("");
        }
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                String msg;
                try {
                    while ((msg = in.readLine()) != null) {
                        chatArea.append("Friend: " + msg + "\n");
                    }
                } catch (IOException e) {
                    chatArea.append("Disconnected.\n");
                }
            }).start();

        } catch (IOException e) {
            chatArea.append("Unable to connect to server.\n");
        }
    }

    public static void main(String[] args) {
        String username = null;
        ChatWindow window = new ChatWindow(username);
    window.setVisible(true);  // Proper way
}

}
