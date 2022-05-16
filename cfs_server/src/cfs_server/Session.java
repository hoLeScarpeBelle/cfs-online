package cfs_server;

import com.mysql.cj.x.protobuf.MysqlxConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Session extends Thread
{
    public String name;
    public int Nplayers;
    private ClientHandler[] clients;
    private int p1_points,p2_points;
    private GameClass game = new GameClass();
    private Server parent;
    private int nRound;
    private long startTime,endTime;
    private DataBaseClass db;

    public Session(String name ,Socket client1,Server parent,String user)
    {
        this.name = name;
        this.Nplayers = 1;
        this.clients = new ClientHandler[2];
        ClientHandler player1 = new ClientHandler(client1,user);
        this.clients[0] = player1;
        this.parent = parent;
        db = new DataBaseClass("localhost", 3306, "db_carta_forbice_sasso", "root", "");
    }
    
    public void run()
    {
        this.nRound = 0;
        System.out.println("inizio della sessione");
        waitSecondPlayer();
        this.startTime = System.currentTimeMillis();
        this.p1_points = 0;
        SendToAll("ready");
        System.out.println("game Ready---------------------");
        while(true)
        {
            try
            {
                nRound ++;
                System.out.println("invia opzione");
                String[] ans = recvFromAll();
                System.out.println("p1:"+ ans[0] +"vs"+ "p2:" + ans[1]);
                playTheGame(Integer.parseInt(ans[0]),Integer.parseInt(ans[1]));
                sendResult(ans);
                System.out.println("p1:" + this.p1_points +"||"+ "p2:" + this.p2_points);
                sleep(3000);
                if(this.p1_points == 3 || this.p2_points == 3)
                    break;
                else
                    SendToAll("continue");
            }
            catch(Exception e)
            {
                System.out.println("error = " + e.toString());
                break;
            }
        }
        this.endTime = System.currentTimeMillis();
        endGame();
        this.parent.removeSession(this);
    }
    
    public void endGame()
    {
        String vincitore,perdente;
        if(this.p1_points == 3 )
        {
            this.clients[0].SendMessage("end;win");
            vincitore = clients[0].getUser();
            this.clients[1].SendMessage("end;lose");
            perdente = this.clients[1].getUser();
        }
        else
        {
            this.clients[1].SendMessage("end;win");
            vincitore = this.clients[1].getUser();
            this.clients[0].SendMessage("end;lose");
            perdente = this.clients[0].getUser();
        }
        long gameTime = this.endTime - this.startTime;
        long second,minutes,hours;
        second = gameTime/1000;
        minutes = second/60;
        hours = minutes/60;
        second = second%60;
        minutes = minutes%60;
        String timeString = hours+":"+ minutes +":"+second;
        System.out.println("tempo = " + timeString);
        db.registrGameStat(vincitore,perdente,this.nRound,timeString);// time = gameTime;user vincitore;user perdente;nRound;tempo
        close();
    }
    
    public void playTheGame(int x , int y)
    {
        char result = game.do_game(x, y);
        if(result == 'x')
        {
            this.p1_points += 1;
        }
        else if(result == 'y')
        {
            this.p2_points += 1;
        }
    }
    
    public void setPlayer2(Socket Client2,String user)
    {
        ClientHandler player2 = new ClientHandler(Client2,user);
        this.clients[1] = player2;
    }
    
    public void waitSecondPlayer()
    {
        while(this.clients[1] == null)
        {
            try {
                sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("finito");
    }
    
    public String[] recvFromAll()
    {
        String[] ans = null;
        try 
        {
            ans = new String[this.clients.length]; 
            int i = 0;
            for(ClientHandler client : clients)
            {
                ans[i] = client.ReadMessage();
                i++; 
            }
        } 
        catch(Exception e) 
        {
            System.out.println("error = " + e.toString());
        }
        return ans;
    }
    
    public void SendToAll(String msg)
    {
        try 
        {
            for(ClientHandler client : this.clients)
            {
                client.SendMessage(msg);
            }
        } 
        catch (Exception e) 
        {
            System.out.println("error = " + e.toString());
        }
    }
    
    public void close()
    {        
        try 
        {
            for(ClientHandler client : this.clients)
            {
                client.close();
            }
        } 
        catch(Exception e) 
        {
            System.out.println("error = " + e.toString());
        }
    }
    
    public void sendResult(String[] ans)
    {
        String result = "";
        this.clients[0].SendMessage(this.p1_points + ";" + this.p2_points + ";"+ game.choiceFromIntToString(Integer.parseInt(ans[1])) + ";" + this.nRound);
        this.clients[1].SendMessage(this.p1_points + ";" + this.p2_points + ";"+ game.choiceFromIntToString(Integer.parseInt(ans[0])) + ";" + this.nRound);
    }
}

class ClientHandler
{
    private Socket client;
    private PrintWriter send;
    private BufferedReader recv;
    public String user;
    
    public ClientHandler(Socket client,String user)
    {
        try 
        {
            this.client = client;
            this.user = user;
            this.send = new PrintWriter(this.client.getOutputStream());
            this.recv = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } 
        catch (Exception e) 
        {
            System.out.println("error = " + e.toString());
        }
    }
    
    public void SendMessage(String msg)
    {
        try 
        {
            this.send.println(msg);
            this.send.flush();
        } 
        catch (Exception e) 
        {
            System.out.println("error = " + e.toString());
            close();
        }
    }
    public String ReadMessage()
    {
        try 
        {
            String msg = this.recv.readLine();
            return msg;
        } 
        catch (IOException ex) 
        {
            System.out.println("error = " + ex.toString());
            close();
        }
        return null;
    }

    public String getUser() 
    {
        return user;
    }
    
    
    public void close()
    {
        try 
        {
            this.client.close();
            this.recv.close();
            this.send.close();
        } 
        catch(Exception e) 
        {
            System.out.println("error = " + e.toString());
        }
    }
}