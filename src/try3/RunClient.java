/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package try3;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
                BufferedImage bi = new Cliente(InetAddress.getByName("127.0.0.1"), 8081).requestFile(doc);
                JFrame frame = new JFrame();
                frame.getContentPane().setLayout(new FlowLayout());
                frame.getContentPane().add(new JLabel(new ImageIcon(bi)));
                frame.pack();
                frame.setVisible(true);
            }
        }
        catch (IOException ex) {
            Logger.getLogger(RunClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
