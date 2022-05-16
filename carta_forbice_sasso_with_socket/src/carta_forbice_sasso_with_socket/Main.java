package carta_forbice_sasso_with_socket;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException 
    {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() 
            {
                new Window();
            }
        });
        /*
        Scanner zz = new Scanner(System.in);
        System.out.println("scelta x:");
        String scelta1 = zz.nextLine();
        System.out.println("scelta y");
        String scelta2 = zz.nextLine();
        do_game(scelta1,scelta2);
        */
    }
    
    public static char chooseWinner(int x , int y)
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
    
    public static int chooseTransl(String str)
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
    
    public static void do_game(String x , String y)
    {
        char z = chooseWinner(chooseTransl(x),chooseTransl(y));
        if(z == 'p')
            System.out.println("parita");
        else if(z == 'x')
            System.out.println("vince x");
        else if(z == 'y')
            System.out.println("vince y");
    }
}
