/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package try3;

import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Omar
 */
public class RunServer {
    public static void main (String [] args){
        Servidor server;
        try {
            server = new Servidor(8081, 500, InetAddress.getByName("25.29.181.82"));
            server.init();
        } catch (IOException ex) {
            Logger.getLogger(RunServer.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
}
