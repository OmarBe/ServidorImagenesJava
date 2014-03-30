/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package try3;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
    private class Connection extends Thread{
        
        public static final int DEFAULT_BUFFER_SIZE = 1024*5;
        
        private DataInputStream dataInputStream;
        private Socket socket;
        
        public Connection(Socket socket){
            try {
                this.socket=socket;
                dataInputStream=new DataInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void run(){
            try{
                String fileName = dataInputStream.readUTF();
                System.out.println(">>Receiving file: "+fileName);
                System.out.println(">>Please wait...");
                FileOutputStream fileOutputStream=new FileOutputStream("D:/"+fileName);
                byte b[]=new byte[DEFAULT_BUFFER_SIZE];
                int len=0;
                System.out.println(dataInputStream);
                while((len=dataInputStream.read(b))>0){
                    fileOutputStream.write(b, 0, len);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
                System.out.println(">>Task completed!");
            } catch (IOException e) {
                e.printStackTrace();
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
        
        Socket socket=null;
        while(running){
            System.out.println(">Waiting for connections...");
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