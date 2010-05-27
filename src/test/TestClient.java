package test;

import client.ChatClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kiki
 */
public class TestClient {

    public static void main(String[] args) {
        try {
            ChatClient foo = new ChatClient();
            foo.sendMessage("test message should be added to the arraylist");
        } catch (IOException ex) {
            Logger.getLogger(TestClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
