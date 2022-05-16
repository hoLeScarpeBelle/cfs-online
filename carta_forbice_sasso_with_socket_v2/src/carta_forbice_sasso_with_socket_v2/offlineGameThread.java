package carta_forbice_sasso_with_socket_v2;

import java.util.logging.Level;
import java.util.logging.Logger;

public class offlineGameThread extends Thread
{
    Game_window parent;
    private int playerResponse = -1;
    private int p1Point,p2Point,maxPoint;
    private GameClass game = new GameClass();
    public offlineGameThread(Game_window parent)
    {
        this.parent = parent;
        p1Point = 0;
        p2Point = 0;
        this.maxPoint = 3;
    }
    
    public void run()
    {
        int botResponse; 
        while(p1Point != maxPoint && p1Point != maxPoint)
        {
            try {
                this.parent.enableAllButton();
                WaitPlayerResponse();
                botResponse = (int) Math.random()*3;
                this.parent.setActionLabel("aspettare la risposta di p2");
                sleep(1000);
                play_the_game(playerResponse, botResponse);
                this.parent.setP2ChoiceLabel(choiceFromIntToString(botResponse));
                this.parent.setPointLabel(p1Point, p2Point);
                for(int i = 3 ; i > -1 ; i--)
                {
                    sleep(1000);
                    this.parent.setActionLabel("la partita riniziera tra "+ i );
                }
                //sleep(3000);
                this.parent.setActionLabel("scegli cosa buttare");
                this.parent.setP1ChoiceLabel("");
                this.parent.setP2ChoiceLabel("");
                this.playerResponse = -1;
            } catch (InterruptedException ex) {
                Logger.getLogger(offlineGameThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(p1Point == 3)
            this.parent.showAllertMessage("p1 vince");
        else
            this.parent.showAllertMessage("p2 vince");
        this.parent.goBack();
        
        //creare un allert che chiede se ricomunciare o andare alla home
    }
    
    public void play_the_game(int x , int y)
    {
        char z = game.do_game(x, y);
        if(z == 'x')
            p1Point ++;
        else if(z == 'y')
            p2Point ++;
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
    
    public void WaitPlayerResponse()
    {
        while(this.playerResponse == -1)
        {
            try {
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(offlineGameThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void setPlayerResponse(int playerResponse) 
    {
        this.playerResponse = playerResponse;
    }
    
}
