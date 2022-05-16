package carta_forbice_sasso_with_socket_v2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class LogInPanel extends JPanel
{
    Window parent;
    public LogInPanel(Window parent) 
    {
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        
        //add(Box.createRigidArea(new Dimension(0,10)));
        JPanel mainPanel = new JPanel(); 
        mainPanel.setBackground(Color.WHITE);
        SpringLayout mainLayout = new SpringLayout();
        mainPanel.setLayout(mainLayout);
        
        JLabel topLabel = new JLabel("Log in" , (int) JLabel.CENTER_ALIGNMENT);
        JTextField usernameField = new JTextField();
        addFocusText(usernameField, "username");
        JPasswordField passwordField = new JPasswordField();
        addFocusText(passwordField, "password");
        JButton submit = new JButton("submit");
        submit.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                try {
                    if(parent.logIn(usernameField.getText(), passwordField.getText()))
                    {
                        JOptionPane.showMessageDialog(parent, "log in avvenuto");
                        Service_type_window service = new Service_type_window(parent);
                        parent.setLoggedUser(usernameField.getText());
                        parent.addPages(service,"1");
                        parent.changePages("1");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(parent, "log in non avvenuto");
                    }
                } 
                catch (IOException ex) 
                {
                    JOptionPane.showMessageDialog(parent, "è avvenuto un problema con il server db");
                    Logger.getLogger(LogInPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        JButton sign_in = new JButton("sing in");
        sign_in.setBorderPainted(true);
        sign_in.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                parent.changePages("0.5");
            }
        });
        
        //layout
        add(mainPanel);
        
        /*
        layout.putConstraint(SpringLayout.NORTH, mainPanel, +40, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.EAST, mainPanel, -30, SpringLayout.EAST, this);
        layout.putConstraint(SpringLayout.SOUTH, mainPanel, -40, SpringLayout.SOUTH, this);
        layout.putConstraint(SpringLayout.WEST, mainPanel, +30, SpringLayout.WEST, this);
        */
        layout.putConstraint(SpringLayout.NORTH, mainPanel, -120, SpringLayout.VERTICAL_CENTER, this);
        layout.putConstraint(SpringLayout.EAST, mainPanel, +100, SpringLayout.HORIZONTAL_CENTER, this);
        layout.putConstraint(SpringLayout.SOUTH, mainPanel, +120, SpringLayout.VERTICAL_CENTER, this);
        layout.putConstraint(SpringLayout.WEST, mainPanel, -100, SpringLayout.HORIZONTAL_CENTER, this);
        
        
        //layout mainpanel
        mainPanel.add(topLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordField);
        mainPanel.add(submit);
        mainPanel.add(sign_in);
        
        //topLabel
        mainLayout.putConstraint(SpringLayout.NORTH , topLabel , 30 , SpringLayout.NORTH, mainPanel);
        mainLayout.putConstraint(SpringLayout.EAST, topLabel, -10, SpringLayout.EAST , mainPanel);
        mainLayout.putConstraint(SpringLayout.WEST, topLabel, 10, SpringLayout.WEST , mainPanel);
        mainLayout.putConstraint(SpringLayout.SOUTH, topLabel, 60, SpringLayout.NORTH, mainPanel);
        
        //usernameField
        mainLayout.putConstraint(SpringLayout.NORTH, usernameField, 10, SpringLayout.SOUTH , topLabel);
        mainLayout.putConstraint(SpringLayout.EAST, usernameField, -10, SpringLayout.EAST , mainPanel);
        mainLayout.putConstraint(SpringLayout.WEST, usernameField, 10, SpringLayout.WEST , mainPanel);
        
        //passwrodField
        mainLayout.putConstraint(SpringLayout.NORTH, passwordField , 10, SpringLayout.SOUTH , usernameField);
        mainLayout.putConstraint(SpringLayout.EAST, passwordField , -10, SpringLayout.EAST , mainPanel);
        mainLayout.putConstraint(SpringLayout.WEST, passwordField , 10, SpringLayout.WEST , mainPanel);
        
        //submit
        mainLayout.putConstraint(SpringLayout.NORTH, submit, 10, SpringLayout.SOUTH, passwordField);
        mainLayout.putConstraint(SpringLayout.EAST, submit , -10, SpringLayout.EAST , mainPanel);
        mainLayout.putConstraint(SpringLayout.WEST, submit , 10, SpringLayout.WEST , mainPanel);
        
        //sing in
        mainLayout.putConstraint(SpringLayout.NORTH, sign_in, 5 , SpringLayout.SOUTH, submit);
        mainLayout.putConstraint(SpringLayout.EAST, sign_in , -10 , SpringLayout.EAST, submit);
        
    }
    
    public void addFocusText(JTextField textField , String text)
    {
        textField.setForeground(Color.GRAY);
        textField.setText(text);
        
        textField.addFocusListener(new FocusListener() 
        {
            @Override
            public void focusGained(FocusEvent e) 
            {
                if(textField.getText().equals(text))//se quando vado a scrivere nel textfield ed c'è il testo di default allora imposta il textfield per scriverci
                {
                    textField.setForeground(Color.BLACK);
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                if(textField.getText().equals(""))
                {
                    textField.setForeground(Color.GRAY);
                    textField.setText(text);
                }
            }
        });
    }
}
