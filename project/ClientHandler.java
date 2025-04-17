import java.io.*;
import java.net.*;

 class ClientHandler implements Runnable {
    @SuppressWarnings("FieldMayBeFinal")
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            do {
                out.println("Enter your username: ");
                username = in.readLine();
            } while (!ChatServer.addClient(username, this));

            System.out.println(username + " has joined the chat.");
            ChatServer.broadcastMessage(username + " has joined the chat!", this);

            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("/pm")) {
                    String[] parts = message.split(" ", 3);
                    if (parts.length >= 3) {
                        String recipient = parts[1];
                        String privateMessage = parts[2];
                        ChatServer.privateMessage(recipient, privateMessage, this);
                    } else {
                        out.println("Invalid private message format. Use: /pm <username> <message>");
                    }
                } else if (message.equalsIgnoreCase("/users")) {
                    out.println("Online Users: " + String.join(", ", ChatServer.getOnlineUsers()));
                } else {
                    System.out.println(username + ": " + message);
                    ChatServer.broadcastMessage(username + ": " + message, this);
                }
            }
        } catch (IOException e) {
            System.out.println(username + " disconnected.");
        } finally {
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing socket for " + username);
            }
            ChatServer.removeClient(username);
            ChatServer.broadcastMessage(username + " has left the chat.", this);
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
