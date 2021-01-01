import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
/**
 * @author :  dev parzival
 * @date :  01 Jan, 2021
 */
public class SockClient implements Runnable{
    private static PrintStream cout;
    private Socket socket=null;
    private DataInputStream in=null;
    private DataOutputStream out=null;
    private Thread thread;
    private String address;
    private int port;
    static{
        cout=System.out;
    }
    public SockClient(String address,int port){
        this.address=address.toString();
        this.port=port;
    }
    public void run(){
        try{
            socket=new Socket(address,port);
            in=new DataInputStream(System.in);
            out=new DataOutputStream(socket.getOutputStream());
            String line="";
            while (true) {
                line=in.readLine();
                if(line.equalsIgnoreCase("bye"))
                    break;
                out.writeUTF(line);
            }
            in.close();
            out.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public void start(){
        thread=new Thread(this);
        thread.start();
    }
    public void join(){
        try{
            thread.join();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    ////////main///////
    public static void main(String $[]){
        SockClient client=new SockClient("127.0.0.1",54000);
        client.start();
        client.join();
    }
}
