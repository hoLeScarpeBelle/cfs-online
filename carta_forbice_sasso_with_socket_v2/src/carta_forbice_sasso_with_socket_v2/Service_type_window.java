package carta_forbice_sasso_with_socket_v2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Service_type_window extends JPanel
{
    private Window parent;
    
    public Service_type_window(Window parent) 
    {
        this.parent = parent;
        setBackground(Color.red);
        setLayout(new GridLayout(3,0));
        JButton vs_bot = new JButton("vs_bot");
        JButton client = new JButton("connect to a session");
        JButton server = new JButton("host session");
        add(vs_bot);
        add(client);
        add(server);
        
        vs_bot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Game_window game_page = new Game_window(parent);
                parent.addPages(game_page, "3");
                parent.changePages("3");
            }
        });
        
        client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                session_conf_panel sess_pages = new session_conf_panel(parent,false);
                parent.addPages(sess_pages,"2");
                parent.changePages("2");
            }
        });
        
        server.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                
                session_conf_panel sess_pages = new session_conf_panel(parent,true);
                parent.addPages(sess_pages,"2");
                parent.changePages("2");
            }
        });
    }
    
}
