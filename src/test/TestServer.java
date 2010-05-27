package test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.ChatServer;

/**
 *
 * @author kiki
 */
public class TestServer {

    public static void main(String[] args) {
        try {
            ChatServer asd = new ChatServer();
        } catch (IOException ex) {
            Logger.getLogger(TestServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
