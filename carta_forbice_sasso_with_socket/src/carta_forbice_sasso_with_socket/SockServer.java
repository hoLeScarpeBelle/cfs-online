package carta_forbice_sasso_with_socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SockServer extends Thread
{
    private ServerSocket ss;
    private ClientHandler clients[];
    private int port;
    private int maxPoint,p1Points,p2Points;
    private GameClass game = new GameClass();
    
    public SockServer(int port) throws IOException
    {
        this.port = port;
        this.maxPoint = 3;
        this.p1Points = 0;
        this.p2Points = 0;
    }
    
    public void run()
    {
        try 
        {
            this.p1Points = 0;
            this.p2Points = 0;
            this.ss = new ServerSocket(port);
            clients = new ClientHandler[2];
            System.out.println("server opened");
            Socket client;
            for(int i = 0 ; i < 2 ; i++)
            {
                client = ss.accept();
                clients[i] = new ClientHandler(client);
                System.out.println("connesso " + i);
            }
            SendToAll("ready");
            System.out.println("game started-------");
            String[] ans;
            while(this.p1Points != maxPoint && this.p2Points != maxPoint)
            {
                System.out.println("ascolto delle risposte");
                ans = recvFromAll();
                playTheGame(Integer.parseInt(ans[0]),Integer.parseInt(ans[1]));
                sendResult(ans);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(SockServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void playTheGame(int x , int y)
    {
        char winner;
        winner = game.do_game(x, y);
        if(winner == 'x')
        {
            this.p1Points ++;
        }
        else if(winner == 'y')
        {
            this.p2Points ++;
        }
    }
    
    public void sendResult(String[] ans)
    {
        String result = "";
        this.clients[0].SendMessage(p1Points + ";" + p2Points + ";"+ game.choiceFromIntToString(Integer.parseInt(ans[1])) );
        this.clients[1].SendMessage(p1Points + ";" + p2Points + ";"+ game.choiceFromIntToString(Integer.parseInt(ans[0])));
    }
    
    public void close()
    {        
        try 
        {
            this.ss.close();
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
}

class ClientHandler
{
    Socket client;
    PrintWriter send;
    BufferedReader recv;
    public ClientHandler(Socket client)
    {
        try 
        {
            this.client = client;
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
