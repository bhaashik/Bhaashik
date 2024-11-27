/*
 * TamMarkerTaskJPanel.java
 *
 * Created on September 26, 2005, 10:15 PM
 */

package bhaashik.corpus.parallel.gui;

import java.io.*;
import java.util.*;
import javax.swing.*;

import bhaashik.GlobalProperties;
import bhaashik.common.types.PropertyType;
import bhaashik.gui.TaskSetupJPanelInterface;
import bhaashik.gui.common.JPanelDialog;

import bhaashik.properties.KeyValueProperties;
import bhaashik.properties.PropertiesManager;
import bhaashik.properties.PropertiesTable;

/**
 *
 * @author  anil
 */
public class ParallelMarkupTaskSetupJPanel extends javax.swing.JPanel
        implements TaskSetupJPanelInterface, JPanelDialog {
     
    private JFrame owner;
    private JDialog dialog;

    private ParallelMarkupWorkJPanel workJPanel;
    
    private String taskName;
    private String taskPropFile;

    private String srcCorpusFile;
    private String tgtCorpusFile;
    private String tgtCorpusUTF8File;

    private String srcTMFile;
    private String tgtTMFile;
    
    private String markerDictFile;
    
    private KeyValueProperties taskKVP;
    private DefaultComboBoxModel taskList;
    
    private boolean newTask;
   
    /** Creates new form TamMarkerTaskJPanel */
    public ParallelMarkupTaskSetupJPanel() {
        taskKVP = new KeyValueProperties();
        newTask = true;
        
        initComponents();
    }

    public ParallelMarkupTaskSetupJPanel(KeyValueProperties kvp, ParallelMarkupWorkJPanel workJPanel) {
        this(kvp);
          
        this.workJPanel = workJPanel;        
    }

    public ParallelMarkupTaskSetupJPanel(KeyValueProperties kvp) {
        taskKVP = kvp;
        newTask = false;
        
        initComponents();

        configure();
        taskNameJTextField.setEditable(false);
        propJTextField.setEditable(false);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        taskJPanel = new javax.swing.JPanel();
        taskNameJLabel = new javax.swing.JLabel();
        taskNameJTextField = new javax.swing.JTextField();
        propJPanel = new javax.swing.JPanel();
        propJLabel = new javax.swing.JLabel();
        propJTextField = new javax.swing.JTextField();
        propertiesJButton = new javax.swing.JButton();
        srcCorpusJPanel = new javax.swing.JPanel();
        srcCorpusJLabel = new javax.swing.JLabel();
        srcCorpusJTextField = new javax.swing.JTextField();
        srcCorpusJButton = new javax.swing.JButton();
        tgtCorpusJPanel = new javax.swing.JPanel();
        tgtCorpusJLabel = new javax.swing.JLabel();
        tgtCorpusJTextField = new javax.swing.JTextField();
        tgtCorpusJButton = new javax.swing.JButton();
        tgtCorpusUTF8JPanel = new javax.swing.JPanel();
        tgtCorpusUTF8JLabel = new javax.swing.JLabel();
        tgtCorpusUTF8JTextField = new javax.swing.JTextField();
        tgtCorpusUTF8JButton = new javax.swing.JButton();
        srcTMListJPanel = new javax.swing.JPanel();
        srcTMListJLabel = new javax.swing.JLabel();
        srcTMListJTextField = new javax.swing.JTextField();
        srcTMListJButton = new javax.swing.JButton();
        tgtTMListJPanel = new javax.swing.JPanel();
        tgtTMListJLabel = new javax.swing.JLabel();
        tgtTMListJTextField = new javax.swing.JTextField();
        tgtTMListJButton = new javax.swing.JButton();
        markerDictJPanel = new javax.swing.JPanel();
        markerDictJLabel = new javax.swing.JLabel();
        markerDictJTextField = new javax.swing.JTextField();
        markerDictJButton = new javax.swing.JButton();
        commandsJPanel = new javax.swing.JPanel();
        OKJButton = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Create Task"));
        setPreferredSize(new java.awt.Dimension(350, 400));
        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        taskJPanel.setLayout(new java.awt.BorderLayout());

        taskNameJLabel.setLabelFor(taskNameJTextField);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bhaashik"); // NOI18N
        taskNameJLabel.setText(bundle.getString("Task_name:")); // NOI18N
        taskJPanel.add(taskNameJLabel, java.awt.BorderLayout.NORTH);

        taskNameJTextField.setText(bundle.getString("Task_name")); // NOI18N
        taskJPanel.add(taskNameJTextField, java.awt.BorderLayout.CENTER);

        add(taskJPanel);

        propJPanel.setLayout(new java.awt.BorderLayout());

        propJLabel.setLabelFor(propJTextField);
        propJLabel.setText(bundle.getString("Task_properties_file:")); // NOI18N
        propJPanel.add(propJLabel, java.awt.BorderLayout.NORTH);

        propJTextField.setText(bundle.getString("Task_properties_file")); // NOI18N
        propJPanel.add(propJTextField, java.awt.BorderLayout.CENTER);

        propertiesJButton.setText(bundle.getString("Browse")); // NOI18N
        propertiesJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                propertiesJButtonActionPerformed(evt);
            }
        });
        propJPanel.add(propertiesJButton, java.awt.BorderLayout.EAST);

        add(propJPanel);

        srcCorpusJPanel.setLayout(new java.awt.BorderLayout());

        srcCorpusJLabel.setLabelFor(srcCorpusJTextField);
        srcCorpusJLabel.setText(bundle.getString("Source_corpus_file:")); // NOI18N
        srcCorpusJPanel.add(srcCorpusJLabel, java.awt.BorderLayout.NORTH);

        srcCorpusJTextField.setText(bundle.getString("Source_corpus_file")); // NOI18N
        srcCorpusJPanel.add(srcCorpusJTextField, java.awt.BorderLayout.CENTER);

        srcCorpusJButton.setText(bundle.getString("Browse")); // NOI18N
        srcCorpusJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                srcCorpusJButtonActionPerformed(evt);
            }
        });
        srcCorpusJPanel.add(srcCorpusJButton, java.awt.BorderLayout.EAST);

        add(srcCorpusJPanel);

        tgtCorpusJPanel.setLayout(new java.awt.BorderLayout());

        tgtCorpusJLabel.setLabelFor(tgtCorpusJTextField);
        tgtCorpusJLabel.setText(bundle.getString("Target_corpus_file:")); // NOI18N
        tgtCorpusJPanel.add(tgtCorpusJLabel, java.awt.BorderLayout.NORTH);

        tgtCorpusJTextField.setText(bundle.getString("Target_corpus_file")); // NOI18N
        tgtCorpusJPanel.add(tgtCorpusJTextField, java.awt.BorderLayout.CENTER);

        tgtCorpusJButton.setText(bundle.getString("Browse")); // NOI18N
        tgtCorpusJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tgtCorpusJButtonActionPerformed(evt);
            }
        });
        tgtCorpusJPanel.add(tgtCorpusJButton, java.awt.BorderLayout.EAST);

        add(tgtCorpusJPanel);

        tgtCorpusUTF8JPanel.setLayout(new java.awt.BorderLayout());

        tgtCorpusUTF8JLabel.setLabelFor(tgtCorpusJTextField);
        tgtCorpusUTF8JLabel.setText(bundle.getString("Target_corpus_file_(UTF8):")); // NOI18N
        tgtCorpusUTF8JPanel.add(tgtCorpusUTF8JLabel, java.awt.BorderLayout.NORTH);

        tgtCorpusUTF8JTextField.setText(bundle.getString("Target_corpus_file_(UTF8)")); // NOI18N
        tgtCorpusUTF8JPanel.add(tgtCorpusUTF8JTextField, java.awt.BorderLayout.CENTER);

        tgtCorpusUTF8JButton.setText(bundle.getString("Browse")); // NOI18N
        tgtCorpusUTF8JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tgtCorpusUTF8JButtonActionPerformed(evt);
            }
        });
        tgtCorpusUTF8JPanel.add(tgtCorpusUTF8JButton, java.awt.BorderLayout.EAST);

        add(tgtCorpusUTF8JPanel);

        srcTMListJPanel.setLayout(new java.awt.BorderLayout());

        srcTMListJLabel.setLabelFor(srcTMListJTextField);
        srcTMListJLabel.setText(bundle.getString("Source_language_marker_file:")); // NOI18N
        srcTMListJPanel.add(srcTMListJLabel, java.awt.BorderLayout.NORTH);

        srcTMListJTextField.setText(bundle.getString("Source_language_marker_file")); // NOI18N
        srcTMListJPanel.add(srcTMListJTextField, java.awt.BorderLayout.CENTER);

        srcTMListJButton.setText(bundle.getString("Browse")); // NOI18N
        srcTMListJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                srcTMListJButtonActionPerformed(evt);
            }
        });
        srcTMListJPanel.add(srcTMListJButton, java.awt.BorderLayout.EAST);

        add(srcTMListJPanel);

        tgtTMListJPanel.setLayout(new java.awt.BorderLayout());

        tgtTMListJLabel.setLabelFor(tgtTMListJTextField);
        tgtTMListJLabel.setText(bundle.getString("Target_language_marker_file:")); // NOI18N
        tgtTMListJPanel.add(tgtTMListJLabel, java.awt.BorderLayout.NORTH);

        tgtTMListJTextField.setText(bundle.getString("Target_language_marker_file")); // NOI18N
        tgtTMListJPanel.add(tgtTMListJTextField, java.awt.BorderLayout.CENTER);

        tgtTMListJButton.setText(bundle.getString("Browse")); // NOI18N
        tgtTMListJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tgtTMListJButtonActionPerformed(evt);
            }
        });
        tgtTMListJPanel.add(tgtTMListJButton, java.awt.BorderLayout.EAST);

        add(tgtTMListJPanel);

        markerDictJPanel.setLayout(new java.awt.BorderLayout());

        markerDictJLabel.setLabelFor(markerDictJTextField);
        markerDictJLabel.setText(bundle.getString("Marker_dictionary_file:")); // NOI18N
        markerDictJPanel.add(markerDictJLabel, java.awt.BorderLayout.NORTH);

        markerDictJTextField.setText(bundle.getString("Marker_dictionary_file")); // NOI18N
        markerDictJPanel.add(markerDictJTextField, java.awt.BorderLayout.CENTER);

        markerDictJButton.setText(bundle.getString("Browse")); // NOI18N
        markerDictJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markerDictJButtonActionPerformed(evt);
            }
        });
        markerDictJPanel.add(markerDictJButton, java.awt.BorderLayout.EAST);

        add(markerDictJPanel);

        OKJButton.setText(bundle.getString("OK")); // NOI18N
        OKJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKJButtonActionPerformed(evt);
            }
        });
        commandsJPanel.add(OKJButton);

        add(commandsJPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void markerDictJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_markerDictJButtonActionPerformed
// TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           markerDictFile = chooser.getSelectedFile().getAbsolutePath();
           
           if(validateFilePath(markerDictFile).equals("") == false)
           {
               markerDictJTextField.setText(markerDictFile);
           }
        }
    }//GEN-LAST:event_markerDictJButtonActionPerformed

    private void tgtCorpusUTF8JButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tgtCorpusUTF8JButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           tgtCorpusUTF8File = chooser.getSelectedFile().getAbsolutePath();
           
           if(validateFilePath(tgtCorpusUTF8File).equals("") == false)
           {
               tgtCorpusUTF8JTextField.setText(tgtCorpusUTF8File);
           }
        }
    }//GEN-LAST:event_tgtCorpusUTF8JButtonActionPerformed

    private void tgtCorpusJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tgtCorpusJButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           tgtCorpusFile = chooser.getSelectedFile().getAbsolutePath();
           
           if(validateFilePath(tgtCorpusFile).equals("") == false)
           {
               tgtCorpusJTextField.setText(tgtCorpusFile);
           }
        }
    }//GEN-LAST:event_tgtCorpusJButtonActionPerformed

    private void OKJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKJButtonActionPerformed
        // TODO add your handling code here:
        boolean valid = true;
        
        taskPropFile = propJTextField.getText();
        srcCorpusFile = srcCorpusJTextField.getText();
        tgtCorpusFile = tgtCorpusJTextField.getText();
        tgtCorpusUTF8File = tgtCorpusUTF8JTextField.getText();
        srcTMFile = srcTMListJTextField.getText();
        tgtTMFile = tgtTMListJTextField.getText();
        markerDictFile = markerDictJTextField.getText();
            
        if(validateTaskPropsFilePath() == false || validateFilePath(srcCorpusFile).equals("") == true
                || validateFilePath(tgtCorpusFile).equals("") == true 
                || validateFilePath(tgtCorpusUTF8File).equals("") == true 
                || validateFilePath(srcTMFile).equals("") == true
                || validateFilePath(tgtTMFile).equals("") == true || validateTaskName() == false)
        {
            valid = false;
        }
                
        if(valid == true)
        {
            PropertiesManager pm = null;
            
            if(workJPanel != null)
            {
                pm = workJPanel.getPropertiesManager();            
            }
            else
            {
                ParallelMarkupMain owner = (ParallelMarkupMain) getOwner();
                pm = owner.getPropertiesManager();
            }

            taskName = taskNameJTextField.getText();

            PropertiesTable tasks = (PropertiesTable) pm.getPropertyContainer("tasks", PropertyType.PROPERTY_TABLE);
            
            if(newTask == true)
            {
                taskList.addElement(taskName);

                String cols[] = {taskName, taskPropFile, GlobalProperties.getIntlString("UTF-8")};
                tasks.addRow(cols);

                try {
                    pm.savePropertyContainer("tasks", PropertyType.PROPERTY_TABLE);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, GlobalProperties.getIntlString("Error"), GlobalProperties.getIntlString("Could_not_save_task_list."), JOptionPane.ERROR_MESSAGE);
                }
            }

            taskKVP.addProperty("TaskName", taskName);
            taskKVP.addProperty("TaskPropFile", taskPropFile);
            taskKVP.addProperty("TaskPropCharset", GlobalProperties.getIntlString("UTF-8"));
            taskKVP.addProperty("SLCorpusFile", srcCorpusFile);
            taskKVP.addProperty("SLCorpusCharset", GlobalProperties.getIntlString("UTF-8"));
            taskKVP.addProperty("TLCorpusFile", tgtCorpusFile);
            taskKVP.addProperty("TLCorpusUTF8File", tgtCorpusUTF8File);
            taskKVP.addProperty("TLCorpusCharset", GlobalProperties.getIntlString("UTF-8"));
            taskKVP.addProperty("SLTMFile", srcTMFile);
            taskKVP.addProperty("SLTMCharset", GlobalProperties.getIntlString("UTF-8"));
            taskKVP.addProperty("TLTMFile", tgtTMFile);
            taskKVP.addProperty("TLTMCharset", GlobalProperties.getIntlString("UTF-8"));
            taskKVP.addProperty("MarkerDictFile", markerDictFile);
            taskKVP.addProperty("MarkerDictCharset", GlobalProperties.getIntlString("UTF-8"));

            if(newTask == true)
                taskKVP.addProperty("CurrentPosition", "1");

            try {
                taskKVP.save(taskPropFile, GlobalProperties.getIntlString("UTF-8"));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, GlobalProperties.getIntlString("Error"), GlobalProperties.getIntlString("Could_not_save_task_information."), JOptionPane.ERROR_MESSAGE);
            }
        }
        
        dialog.setVisible(false);
    }//GEN-LAST:event_OKJButtonActionPerformed

    private void propertiesJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_propertiesJButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           taskPropFile = chooser.getSelectedFile().getAbsolutePath();
           
           if(validateTaskPropsFilePath() == true)
           {
               propJTextField.setText(taskPropFile); 
           }
        }
    }//GEN-LAST:event_propertiesJButtonActionPerformed

    private void tgtTMListJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tgtTMListJButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           tgtTMFile = chooser.getSelectedFile().getAbsolutePath();
           
           if(validateFilePath(tgtTMFile).equals("") == false)
           {
               tgtTMListJTextField.setText(tgtTMFile);
           }
        }
    }//GEN-LAST:event_tgtTMListJButtonActionPerformed

    private void srcTMListJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_srcTMListJButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           srcTMFile = chooser.getSelectedFile().getAbsolutePath();
           
           if(validateFilePath(srcTMFile).equals("") == false)
           {
               srcTMListJTextField.setText(srcTMFile);
           }
        }
    }//GEN-LAST:event_srcTMListJButtonActionPerformed

    private void srcCorpusJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_srcCorpusJButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           srcCorpusFile = chooser.getSelectedFile().getAbsolutePath();
           
           if(validateFilePath(srcCorpusFile).equals("") == false)
           {
               srcCorpusJTextField.setText(srcCorpusFile);
           }
        }
    }//GEN-LAST:event_srcCorpusJButtonActionPerformed

    private boolean validateTaskName()
    {
        ParallelMarkupMain owner = (ParallelMarkupMain) getOwner();
        PropertiesManager pm = owner.getPropertiesManager();
        
        taskName = taskNameJTextField.getText();
        
        PropertiesTable tasks = (PropertiesTable) pm.getPropertyContainer("tasks", PropertyType.PROPERTY_TABLE);
        Vector rows = tasks.getRows("TaskName", taskName);
        
        if(rows.size() == 1)
        {
            JOptionPane.showMessageDialog(this, GlobalProperties.getIntlString("Task_name_alresdy_exists:_") + taskName, GlobalProperties.getIntlString("Error"), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }

    private boolean validateTaskPropsFilePath()
    {
        ParallelMarkupMain owner = (ParallelMarkupMain) getOwner();
        String ws = owner.getWorkspace();
        
        if(taskPropFile.startsWith(File.separator) == true)
        {
            if(taskPropFile.startsWith(ws) == false)
            {
                JOptionPane.showMessageDialog(this, GlobalProperties.getIntlString("Files_should_be_in_the_workspace_directory:_") + ws, GlobalProperties.getIntlString("Error"), JOptionPane.ERROR_MESSAGE);
                return false;
            }
//            else
//            {
//                taskPropFile = taskPropFile.substring(ws.length());
//                taskPropFile.replaceFirst(ws, "");
//            }
        }
        
        return true;
    }
    
    private void configure()
    {
        taskPropFile = taskKVP.getPropertyValue("TaskPropFile");

        srcCorpusFile = taskKVP.getPropertyValue("SLCorpusFile");
        tgtCorpusFile = taskKVP.getPropertyValue("TLCorpusFile");
        tgtCorpusUTF8File = taskKVP.getPropertyValue("TLCorpusUTF8File");

        srcTMFile = taskKVP.getPropertyValue("SLTMFile");
        tgtTMFile = taskKVP.getPropertyValue("TLTMFile");

        markerDictFile = taskKVP.getPropertyValue("MarkerDictFile");
        
        propJTextField.setText(taskPropFile);
        srcCorpusJTextField.setText(srcCorpusFile);
        tgtCorpusJTextField.setText(tgtCorpusFile);
        tgtCorpusUTF8JTextField.setText(tgtCorpusUTF8File);
        srcTMListJTextField.setText(srcTMFile);
        tgtTMListJTextField.setText(tgtTMFile);
        markerDictJTextField.setText(markerDictFile);
    }

    private String validateFilePath(String fn)
    {
        ParallelMarkupMain owner = (ParallelMarkupMain) getOwner();
        String ws = owner.getWorkspace();
        
        if(fn.startsWith(File.separator) == true)
        {
            if(fn.startsWith(ws) == false)
            {
                JOptionPane.showMessageDialog(this, GlobalProperties.getIntlString("Files_should_be_in_the_workspace_directory:_") + ws, GlobalProperties.getIntlString("Error"), JOptionPane.ERROR_MESSAGE);
                return "";
            }
//            else
//            {
//                fn = taskPropFile.substring(ws.length());
//                fn.replaceFirst(ws, "");
//            }
        }
        
        return fn;
    }
    
    public JFrame getOwner()
    {
        return owner;
    }
    
    public void setOwner(JFrame f)
    {
        owner = f;
    }
    
    public void setDialog(JDialog d)
    {
        dialog = d;
    }
    
    public void setTaskList(DefaultComboBoxModel l)
    {
        taskList = l;
    }
      
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
	
//        JFrame.setDefaultLookAndFeelDecorated(true);
//
//        //Create and set up the window.
//        JFrame frame = new JFrame("Bhaashik Syntactic Annotation");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//	ParallelMarkupTaskSetupJPanel newContentPane = new ParallelMarkupTaskSetupJPanel(true);
//        
//        ((TaskSetupJPanelInterface) newContentPane).setOwner(frame);
//	
//	newContentPane.owner = frame;
//        newContentPane.setOpaque(true); //content panes must be opaque
//        frame.setContentPane(newContentPane);
//
//        //Display the window.
////        frame.pack();
//	
//        int xinset = 280;
//        int yinset = 140;
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        frame.setBounds(xinset, yinset,
//		screenSize.width  - xinset*2,
//		screenSize.height - yinset*2);
//
//	frame.setVisible(true);
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton OKJButton;
    public javax.swing.JPanel commandsJPanel;
    public javax.swing.JButton markerDictJButton;
    public javax.swing.JLabel markerDictJLabel;
    public javax.swing.JPanel markerDictJPanel;
    public javax.swing.JTextField markerDictJTextField;
    public javax.swing.JLabel propJLabel;
    public javax.swing.JPanel propJPanel;
    public javax.swing.JTextField propJTextField;
    public javax.swing.JButton propertiesJButton;
    public javax.swing.JButton srcCorpusJButton;
    public javax.swing.JLabel srcCorpusJLabel;
    public javax.swing.JPanel srcCorpusJPanel;
    public javax.swing.JTextField srcCorpusJTextField;
    public javax.swing.JButton srcTMListJButton;
    public javax.swing.JLabel srcTMListJLabel;
    public javax.swing.JPanel srcTMListJPanel;
    public javax.swing.JTextField srcTMListJTextField;
    public javax.swing.JPanel taskJPanel;
    public javax.swing.JLabel taskNameJLabel;
    public javax.swing.JTextField taskNameJTextField;
    public javax.swing.JButton tgtCorpusJButton;
    public javax.swing.JLabel tgtCorpusJLabel;
    public javax.swing.JPanel tgtCorpusJPanel;
    public javax.swing.JTextField tgtCorpusJTextField;
    public javax.swing.JButton tgtCorpusUTF8JButton;
    public javax.swing.JLabel tgtCorpusUTF8JLabel;
    public javax.swing.JPanel tgtCorpusUTF8JPanel;
    public javax.swing.JTextField tgtCorpusUTF8JTextField;
    public javax.swing.JButton tgtTMListJButton;
    public javax.swing.JLabel tgtTMListJLabel;
    public javax.swing.JPanel tgtTMListJPanel;
    public javax.swing.JTextField tgtTMListJTextField;
    // End of variables declaration//GEN-END:variables
    
}
