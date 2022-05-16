package carta_forbice_sasso_with_socket_v2;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

public class time_thread extends Thread
{
    private JLabel label;
    private long startTime;
    private boolean run;
    //2 metodi
    //1 prendo quando comincio e continuo a scrivere la differenza
    //2 aggiungo un secondo e continuo;//spreciso perche con l'esecuzione della trasformazione si perde tempo che crea un delay
    public time_thread(JLabel label)
    {
        this.label = label;
        this.startTime = System.currentTimeMillis();
    }
    
    public void run()
    {
        long second,minutes,hours;
        long relatedSecond,relatedMinutes;
        second = 0;
        minutes = 0;
        hours = 0;
        String timeString = null;
        long currentTime; 
        run = true;
        while (run) 
        {       
            try {
                //metod 1
                //sleep(1000);
                //second ++;
                
                //metod 2
                currentTime = System.currentTimeMillis() - startTime;
                timeString = "";
                second = currentTime/1000;
                relatedSecond = second%60;
                
                minutes = second/60;
                relatedMinutes = minutes%60;
                
                /*hours = (minutes/60)%60;
                if(hours < 10)
                    timeString += "0" + hours + ":";
                else
                    timeString += "" + hours + ":";
                */
                if(relatedMinutes < 10)
                    timeString += "0" + relatedMinutes;
                else
                    timeString += "" + relatedMinutes;
                if(relatedSecond < 10)
                    timeString += ":0"+ relatedSecond;
                else
                    timeString += ":" + relatedSecond;
                
                this.label.setText(timeString);
            } 
            catch (Exception ex) 
            {
                System.out.println("error = " + ex.toString());
                run = false;
            }
        }
    }
    public void kill() throws InterruptedException
    {
        this.run = false;
        join();
    }
}
