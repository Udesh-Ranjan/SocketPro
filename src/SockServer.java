import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataInputStream;
import java.io.BufferedInputStream;
/**
 * @author :  dev parzival
 * @date :  01 Jan, 2021
 */
public class SockServer implements Runnable{
    private static PrintStream cout;
    private Socket socket=null;
    private ServerSocket server=null;
    private DataInputStream in=null;
    private Thread thread;
    private int port;
    static{
        cout=System.out;
    }
    public SockServer(int port){
        this.port =port;
    }
    @Override
    public void run(){
        try{
            String line="";
            server=new ServerSocket(port);
            socket=server.accept();
            cout.println("Client Connected");
            in=new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            while(true) {
                    line=in.readUTF();
                    if(line.equalsIgnoreCase("bye")){
                        cout.println("Ok, bye!!");
                        break;
                    }
                    cout.println(line);
            }
            in.close();
            socket.close();
            server.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void start(){
        thread=new Thread(this);
        thread.start();
    }
    public void join(){
        try {
            thread.join();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    ////////main///////
    public static void main(String $[]){
        SockServer server=new SockServer(54000);
        server.start();
        server.join();
    }
}
