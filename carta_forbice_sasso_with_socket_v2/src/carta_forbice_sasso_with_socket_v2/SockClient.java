package carta_forbice_sasso_with_socket_v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SockClient extends Thread
{
    private static String serverIp = "127.0.0.1";
    private static int serverPort = 1234;
    private String sessionName;
    private String operation;
    private Game_window parent;
    private PrintWriter send;
    private BufferedReader recv;
    //private String playerResponse; per aspetta c'è gia il readline
            
    public SockClient(String sessionName , String operation,Game_window parent) 
    {
        this.operation = operation;
        this.sessionName = sessionName;
        this.parent = parent;
    }
    
    public void run()
    {
        try 
        {
            Socket sock = new Socket(this.serverIp, this.serverPort);
            this.send = new PrintWriter(sock.getOutputStream());
            this.recv = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            boolean run = true;
            
            //crezione o connessione della sessione
            sendMsg(this.operation+";"+this.sessionName + ";" + parent.getUser());
            String str = recvMsg();
            if(str.equals("not_found"))
            {
                System.out.println("sessione non trovata");
                parent.showAllertMessage("sessione non trovata");
                parent.goBack();
            }
            else if(str.equals("name_taken"))
            {
                System.out.println("name gia usato");
                parent.showAllertMessage("name gia usato");
                parent.goBack();
            }
            else if(str.equals("session_full"))
            {
                System.out.println("sessione piena");
                parent.showAllertMessage("sessione piena");
                parent.goBack();
            }
            else if(str.equals("ready"))
            {   
                parent.enableAllButton();
                parent.setActionLabel("scegli cosa buttare");
                //gioco
                String result,splittedResult[];
                while(true)
                {
                    //risultati giocata
                    result = recvMsg();
                    splittedResult = result.split(";");//0 = punti p1 ; 1 = punti p2  ; 2 = scelta di p2 ; 3 = round;
                    
                    //effettuo i cambiamenti al jpanel
                    parent.setPointLabel(Integer.parseInt(splittedResult[0]),Integer.parseInt(splittedResult[1]));
                    parent.setP2ChoiceLabel(splittedResult[2]);
                    parent.setActionLabel("il gioco ripartira a breve");//se ho tempo mettere che cambia ad ogni secondo
                    parent.setRoundLabel(Integer.parseInt(splittedResult[3]));
                    
                    //aspetto per sapere se il gioco è finito
                    result = recvMsg();
                    splittedResult = result.split(";");// 0 = end or continue ; 1 = se vinto o no
                    if(splittedResult[0].equals("end"))
                    {
                        if(splittedResult[1].equals("win"))
                            parent.showAllertMessage("you win");
                        else
                            parent.showAllertMessage("you lose");
                        parent.goBack();
                        break;
                    }
                    else
                    {
                        parent.enableAllButton();
                        parent.setActionLabel("scegli cosa buttare");
                        parent.setP1ChoiceLabel("");
                        parent.setP2ChoiceLabel("");
                    }
                }
            }
            
            try 
            {
                this.recv.close();
                this.send.close();
                sock.close();
            } 
            catch (IOException ex)
            {
                //Logger.getLogger(SockClient.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("errore in chiusura");
            }
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(SockClient.class.getName()).log(Level.SEVERE, null, ex);
            parent.showAllertMessage("errore nella apertura del socket");
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
