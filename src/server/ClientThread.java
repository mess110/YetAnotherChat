package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

/**
 *
 * @author kiki
 */
public class ClientThread extends Thread {

    private PrintStream os = null;
    private BufferedReader br = null;
    private Socket clientSocket = null;
    private ArrayList<ClientThread> tr;

    public ClientThread(Socket clientSocket, ArrayList<ClientThread> tr) {
        this.clientSocket = clientSocket;

        try {
            clientSocket.setSoTimeout(ChatServer.DEFAULT_TIMEOUT);
        } catch (SocketException se) {
            System.err.println("Unable to set socket option SO_TIMEOUT");
        }

        this.tr = tr;
    }

    @Override
    public void run() {
        try {
            String line;
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintStream(clientSocket.getOutputStream());
            do {
                line = br.readLine();
                for (int i = 0; i < tr.size(); i++) {
                    tr.get(i).os.println(line);
                }
            } while (line != null);

            for (int i = 0; i < tr.size(); i++) {
                if (tr.get(i) != this) {
                    tr.get(i).os.println("*** The user is leaving the chat room !!! ***");
                }
            }

            for (int i = 0; i < tr.size(); i++) {
                if (tr.get(i) == this) {
                    tr.remove(i);
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            //Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                //Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
            os.close();
            try {
                clientSocket.close();
            } catch (IOException ex) {
                //Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }


    }
}
