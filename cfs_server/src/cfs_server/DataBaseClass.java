package cfs_server;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DataBaseClass 
{

    private Connection dbConnection;
    private Statement db;
    private static String pathConnector = "com.mysql.cj.jdbc.Driver";
            
    public DataBaseClass(String serverName,int portNumber,String dbName,String dbUser,String password) 
    {
        try 
        {
            Class.forName(pathConnector);
        } 
        catch (ClassNotFoundException ex) 
        {
            System.out.println("connector not loaded");
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try 
        {
            this.dbConnection = DriverManager.getConnection("jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName,dbUser,password);
            this.db = this.dbConnection.createStatement();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet doQuery(String queryString)
    {
        ResultSet data = null;
        try 
        {
            data = this.db.executeQuery(queryString);
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
        
    }
    
    public String[][] fromResultSetToArray(ResultSet data)
    {
        int Nrow = 0,Ncolumn = 0;
        String[][] result = null;
        try {
            ResultSetMetaData info = data.getMetaData();
            Ncolumn = info.getColumnCount();
            if(data.last())
            {
                Nrow = data.getRow();
                data.beforeFirst();
                System.out.println("row = " + Nrow);
            }
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public void InserTo(String query)
    {
        try 
        {
            this.db.executeUpdate(query);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean checkResult(String query)
    {
        ResultSet data = doQuery(query);
        try 
        {
            if(!data.isBeforeFirst())
                return false;
            else 
                return true;
        } 
        catch (SQLException ex) 
        {
            System.out.println("controllo fallito");
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public void printAllTable(String tableName)
    {
        try {
            ResultSet data = doQuery("SELECT * FROM "+ tableName);
            while(data.next())
            {
                System.out.println("" + data.toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean addNewUser(String username , String password)
    {
        
        if(checkResult("SELECT * FROM users WHERE username = '"+ username + "' "))
        {    System.out.println("nome gia preso");
            return false;
        }
        else
        {
            try 
            {
                InserTo("INSERT INTO users(username,password) VALUES( '"+ username +"' , '"+ password +"');" );
                return true;
            } catch (Exception e) 
            {
                return false;
            }
        }        
    }
    
    public boolean logIn(String username , String password)
    {
        return checkResult("SELECT * FROM users WHERE username = '"+username+"' AND password = '" + password + "' AND logged = 0");
    }
    
    public void registrGameStat(String vincitore,String perdente,int nRound,String time)
    {
        /*
        Date date = java.util.Calendar.getInstance().getTime();
        System.out.println("data = " + date.toString());
        String[] splittedDate = date.toString().split(" ");
        String stringData = splittedDate[3].replace(':', '-');
        */
        // stats_partita(id_partita,data,vincitore,perdente,nround,tempo);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");// "dd/MM/yyyy HH:mm:ss"
        Date data = new Date();
        String stringData = formatter.format(data);
        //String[] splittedData = stringData.split("/");
        //stringData = splittedData[2] + "-" + splittedData[1] + "-" + splittedData[0];
        stringData = stringData.replace("/", "-");
        System.out.println(stringData);
        System.out.println("INSERT INTO stats_partita(data,vincitore,perdente,nRound,tempo) VALUES('"+stringData+"','"+ vincitore +"','" +perdente +"',"+ nRound +",'"+time+"')");
        InserTo("INSERT INTO stats_partita(data,vincitore,perdente,nRound,tempo) VALUES('"+stringData+"','"+ vincitore +"','" +perdente +"',"+ nRound +",'"+time+"')");
    }
    
    public void updateUserStat()
    {
        
    }
    
    public void updateLogged(String user,boolean loggedState) throws SQLException
    {
        if(loggedState)
            this.db.executeUpdate("UPDATE users SET logged = 1 WHERE username = '" + user + "'");
        else
            this.db.executeUpdate("UPDATE users SET logged = 0 WHERE username = '" + user + "'");
    }
}
