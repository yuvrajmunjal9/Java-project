// ChatServer.java
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    ArrayList<String> myArrayList = new ArrayList<>();

    static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started. Waiting for clients...");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");
            ClientHandler clientHandler = new ClientHandler(socket);
            clients.add(clientHandler);
            clientHandler.start();
        }
    }

    // public static ArrayList<ClientHandler> getClients() {
    //     return clients;
    // }

    // public static void setClients(ArrayList<ClientHandler> clients) {
    //     ChatServer.clients = clients;
    // }

    static void broadcastMessage(String string, ClientHandler aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    static void removeClient(String username) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    static boolean addClient(String username, ClientHandler aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    static void privateMessage(String recipient, String privateMessage, ClientHandler aThis) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    static CharSequence[] getOnlineUsers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<String> getMyArrayList() {
        return myArrayList;
    }

    public void setMyArrayList(ArrayList<String> myArrayList) {
        this.myArrayList = myArrayList;
    }
}

class ClientHandler extends Thread {
    Socket socket;
    BufferedReader in;
    PrintWriter out;

    ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @SuppressWarnings("override")
    public void run() {
        try {
            String msg;
            while ((msg = in.readLine()) != null) {
                System.out.println("Received: " + msg);
                for (ClientHandler client : ChatServer.clients) {
                    if (client != this) {
                        client.out.println(msg);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected");
        }
    }
}
