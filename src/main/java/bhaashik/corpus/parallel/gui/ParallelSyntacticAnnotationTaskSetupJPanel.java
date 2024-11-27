/*
 * SyntacticAnnotationTaskJPanel.java
 *
 * Created on October 9, 2005, 9:51 PM
 */

package bhaashik.corpus.parallel.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Frame;
import java.io.*;
import java.util.*;
import javax.swing.*;

import bhaashik.GlobalProperties;
import bhaashik.common.types.PropertyType;
import bhaashik.corpus.ssf.tree.SSFNode;
import bhaashik.gui.TaskSetupJPanelInterface;
import bhaashik.gui.clients.AnnotationClient;
import bhaashik.gui.common.JPanelDialog;

import bhaashik.gui.common.BhaashikLanguages;
import bhaashik.properties.KeyValueProperties;
import bhaashik.properties.PropertiesManager;
import bhaashik.properties.PropertiesTable;

/**
 *
 * @author  anil
 */
public class ParallelSyntacticAnnotationTaskSetupJPanel extends javax.swing.JPanel
        implements TaskSetupJPanelInterface, JPanelDialog
{
    private JFrame owner;
    private JDialog dialog;

    private JDialog workDialog;
    
    private String langauge;
    private String encoding;

    private DefaultComboBoxModel langauges;
    private DefaultComboBoxModel encodings;

    private String taskName;
    private String taskPropFile;

    private String ssfPropFile;
    private String fsPropFile;
    private String mfFile; // mandatory features
    private String ssfSrcCorpusStoryFile;
    private String ssfTgtCorpusStoryFile;

    private String posTagsFile;
    private String phraseNamesFile;
    
    private KeyValueProperties taskKVP;
    private DefaultComboBoxModel taskList;
    
    private boolean newTask;
    private boolean standAloneMode;
    
    /** Creates new form SyntacticAnnotationTaskJPanel */
    public ParallelSyntacticAnnotationTaskSetupJPanel() {
        taskKVP = new KeyValueProperties();
        newTask = true;
	standAloneMode = false;

        initComponents();
	
	langauges = new DefaultComboBoxModel();
	encodings = new DefaultComboBoxModel();

	BhaashikLanguages.fillLanguages(langauges);
	BhaashikLanguages.fillEncodings(encodings, "hin");
	
	languageJComboBox.setModel(langauges);
	encodingJComboBox.setModel(encodings);
	
	setDefaults();
    }

    public ParallelSyntacticAnnotationTaskSetupJPanel(String ssfSrcFile, String ssfTgtFile, boolean standAloneMode)
    {
	this(true);
	
	ssfSrcCorpusStoryFile = ssfSrcFile;
	ssfTgtCorpusStoryFile = ssfTgtFile;
	ssfSrcCorpusJTextField.setText(ssfSrcFile);
	ssfTgtCorpusJTextField.setText(ssfTgtFile);
	generateTaskNameAndPropFile();
    }
    
    public ParallelSyntacticAnnotationTaskSetupJPanel(boolean standAloneMode) {
        taskKVP = new KeyValueProperties();
        newTask = true;
	this.standAloneMode = standAloneMode;

        initComponents();
	
	langauges = new DefaultComboBoxModel();
	encodings = new DefaultComboBoxModel();

	BhaashikLanguages.fillLanguages(langauges);
	BhaashikLanguages.fillEncodings(encodings, "hin");
	
	languageJComboBox.setModel(langauges);
	encodingJComboBox.setModel(encodings);
	
	setDefaults();
    }

    public ParallelSyntacticAnnotationTaskSetupJPanel(KeyValueProperties kvp) {
        taskKVP = kvp;
        newTask = false;
	standAloneMode = false;
        
        initComponents();

	langauges = new DefaultComboBoxModel();
	encodings = new DefaultComboBoxModel();

	BhaashikLanguages.fillLanguages(langauges);
	BhaashikLanguages.fillEncodings(encodings, "hin");
	
	languageJComboBox.setModel(langauges);
	encodingJComboBox.setModel(encodings);

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

        languageJPanel = new javax.swing.JPanel();
        languageJLabel = new javax.swing.JLabel();
        languageJComboBox = new javax.swing.JComboBox();
        encodingJPanel = new javax.swing.JPanel();
        encodingJLabel = new javax.swing.JLabel();
        encodingJComboBox = new javax.swing.JComboBox();
        ssfCorpusJPanel = new javax.swing.JPanel();
        ssfSrcCorpusJLabel = new javax.swing.JLabel();
        ssfSrcCorpusJTextField = new javax.swing.JTextField();
        ssfSrcCorpusJButton = new javax.swing.JButton();
        ssfCorpusUTF8JPanel = new javax.swing.JPanel();
        ssfTgtCorpusJLabel = new javax.swing.JLabel();
        ssfTgtCorpusJTextField = new javax.swing.JTextField();
        ssfTgtCorpusJButton = new javax.swing.JButton();
        taskJPanel = new javax.swing.JPanel();
        taskNameJLabel = new javax.swing.JLabel();
        taskNameJTextField = new javax.swing.JTextField();
        propJPanel = new javax.swing.JPanel();
        propJLabel = new javax.swing.JLabel();
        propJTextField = new javax.swing.JTextField();
        propertiesJButton = new javax.swing.JButton();
        ssfPropsJPanel = new javax.swing.JPanel();
        ssfPropsJLabel = new javax.swing.JLabel();
        ssfPropsJTextField = new javax.swing.JTextField();
        ssfPropsJButton = new javax.swing.JButton();
        mfeaturesJPanel = new javax.swing.JPanel();
        mfeaturesJLabel = new javax.swing.JLabel();
        mfeaturesJTextField = new javax.swing.JTextField();
        mfeaturesJButton = new javax.swing.JButton();
        fsPropsJPanel = new javax.swing.JPanel();
        fsPropsJLabel = new javax.swing.JLabel();
        fsPropsJTextField = new javax.swing.JTextField();
        fsPropsJButton = new javax.swing.JButton();
        phraseNamesJPanel = new javax.swing.JPanel();
        phraseNamesJLabel = new javax.swing.JLabel();
        phraseNamesJTextField = new javax.swing.JTextField();
        phraseNamesJButton = new javax.swing.JButton();
        posTagsJPanel = new javax.swing.JPanel();
        posTagsJLabel = new javax.swing.JLabel();
        posTagsJTextField = new javax.swing.JTextField();
        posTagsJButton = new javax.swing.JButton();
        booleanOptionsJPanel = new javax.swing.JPanel();
        saveTaskFileJCheckBox = new javax.swing.JCheckBox();
        commandsJPanel = new javax.swing.JPanel();
        OKJButton = new javax.swing.JButton();

        setLayout(new javax.swing.BoxLayout(this, javax.swing.BoxLayout.Y_AXIS));

        languageJPanel.setLayout(new java.awt.BorderLayout());

        languageJLabel.setLabelFor(languageJComboBox);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bhaashik"); // NOI18N
        languageJLabel.setText(bundle.getString("Language:_")); // NOI18N
        languageJPanel.add(languageJLabel, java.awt.BorderLayout.WEST);

        languageJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                languageJComboBoxActionPerformed(evt);
            }
        });
        languageJPanel.add(languageJComboBox, java.awt.BorderLayout.CENTER);

        add(languageJPanel);

        encodingJPanel.setLayout(new java.awt.BorderLayout());

        encodingJLabel.setLabelFor(encodingJComboBox);
        encodingJLabel.setText(bundle.getString("Encoding:__")); // NOI18N
        encodingJPanel.add(encodingJLabel, java.awt.BorderLayout.WEST);

        encodingJPanel.add(encodingJComboBox, java.awt.BorderLayout.CENTER);

        add(encodingJPanel);

        ssfCorpusJPanel.setLayout(new java.awt.BorderLayout());

        ssfSrcCorpusJLabel.setLabelFor(ssfSrcCorpusJTextField);
        ssfSrcCorpusJLabel.setText(bundle.getString("SSF_source_language_corpus_story_file:")); // NOI18N
        ssfCorpusJPanel.add(ssfSrcCorpusJLabel, java.awt.BorderLayout.NORTH);

        ssfSrcCorpusJTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                ssfSrcCorpusJTextFieldCaretUpdate(evt);
            }
        });
        ssfCorpusJPanel.add(ssfSrcCorpusJTextField, java.awt.BorderLayout.CENTER);

        ssfSrcCorpusJButton.setText(bundle.getString("Browse")); // NOI18N
        ssfSrcCorpusJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ssfSrcCorpusJButtonActionPerformed(evt);
            }
        });
        ssfCorpusJPanel.add(ssfSrcCorpusJButton, java.awt.BorderLayout.EAST);

        add(ssfCorpusJPanel);

        ssfCorpusUTF8JPanel.setLayout(new java.awt.BorderLayout());

        ssfTgtCorpusJLabel.setLabelFor(ssfTgtCorpusJTextField);
        ssfTgtCorpusJLabel.setText(bundle.getString("SSF_target_language_corpus_story_file:")); // NOI18N
        ssfCorpusUTF8JPanel.add(ssfTgtCorpusJLabel, java.awt.BorderLayout.NORTH);
        ssfCorpusUTF8JPanel.add(ssfTgtCorpusJTextField, java.awt.BorderLayout.CENTER);

        ssfTgtCorpusJButton.setText(bundle.getString("Browse")); // NOI18N
        ssfTgtCorpusJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ssfTgtCorpusJButtonActionPerformed(evt);
            }
        });
        ssfCorpusUTF8JPanel.add(ssfTgtCorpusJButton, java.awt.BorderLayout.EAST);

        add(ssfCorpusUTF8JPanel);

        taskJPanel.setLayout(new java.awt.BorderLayout());

        taskNameJLabel.setLabelFor(taskNameJTextField);
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

        ssfPropsJPanel.setLayout(new java.awt.BorderLayout());

        ssfPropsJLabel.setLabelFor(ssfPropsJTextField);
        ssfPropsJLabel.setText(bundle.getString("SSF_properties_file:")); // NOI18N
        ssfPropsJPanel.add(ssfPropsJLabel, java.awt.BorderLayout.NORTH);

        ssfPropsJTextField.setText(bundle.getString("SSF_properties_file")); // NOI18N
        ssfPropsJPanel.add(ssfPropsJTextField, java.awt.BorderLayout.CENTER);

        ssfPropsJButton.setText(bundle.getString("Browse")); // NOI18N
        ssfPropsJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ssfPropsJButtonActionPerformed(evt);
            }
        });
        ssfPropsJPanel.add(ssfPropsJButton, java.awt.BorderLayout.EAST);

        add(ssfPropsJPanel);

        mfeaturesJPanel.setLayout(new java.awt.BorderLayout());

        mfeaturesJLabel.setLabelFor(mfeaturesJTextField);
        mfeaturesJLabel.setText(bundle.getString("Mandatory_features_file:")); // NOI18N
        mfeaturesJPanel.add(mfeaturesJLabel, java.awt.BorderLayout.NORTH);

        mfeaturesJTextField.setText(bundle.getString("Mandatory_features_file")); // NOI18N
        mfeaturesJPanel.add(mfeaturesJTextField, java.awt.BorderLayout.CENTER);

        mfeaturesJButton.setText(bundle.getString("Browse")); // NOI18N
        mfeaturesJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mfeaturesJButtonActionPerformed(evt);
            }
        });
        mfeaturesJPanel.add(mfeaturesJButton, java.awt.BorderLayout.EAST);

        add(mfeaturesJPanel);

        fsPropsJPanel.setLayout(new java.awt.BorderLayout());

        fsPropsJLabel.setLabelFor(fsPropsJTextField);
        fsPropsJLabel.setText(bundle.getString("Feature_structure_properties_file:")); // NOI18N
        fsPropsJPanel.add(fsPropsJLabel, java.awt.BorderLayout.NORTH);

        fsPropsJTextField.setText(bundle.getString("Feature_structure_properties_file")); // NOI18N
        fsPropsJPanel.add(fsPropsJTextField, java.awt.BorderLayout.CENTER);

        fsPropsJButton.setText(bundle.getString("Browse")); // NOI18N
        fsPropsJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fsPropsJButtonActionPerformed(evt);
            }
        });
        fsPropsJPanel.add(fsPropsJButton, java.awt.BorderLayout.EAST);

        add(fsPropsJPanel);

        phraseNamesJPanel.setLayout(new java.awt.BorderLayout());

        phraseNamesJLabel.setLabelFor(phraseNamesJTextField);
        phraseNamesJLabel.setText(bundle.getString("Phrase_names_file:")); // NOI18N
        phraseNamesJPanel.add(phraseNamesJLabel, java.awt.BorderLayout.NORTH);

        phraseNamesJTextField.setText(bundle.getString("Phrase_names_file")); // NOI18N
        phraseNamesJPanel.add(phraseNamesJTextField, java.awt.BorderLayout.CENTER);

        phraseNamesJButton.setText(bundle.getString("Browse")); // NOI18N
        phraseNamesJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phraseNamesJButtonActionPerformed(evt);
            }
        });
        phraseNamesJPanel.add(phraseNamesJButton, java.awt.BorderLayout.EAST);

        add(phraseNamesJPanel);

        posTagsJPanel.setLayout(new java.awt.BorderLayout());

        posTagsJLabel.setLabelFor(posTagsJTextField);
        posTagsJLabel.setText(bundle.getString("POS_tags_file:")); // NOI18N
        posTagsJPanel.add(posTagsJLabel, java.awt.BorderLayout.NORTH);

        posTagsJTextField.setText(bundle.getString("POS_tags_file")); // NOI18N
        posTagsJPanel.add(posTagsJTextField, java.awt.BorderLayout.CENTER);

        posTagsJButton.setText(bundle.getString("Browse")); // NOI18N
        posTagsJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                posTagsJButtonActionPerformed(evt);
            }
        });
        posTagsJPanel.add(posTagsJButton, java.awt.BorderLayout.EAST);

        add(posTagsJPanel);

        booleanOptionsJPanel.setLayout(new java.awt.GridLayout(0, 1, 0, 4));

        saveTaskFileJCheckBox.setText(bundle.getString("Save_task_properties_file")); // NOI18N
        saveTaskFileJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        saveTaskFileJCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        booleanOptionsJPanel.add(saveTaskFileJCheckBox);

        add(booleanOptionsJPanel);

        OKJButton.setText(bundle.getString("OK")); // NOI18N
        OKJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKJButtonActionPerformed(evt);
            }
        });
        commandsJPanel.add(OKJButton);

        add(commandsJPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void languageJComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_languageJComboBoxActionPerformed
    {//GEN-HEADEREND:event_languageJComboBoxActionPerformed
// TODO add your handling code here:
	langauge = (String) languageJComboBox.getSelectedItem();
	BhaashikLanguages.fillEncodings(encodings, BhaashikLanguages.getLanguageCode(langauge));
    }//GEN-LAST:event_languageJComboBoxActionPerformed

    private void ssfSrcCorpusJTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_ssfSrcCorpusJTextFieldCaretUpdate
// TODO add your handling code here:
	generateTaskNameAndPropFile();	
    }//GEN-LAST:event_ssfSrcCorpusJTextFieldCaretUpdate

    private void mfeaturesJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mfeaturesJButtonActionPerformed
// TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           mfFile = chooser.getSelectedFile().getAbsolutePath();
           
           if(validateFileName(mfFile).equals("") == false)
           {
               mfeaturesJTextField.setText(mfFile);
           }
        }
    }//GEN-LAST:event_mfeaturesJButtonActionPerformed

    private void posTagsJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_posTagsJButtonActionPerformed
// TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           posTagsFile = chooser.getSelectedFile().getAbsolutePath();
           
           if(validateFileName(posTagsFile).equals("") == false)
           {
               posTagsJTextField.setText(posTagsFile);
           }
        }
    }//GEN-LAST:event_posTagsJButtonActionPerformed

    private void phraseNamesJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phraseNamesJButtonActionPerformed
// TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           phraseNamesFile = chooser.getSelectedFile().getAbsolutePath();
           
           if(validateFileName(phraseNamesFile).equals("") == false)
           {
               phraseNamesJTextField.setText(phraseNamesFile);
           }
        }
    }//GEN-LAST:event_phraseNamesJButtonActionPerformed

    private void OKJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKJButtonActionPerformed
// TODO add your handling code here:
        boolean valid = true;
	
	langauge = (String) languageJComboBox.getSelectedItem();
	encoding = (String) encodingJComboBox.getSelectedItem();
        
        taskPropFile = propJTextField.getText();
        ssfPropFile = ssfPropsJTextField.getText();
        fsPropFile = fsPropsJTextField.getText();
        mfFile = mfeaturesJTextField.getText();

        posTagsFile = posTagsJTextField.getText();
        phraseNamesFile = phraseNamesJTextField.getText();;
        
        ssfSrcCorpusStoryFile = ssfSrcCorpusJTextField.getText();
        ssfTgtCorpusStoryFile = ssfTgtCorpusJTextField.getText();
            
        if(validateTaskPropsFileName() == false || validateFileName(ssfPropFile).equals("") == true
                || validateFileName(mfFile).equals("") == true  || validateFileName(fsPropFile).equals("") == true 
                || validateFileName(ssfSrcCorpusStoryFile).equals("") == true 
                || validateFileName(ssfTgtCorpusStoryFile).equals("") == true
                || validateTaskName() == false)
        {
            valid = false;
        }
                
        if(valid == true)
        {
	    if(standAloneMode == false)
	    {
		AnnotationClient owner = (AnnotationClient) getOwner();
		PropertiesManager pm = owner.getPropertiesManager();

		String taskName = taskNameJTextField.getText();

		PropertiesTable tasks = (PropertiesTable) pm.getPropertyContainer("tasks", PropertyType.PROPERTY_TABLE);

		if(newTask == true)
		{
		    taskList.addElement(taskName);

		    String cols[] = {taskName, taskPropFile, GlobalProperties.getIntlString("UTF-8")};
		    tasks.addRow(cols);

		    try {
			pm.savePropertyContainer(GlobalProperties.getIntlString("tasks"), PropertyType.PROPERTY_TABLE);
		    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, GlobalProperties.getIntlString("Error"), GlobalProperties.getIntlString("Could_not_save_task_list."), JOptionPane.ERROR_MESSAGE);
		    }
		}
		else
		{
		    taskName = (String) taskList.getSelectedItem();
		    Vector vec = tasks.getValues("TaskName", taskName, new String[]{"TaskKVPropFile"});

		    if(vec.size() != 1)
			JOptionPane.showMessageDialog(this, GlobalProperties.getIntlString("Error"), GlobalProperties.getIntlString("Could_not_save_task_information."), JOptionPane.ERROR_MESSAGE);

		    taskPropFile = (String) ((Vector) vec.get(0)).get(0);
		}
	    }
	    else
	    {
		if((new File(taskPropFile)).exists())
		{
		    KeyValueProperties kvp = new KeyValueProperties();

		    try {
			kvp.read(taskPropFile, GlobalProperties.getIntlString("UTF-8"));
		    } catch (FileNotFoundException ex) {
			ex.printStackTrace();
		    } catch (IOException ex) {
			ex.printStackTrace();
		    }

		    taskKVP.addProperty("CurrentPosition", kvp.getPropertyValue("CurrentPosition"));
		}
		else
		    taskKVP.addProperty("CurrentPosition", "1");
	    }

            taskKVP.addProperty("Language", BhaashikLanguages.getLangEncCode(langauge, encoding));
            taskKVP.addProperty("TaskName", taskName);
            taskKVP.addProperty("TaskPropFile", taskPropFile);
            taskKVP.addProperty("TaskPropCharset", GlobalProperties.getIntlString("UTF-8"));
            taskKVP.addProperty("SSFPropFile", ssfPropFile);
            taskKVP.addProperty("SSFPropCharset", GlobalProperties.getIntlString("UTF-8"));
            taskKVP.addProperty("FSPropFile", fsPropFile);
            taskKVP.addProperty("FSPropCharset", GlobalProperties.getIntlString("UTF-8"));
            taskKVP.addProperty("MFeaturesFile", mfFile);
            taskKVP.addProperty("MFeaturesCharset", GlobalProperties.getIntlString("UTF-8"));
            taskKVP.addProperty("SSFSrcCorpusStoryFile", ssfSrcCorpusStoryFile);
            taskKVP.addProperty("SSFCorpusCharset", GlobalProperties.getIntlString("UTF-8"));
            taskKVP.addProperty("SSFTgtCorpusStoryFile", ssfTgtCorpusStoryFile);

            taskKVP.addProperty("POSTagsFile", posTagsFile);
            taskKVP.addProperty("POSTagsCharset", GlobalProperties.getIntlString("UTF-8"));

            taskKVP.addProperty("PhraseNamesFile", phraseNamesFile);
            taskKVP.addProperty("PhraseNamesCharset", GlobalProperties.getIntlString("UTF-8"));
            
            if(newTask == true && standAloneMode == false)
                taskKVP.addProperty("CurrentPosition", "1");

	    if(saveTaskFileJCheckBox.isSelected())
	    {
		try {
			taskKVP.save(taskPropFile, GlobalProperties.getIntlString("UTF-8"));
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		    JOptionPane.showMessageDialog(this, GlobalProperties.getIntlString("Error"), GlobalProperties.getIntlString("Could_not_save_task_information."), JOptionPane.ERROR_MESSAGE);
		}
	    }
        }
        
	if(standAloneMode == false)
	    dialog.setVisible(false);
	else
	{
	    showWorkDialog();
	}
    }//GEN-LAST:event_OKJButtonActionPerformed

    private void ssfTgtCorpusJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ssfTgtCorpusJButtonActionPerformed
// TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           ssfTgtCorpusStoryFile = chooser.getSelectedFile().getAbsolutePath();
           
           if(validateFileName(ssfTgtCorpusStoryFile).equals("") == false)
           {
               ssfTgtCorpusJTextField.setText(ssfTgtCorpusStoryFile);
           }
        }
    }//GEN-LAST:event_ssfTgtCorpusJButtonActionPerformed

    private void ssfSrcCorpusJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ssfSrcCorpusJButtonActionPerformed
// TODO add your handling code here:
	String path = null;

	if(ssfSrcCorpusStoryFile != null)
	{
	    File tfile = new File(ssfSrcCorpusStoryFile);

	    if(tfile.exists())
	    {
		path = tfile.getParentFile().getAbsolutePath();
	    }
	}

	JFileChooser chooser = null;

	if(path != null)
	    chooser = new JFileChooser(path);
	else
	    chooser = new JFileChooser();
	    
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           ssfSrcCorpusStoryFile = chooser.getSelectedFile().getAbsolutePath();
           
           if(validateFileName(ssfSrcCorpusStoryFile).equals("") == false)
           {
               ssfSrcCorpusJTextField.setText(ssfSrcCorpusStoryFile);
	       generateTaskNameAndPropFile();
//	       
//	       File cfile = new File(ssfCorpusStoryFile);
//	       
//	       if(standAloneMode || taskName.equals(""))
//	       {
//		    taskName = cfile.getName();
//		    taskNameJTextField.setText(taskName);
//	       }
//	       
//	       if(standAloneMode || taskPropFile.equals(""))
//	       {
//		    taskPropFile = "task-" + taskName;
//		    File tpfile = new File(cfile.getParent(), taskPropFile);
//		    taskPropFile = tpfile.getAbsolutePath();
//		    propJTextField.setText(taskPropFile);
//	       }
           }
        }
    }//GEN-LAST:event_ssfSrcCorpusJButtonActionPerformed

    private void fsPropsJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fsPropsJButtonActionPerformed
// TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           fsPropFile = chooser.getSelectedFile().getAbsolutePath();
           
           if(validateFileName(fsPropFile).equals("") == false)
           {
               fsPropsJTextField.setText(fsPropFile);
           }
        }
    }//GEN-LAST:event_fsPropsJButtonActionPerformed

    private void ssfPropsJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ssfPropsJButtonActionPerformed
// TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           ssfPropFile = chooser.getSelectedFile().getAbsolutePath();
           
           if(validateFileName(ssfPropFile).equals("") == false)
           {
               ssfPropsJTextField.setText(ssfPropFile);
           }
        }
    }//GEN-LAST:event_ssfPropsJButtonActionPerformed

    private void propertiesJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_propertiesJButtonActionPerformed
// TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
           taskPropFile = chooser.getSelectedFile().getAbsolutePath();
           
           if(validateTaskPropsFileName() == true)
           {
               propJTextField.setText(taskPropFile); 
           }
        }
    }//GEN-LAST:event_propertiesJButtonActionPerformed
    
    private void generateTaskNameAndPropFile()
    {
	String ssfSrcFileStr = ssfSrcCorpusJTextField.getText();
	String ssfTgtFileStr = ssfTgtCorpusJTextField.getText();

	File csfile = new File(ssfSrcFileStr);
	File ctfile = new File(ssfTgtFileStr);
	
	if(csfile.exists() == false)
	    return;
	if(ctfile.exists() == false)
	    return;

	if(standAloneMode || taskName.equals(""))
	{
	    taskName = csfile.getName() + "::" + ctfile.getName();
	    taskNameJTextField.setText(taskName);
	}

	if(standAloneMode || taskPropFile.equals(""))
	{
	    taskPropFile = "task-" + taskName;
	    File tpfile = new File(csfile.getParent(), taskPropFile);
	    taskPropFile = tpfile.getAbsolutePath();
	    propJTextField.setText(taskPropFile);
	}
    }
    
    private boolean validateTaskName()
    {
	if(AnnotationClient.class.isInstance(getOwner()) == false)
	    return true;

	AnnotationClient owner = (AnnotationClient) getOwner();
        PropertiesManager pm = owner.getPropertiesManager();
        
        String taskName = taskNameJTextField.getText();
        
        PropertiesTable tasks = (PropertiesTable) pm.getPropertyContainer("tasks", PropertyType.PROPERTY_TABLE);
        Vector rows = tasks.getRows("TaskName", taskName);
        
        if(rows.size() == 1)
        {
            JOptionPane.showMessageDialog(this, GlobalProperties.getIntlString("Task_name_alresdy_exists:_") + taskName, GlobalProperties.getIntlString("Error"), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }

    private boolean validateTaskPropsFileName()
    {
	if(AnnotationClient.class.isInstance(getOwner()) == false)
	    return true;

        AnnotationClient owner = (AnnotationClient) getOwner();
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
        
        ssfPropFile = taskKVP.getPropertyValue("SSFPropFile");
        fsPropFile = taskKVP.getPropertyValue("FSPropFile");
        mfFile = taskKVP.getPropertyValue("MFeaturesFile");
        ssfSrcCorpusStoryFile = taskKVP.getPropertyValue("SSFSrcCorpusStoryFile");
        ssfTgtCorpusStoryFile = taskKVP.getPropertyValue("SSFTgtCorpusStoryFile");

        posTagsFile = taskKVP.getPropertyValue("POSTagsFile");
        phraseNamesFile = taskKVP.getPropertyValue("PhraseNamesFile");

        propJTextField.setText(taskPropFile);
        ssfPropsJTextField.setText(ssfPropFile);
        fsPropsJTextField.setText(fsPropFile);
        mfeaturesJTextField.setText(mfFile);
        ssfSrcCorpusJTextField.setText(ssfSrcCorpusStoryFile);
        ssfTgtCorpusJTextField.setText(ssfTgtCorpusStoryFile);
        
        posTagsJTextField.setText(posTagsFile);
        phraseNamesJTextField.setText(phraseNamesFile);
    }

    private String validateFileName(String fn)
    {
	if(AnnotationClient.class.isInstance(getOwner()) == false)
	    return fn;

        AnnotationClient owner = (AnnotationClient) getOwner();
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
    
    public void setDefaults()
    {
	langauge = "Hindi";
	encoding = GlobalProperties.getIntlString("UTF-8");
	
	taskName = "";
        taskPropFile = "";
        
        ssfPropFile = GlobalProperties.resolveRelativePath("props/ssf-props.txt");
        fsPropFile = GlobalProperties.resolveRelativePath("props/fs-props.txt");
        mfFile = GlobalProperties.resolveRelativePath("props/fs-mandatory-attribs.txt");

	ssfSrcCorpusStoryFile = "";
        ssfTgtCorpusStoryFile = "";

//        posTagsFile = GlobalProperties.resolveRelativePath("workspace/syn-annotation/pos-tags.txt");
//        phraseNamesFile = GlobalProperties.resolveRelativePath("workspace/syn-annotation/phrase-names.txt");
        posTagsFile = GlobalProperties.resolveRelativePath(SSFNode.getPOSTagsPath("workspace/syn-annotation", BhaashikLanguages.getLangEncCode(langauge, encoding)));
        phraseNamesFile = GlobalProperties.resolveRelativePath(SSFNode.getPhraseNamesPath("workspace/syn-annotation", BhaashikLanguages.getLangEncCode(langauge, encoding)));
	
	languageJComboBox.setSelectedItem(langauge);
	encodingJComboBox.setSelectedItem(encoding);
	
	taskNameJTextField.setText(taskName);
        propJTextField.setText(taskPropFile);
	
        ssfPropsJTextField.setText(ssfPropFile);
        fsPropsJTextField.setText(fsPropFile);
        mfeaturesJTextField.setText(mfFile);
	
        ssfSrcCorpusJTextField.setText(ssfSrcCorpusStoryFile);
        ssfTgtCorpusJTextField.setText(ssfTgtCorpusStoryFile);
        
        posTagsJTextField.setText(posTagsFile);
        phraseNamesJTextField.setText(phraseNamesFile);
    }
    
    public Frame getOwner()
    {
        return owner;
    }
    
    public void setOwner(Frame f)
    {
        owner = (JFrame) f;
    }
    
    public void setDialog(JDialog d)
    {
        dialog = d;
    }
    
    public void getDialog(JDialog d)
    {
        dialog = d;
    }
    
    public void setTaskList(DefaultComboBoxModel l)
    {
        taskList = l;
    }
    
    public KeyValueProperties getTaskProps()
    {
	return taskKVP;
    }
    
    public JDialog getWorkDialog()
    {
	return workDialog;
    }
    
    private void showWorkDialog()
    {
	workDialog = null;
	
	if(dialog != null)
	    workDialog = new JDialog(dialog, taskName, true);
	else
	    workDialog = new JDialog(owner, taskName, true);
        
	ParallelSyntacticAnnotationWorkJPanel workJPanel = new ParallelSyntacticAnnotationWorkJPanel(taskKVP);

        workJPanel.setOwner(this.getOwner());
        workJPanel.setDialog(workDialog);
        workJPanel.setTaskName(taskName);
        workJPanel.configure();
        
        workDialog.add(workJPanel);
	
        int inset = 5;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        workDialog.setBounds(inset, inset,
		screenSize.width  - inset*2,
		screenSize.height - inset*9);

	workDialog.setVisible(true);
	
//	((AnnotationClient) owner).setWorkJPanel(workJPanel);
    }
     
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.!
     */
    private static void createAndShowGUI() {
	
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame(GlobalProperties.getIntlString("Bhaashik_Parallel_Syntactic_Annotation"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	ParallelSyntacticAnnotationTaskSetupJPanel newContentPane = new ParallelSyntacticAnnotationTaskSetupJPanel(true);
        
        newContentPane.setOwner(frame);
	
	newContentPane.owner = frame;
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
//        frame.pack();
	
        int xinset = 280;
        int yinset = 140;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(xinset, yinset,
		screenSize.width  - xinset*2,
		screenSize.height - yinset*2);

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
    public javax.swing.JButton OKJButton;
    public javax.swing.JPanel booleanOptionsJPanel;
    public javax.swing.JPanel commandsJPanel;
    public javax.swing.JComboBox encodingJComboBox;
    public javax.swing.JLabel encodingJLabel;
    public javax.swing.JPanel encodingJPanel;
    public javax.swing.JButton fsPropsJButton;
    public javax.swing.JLabel fsPropsJLabel;
    public javax.swing.JPanel fsPropsJPanel;
    public javax.swing.JTextField fsPropsJTextField;
    public javax.swing.JComboBox languageJComboBox;
    public javax.swing.JLabel languageJLabel;
    public javax.swing.JPanel languageJPanel;
    public javax.swing.JButton mfeaturesJButton;
    public javax.swing.JLabel mfeaturesJLabel;
    public javax.swing.JPanel mfeaturesJPanel;
    public javax.swing.JTextField mfeaturesJTextField;
    public javax.swing.JButton phraseNamesJButton;
    public javax.swing.JLabel phraseNamesJLabel;
    public javax.swing.JPanel phraseNamesJPanel;
    public javax.swing.JTextField phraseNamesJTextField;
    public javax.swing.JButton posTagsJButton;
    public javax.swing.JLabel posTagsJLabel;
    public javax.swing.JPanel posTagsJPanel;
    public javax.swing.JTextField posTagsJTextField;
    public javax.swing.JLabel propJLabel;
    public javax.swing.JPanel propJPanel;
    public javax.swing.JTextField propJTextField;
    public javax.swing.JButton propertiesJButton;
    public javax.swing.JCheckBox saveTaskFileJCheckBox;
    public javax.swing.JPanel ssfCorpusJPanel;
    public javax.swing.JPanel ssfCorpusUTF8JPanel;
    public javax.swing.JButton ssfPropsJButton;
    public javax.swing.JLabel ssfPropsJLabel;
    public javax.swing.JPanel ssfPropsJPanel;
    public javax.swing.JTextField ssfPropsJTextField;
    public javax.swing.JButton ssfSrcCorpusJButton;
    public javax.swing.JLabel ssfSrcCorpusJLabel;
    public javax.swing.JTextField ssfSrcCorpusJTextField;
    public javax.swing.JButton ssfTgtCorpusJButton;
    public javax.swing.JLabel ssfTgtCorpusJLabel;
    public javax.swing.JTextField ssfTgtCorpusJTextField;
    public javax.swing.JPanel taskJPanel;
    public javax.swing.JLabel taskNameJLabel;
    public javax.swing.JTextField taskNameJTextField;
    // End of variables declaration//GEN-END:variables
    
}
