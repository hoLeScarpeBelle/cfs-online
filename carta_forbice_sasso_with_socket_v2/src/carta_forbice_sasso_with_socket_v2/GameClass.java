
package carta_forbice_sasso_with_socket_v2;

public class GameClass 
{

    public GameClass() 
    {
    }
        public char chooseWinner(int x , int y)
    {
        
        if(x == y)
        {
            return 'p';
        }
        else if((x + 1)%3 == y)
        {
            return 'y';
        }
        else if((x + 2)%3 == y)
        {
            return 'x';
        }
        
        return 'n';
    }
    
    public int SelectionTransl(String str)
    {
        if(str.equals("carta"))
            return 0;
        else if(str.equals("forbice"))
            return 1;
        else if(str.equals("sasso"))
            return 2;
        else
            return -1;
    }
    
    public char do_game(String x , String y)//da ottimizzare
    {
        char z = chooseWinner(SelectionTransl(x),SelectionTransl(y));
        if(z == 'p')
        {
            System.out.println("parita");
        }
        else if(z == 'x')
        {
            System.out.println("vince x");
        }
        else if(z == 'y')
        {
            System.out.println("vince y");
        }
        return z;
    }
    
    public char do_game(int x , int y)//senza la traduzione
    {
        char z = chooseWinner(x,y);
        if(z == 'p')
        {
            System.out.println("parita");
        }
        else if(z == 'x')
        {
            System.out.println("vince x");
        }
        else if(z == 'y')
        {
            System.out.println("vince y");
        }
        return z;
    }
    
    public String choiceFromIntToString(int x)
    {
        if(x == 0)
            return "carta";
        else if(x == 1)
            return "forbice";
        else if(x == 2)
            return "sasso";
        else
            return null;
    }
}
