package cfs_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlServer extends Thread
{
    public SqlServer()
    {
    }        

    public void run() {
        try {
            String ip = "127.0.0.1";
            int port =1235;
            
            ServerSocket ss= new ServerSocket(port);
            DataBaseClass db = new DataBaseClass("127.0.0.1", 3306, "db_carta_forbice_sasso","root","");
            
            Socket client;
            while (true)
            {
                client = ss.accept();
                ServerThread st = new ServerThread(client, db);
                st.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(SqlServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

class ServerThread extends Thread
{
    Socket client;
    PrintWriter send; 
    BufferedReader recv;
    DataBaseClass db_class;
    
    public ServerThread(Socket client,DataBaseClass db_class) 
    {
        try {
            this.client = client;
            this.db_class = db_class;
            send = new PrintWriter(client.getOutputStream());
            recv = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run()
    {
        try {
            String userData = recv.readLine();// username ; password ; operazione
            String[] splittedUserData = userData.split(";");
            if (splittedUserData[2].equals("logIn")) 
            {
                if (this.db_class.logIn(splittedUserData[0], splittedUserData[1])) 
                {
                    this.db_class.updateLogged(splittedUserData[0], true);
                    send.println("true");
                }
                else 
                {
                    send.println("false");
                }
                send.flush();
            }
            else if(splittedUserData[2].equals("SignIn"))
            {
                if(this.db_class.addNewUser(splittedUserData[0], splittedUserData[1]))
                {
                    send.println("true");
                }
                else
                {
                    send.println("false");
                }
                send.flush();
            }
            else if(splittedUserData[2].equals("logOut"))
            {
                this.db_class.updateLogged(splittedUserData[0], false);
            }
            this.client.close();
            this.recv.close();
            this.send.close();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}