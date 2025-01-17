/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PropbankInfoJPanel.java
 *
 * Created on 4 Apr, 2010, 12:34:24 PM
 */

package bhaashik.corpus.ssf.gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import bhaashik.datastr.ConcurrentLinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;

import bhaashik.GlobalProperties;
import bhaashik.corpus.ssf.features.impl.FSProperties;
import bhaashik.corpus.ssf.features.impl.FeatureStructuresImpl;
import bhaashik.corpus.ssf.tree.SSFNode;
import bhaashik.corpus.ssf.tree.SSFPhrase;
import bhaashik.common.BhaashikClientsStateData;
import bhaashik.common.types.ShortcutType;
import bhaashik.gui.clients.AnnotationClient;
import bhaashik.gui.common.JPanelDialog;
import bhaashik.gui.common.LocalKeyboardShorcutEditorJPanel;
import bhaashik.gui.common.BhaashikJDialog;
import bhaashik.propbank.Frameset;
import bhaashik.propbank.FramesetPredicate;
import bhaashik.propbank.FramesetRoleset;
import bhaashik.propbank.gui.FramesetExamplesJPanel;
import bhaashik.properties.PropertiesTable;
import bhaashik.properties.PropertyTokens;
import bhaashik.table.BhaashikTableModel;
import bhaashik.text.enc.conv.BhaashikEncodingConverter;
import bhaashik.text.enc.conv.UTF82WX;
import bhaashik.common.BhaashikShortcutsData;
import bhaashik.common.types.ClientType;
import bhaashik.propbank.gui.FramesetJPanel;
import bhaashik.properties.KeyValueProperties;
import bhaashik.tree.gui.BhaashikTreeJPanel;
import bhaashik.util.UtilityFunctions;

/**
 *
 * @author anil
 */
public class PropbankInfoJPanel extends javax.swing.JPanel {

    protected JFrame owner;
    protected JDialog dialog;

    protected AnnotationClient annotationClient;
    protected BhaashikTreeJPanel ssfPhraseJPanel;

    protected String navigationWord = "कर";
    protected String navigationTag = "^V";

    protected String wordNavigationFile = "workspace/syn-annotation/word-navigation-list.txt";
    protected String tagNavigationFile = "workspace/syn-annotation/tag-navigation-list.txt";
    protected DefaultComboBoxModel wordNavigationComboBoxModel;
    protected DefaultComboBoxModel tagNavigationComboBoxModel;

    // Only for frameset annotation
    protected Frameset frameset;
    protected FramesetJPanel framesetJPanel;

    protected KeyValueProperties wpos2sposMap; // word position to sentence position mode
    protected KeyValueProperties spos2wposMap; // word position to sentence position mode

    protected String framesetDTD = GlobalProperties.resolveRelativePath("data/propbank/resource/frameset/frameset.dtd");
    protected String framesetAttibVals = GlobalProperties.resolveRelativePath("data/propbank/resource/frameset/attrib-vals.txt");

    protected static Action[] argumentActions;
    protected static ConcurrentLinkedHashMap<String,KeyStroke> argumentActionsKeyMap;

    protected static InputMap customInputMap;
    protected static ActionMap customActionMap;

    protected static BhaashikTableModel shortcutsTable;

    protected String charset = "UTF-8";
    protected String langEnc = "hin::utf8";

    protected BhaashikEncodingConverter converter;

    /** Creates new form PropbankInfoJPanel */
    public PropbankInfoJPanel(AnnotationClient annotationClient, String langEnc) {
        initComponents();

        this.annotationClient = annotationClient;

        this.langEnc = langEnc;
        converter = new UTF82WX(langEnc);

        argumentActionsKeyMap = new ConcurrentLinkedHashMap<String,KeyStroke>();

        customInputMap = new InputMap();
        customActionMap = new ActionMap();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        wordNavigationDetailsJPanel = new javax.swing.JPanel();
        wordNavigationFileJPanel = new javax.swing.JPanel();
        wordNavigationFileJLabel = new javax.swing.JLabel();
        wordNavigationFileJTextField = new javax.swing.JTextField();
        wordNavigationFileJButton = new javax.swing.JButton();
        tagNavigationFileJPanel = new javax.swing.JPanel();
        tagNavigationFileJLabel = new javax.swing.JLabel();
        tagNavigationFileJTextField = new javax.swing.JTextField();
        tagNavigationFileJButton = new javax.swing.JButton();
        wordNavigationSelectionJPanel = new javax.swing.JPanel();
        wordNavigationSelectionJLabel = new javax.swing.JLabel();
        wordNavigationSelectionJComboBox = new javax.swing.JComboBox();
        tagNavigationSelectionJPanel = new javax.swing.JPanel();
        tagNavigationSelectionJLabel = new javax.swing.JLabel();
        tagNavigationSelectionJComboBox = new javax.swing.JComboBox();
        extraInfoJSplitPane = new javax.swing.JSplitPane();
        framesetViewJPanel = new javax.swing.JPanel();
        wordTitleJPanel = new javax.swing.JPanel();
        wordJLabel = new javax.swing.JLabel();
        wordJComboBox = new javax.swing.JComboBox();
        exampleJButton = new javax.swing.JButton();
        wordInfoJButton = new javax.swing.JButton();
        shortcutsJButton = new javax.swing.JButton();
        wordInfoJPanel = new javax.swing.JPanel();
        wordInfoJScrollPane = new javax.swing.JScrollPane();
        wordInfoJTextPane = new javax.swing.JTextPane();
        argumentsViewJPanel = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        wordNavigationDetailsJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Navigation Details"));
        wordNavigationDetailsJPanel.setLayout(new java.awt.GridLayout(0, 1, 0, 3));

        wordNavigationFileJPanel.setLayout(new java.awt.BorderLayout());

        wordNavigationFileJLabel.setText("Word Stem File: ");
        wordNavigationFileJPanel.add(wordNavigationFileJLabel, java.awt.BorderLayout.WEST);

        wordNavigationFileJTextField.setToolTipText("Enter the query to search for data in the document");
        wordNavigationFileJTextField.setPreferredSize(new java.awt.Dimension(200, 25));
        wordNavigationFileJPanel.add(wordNavigationFileJTextField, java.awt.BorderLayout.CENTER);

        wordNavigationFileJButton.setText("Browse");
        wordNavigationFileJButton.setToolTipText("Browse to the file containing the list of words/stems for navigation");
        wordNavigationFileJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wordNavigationFileJButtonActionPerformed(evt);
            }
        });
        wordNavigationFileJPanel.add(wordNavigationFileJButton, java.awt.BorderLayout.EAST);

        wordNavigationDetailsJPanel.add(wordNavigationFileJPanel);

        tagNavigationFileJPanel.setLayout(new java.awt.BorderLayout());

        tagNavigationFileJLabel.setText("Tag File           : ");
        tagNavigationFileJPanel.add(tagNavigationFileJLabel, java.awt.BorderLayout.WEST);

        tagNavigationFileJTextField.setToolTipText("Enter the query to search for data in the document");
        tagNavigationFileJTextField.setPreferredSize(new java.awt.Dimension(200, 25));
        tagNavigationFileJPanel.add(tagNavigationFileJTextField, java.awt.BorderLayout.CENTER);

        tagNavigationFileJButton.setText("Browse");
        tagNavigationFileJButton.setToolTipText("Browse to the file containing the list of words/stems for navigation");
        tagNavigationFileJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tagNavigationFileJButtonActionPerformed(evt);
            }
        });
        tagNavigationFileJPanel.add(tagNavigationFileJButton, java.awt.BorderLayout.EAST);

        wordNavigationDetailsJPanel.add(tagNavigationFileJPanel);

        wordNavigationSelectionJPanel.setLayout(new java.awt.BorderLayout());

        wordNavigationSelectionJLabel.setText("Word Stem: ");
        wordNavigationSelectionJLabel.setToolTipText("Word stem for navigation");
        wordNavigationSelectionJPanel.add(wordNavigationSelectionJLabel, java.awt.BorderLayout.WEST);

        wordNavigationSelectionJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wordNavigationSelectionJComboBoxActionPerformed(evt);
            }
        });
        wordNavigationSelectionJPanel.add(wordNavigationSelectionJComboBox, java.awt.BorderLayout.CENTER);

        wordNavigationDetailsJPanel.add(wordNavigationSelectionJPanel);

        tagNavigationSelectionJPanel.setLayout(new java.awt.BorderLayout());

        tagNavigationSelectionJLabel.setText("Tag           : ");
        tagNavigationSelectionJLabel.setToolTipText("Tag regex for navigation");
        tagNavigationSelectionJPanel.add(tagNavigationSelectionJLabel, java.awt.BorderLayout.WEST);

        tagNavigationSelectionJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tagNavigationSelectionJComboBoxActionPerformed(evt);
            }
        });
        tagNavigationSelectionJPanel.add(tagNavigationSelectionJComboBox, java.awt.BorderLayout.CENTER);

        wordNavigationDetailsJPanel.add(tagNavigationSelectionJPanel);

        add(wordNavigationDetailsJPanel, java.awt.BorderLayout.NORTH);

        extraInfoJSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        extraInfoJSplitPane.setResizeWeight(0.5);
        extraInfoJSplitPane.setOneTouchExpandable(true);

        framesetViewJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Frameset View"));
        framesetViewJPanel.setLayout(new java.awt.BorderLayout());

        wordTitleJPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        wordJLabel.setText("Word: "); // NOI18N
        wordTitleJPanel.add(wordJLabel);

        wordTitleJPanel.add(wordJComboBox);

        exampleJButton.setText("Example"); // NOI18N
        exampleJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exampleJButtonActionPerformed(evt);
            }
        });
        wordTitleJPanel.add(exampleJButton);

        wordInfoJButton.setText("Frameset"); // NOI18N
        wordInfoJButton.setEnabled(false);
        wordInfoJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wordInfoJButtonActionPerformed(evt);
            }
        });
        wordTitleJPanel.add(wordInfoJButton);

        shortcutsJButton.setText("Shortcuts");
        shortcutsJButton.setToolTipText("Edit the keyboard shortcuts");
        shortcutsJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shortcutsJButtonActionPerformed(evt);
            }
        });
        wordTitleJPanel.add(shortcutsJButton);

        framesetViewJPanel.add(wordTitleJPanel, java.awt.BorderLayout.NORTH);

        wordInfoJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Roleset Information"));
        wordInfoJPanel.setLayout(new java.awt.BorderLayout());

        wordInfoJScrollPane.setViewportView(wordInfoJTextPane);

        wordInfoJPanel.add(wordInfoJScrollPane, java.awt.BorderLayout.CENTER);

        framesetViewJPanel.add(wordInfoJPanel, java.awt.BorderLayout.CENTER);

        extraInfoJSplitPane.setTopComponent(framesetViewJPanel);

        argumentsViewJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Arguments View"));
        argumentsViewJPanel.setLayout(new java.awt.GridLayout(0, 4, 4, 4));
        extraInfoJSplitPane.setBottomComponent(argumentsViewJPanel);

        add(extraInfoJSplitPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void wordNavigationFileJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wordNavigationFileJButtonActionPerformed
        // TODO add your handling code here:
        String path = null;

        KeyValueProperties stateKVProps = BhaashikClientsStateData.getSateData().getPropertiesValue(ClientType.SYNTACTIC_ANNOTATION.toString());

        if(wordNavigationFile != null) {
            File sfile = new File(wordNavigationFile);

            if(sfile.exists() && sfile.getParentFile() != null)
                path = sfile.getParent();
            else
                path = stateKVProps.getPropertyValue("WordNavigationFile");
        } else
            path = stateKVProps.getPropertyValue("WordNavigationFile");

        JFileChooser chooser = null;

        if(path != null)
            chooser = new JFileChooser(path);
        else
            chooser = new JFileChooser();

        int returnVal = chooser.showOpenDialog(this);

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            wordNavigationFile = chooser.getSelectedFile().getAbsolutePath();

            initWordNavigationList();

            stateKVProps.addProperty("WordNavigationFile", chooser.getSelectedFile().getParent());
        }
}//GEN-LAST:event_wordNavigationFileJButtonActionPerformed

    private void tagNavigationFileJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tagNavigationFileJButtonActionPerformed
        // TODO add your handling code here:
        String path = null;

        KeyValueProperties stateKVProps = BhaashikClientsStateData.getSateData().getPropertiesValue(ClientType.SYNTACTIC_ANNOTATION.toString());

        if(tagNavigationFile != null) {
            File sfile = new File(tagNavigationFile);

            if(sfile.exists() && sfile.getParentFile() != null)
                path = sfile.getParent();
            else
                path = stateKVProps.getPropertyValue("TagNavigationFile");
        } else
            path = stateKVProps.getPropertyValue("TagNavigationFile");

        JFileChooser chooser = null;

        if(path != null)
            chooser = new JFileChooser(path);
        else
            chooser = new JFileChooser();

        int returnVal = chooser.showOpenDialog(this);

        if(returnVal == JFileChooser.APPROVE_OPTION) {
            tagNavigationFile = chooser.getSelectedFile().getAbsolutePath();

            initTagNavigationList();

            stateKVProps.addProperty("TagNavigationFile", chooser.getSelectedFile().getParent());
        }
}//GEN-LAST:event_tagNavigationFileJButtonActionPerformed

    private void wordNavigationSelectionJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wordNavigationSelectionJComboBoxActionPerformed
        // TODO add your handling code here:
        navigationWord = (String) wordNavigationSelectionJComboBox.getSelectedItem();

        if(annotationClient instanceof SyntacticAnnotationWorkJPanel)
            ((SyntacticAnnotationWorkJPanel) annotationClient).initPropbank();
        else if(annotationClient instanceof SSFCorpusAnalyzerJPanel)
            ((SSFCorpusAnalyzerJPanel) annotationClient).initPropbank();
}//GEN-LAST:event_wordNavigationSelectionJComboBoxActionPerformed

    private void tagNavigationSelectionJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tagNavigationSelectionJComboBoxActionPerformed
        // TODO add your handling code here:
        navigationTag = (String) tagNavigationSelectionJComboBox.getSelectedItem();

        if(annotationClient instanceof SyntacticAnnotationWorkJPanel)
            ((SyntacticAnnotationWorkJPanel) annotationClient).initPropbank();
        else if(annotationClient instanceof SSFCorpusAnalyzerJPanel)
            ((SSFCorpusAnalyzerJPanel) annotationClient).initPropbank();
}//GEN-LAST:event_tagNavigationSelectionJComboBoxActionPerformed

    private void exampleJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exampleJButtonActionPerformed
        // TODO add your handling code here:
        editWordInfoExamples();
}//GEN-LAST:event_exampleJButtonActionPerformed

    private void wordInfoJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wordInfoJButtonActionPerformed
        // TODO add your handling code here:
        editWordInfo();
}//GEN-LAST:event_wordInfoJButtonActionPerformed

    private void shortcutsJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shortcutsJButtonActionPerformed
        // TODO add your handling code here:
        LocalKeyboardShorcutEditorJPanel kePanel = new LocalKeyboardShorcutEditorJPanel(shortcutsTable);
        kePanel.setActions(argumentActions);
        kePanel.setActionKeyMap(argumentActionsKeyMap);
        kePanel.prepareShortcutTable();

        BhaashikJDialog dlg = new BhaashikJDialog((Frame) getOwner(), "Edit Shortcuts", true, kePanel);

        if(annotationClient instanceof SyntacticAnnotationWorkJPanel)
            dlg.addWindowListener(((SyntacticAnnotationWorkJPanel) annotationClient));
        else if(annotationClient instanceof SSFCorpusAnalyzerJPanel)
            dlg.addWindowListener(((SSFCorpusAnalyzerJPanel) annotationClient));

        UtilityFunctions.maxmize(dlg);
        dlg.setVisible(true);

        kePanel.readShortcuts();

        //        JTree jtree = ssfPhraseJPanel.getJTree();

        //        InputMap im = getInputMap();
        //        ActionMap am = getActionMap();

        //        registerKeyboardAction(new ArgumentAction(ssfPhraseJPanel, "ARG0"), "ARG0",
        //                KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false),
        //                JComponent.WHEN_IN_FOCUSED_WINDOW);

        customInputMap = kePanel.getInputMap();
        customActionMap = kePanel.getCustomActionMap();

        argumentActionsKeyMap = kePanel.getActionKeyMap();

        BhaashikShortcutsData.registerShortcuts(this, customActionMap, argumentActionsKeyMap);

        //        Iterator itr = argumentActionsKeyMap.keySet().iterator();
        //
        //        while(itr.hasNext())
        //        {
        //            String actionName = (String) itr.next();
        //            registerKeyboardAction(am.get(actionName), actionName,
        //                    argumentActionsKeyMap.get(actionName),
        //                    JComponent.WHEN_IN_FOCUSED_WINDOW);
        //        }

        //        UtilityFunctions.addAll(getInputMap(), kePanel.getCustomInputMap());
        //        UtilityFunctions.addAll(getActionMap(), kePanel.getCustomActionMap());
        //        UtilityFunctions.addAll(ssfPhraseJPanel.getInputMap(), kePanel.getInputMap());
        //        UtilityFunctions.addAll(jtree.getInputMap(), kePanel.getInputMap());
        //        UtilityFunctions.addAll(jtree.getInputMap(), kePanel.getInputMap());
}//GEN-LAST:event_shortcutsJButtonActionPerformed


    public void initWordNavigationList()
    {
        wordNavigationFileJTextField.setText(wordNavigationFile);

        PropertyTokens navigationPT = new PropertyTokens();

        try {
            navigationPT.read(wordNavigationFile, charset);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SyntacticAnnotationWorkJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SyntacticAnnotationWorkJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        wordNavigationComboBoxModel = PropertyTokens.getListModel(navigationPT);

        wordNavigationSelectionJComboBox.setModel(wordNavigationComboBoxModel);
    }

    public void initTagNavigationList()
    {
        tagNavigationFileJTextField.setText(tagNavigationFile);

        PropertyTokens navigationPT = new PropertyTokens();

        try {
            navigationPT.read(tagNavigationFile, charset);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SyntacticAnnotationWorkJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SyntacticAnnotationWorkJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        tagNavigationComboBoxModel = PropertyTokens.getListModel(navigationPT);

        tagNavigationSelectionJComboBox.setModel(tagNavigationComboBoxModel);
    }

    public void fillArgActions()
    {
        if(argumentsViewJPanel.getComponentCount() == 0)
        {
            FSProperties fsProperties = FeatureStructuresImpl.getFSProperties();
            String values[] = fsProperties.getNonMandatoryAttributeValues("pbank");

            argumentActions = new ArgumentAction[values.length];

            for(int i = 0; i < values.length; i++)
            {
                ArgumentAction argumentAction = new ArgumentAction(ssfPhraseJPanel, values[i], null,
                        GlobalProperties.getIntlString("Set_the_argument_to_") + values[i], 0, null);

                argumentsViewJPanel.add(new JButton(argumentAction));
                argumentActions[i] = argumentAction;
            }
        }
    }

    private void editWordInfoExamples()
    {
        if(frameset.countPredicates() == 0)
            return;

        FramesetPredicate framesetPredicate = frameset.getPredicate(0);

        if(framesetPredicate != null)
        {
            FramesetRoleset framesetRoleset = framesetPredicate.getRoleset(0);

            FramesetExamplesJPanel examplesJPanel = new FramesetExamplesJPanel(langEnc);

            if(owner != null)
                examplesJPanel.setOwner(owner);
            else if(dialog != null)
                examplesJPanel.setDialog(dialog);

            examplesJPanel.setRoleset(framesetRoleset);

            Frameset.readAttributeValues(framesetAttibVals, charset);

            examplesJPanel.init();

            BhaashikJDialog editJDialog = null;

            if(owner != null)
                editJDialog = new BhaashikJDialog(owner, GlobalProperties.getIntlString("Edit_Examples"), true, (JPanelDialog) examplesJPanel);
            else if(dialog != null)
                editJDialog = new BhaashikJDialog(dialog, GlobalProperties.getIntlString("Edit_Examples"), true, (JPanelDialog) examplesJPanel);

            editJDialog.pack();

            int inset = 35;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            editJDialog.setBounds(inset, inset,
            screenSize.width  - inset*5,
            screenSize.height - inset*4);

            UtilityFunctions.centre(editJDialog);

            editJDialog.setVisible(true);
        }
    }

    public void displayWordInfo(String wxLexData)
    {
        setWordInfoText("");

        frameset = new Frameset();

        String wordInfoFilePath = GlobalProperties.resolveRelativePath("data/propbank/resource/frameset/" + wxLexData + ".xml");

        frameset.setFilePath(wordInfoFilePath);
        frameset.setCharset(charset);

        File wordInfoFile = new File(wordInfoFilePath);

        if(wordInfoFile.canRead())
        {
            try
            {
                frameset.read(wordInfoFilePath, charset);
            } catch (UnsupportedEncodingException ex)
            {
                Logger.getLogger(SyntacticAnnotationWorkJPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex)
            {
                Logger.getLogger(SyntacticAnnotationWorkJPanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex)
            {
                Logger.getLogger(SyntacticAnnotationWorkJPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            if(frameset.countPredicates() == 0)
                frameset.addPredicate(new FramesetPredicate());

            FramesetPredicate framesetPredicate = frameset.getPredicate(0);

            if(framesetPredicate != null)
            {
                if(framesetPredicate.countRolesets() == 0)
                    framesetPredicate.addRoleset(new FramesetRoleset());

                FramesetRoleset framesetRoleset = framesetPredicate.getRoleset(0);
                String wordInfo = framesetRoleset.getRolesetInfo();
                setWordInfoText(wordInfo);
            }
        }
        else
        {

        }
    }

    private void editWordInfo()
    {
        framesetJPanel = new FramesetJPanel(frameset, langEnc);

        if(owner != null)
            framesetJPanel.setOwner(owner);
        else if(dialog != null)
            framesetJPanel.setDialog(dialog);

        framesetJPanel.init();

        BhaashikJDialog editJDialog = null;

        if(owner != null)
            editJDialog = new BhaashikJDialog(owner, GlobalProperties.getIntlString("Edit_Frameset"), true, (JPanelDialog) framesetJPanel);
        else if(dialog != null)
            editJDialog = new BhaashikJDialog(dialog, GlobalProperties.getIntlString("Edit_Frameset"), true, (JPanelDialog) framesetJPanel);

    	editJDialog.pack();

        int inset = 35;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        editJDialog.setBounds(inset, inset,
		screenSize.width  - inset*5,
		screenSize.height - inset*4);

    	UtilityFunctions.centre(editJDialog);

        editJDialog.setVisible(true);
    }

    public static void loadShortcuts(AnnotationClient editorInstance) {
        shortcutsTable = BhaashikShortcutsData.getShortcutsData().getPropertiesValue(ShortcutType.FRAMESET_EDITING_INTERFACE.toString());

        if(shortcutsTable.getRowCount() == 0)
            shortcutsTable = BhaashikShortcutsData.getShortcutsTable(argumentActions, customInputMap, customActionMap, argumentActionsKeyMap);

        BhaashikShortcutsData.readShortcuts(shortcutsTable, argumentActions, customInputMap, customActionMap, argumentActionsKeyMap);

        BhaashikShortcutsData.registerShortcuts((JComponent) editorInstance, customActionMap, argumentActionsKeyMap);
    }

    public static void saveShortcuts() {
        if(shortcutsTable == null)
            shortcutsTable = new PropertiesTable(0, 3);

        BhaashikShortcutsData.getShortcutsData().addProperties(ShortcutType.FRAMESET_EDITING_INTERFACE.toString(), shortcutsTable);
        BhaashikShortcutsData.save();
    }

    public void removeAllWordStems()
    {
        wordJComboBox.removeAllItems();
    }

    public void addWordStem(String wxLexData)
    {
        wordJComboBox.addItem(wxLexData);
    }

    public void selectWordStem(String wxLexData)
    {
        wordJComboBox.setSelectedItem(wxLexData);
    }

    public void setWordInfoText(String wxLexData)
    {
        wordInfoJTextPane.setText(wxLexData);
    }

    public void setLangEnc(String langEnc)
    {
        this.langEnc = langEnc;
        UtilityFunctions.setComponentFont(wordInfoJTextPane, langEnc);
        converter = new UTF82WX(langEnc);
    }

    public String getNavigationWord()
    {
        return navigationWord;
    }

    public String getNavigationTag()
    {
        return navigationTag;
    }

    public KeyValueProperties getWPos2SPosMap()
    {
        return wpos2sposMap;
    }

    public void setWPos2SPosMap(KeyValueProperties map)
    {
        wpos2sposMap = map;
    }

    public void prepareSPos2WPosMap()
    {
        spos2wposMap = wpos2sposMap.getReverse();
    }

    public KeyValueProperties getSPos2WPosMap()
    {
        return spos2wposMap;
    }

    public BhaashikEncodingConverter getEncodingConverter()
    {
        return converter;
    }

    public Frame getOwner() {
        return owner;
    }

    public BhaashikTreeJPanel getSSFPhraseJPanel()
    {
        return ssfPhraseJPanel;
    }

    public void setSSFPhraseJPanel(BhaashikTreeJPanel ssfPhraseJPanel)
    {
        this.ssfPhraseJPanel = ssfPhraseJPanel;

        for (int i = 0; i < argumentActions.length; i++) {
            ArgumentAction action = (ArgumentAction) argumentActions[i];

            action.setSSFPhraseJPanel(ssfPhraseJPanel);
        }
    }

    public void setOwner(Frame frame) {
        owner = (JFrame) frame;

        if(annotationClient instanceof SyntacticAnnotationWorkJPanel)
            owner.addWindowListener(((SyntacticAnnotationWorkJPanel) annotationClient));
        else if(annotationClient instanceof SSFCorpusAnalyzerJPanel)
            owner.addWindowListener(((SSFCorpusAnalyzerJPanel) annotationClient));
    }


    private class ArgumentAction extends AbstractAction {

        protected BhaashikTreeJPanel bhaashikTreeJPanel;

        public ArgumentAction(BhaashikTreeJPanel bhaashikTreeJPanel, String text, ImageIcon icon,
                          String desc, Integer mnemonic, KeyStroke acclerator) {
            super(text, icon);

            this.bhaashikTreeJPanel = bhaashikTreeJPanel;

            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
            putValue(ACCELERATOR_KEY, acclerator);
        }

        public ArgumentAction(BhaashikTreeJPanel bhaashikTreeJPanel, String text) {
            super(text);

            this.bhaashikTreeJPanel = bhaashikTreeJPanel;
        }

        public void setSSFPhraseJPanel(BhaashikTreeJPanel bhaashikTreeJPanel)
        {
            this.bhaashikTreeJPanel = bhaashikTreeJPanel;
        }

        public void actionPerformed(ActionEvent e) {

            JTree jtree = bhaashikTreeJPanel.getJTree();
            TreePath currentSelection = jtree.getSelectionPath();

            if (currentSelection != null)
            {
                SSFNode currentNode = (SSFNode) (currentSelection.getLastPathComponent());

                String acommand = e.getActionCommand();

//                if(currentNode instanceof SSFLexItem && currentNode.getAttributeValueString(SSFNode.HIGHLIGHT).equalsIgnoreCase("true"))
                if(currentNode instanceof SSFPhrase && currentNode.getName().equals("NP"))
                {
                    if(acommand.equalsIgnoreCase("ARG-ERASE"))
                        currentNode.removeAttribute("pbank");
                    else if(acommand.equalsIgnoreCase("Other"))
                    {
                        acommand = JOptionPane.showInputDialog(GlobalProperties.getIntlString("Please_enter_the_attribute_value"), "");

                        if(acommand != null)
                        {
                            currentNode.setAttributeValue("pbank", acommand);
                        } 
                    }
                    else
                        currentNode.setAttributeValue("pbank", acommand);

                    bhaashikTreeJPanel.editTreeNode(null);
                    jtree.updateUI();
                }
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel argumentsViewJPanel;
    private javax.swing.JButton exampleJButton;
    private javax.swing.JSplitPane extraInfoJSplitPane;
    private javax.swing.JPanel framesetViewJPanel;
    private javax.swing.JButton shortcutsJButton;
    private javax.swing.JButton tagNavigationFileJButton;
    private javax.swing.JLabel tagNavigationFileJLabel;
    private javax.swing.JPanel tagNavigationFileJPanel;
    private javax.swing.JTextField tagNavigationFileJTextField;
    private javax.swing.JComboBox tagNavigationSelectionJComboBox;
    private javax.swing.JLabel tagNavigationSelectionJLabel;
    private javax.swing.JPanel tagNavigationSelectionJPanel;
    private javax.swing.JButton wordInfoJButton;
    private javax.swing.JPanel wordInfoJPanel;
    private javax.swing.JScrollPane wordInfoJScrollPane;
    private javax.swing.JTextPane wordInfoJTextPane;
    private javax.swing.JComboBox wordJComboBox;
    private javax.swing.JLabel wordJLabel;
    private javax.swing.JPanel wordNavigationDetailsJPanel;
    private javax.swing.JButton wordNavigationFileJButton;
    private javax.swing.JLabel wordNavigationFileJLabel;
    private javax.swing.JPanel wordNavigationFileJPanel;
    private javax.swing.JTextField wordNavigationFileJTextField;
    private javax.swing.JComboBox wordNavigationSelectionJComboBox;
    private javax.swing.JLabel wordNavigationSelectionJLabel;
    private javax.swing.JPanel wordNavigationSelectionJPanel;
    private javax.swing.JPanel wordTitleJPanel;
    // End of variables declaration//GEN-END:variables

}
