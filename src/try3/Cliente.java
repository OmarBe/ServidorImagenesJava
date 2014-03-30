/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package try3;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

class Cliente extends Socket implements Runnable{
    public static final int DEFAULT_BUFFER_SIZE = 1024;
    
    private DataOutputStream dataOutputStream;
    
    private FileInputStream fileInputStream;
    public Cliente(InetAddress address,int port) throws IOException{
        super(address,port);
        initStreams(getInputStream(), getOutputStream());
    }
    public void initStreams(InputStream inputStream,OutputStream outputStream){
        dataOutputStream=new DataOutputStream(outputStream);
    }
    public void upFile(File file){
        if(file.exists()&&file.length()>0){
            try {
                fileInputStream=new FileInputStream(file);
                dataOutputStream.writeUTF(file.getName());
                dataOutputStream.flush();
                new Thread(this).start();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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