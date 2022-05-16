package carta_forbice_sasso_with_socket;

import com.sun.nio.sctp.PeerAddressChangeNotification;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.SpringLayout;

public class Sock_configuration_window extends JPanel
{

    public Sock_configuration_window(Window parent,boolean server) 
    {
        //setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        
        final int dist_top = 30;
        
        JLabel address_label = new JLabel("address:");
        JTextField address_field = new JTextField();
        JLabel port_label = new JLabel("port:");
        JTextField port_field = new JTextField();
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton back_button = new JButton("back");
        JButton next_button = new JButton();
        if(server)
            next_button.setText("host");
        else
            next_button.setText("connect");
        
        back_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                parent.changePages("0");
            }
        });
        next_button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    Game_window game;
                    if (server) 
                    {
                        SockServer ss = new SockServer(Integer.parseInt(port_field.getText()));//levare la selezione del ip nel server ed mettere le modifiche che si possono fare alla partita
                        ss.start();
                        game = new Game_window("127.0.0.1",Integer.parseInt(port_field.getText()),parent);
                    } 
                    else 
                    {
                        game = new Game_window(address_field.getText(), Integer.parseInt(port_field.getText()),parent);
                    }
                    parent.addPages(game, "1");
                    parent.changePages("1");
                }
                catch(Exception ex)
                {
                    System.out.println("error = " + ex.toString());
                }
            }
        });
        buttonPanel.add(back_button);
        buttonPanel.add(next_button);
        
        //layout
        
        if(!server)
        {
            add(address_label);
            add(address_field);
            //address label
            layout.putConstraint(SpringLayout.WEST, address_label, 0, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, address_label, dist_top, SpringLayout.NORTH, this);
            //layout.putConstraint(SpringLayout.EAST, pp, 0, SpringLayout.WEST, ss);
        
            //addressField
            layout.putConstraint(SpringLayout.WEST, address_field, 0, SpringLayout.EAST, address_label);
            layout.putConstraint(SpringLayout.NORTH, address_field, dist_top, SpringLayout.NORTH, this);
            layout.putConstraint(SpringLayout.EAST, address_field, -20, SpringLayout.HORIZONTAL_CENTER,this);
        
            //port label
            //layout.putConstraint(SpringLayout.EAST, zz, 0, SpringLayout.WEST, hh);
            layout.putConstraint(SpringLayout.WEST, port_label, 10, SpringLayout.HORIZONTAL_CENTER, this);
            layout.putConstraint(SpringLayout.NORTH, port_label, dist_top, SpringLayout.NORTH, this);
        
            //port field
            layout.putConstraint(SpringLayout.WEST, port_field, 0, SpringLayout.EAST,port_label);
            layout.putConstraint(SpringLayout.NORTH, port_field, dist_top, SpringLayout.NORTH, this);
            layout.putConstraint(SpringLayout.EAST, port_field, -20, SpringLayout.EAST, this);
        }
        else
        {
            //port label
            //layout.putConstraint(SpringLayout.EAST, zz, 0, SpringLayout.WEST, hh);
            layout.putConstraint(SpringLayout.WEST, port_label, 10, SpringLayout.WEST, this);
            layout.putConstraint(SpringLayout.NORTH, port_label, dist_top, SpringLayout.NORTH, this);
            
            //port field
            layout.putConstraint(SpringLayout.WEST, port_field, 0, SpringLayout.EAST,port_label);
            layout.putConstraint(SpringLayout.NORTH, port_field, dist_top, SpringLayout.NORTH, this);
            layout.putConstraint(SpringLayout.EAST, port_field, -20, SpringLayout.HORIZONTAL_CENTER, this);
        
            
        }
        
        //Button Panel
        layout.putConstraint(SpringLayout.NORTH, buttonPanel, 10, SpringLayout.SOUTH, address_field);
        layout.putConstraint(SpringLayout.WEST, buttonPanel, 0, SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.EAST, buttonPanel, 0, SpringLayout.EAST, this);
        

        add(port_label);
        add(port_field);
        add(buttonPanel);
    }    
    
}
