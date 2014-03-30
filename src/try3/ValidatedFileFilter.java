/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package try3;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Omar
 */
public class ValidatedFileFilter extends FileFilter {
    
    @Override
    public boolean accept(File f) {
        String name = f.getName();
        if(     name.endsWith(".jpg") ||
                name.endsWith(".png") ||
                name.endsWith(".pdf") ||
                name.endsWith(".jpeg")||
                name.endsWith(".png") ||
                f.isDirectory()
                )
            return true;
        else return false;
    }
    
    @Override
    public String getDescription() {
        return "Imagenes";
    }
}
