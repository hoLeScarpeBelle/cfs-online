package carta_forbice_sasso_with_socket;

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
        JButton client = new JButton("connect to a server");
        JButton server = new JButton("host server");
        add(vs_bot);
        add(client);
        add(server);
        
        vs_bot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Game_window game_page = new Game_window(parent);
                parent.addPages(game_page, "1");
                parent.changePages("1");
            }
        });
        
        client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Sock_configuration_window conf_pages = new Sock_configuration_window(parent, false);
                parent.addPages(conf_pages, "1");
                parent.changePages("1");
            }
        });
        
        server.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Sock_configuration_window conf_pages = new Sock_configuration_window(parent, true);
                parent.addPages(conf_pages, "1");
                parent.changePages("1");
            }
        });
    }
    
}
