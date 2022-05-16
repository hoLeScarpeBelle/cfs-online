package cfs_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread
{
    private String ip;
    private int port;
    private ServerSocket ss;
    private ArrayList<Session> sessions = new ArrayList<Session>();
    
    public Server(String ip , int port)
    {
        this.ip = ip;
        this.port = port;
    }
    
    public void run()
    {
        try {
            this.ss = new ServerSocket(this.port);
            
            Socket client;
            BufferedReader recv;
            String clientResponse , splittedStr[];
            System.out.println("");
            while(true)
            {
                client = ss.accept();
                recv = new BufferedReader(new InputStreamReader(client.getInputStream()));
                
                clientResponse = recv.readLine();// str = "azione";"nomeStanza";"user che chiede il servizio"
                System.out.println("inizio connessione");
                splittedStr = clientResponse.split(";");
                System.out.println(clientResponse);
                if(splittedStr[0].equals("create"))
                {
                    createSession(client, splittedStr[1],splittedStr[2]);
                }
                else if(splittedStr[0].equals("connect"))
                {
                    connectToSession(client, splittedStr[1],splittedStr[2]);
                }
                System.out.println("connesso");
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void getSessionList()
    {
    }
    
    public void createSession(Socket client,String name,String user) throws IOException
    {
        boolean takenName = false;
        PrintWriter send = new PrintWriter(client.getOutputStream());
        for(Session session : this.sessions)
        {
            if(session.name == name)
            {
                takenName = true;
                break;
            }
        }
        
        if(takenName)
        {
            send.println("name_takent");
            send.flush();
        }
        else
        {
            System.out.println("sessione creata");
            Session sess = new Session(name,client,this,user);
            this.sessions.add(sess);
            sess.start();
        }
    }
    
    public void connectToSession(Socket client,String name,String user) throws IOException
    {
        System.out.println("connessione alla sessione");
        boolean found = false;
        boolean fullSession = false;
        PrintWriter send = new PrintWriter(client.getOutputStream());
        for(Session session : this.sessions)
        {
            if(session.name.equals(name))
            {
                if(session.Nplayers  != 2)
                    session.setPlayer2(client,user);
                else
                    fullSession = true;
                found = true;
                break;
            }
        }
        if(found)
        {
            if(fullSession)
            {
                send.println("session_full");
                send.flush();
            }
        }
        else
        {
            send.println("not_found");
            send.flush();
        }
        System.out.println("fine connessione");
    }
    
    public void removeSession(Session sess)
    {
        this.sessions.remove(sess);
    }
}
