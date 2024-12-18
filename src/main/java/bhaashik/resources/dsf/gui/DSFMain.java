/*
 * DSFJFrame.java
 *
 * Created on September 26, 2005, 12:56 AM
 */

package bhaashik.resources.dsf.gui;

/**
 *
 * @author  anil
 */
public class DSFMain extends javax.swing.JFrame {
    
    /** Creates new form DSFJFrame */
    public DSFMain() {
        initComponents();
        setSize(400, 400);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        dsfMainJPanel = new javax.swing.JPanel();
        taskJPanel = new javax.swing.JPanel();
        taskNameJLabel = new javax.swing.JLabel();
        taskLableJLabel = new javax.swing.JLabel();
        batchSizeJPanel = new javax.swing.JPanel();
        batchSizeJLabel = new javax.swing.JLabel();
        batchSizeJComboBox = new javax.swing.JComboBox();
        percentJCheckBox = new javax.swing.JCheckBox();
        dsfEntriesJPanel = new javax.swing.JPanel();
        dsfEntriesJLabel = new javax.swing.JLabel();
        dsfEntriesJTable = new javax.swing.JTable();
        commandsJPanel = new javax.swing.JPanel();
        editJButton = new javax.swing.JButton();
        nextJButton = new javax.swing.JButton();
        resetJButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dsfMainJPanel.setLayout(new javax.swing.BoxLayout(dsfMainJPanel, javax.swing.BoxLayout.Y_AXIS));

        taskJPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        taskNameJLabel.setLabelFor(taskNameJLabel);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bhaashik"); // NOI18N
        taskNameJLabel.setText(bundle.getString("Task:_")); // NOI18N
        taskJPanel.add(taskNameJLabel);

        taskLableJLabel.setText(bundle.getString("task_name")); // NOI18N
        taskJPanel.add(taskLableJLabel);

        dsfMainJPanel.add(taskJPanel);

        batchSizeJPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        batchSizeJLabel.setLabelFor(batchSizeJComboBox);
        batchSizeJLabel.setText(bundle.getString("Batch_size:")); // NOI18N
        batchSizeJPanel.add(batchSizeJLabel);
        batchSizeJPanel.add(batchSizeJComboBox);

        percentJCheckBox.setText(bundle.getString("Percent")); // NOI18N
        batchSizeJPanel.add(percentJCheckBox);

        dsfMainJPanel.add(batchSizeJPanel);

        dsfEntriesJPanel.setLayout(new java.awt.BorderLayout());

        dsfEntriesJLabel.setLabelFor(dsfEntriesJTable);
        dsfEntriesJLabel.setText(bundle.getString("DSF_Entries")); // NOI18N
        dsfEntriesJPanel.add(dsfEntriesJLabel, java.awt.BorderLayout.NORTH);

        dsfEntriesJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        dsfEntriesJPanel.add(dsfEntriesJTable, java.awt.BorderLayout.CENTER);

        dsfMainJPanel.add(dsfEntriesJPanel);

        editJButton.setText(bundle.getString("Edit")); // NOI18N
        editJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editJButtonActionPerformed(evt);
            }
        });
        commandsJPanel.add(editJButton);

        nextJButton.setText(bundle.getString("Next")); // NOI18N
        commandsJPanel.add(nextJButton);

        resetJButton.setText(bundle.getString("Reset")); // NOI18N
        commandsJPanel.add(resetJButton);

        dsfMainJPanel.add(commandsJPanel);

        dsfMainJPanel.setBounds(0, 0, 400, 280);
        desktopPane.add(dsfMainJPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(desktopPane, java.awt.BorderLayout.CENTER);

        fileMenu.setText(bundle.getString("File")); // NOI18N
        fileMenu.add(openMenuItem);

        saveMenuItem.setText(bundle.getString("Save")); // NOI18N
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setText(bundle.getString("Save_As_...")); // NOI18N
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setText(bundle.getString("Exit")); // NOI18N
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText(bundle.getString("Edit")); // NOI18N

        cutMenuItem.setText(bundle.getString("Cut")); // NOI18N
        editMenu.add(cutMenuItem);

        copyMenuItem.setText(bundle.getString("Copy")); // NOI18N
        editMenu.add(copyMenuItem);

        pasteMenuItem.setText(bundle.getString("Paste")); // NOI18N
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setText(bundle.getString("Delete")); // NOI18N
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

        helpMenu.setText(bundle.getString("Help")); // NOI18N

        contentMenuItem.setText(bundle.getString("Contents")); // NOI18N
        helpMenu.add(contentMenuItem);

        aboutMenuItem.setText(bundle.getString("About")); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void editJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editJButtonActionPerformed
        // TODO add your handling code here:
//        DSFEditJDialog editDialog = new DSFEditJDialog(this, "DSF Edit", true);
//        editDialog.setVisible(true);
    }//GEN-LAST:event_editJButtonActionPerformed
    
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DSFMain().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JComboBox batchSizeJComboBox;
    private javax.swing.JLabel batchSizeJLabel;
    private javax.swing.JPanel batchSizeJPanel;
    private javax.swing.JPanel commandsJPanel;
    private javax.swing.JMenuItem contentMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JLabel dsfEntriesJLabel;
    private javax.swing.JPanel dsfEntriesJPanel;
    private javax.swing.JTable dsfEntriesJTable;
    private javax.swing.JPanel dsfMainJPanel;
    private javax.swing.JButton editJButton;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JButton nextJButton;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JCheckBox percentJCheckBox;
    private javax.swing.JButton resetJButton;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JPanel taskJPanel;
    private javax.swing.JLabel taskLableJLabel;
    private javax.swing.JLabel taskNameJLabel;
    // End of variables declaration//GEN-END:variables
    
}
