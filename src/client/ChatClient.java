package client;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kiki
 */
public class ChatClient {

    private static final int DEFAULT_PORT = 12345;
    private static final String DEFAULT_HOST = "localhost";
    private PrintStream pout = null;
    private BufferedReader br = null;
    private Socket socket = null;
    private ArrayList<String> receivedMessages = new ArrayList<String>();

    public ChatClient() throws IOException {
        socket = new Socket(DEFAULT_HOST, DEFAULT_PORT);
        connect();
    }

    public ChatClient(String host, int port) throws IOException {
        socket = new Socket(host, port);
        connect();
    }

    private void connect() throws IOException {
        pout = new PrintStream(socket.getOutputStream());
        new Thread() {

            @Override
            public void run() {
                try {
                    String message;
                    br = new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));
                    while ((message = br.readLine()) != null) {
                        receivedMessages.add(message);
                        System.out.println(message);
                    }

                    close();
                } catch (IOException ex) {
                    Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }

    public ArrayList<String> getReceivedMessages() {
        return receivedMessages;
    }

    public void clear() {
        receivedMessages.clear();
    }

    private void close() throws IOException {
        br.close();
        pout.close();
        socket.close();
    }

    public void sendMessage(String s) {
        pout.println(s);
    }
}
