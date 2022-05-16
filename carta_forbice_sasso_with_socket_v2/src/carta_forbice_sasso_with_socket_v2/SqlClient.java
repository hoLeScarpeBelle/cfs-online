package carta_forbice_sasso_with_socket_v2;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.event.PrintEvent;

public class SqlClient 
{
    public boolean eseguiLogIn(String username , String password) throws IOException
    {
        String ip = "127.0.0.1";
        int port = 1235;
        Socket sock = new Socket(ip,port);
        PrintWriter send = new PrintWriter(sock.getOutputStream());
        BufferedReader recv = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        send.println(username+";"+password+";"+ "logIn");
        send.flush();
        String str = recv.readLine();
        System.out.println("message = " + str);
        if(str.equals("true"))
            return true;
        else
            return false;
    }
    
    public boolean eseguiSignIn(String username,String password) throws IOException
    {
        String ip = "127.0.0.1";
        int port = 1235;
        Socket sock = new Socket(ip,port);
        PrintWriter send = new PrintWriter(sock.getOutputStream());
        BufferedReader recv = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        send.println(username+";"+password+";"+ "signIn");
        send.flush();
        String str = recv.readLine();
        System.out.println("message = " + str);
        if(str.equals("true"))
            return true;
        else
            return false;
    }
    
    public void eseguiLogOut(String username) throws IOException
    {
        String ip = "127.0.0.1";
        int port = 1235;
        Socket sock = new Socket(ip,port);
        PrintWriter send = new PrintWriter(sock.getOutputStream());
        send.println(username+"; ;"+ "logOut");
        send.flush();
    }
}
