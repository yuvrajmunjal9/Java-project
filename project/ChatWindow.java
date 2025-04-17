import java.awt.*;
import javax.swing.*;

public class ChatWindow extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    @SuppressWarnings("FieldMayBeFinal")
    private JButton sendButton;

    public ChatWindow(String username) {
        setTitle("Chat - " + username);
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();
        sendButton = new JButton("Send");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Example action: appends message to chatArea on send click
        sendButton.addActionListener(e -> {
            String message = inputField.getText();
            chatArea.append("Me: " + message + "\n");
            inputField.setText("");
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatWindow window = new ChatWindow("User1");
            window.setVisible(true);
        });
    }
}
