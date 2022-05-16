package carta_forbice_sasso_with_socket;

import com.sun.java.accessibility.util.AWTEventMonitor;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
//https://www.youtube.com/watch?v=BDS8CkEYzeo //una playlist che mi ritrovavo ad ascoltare se me mi scordo di cancelarla perfavore non fateci caso 
public class Game_window extends JPanel 
{
    Window parent;
    private int nRound;
    private int p1Point,p2Point;
    private JLabel p1Choice_label,p2Choice_label,action_label,p1Point_label,p2Point_label;
    private JButton paper_botton,rock_button,scissor_button;
    
    public Game_window(Window parent)
    {
        //setBackground(Color.BLUE);
        this.parent = parent;
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        offlineGameThread gameBot = new offlineGameThread(this);
        
        //selection panel
        action_label = new JLabel("scrgli cosa buttare", (int) JLabel.CENTER_ALIGNMENT);
        p1Choice_label = new JLabel("p1", (int) JLabel.CENTER_ALIGNMENT);
        p2Choice_label = new JLabel("p2", (int) JLabel.CENTER_ALIGNMENT);
        JLabel time_label = new JLabel("00:00", (int) JLabel.CENTER_ALIGNMENT);
        time_thread timeTh = new time_thread(time_label);
        timeTh.start();
        time_label.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        
        add(p1Choice_label);
        add(p2Choice_label);
        add(time_label);
        add(action_label);
        
        //info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.LINE_AXIS));
        infoPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.BLACK));
        infoPanel.setBackground(Color.red);
        this.p1Point_label = new JLabel("p1:00");
        this.p2Point_label = new JLabel("p2:00");
        
        infoPanel.add(this.p1Point_label);
        infoPanel.add(Box.createHorizontalGlue());
        infoPanel.add(new JLabel("round n"));
        infoPanel.add(Box.createHorizontalGlue());
        infoPanel.add(this.p2Point_label);
        add(infoPanel);
        
        //button panel 
        JPanel buttonPanel = new JPanel(new GridLayout(0,3));
        paper_botton = new JButton("carta");
        paper_botton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                p1Choice_label.setText("<html>   p1: <br> carta<html>");
                gameBot.setPlayerResponse(0);
                disableAllButton();
            }
        });
        scissor_button = new JButton("forbice");
        scissor_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                p1Choice_label.setText("<html> p1: <br> forbice<html>");
                gameBot.setPlayerResponse(1);
                disableAllButton();
            }
        });
        rock_button = new JButton("sasso");
        rock_button.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                p1Choice_label.setText("<html>p1: <br> sasso<html>");
                gameBot.setPlayerResponse(2);
                disableAllButton();
            }
        });
        
        buttonPanel.add(paper_botton);
        buttonPanel.add(scissor_button);
        buttonPanel.add(rock_button);
        add(buttonPanel);
        //layout
        
        //action label
        layout.putConstraint(SpringLayout.WEST , action_label ,0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH , action_label, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, action_label, 0,SpringLayout.EAST , this);
        layout.putConstraint(SpringLayout.SOUTH, action_label, +20, SpringLayout.NORTH, this);
        
        //p1 choice label
        layout.putConstraint(SpringLayout.WEST , p1Choice_label ,0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH , p1Choice_label, 0, SpringLayout.SOUTH, action_label);
        layout.putConstraint(SpringLayout.EAST, p1Choice_label, -20,SpringLayout.HORIZONTAL_CENTER , this);
        layout.putConstraint(SpringLayout.SOUTH, p1Choice_label, -20, SpringLayout.VERTICAL_CENTER, this);
        
        //time label
        layout.putConstraint(SpringLayout.WEST, time_label, 0, SpringLayout.EAST, p1Choice_label);
        layout.putConstraint(SpringLayout.NORTH, time_label, 0, SpringLayout.SOUTH, action_label);
        layout.putConstraint(SpringLayout.EAST, time_label, 20, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.SOUTH, time_label, -20, SpringLayout.VERTICAL_CENTER, this);
        
        //p2 choice label
        layout.putConstraint(SpringLayout.WEST, p2Choice_label, 0, SpringLayout.EAST, time_label);
        layout.putConstraint(SpringLayout.NORTH, p2Choice_label, 0, SpringLayout.SOUTH, action_label);
        layout.putConstraint(SpringLayout.EAST, p2Choice_label, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, p2Choice_label, -10, SpringLayout.VERTICAL_CENTER, this);
        
        //info panel
        layout.putConstraint(SpringLayout.WEST, infoPanel, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH,infoPanel,0,SpringLayout.SOUTH,time_label);
        layout.putConstraint(SpringLayout.EAST,infoPanel,0,SpringLayout.EAST,this);
        layout.putConstraint(SpringLayout.SOUTH,infoPanel,10,SpringLayout.VERTICAL_CENTER,this);
        
        //button panel
        layout.putConstraint(SpringLayout.WEST, buttonPanel, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, buttonPanel, 0,SpringLayout.SOUTH, infoPanel);
        layout.putConstraint(SpringLayout.EAST, buttonPanel, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, buttonPanel, 0,SpringLayout.SOUTH, this);
                    
        gameBot.start();
    }
    
    public Game_window(String ip,int port , Window parent)
    {
        //setBackground(Color.GREEN);
        this.parent = parent;
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        SockClient sock = new SockClient(ip, port, this);
                
        //selection panel
        action_label = new JLabel("aspettare i giocatori", (int) JLabel.CENTER_ALIGNMENT);
        p1Choice_label = new JLabel("p1", (int) JLabel.CENTER_ALIGNMENT);
        p2Choice_label = new JLabel("p2", (int) JLabel.CENTER_ALIGNMENT);
        JLabel time_label = new JLabel("00:00", (int) JLabel.CENTER_ALIGNMENT);
        time_thread timeTh = new time_thread(time_label);
        timeTh.start();
        time_label.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        
        add(p1Choice_label);
        add(p2Choice_label);
        add(time_label);
        add(action_label);
        
        //info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.LINE_AXIS));
        infoPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.BLACK));
        infoPanel.setBackground(Color.red);
        this.p1Point_label = new JLabel("p1:00");
        this.p2Point_label = new JLabel("p2:00");
        
        infoPanel.add(this.p1Point_label);
        infoPanel.add(Box.createHorizontalGlue());
        infoPanel.add(new JLabel("round n"));
        infoPanel.add(Box.createHorizontalGlue());
        infoPanel.add(this.p2Point_label);
        add(infoPanel);
        
        //button panel 
        JPanel buttonPanel = new JPanel(new GridLayout(0,3));
        paper_botton = new JButton("carta");
        paper_botton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                p1Choice_label.setText("<html>   p1: <br> carta<html>");
                disableAllButton();
                sock.sendMsg("0");
            }
        });
        scissor_button = new JButton("forbice");
        scissor_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                p1Choice_label.setText("<html> p1: <br> forbice<html>");
                disableAllButton();
                sock.sendMsg("1");
            }
        });
        rock_button = new JButton("sasso");
        rock_button.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                p1Choice_label.setText("<html>p1: <br> sasso<html>");
                disableAllButton();
                sock.sendMsg("0");
            }
        });
        
        buttonPanel.add(paper_botton);
        buttonPanel.add(scissor_button);
        buttonPanel.add(rock_button);
        add(buttonPanel);
        //layout
        
        //action label
        layout.putConstraint(SpringLayout.WEST , action_label ,0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH , action_label, 0, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, action_label, 0,SpringLayout.EAST , this);
        layout.putConstraint(SpringLayout.SOUTH, action_label, +20, SpringLayout.NORTH, this);
        
        //p1 choice label
        layout.putConstraint(SpringLayout.WEST , p1Choice_label ,0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH , p1Choice_label, 0, SpringLayout.SOUTH, action_label);
        layout.putConstraint(SpringLayout.EAST, p1Choice_label, -20,SpringLayout.HORIZONTAL_CENTER , this);
        layout.putConstraint(SpringLayout.SOUTH, p1Choice_label, -20, SpringLayout.VERTICAL_CENTER, this);
        
        //time label
        layout.putConstraint(SpringLayout.WEST, time_label, 0, SpringLayout.EAST, p1Choice_label);
        layout.putConstraint(SpringLayout.NORTH, time_label, 0, SpringLayout.SOUTH, action_label);
        layout.putConstraint(SpringLayout.EAST, time_label, 20, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.SOUTH, time_label, -20, SpringLayout.VERTICAL_CENTER, this);
        
        //p2 choice label
        layout.putConstraint(SpringLayout.WEST, p2Choice_label, 0, SpringLayout.EAST, time_label);
        layout.putConstraint(SpringLayout.NORTH, p2Choice_label, 0, SpringLayout.SOUTH, action_label);
        layout.putConstraint(SpringLayout.EAST, p2Choice_label, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, p2Choice_label, -10, SpringLayout.VERTICAL_CENTER, this);
        
        //info panel
        layout.putConstraint(SpringLayout.WEST, infoPanel, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH,infoPanel,0,SpringLayout.SOUTH,time_label);
        layout.putConstraint(SpringLayout.EAST,infoPanel,0,SpringLayout.EAST,this);
        layout.putConstraint(SpringLayout.SOUTH,infoPanel,10,SpringLayout.VERTICAL_CENTER,this);
        
        //button panel
        layout.putConstraint(SpringLayout.WEST, buttonPanel, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, buttonPanel, 0,SpringLayout.SOUTH, infoPanel);
        layout.putConstraint(SpringLayout.EAST, buttonPanel, 0, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, buttonPanel, 0,SpringLayout.SOUTH, this);
        
        sock.start();
        disableAllButton();
    }
    
    public Game_window(SockServer sock , Window parent)
    {
        setBackground(Color.YELLOW);
        this.parent = parent;
    }
    
    public void setPointLabel(int x , int y)
    {
        this.p1Point = x;
        this.p2Point = y;
        if(x < 10)
            this.p1Point_label.setText("p1:0"+x);
        else
            this.p1Point_label.setText("p1:" + x);
        if(y < 10)
            this.p2Point_label.setText("p2:0"+y);
        else
            this.p2Point_label.setText("p2:" + y);
    }
    public void disableAllButton()
    {
        this.paper_botton.setEnabled(false);
        this.rock_button.setEnabled(false);
        this.scissor_button.setEnabled(false);
    }
    public void enableAllButton()
    {
        this.paper_botton.setEnabled(true);
        this.rock_button.setEnabled(true);
        this.scissor_button.setEnabled(true);
    }
    public void setActionLabel(String x)//sostituire con un vettore di scelte
    {
        this.action_label.setText(x);
    }
    public void setP2ChoiceLabel(String choice)
    {
        this.p2Choice_label.setText("<html> p2: <br>" +choice + "</html>");
    }
    public void setP1ChoiceLabel(String choice)
    {
        this.p1Choice_label.setText("<html> p1: <br>" +choice + "</html>");
    }
}
