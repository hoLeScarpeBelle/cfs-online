package carta_forbice_sasso_with_socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SockClient extends Thread 
{
    public int port;
    public String ip;
    private Game_window parent;
    private Socket client;
    private PrintWriter send; 
    private BufferedReader recv;
            
    public SockClient(String ip,int port,Game_window parent)
    {
        this.ip = ip;
        this.port = port;
        this.parent = parent;
    }
    
    public void run()
    {
        try {
            System.out.println("waiting ------");
            this.client = new Socket(this.ip , this.port);
            this.send = new PrintWriter(client.getOutputStream());
            this.recv = new BufferedReader(new InputStreamReader(client.getInputStream()));
            
            recv.readLine();//aspetta che siano collegati due client
            this.parent.setActionLabel("scegli cosa buttare");
            this.parent.enableAllButton();
            
            String result,splittedResult[];
            while(true)
            {
                result = recvMsg();
                splittedResult = result.split(";");//0 = punti p1 ; 1 = punti p2  ; 3 = scelta di p2;
                this.parent.setPointLabel(Integer.parseInt(splittedResult[0]),Integer.parseInt(splittedResult[1]));
                this.parent.setP2ChoiceLabel(splittedResult[2]);
                this.parent.enableAllButton();//collegare il client con l'interfaccia
            }
            
        } catch (IOException ex) {
            Logger.getLogger(SockClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void sendMsg(String msg)
    {
        this.send.println(msg);
        this.send.flush();
    }
    
    public String recvMsg()
    {
        try 
        {
            return this.recv.readLine();
        }
        catch (IOException ex) 
        {
            Logger.getLogger(SockClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
