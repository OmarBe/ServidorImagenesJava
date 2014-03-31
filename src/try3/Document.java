/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package try3;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author Omar
 */
public class Document implements Serializable {
    
    private String CARRERA;
    
    private int MATRICULA;
    
    private File DOC;
    
    public Document(String carr, int mat, File doc){
        CARRERA = carr;
        MATRICULA = mat;
        DOC = doc;
    }
    
    public void setCarrera(String carrera){
        CARRERA = carrera;
    }
    
    public void setMatricula(int mat){
        MATRICULA = mat;
    }
    
    public void setDocument(File f){
        DOC = f;
    }
    
    public String getCarrera(){
        return CARRERA;
    }
    
    public int getMatricula(){
        return MATRICULA;
    }
    
    public File getDocument(){
        return DOC;
    }
    
    
}
