package carta_forbice_sasso_with_socket;

import carta_forbice_sasso_with_socket.Service_type_window;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Window extends JFrame
{
    private CardLayout pages;
    private JPanel pages_cont;
    
    public Window()
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        setTitle("carta forbice e sasso");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(300,300));
        setVisible(true);
        Window parent = this;
        
        this.pages_cont = new JPanel();
        this.pages = new CardLayout();
        this.pages_cont.setLayout(pages);
        
        Service_type_window service_page = new Service_type_window(parent);
        //game_window game_page = new game_window(parent);
        this.pages_cont.add(service_page, "0");
        //pages_cont.add(game_page , "1");
        
        add(pages_cont);
        this.pages.show(pages_cont , "0");
    }
    
    public void changePages(String id)
    {
        pages.show(pages_cont, id);
        //pack();
    }
    
    public void addPages(JPanel page, String id)
    {
        pages_cont.add(page,id);
    }
}
