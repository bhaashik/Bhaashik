/*
 * FileMatcherJPanel.java
 *
 * Created on March 21, 2006, 10:33 PM
 */

package bhaashik.util.gui;

import java.awt.*;
import javax.swing.*;

import bhaashik.GlobalProperties;
import bhaashik.common.types.ClientType;
import bhaashik.gui.clients.BhaashikClient;
import bhaashik.gui.common.JPanelDialog;
import bhaashik.util.UtilityFunctions;

/**
 *
 * @author  anil
 */
public class FileMatcherJPanel extends javax.swing.JPanel implements JPanelDialog, BhaashikClient {

    protected ClientType clientType = ClientType.LANGUAGE_ENCODING_IDENTIFIER;

    protected JFrame owner;
    protected JDialog dialog;
    protected Component parentComponent;
    
    protected String langEnc;
    protected String title = "";
    
    /** Creates new form FileMatcherJPanel */
    public FileMatcherJPanel() {
	initComponents();

        parentComponent = this;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        corpusTypeJPanel = new javax.swing.JPanel();
        corpusTypeLeftJPanel = new javax.swing.JPanel();
        dataCorpusJLabel = new javax.swing.JLabel();
        modelCorpusJLabel = new javax.swing.JLabel();
        corpusTypeRightJPanel = new javax.swing.JPanel();
        dataCorpusJComboBox = new javax.swing.JComboBox();
        modelCorpusJComboBox = new javax.swing.JComboBox();
        ioJPanel = new javax.swing.JPanel();
        dataJPanel = new javax.swing.JPanel();
        dataDirJLabel = new javax.swing.JLabel();
        dataDirJTextField = new javax.swing.JTextField();
        dataDirJButton = new javax.swing.JButton();
        modelDirJPanel = new javax.swing.JPanel();
        outDirJLabel = new javax.swing.JLabel();
        outDirJTextField = new javax.swing.JTextField();
        outDirJButton = new javax.swing.JButton();
        optionsJPanel = new javax.swing.JPanel();
        optionLeftJPanel = new javax.swing.JPanel();
        matchMethodJLabel = new javax.swing.JLabel();
        charsetJLabel = new javax.swing.JLabel();
        numCloseMatchesJLabel = new javax.swing.JLabel();
        optionRightJPanel = new javax.swing.JPanel();
        matchMethodJComboBox = new javax.swing.JComboBox();
        charsetJTextField = new javax.swing.JTextField();
        numCloseMatchesJComboBox = new javax.swing.JComboBox();
        booleanOptionsJPanelJPanel = new javax.swing.JPanel();
        otherCommonOptionsJPanel = new javax.swing.JPanel();
        recursiveJCheckBox = new javax.swing.JCheckBox();
        someSpecialOptionsJPanel = new javax.swing.JPanel();
        commandsJPanel = new javax.swing.JPanel();
        defaultsJButton = new javax.swing.JButton();
        matchJButton = new javax.swing.JButton();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        corpusTypeJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Corpus Type"));
        corpusTypeJPanel.setLayout(new java.awt.BorderLayout());

        corpusTypeLeftJPanel.setLayout(new java.awt.GridLayout(0, 1, 0, 4));

        dataCorpusJLabel.setLabelFor(dataCorpusJComboBox);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bhaashik"); // NOI18N
        dataCorpusJLabel.setText(bundle.getString("Data:_")); // NOI18N
        corpusTypeLeftJPanel.add(dataCorpusJLabel);

        modelCorpusJLabel.setLabelFor(modelCorpusJComboBox);
        modelCorpusJLabel.setText(bundle.getString("Model:_")); // NOI18N
        corpusTypeLeftJPanel.add(modelCorpusJLabel);

        corpusTypeJPanel.add(corpusTypeLeftJPanel, java.awt.BorderLayout.WEST);

        corpusTypeRightJPanel.setLayout(new java.awt.GridLayout(0, 1, 0, 4));

        corpusTypeRightJPanel.add(dataCorpusJComboBox);

        corpusTypeRightJPanel.add(modelCorpusJComboBox);

        corpusTypeJPanel.add(corpusTypeRightJPanel, java.awt.BorderLayout.CENTER);

        add(corpusTypeJPanel);

        ioJPanel.setLayout(new javax.swing.BoxLayout(ioJPanel, javax.swing.BoxLayout.Y_AXIS));

        dataJPanel.setLayout(new java.awt.BorderLayout());

        dataDirJLabel.setLabelFor(dataDirJTextField);
        dataDirJLabel.setText(bundle.getString("Data_directory:")); // NOI18N
        dataJPanel.add(dataDirJLabel, java.awt.BorderLayout.NORTH);
        dataJPanel.add(dataDirJTextField, java.awt.BorderLayout.CENTER);

        dataDirJButton.setText(bundle.getString("Browse")); // NOI18N
        dataJPanel.add(dataDirJButton, java.awt.BorderLayout.EAST);

        ioJPanel.add(dataJPanel);

        modelDirJPanel.setLayout(new java.awt.BorderLayout());

        outDirJLabel.setText(bundle.getString("Model_directory:")); // NOI18N
        modelDirJPanel.add(outDirJLabel, java.awt.BorderLayout.NORTH);
        modelDirJPanel.add(outDirJTextField, java.awt.BorderLayout.CENTER);

        outDirJButton.setText(bundle.getString("Browse")); // NOI18N
        modelDirJPanel.add(outDirJButton, java.awt.BorderLayout.EAST);

        ioJPanel.add(modelDirJPanel);

        add(ioJPanel);

        optionsJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Matching Options"));
        optionsJPanel.setLayout(new java.awt.BorderLayout());

        optionLeftJPanel.setLayout(new java.awt.GridLayout(0, 1));

        matchMethodJLabel.setLabelFor(matchMethodJComboBox);
        matchMethodJLabel.setText(bundle.getString("Match_method:_")); // NOI18N
        optionLeftJPanel.add(matchMethodJLabel);

        charsetJLabel.setText(bundle.getString("Charset_or_encoding:_")); // NOI18N
        optionLeftJPanel.add(charsetJLabel);

        numCloseMatchesJLabel.setLabelFor(numCloseMatchesJComboBox);
        numCloseMatchesJLabel.setText(bundle.getString("Show_number_of_closest_matches:_")); // NOI18N
        optionLeftJPanel.add(numCloseMatchesJLabel);

        optionsJPanel.add(optionLeftJPanel, java.awt.BorderLayout.WEST);

        optionRightJPanel.setLayout(new java.awt.GridLayout(0, 1));

        matchMethodJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Word-by-word sequential" }));
        optionRightJPanel.add(matchMethodJComboBox);

        charsetJTextField.setText(bundle.getString("UTF8")); // NOI18N
        optionRightJPanel.add(charsetJTextField);

        numCloseMatchesJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        optionRightJPanel.add(numCloseMatchesJComboBox);

        optionsJPanel.add(optionRightJPanel, java.awt.BorderLayout.CENTER);

        add(optionsJPanel);

        booleanOptionsJPanelJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Some Other Options"));
        booleanOptionsJPanelJPanel.setLayout(new java.awt.GridLayout(1, 0));

        otherCommonOptionsJPanel.setLayout(new java.awt.GridLayout(0, 1, 0, 4));

        recursiveJCheckBox.setText(bundle.getString("Look_into_sub-directories")); // NOI18N
        recursiveJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        recursiveJCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        otherCommonOptionsJPanel.add(recursiveJCheckBox);

        booleanOptionsJPanelJPanel.add(otherCommonOptionsJPanel);

        someSpecialOptionsJPanel.setLayout(new java.awt.GridLayout(0, 1, 0, 4));
        booleanOptionsJPanelJPanel.add(someSpecialOptionsJPanel);

        add(booleanOptionsJPanelJPanel);

        commandsJPanel.setLayout(new java.awt.GridLayout(1, 0, 4, 4));

        defaultsJButton.setText(bundle.getString("Defaults")); // NOI18N
        commandsJPanel.add(defaultsJButton);

        matchJButton.setText(bundle.getString("Match")); // NOI18N
        commandsJPanel.add(matchJButton);

        add(commandsJPanel);
    }// </editor-fold>//GEN-END:initComponents

    public ClientType getClientType()
    {
        return clientType;
    }

    public String getLangEnc()
    {
        return langEnc;
    }
    
    public Frame getOwner() {
        return owner;
    }
    
    public void setOwner(Frame f) {
        owner = (JFrame) f;
    }

    public void setParentComponent(Component parentComponent)
    {
        this.parentComponent = parentComponent;
    }

    public void setDialog(JDialog dialog) {
        this.dialog= dialog;
    }
    
    public String getTitle() {
        return title;
    }

    public JMenuBar getJMenuBar() {
        return null;
    }

    public JPopupMenu getJPopupMenu() {
        return null;
    }

    public JToolBar getJToolBar() {
        return null;
    }
      
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame(GlobalProperties.getIntlString("Bhaashik_File_Matcher"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        FileMatcherJPanel newContentPane = new FileMatcherJPanel();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
	
//        int xinset = 200;
//        int yinset = 250;
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        frame.setBounds(xinset, yinset,
//		screenSize.width  - xinset*2,
//		screenSize.height - yinset*2);

	UtilityFunctions.centre(frame);
	
	frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JPanel booleanOptionsJPanelJPanel;
    protected javax.swing.JLabel charsetJLabel;
    protected javax.swing.JTextField charsetJTextField;
    protected javax.swing.JPanel commandsJPanel;
    protected javax.swing.JPanel corpusTypeJPanel;
    protected javax.swing.JPanel corpusTypeLeftJPanel;
    protected javax.swing.JPanel corpusTypeRightJPanel;
    protected javax.swing.JComboBox dataCorpusJComboBox;
    protected javax.swing.JLabel dataCorpusJLabel;
    protected javax.swing.JButton dataDirJButton;
    protected javax.swing.JLabel dataDirJLabel;
    protected javax.swing.JTextField dataDirJTextField;
    protected javax.swing.JPanel dataJPanel;
    protected javax.swing.JButton defaultsJButton;
    protected javax.swing.JPanel ioJPanel;
    protected javax.swing.JButton matchJButton;
    protected javax.swing.JComboBox matchMethodJComboBox;
    protected javax.swing.JLabel matchMethodJLabel;
    protected javax.swing.JComboBox modelCorpusJComboBox;
    protected javax.swing.JLabel modelCorpusJLabel;
    protected javax.swing.JPanel modelDirJPanel;
    protected javax.swing.JComboBox numCloseMatchesJComboBox;
    protected javax.swing.JLabel numCloseMatchesJLabel;
    protected javax.swing.JPanel optionLeftJPanel;
    protected javax.swing.JPanel optionRightJPanel;
    protected javax.swing.JPanel optionsJPanel;
    protected javax.swing.JPanel otherCommonOptionsJPanel;
    protected javax.swing.JButton outDirJButton;
    protected javax.swing.JLabel outDirJLabel;
    protected javax.swing.JTextField outDirJTextField;
    protected javax.swing.JCheckBox recursiveJCheckBox;
    protected javax.swing.JPanel someSpecialOptionsJPanel;
    // End of variables declaration//GEN-END:variables
    
}
