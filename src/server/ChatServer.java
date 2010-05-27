package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 *
 * @author kiki
 */
public class ChatServer {

    public static final int DEFAULT_TIMEOUT = 30000;
    public static final int DEFAULT_PORT = 12345;
    private int port;
    private Socket clientSocket = null;
    private ServerSocket serverSocket = null;
    private ArrayList<ClientThread> tr = new ArrayList<ClientThread>();

    public ChatServer() throws IOException {
        port = DEFAULT_PORT;
        listen();
    }

    public ChatServer(int port) throws IOException {
        this.port = port;
        listen();
    }

    private void listen() throws IOException {

        serverSocket = new ServerSocket(port);

        while (true) {
            try {
                clientSocket = serverSocket.accept();
                ClientThread foo = new ClientThread(clientSocket, tr);
                tr.add(foo);
                foo.start();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
