/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package try3;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class Cliente extends Socket implements Runnable, java.io.Serializable{
    public static final int DEFAULT_BUFFER_SIZE = 1024;
    
    private ObjectOutputStream dataOutputStream;
    private ObjectInputStream dataInputStream;
    
    private FileInputStream fileInputStream;
    public Cliente(InetAddress address,int port) throws IOException{
        super(address,port);
        initStreams(getInputStream(), getOutputStream());
    }
    public void initStreams(InputStream inputStream,OutputStream outputStream){
        try {
            dataOutputStream=new ObjectOutputStream(outputStream);
            dataInputStream=new ObjectInputStream(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void upFile(Document file){
        if(file.getDocument().exists()&&file.getDocument().length()>0){
            try {
                dataOutputStream.writeUTF("u");
                fileInputStream=new FileInputStream(file.getDocument());
                //dataOutputStream.writeUTF(file.getDocument().getName());
                dataOutputStream.writeObject(file);
                dataOutputStream.flush();
                new Thread(this).start();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public BufferedImage requestFile(Document file){
        if(file.getDocument().exists()&&file.getDocument().length()>0){
            try {
                dataOutputStream.writeUTF("r");
                String path = file.getCarrera()+"/"+file.getMatricula()+"/"+file.getDocument().getName();
                //dataOutputStream.writeUTF(file.getDocument().getName());
                dataOutputStream.writeObject(path);
                dataOutputStream.flush();
                int width = (int) dataInputStream.readObject();
                int height = (int) dataInputStream.readObject();
                
                int data [][] = (int [][]) dataInputStream.readObject();
                BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                for (int w=0; w<width; w++)
                    for (int h=0; h<height; h++)
                        bi.setRGB(w,h,data[w][h]);
                return bi;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    @Override
    public void run() {
        byte b[]=new byte[DEFAULT_BUFFER_SIZE];
        int len=0,off=0;
        try {
            while((len=fileInputStream.read(b))>0)
                dataOutputStream.write(b, off, len);
            dataOutputStream.flush();
        } catch (IOException e1) {
            e1.printStackTrace();
        }finally{
            try {
                close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}