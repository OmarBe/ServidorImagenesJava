/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package try3;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Adler32;
import java.util.zip.GZIPOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
/**
 * @author Leyer
 */
class Servidor extends ServerSocket implements Runnable{
    private boolean running = false;
    
    public Servidor(int port, int backlog, InetAddress bindAddr)
            throws IOException {
        super(port, backlog, bindAddr);
        running=true;
        System.out.println(">Server started on port: "+port);
    }
    public void init(){
        new Thread(this).start();
    }
    private class Connection extends Thread implements java.io.Serializable{
        
        public static final int DEFAULT_BUFFER_SIZE = 1024*5;
        
        private ObjectInputStream dataInputStream;
        private ObjectOutputStream dataOutputStream;
        private Socket socket;
        
        public Connection(Socket socket){
            try {
                this.socket=socket;
                dataInputStream=new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run(){
            try{
                dataOutputStream=new ObjectOutputStream(socket.getOutputStream());
                if(dataInputStream.readUTF().equals("u")){
                    //String fileName = dataInputStream.readUTF();
                    Document doc = (Document) dataInputStream.readObject();
                    String fileName = doc.getDocument().getName();
                    System.out.println(">>Receiving file: "+fileName);
                    System.out.println(">>Please wait...");
                    File folder = new File("D://"+doc.getCarrera()+"/"+doc.getMatricula()+"/");
                    if(!folder.exists()){
                        folder.mkdirs();
                        System.out.println("No existe!!");
                    } else
                        System.out.println("Existe!!");
                    FileOutputStream fileOutputStream=new FileOutputStream("D://"+doc.getCarrera()+"/"+doc.getMatricula()+"/"+fileName);
                    byte b[]=new byte[DEFAULT_BUFFER_SIZE];
                    int len;
                    System.out.println(dataInputStream);
                    while((len=dataInputStream.read(b))>0){
                        fileOutputStream.write(b, 0, len);
                    }
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    System.out.println(">>Task completed!");
                } else {
                    byte compressedData[];
                    String path = (String) dataInputStream.readObject();
                    ImageIcon in = new ImageIcon(Toolkit.getDefaultToolkit().createImage("D://"+path));
                    BufferedImage bi = new BufferedImage(in.getIconWidth(), in.getIconHeight(), BufferedImage.TYPE_INT_RGB);
                    in.paintIcon(null,bi.createGraphics(),0,0);
                    int width = bi.getWidth();
                    int height = bi.getHeight();
                    dataOutputStream.writeObject(width);
                    dataOutputStream.flush();
                    dataOutputStream.writeObject(height);
                    dataOutputStream.flush();
                    int data[][] = new int[width][height];
                    
                    for (int w=0; w<width; w++)
                        for (int h=0; h<height; h++)
                            data[w][h] = bi.getRGB(w,h);
                    try {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        //dataOutputStream = new ObjectOutputStream(new GZIPOutputStream(baos, 512*1024));
                        dataOutputStream.writeObject(data);
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dataOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public boolean isRunning(){
        return running;
    }
    @Override
    public void run() {
        System.out.println(">Waiting for connections...");
        Socket socket=null;
        while(running){
            try {
                socket=accept();
                System.out.println(">>New Connection Received: "+socket.getInetAddress());
                System.out.println(">>Starting thread for connection...");
                Connection connection=new Connection(socket);
                connection.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}