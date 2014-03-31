/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package try3;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JFileChooser;

public class Transfers{
    public static void main(String[] argv) {
        try {
            Servidor server=new Servidor(8081, 500, InetAddress.getByName("127.0.0.1"));
            server.init();
            if(server.isRunning()){
                JFileChooser fc = new JFileChooser();
                fc.setFileFilter(new ValidatedFileFilter());
                if( fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION )
                {
                    System.out.println(fc.getSelectedFile().getAbsolutePath());
                    new Cliente(InetAddress.getByName("127.0.0.1"), 8081).upFile(new Document("",0,fc.getSelectedFile()));
                }
            }
        } catch (UnknownHostException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
        
    }
    
}