/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package try3;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Omar
 */
public class RunClient {
    public static void main (String [] args){
        try {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new ValidatedFileFilter());
            if( fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION )
            {
                
                System.out.println(fc.getSelectedFile().getAbsolutePath());
                Document doc = new Document("ISW",274367,fc.getSelectedFile());
                new Cliente(InetAddress.getByName("25.29.181.82"), 8081).upFile(doc);
            }
        }
        catch (IOException ex) {
            Logger.getLogger(RunClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
