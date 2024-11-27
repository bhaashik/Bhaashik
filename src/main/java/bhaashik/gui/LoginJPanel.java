/*
 * LoginJPanel.java
 *
 * Created on October 3, 2005, 10:48 PM
 */

package bhaashik.gui;

import java.rmi.RemoteException;
import javax.swing.*;

import bhaashik.GlobalProperties;
import bhaashik.servers.UserManagerRI;

/**
 *
 * @author  anil
 */
public class LoginJPanel extends javax.swing.JPanel {

    private boolean loggedIn;
    private String userName;
    
    private JFrame owner;
    private JDialog dialog;
    
    private UserManagerRI userManagerRI;
  
    /** Creates new form LoginJPanel */
    public LoginJPanel(UserManagerRI umri, String un) {
        
        initComponents();

        userManagerRI = umri;
        userName = un;

        if(un != null && un.equals("") == false)
            nameJTextField.setText(un);
        
//        Enumeration enm = UserType.elements();
//        while(enm.hasMoreElements())
//        {
//            BhaashikType u = (BhaashikType) enm.nextElement();
//            typeJComboBox.addItem(u);
//        }
//        
//        if(ut != null && ut.toString().equals("") == false)
//            typeJComboBox.setSelectedItem(ut);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelsJPanel = new javax.swing.JPanel();
        nameJLabel = new javax.swing.JLabel();
        passwordJLabel = new javax.swing.JLabel();
        editJPanel = new javax.swing.JPanel();
        nameJTextField = new javax.swing.JTextField();
        passwordJPasswordField = new javax.swing.JPasswordField();
        commandsJPanel = new javax.swing.JPanel();
        loginJButton = new javax.swing.JButton();
        cancelJButton = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setLayout(new java.awt.BorderLayout());

        labelsJPanel.setLayout(new java.awt.GridLayout(2, 1));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bhaashik"); // NOI18N
        nameJLabel.setText(bundle.getString("User_name:_")); // NOI18N
        labelsJPanel.add(nameJLabel);

        passwordJLabel.setText(bundle.getString("Password:_")); // NOI18N
        labelsJPanel.add(passwordJLabel);

        add(labelsJPanel, java.awt.BorderLayout.WEST);

        editJPanel.setLayout(new java.awt.GridLayout(2, 1));
        editJPanel.add(nameJTextField);
        editJPanel.add(passwordJPasswordField);

        add(editJPanel, java.awt.BorderLayout.CENTER);

        commandsJPanel.setLayout(new java.awt.GridLayout(1, 1));

        loginJButton.setText(bundle.getString("Login")); // NOI18N
        loginJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginJButtonActionPerformed(evt);
            }
        });
        commandsJPanel.add(loginJButton);

        cancelJButton.setText(bundle.getString("Cancel")); // NOI18N
        cancelJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelJButtonActionPerformed(evt);
            }
        });
        commandsJPanel.add(cancelJButton);

        add(commandsJPanel, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void cancelJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelJButtonActionPerformed
// TODO add your handling code here:
        dialog.setVisible(false);
    }//GEN-LAST:event_cancelJButtonActionPerformed

    private void loginJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginJButtonActionPerformed
// TODO add your handling code here:
        userName = nameJTextField.getText();
        passwordJPasswordField.getPassword();

        String pw = new String(passwordJPasswordField.getPassword());
        String result = "";
        
        try {
	    System.out.println(userName);
            result = userManagerRI.loginUser(userName, pw);
	    System.out.println(GlobalProperties.getIntlString("Result:") + result);
            
            if(result.equals(userName))
            {
                loggedIn = true;
                dialog.setVisible(false);
            }
            else
                JOptionPane.showMessageDialog(this, result, GlobalProperties.getIntlString("Error"), JOptionPane.ERROR_MESSAGE);
                
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_loginJButtonActionPerformed
     
    public JFrame getOwner()
    {
        return owner;
    }
    
    public void setOwner(JFrame jframe)
    {
        owner = (JFrame) jframe;
    }
     
    public void setDialog(JDialog d)
    {
        dialog = d;
    }
    
    public boolean isLoggedIn()
    {
        return loggedIn;
    }
     
    public String getUserName()
    {
        return userName;
    }
     
    public void setUserName(String un)
    {
        userName = un;
    }
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton cancelJButton;
    public javax.swing.JPanel commandsJPanel;
    public javax.swing.JPanel editJPanel;
    public javax.swing.JPanel labelsJPanel;
    public javax.swing.JButton loginJButton;
    public javax.swing.JLabel nameJLabel;
    public javax.swing.JTextField nameJTextField;
    public javax.swing.JLabel passwordJLabel;
    public javax.swing.JPasswordField passwordJPasswordField;
    // End of variables declaration//GEN-END:variables
    
}
