
package carta_forbice_sasso_with_socket_v2;
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

public class session_conf_panel extends JPanel
{

    public session_conf_panel(Window parent,boolean server) 
    {
        //setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        
        final int dist_top = 30;
        
        JLabel sessionName_label = new JLabel("session name:");
        JTextField sessionName_field = new JTextField();
        
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
                    String operation;
                    if (server) 
                    {
                        operation = "create";
                    } 
                    else 
                    {
                        operation = "connect";
                    }
                    game = new Game_window(sessionName_field.getText(),operation,parent);
                    parent.addPages(game, "3");
                    parent.changePages("3");
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
        add(buttonPanel);        
        add(sessionName_label);
        add(sessionName_field);
            
        //address label
        layout.putConstraint(SpringLayout.WEST, sessionName_label, 0, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, sessionName_label, dist_top, SpringLayout.NORTH, this);
        //layout.putConstraint(SpringLayout.EAST, pp, 0, SpringLayout.WEST, ss);
        
        //addressField
        layout.putConstraint(SpringLayout.WEST, sessionName_field, 0, SpringLayout.EAST, sessionName_label);
        layout.putConstraint(SpringLayout.NORTH, sessionName_field, dist_top, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, sessionName_field, -20, SpringLayout.HORIZONTAL_CENTER,this);
        
        //Button Panel
        layout.putConstraint(SpringLayout.NORTH, buttonPanel, 10, SpringLayout.SOUTH, sessionName_field);
        layout.putConstraint(SpringLayout.WEST, buttonPanel, 0, SpringLayout.WEST,this);
        layout.putConstraint(SpringLayout.EAST, buttonPanel, 0, SpringLayout.EAST, this);
        
    }    
    
}