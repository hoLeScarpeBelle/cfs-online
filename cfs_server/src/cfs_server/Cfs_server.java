package cfs_server;

public class Cfs_server 
{

    public static void main( String[]args )
    {
        Server s = new Server("127.0.0.1" , 1234);
        SqlServer ss = new SqlServer();
        s.start();
        ss.start();
    }
}
