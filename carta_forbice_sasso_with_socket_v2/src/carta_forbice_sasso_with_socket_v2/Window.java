package carta_forbice_sasso_with_socket_v2;

import com.mysql.cj.xdevapi.DbDoc;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Window extends JFrame
{
    private CardLayout pages;
    private JPanel pages_cont;
    private String loggedUser;
    private SqlClient Sc;
    
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
        ImageIcon img = new ImageIcon(getClass().getResource("icon.png"));//"C:\\Users\\user\\Desktop\\carta forbice e sasso\\iconV1.png");
        setIconImage(img.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(300,300));
        setVisible(true);
        Window parent = this;
        Sc = new SqlClient();
        
        this.pages_cont = new JPanel();
        this.pages = new CardLayout();
        this.pages_cont.setLayout(pages);
        
        LogInPanel logIn = new LogInPanel(parent);
        SignInPanel signIn = new SignInPanel(parent);
        this.addPages(logIn, "0");
        this.addPages(signIn, "0.5");
        
        add(pages_cont);
        this.pages.show(pages_cont , "0");
        
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent e) 
            {
                System.out.println("chiudere");
                try 
                {
                    System.out.println("user = " + loggedUser);
                    Sc.eseguiLogOut(loggedUser);
                    //db_class.updateLogged(getLoggedUser(),false);
                }
                catch (IOException ex) {
                    Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        });
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

    public void setLoggedUser(String loggedUser) {
        this.loggedUser = loggedUser;
    }

    public String getLoggedUser() {
        return loggedUser;
    }
    
    public boolean logIn(String username , String password) throws IOException
    {
        return Sc.eseguiLogIn(username, password);
    }
    
    public boolean SignIn(String username , String password) throws IOException
    {
        return Sc.eseguiSignIn(username, password);
    }
    
}
