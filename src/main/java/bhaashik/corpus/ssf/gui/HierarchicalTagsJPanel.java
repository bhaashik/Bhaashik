/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * HierarchicalTagsJPanel.java
 *
 * Created on 5 Jan, 2011, 8:44:18 AM
 */

package bhaashik.corpus.ssf.gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import bhaashik.datastr.ConcurrentLinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import bhaashik.GlobalProperties;
import bhaashik.corpus.ssf.tree.SSFNode;
import bhaashik.common.types.CorpusType;
import bhaashik.gui.common.JPanelDialog;
import bhaashik.properties.KeyValueProperties;
import bhaashik.properties.PropertyTokens;
import bhaashik.util.UtilityFunctions;

/**
 *
 * @author anil
 */
public class HierarchicalTagsJPanel extends javax.swing.JPanel implements JPanelDialog {

    private JDialog dialog;

    protected CorpusType corpusType = CorpusType.SSF_FORMAT;

    protected KeyValueProperties tagLevelMap;
    protected ConcurrentLinkedHashMap<String, JComboBox> tagLevelJComboBoxMap;
    protected PropertyTokens tags;

    protected DefaultComboBoxModel tagTypes;

    protected boolean noUI;
    protected String tagType = "Phrase Names";

    protected String tagLevelMapPath;
    protected String tagsPath;

    protected String langEnc;
    protected String charset;

    public static final String HIERARCHICAL_TAGS_SEPARATOR = "__";

    public static final String POS_TAGS = "POS Tags";
    public static final String PHRASE_NAMES = "Phrase Names";

    /** Creates new form HierarchicalTagsJPanel */
    public HierarchicalTagsJPanel(String langEnc, String charset, boolean noUI) {
        initComponents();

        this.langEnc = langEnc;
        this.charset = charset;

        tagTypes = new DefaultComboBoxModel();

        tagTypes.addElement(POS_TAGS);
        tagTypes.addElement(PHRASE_NAMES);

        tagTypeJComboBox.setModel(tagTypes);

        this.noUI = noUI;

        loadTags(noUI, corpusType, null, null);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tagTypeJPanel = new javax.swing.JPanel();
        tagTypeJLabel = new javax.swing.JLabel();
        tagTypeJComboBox = new javax.swing.JComboBox();
        tagsJScrollPane = new javax.swing.JScrollPane();
        tagsJPanel = new javax.swing.JPanel();
        commandsJPanel = new javax.swing.JPanel();
        okJButton = new javax.swing.JButton();
        cancelJButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        tagTypeJLabel.setText("Tag Type: ");
        tagTypeJPanel.add(tagTypeJLabel);

        tagTypeJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "POS Tags", "Phrase Names" }));
        tagTypeJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tagTypeJComboBoxActionPerformed(evt);
            }
        });
        tagTypeJPanel.add(tagTypeJComboBox);

        add(tagTypeJPanel, java.awt.BorderLayout.PAGE_START);

        tagsJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Select the tag level for each category"));
        tagsJPanel.setLayout(new java.awt.GridLayout(0, 2, 10, 4));
        tagsJScrollPane.setViewportView(tagsJPanel);

        add(tagsJScrollPane, java.awt.BorderLayout.CENTER);

        okJButton.setText("OK");
        okJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okJButtonActionPerformed(evt);
            }
        });
        commandsJPanel.add(okJButton);

        cancelJButton.setText("Cancel");
        cancelJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelJButtonActionPerformed(evt);
            }
        });
        commandsJPanel.add(cancelJButton);

        add(commandsJPanel, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void tagTypeJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tagTypeJComboBoxActionPerformed
        // TODO add your handling code here:
        loadTags(noUI, corpusType, null, null);
    }//GEN-LAST:event_tagTypeJComboBoxActionPerformed

    private void okJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okJButtonActionPerformed
        // TODO add your handling code here:
        save();

        if(dialog != null)
            dialog.setVisible(false);
    }//GEN-LAST:event_okJButtonActionPerformed

    private void cancelJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelJButtonActionPerformed
        // TODO add your handling code here:
        if(dialog != null)
            dialog.setVisible(false); 
    }//GEN-LAST:event_cancelJButtonActionPerformed

    private void loadTags(boolean noUI, CorpusType corpusType, String tpath, String ttype)
    {
        tagsJPanel.removeAll();
        tagLevelJComboBoxMap = new ConcurrentLinkedHashMap<String, JComboBox>();

        if(ttype == null)
            tagType = (String) tagTypeJComboBox.getSelectedItem();
        else
            tagType = ttype;

        if(tagType.equals(POS_TAGS))
        {
            if(tpath == null)
            {
                tagsPath = SSFNode.getPOSTagsPath("workspace/syn-annotation", langEnc);
            }
            else
            {
                tagsPath = tpath;
            }
            
            tagLevelMapPath = SSFNode.getPOSTagLevelsMapPath("workspace/syn-annotation", langEnc);
        }
        else if(tagType.equals(PHRASE_NAMES))
        {
            if(tpath == null)
            {
                tagsPath = SSFNode.getPhraseNamesPath("workspace/syn-annotation", langEnc);
            }
            else
            {
                tagsPath = tpath;
            }

            tagLevelMapPath = SSFNode.getPhraseNamesLevelsMapPath("workspace/syn-annotation", langEnc);
        }

        try {
            tags = new PropertyTokens(tagsPath, charset);
            tagLevelMap = new KeyValueProperties(tagLevelMapPath, charset);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HierarchicalTagsJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HierarchicalTagsJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        if(noUI)
            return;

        if(areTagsHierarchical(tags) == false)
        {
            if(dialog != null)
                JOptionPane.showMessageDialog(dialog, "Current tags of type " + tagType + " are not hierarchical.", GlobalProperties.getIntlString("Error"), JOptionPane.ERROR_MESSAGE);

            refresh();

            return;
        }

        int count = tags.countTokens();

        for (int i = 0; i < count; i++)
        {
            String tag = tags.getToken(i);

            JLabel tagLabel = new JLabel();
            tagLabel.setText(tag);

            JComboBox tagLevelJComboBox = new JComboBox();

            DefaultComboBoxModel tagLevels = new DefaultComboBoxModel();

            String levels[] = tag.split(HIERARCHICAL_TAGS_SEPARATOR);

            for (int j = 0; j < levels.length; j++) {
                String level = levels[j];
                tagLevels.addElement((j + 1) + "::" + level);
            }

            tagLevelJComboBox.setModel(tagLevels);

            tagsJPanel.add(tagLabel);
            tagsJPanel.add(tagLevelJComboBox);

            tagLevelJComboBoxMap.put(tag, tagLevelJComboBox);

            String selLevel = tagLevelMap.getPropertyValue(tag);

            if(selLevel != null)
            {
                String parts[] = selLevel.split("::");

                if(parts.length == 2)
                {
                    int levelIndex = Integer.parseInt(parts[0]);
                    tagLevelJComboBox.setSelectedIndex(levelIndex - 1);
                }
            }
            else
            {
                tagLevelJComboBox.setSelectedIndex(levels.length - 1);
            }
        }

        refresh();
    }

    private void refresh()
    {

        if(dialog != null)
        {
            dialog.setSize(300, 600);
            UtilityFunctions.centre(dialog);
        }
    }

    public static boolean areTagsHierarchical(PropertyTokens tags)
    {
        int count = tags.countTokens();

        for (int i = 0; i < count; i++)
        {
            String tag = tags.getToken(i);

            String levels[] = tag.split(HIERARCHICAL_TAGS_SEPARATOR);
            
            if(levels.length > 1)
                return true;
        }

        return false;
    }

    private void save()
    {
        if(tags == null && tagLevelMap == null)
            return;

        int count = tags.countTokens();

        for (int i = 0; i < count; i++)
        {
            String tag = tags.getToken(i);

            JComboBox tagLevelJComboBox = tagLevelJComboBoxMap.get(tag);

            String level = (String) tagLevelJComboBox.getSelectedItem();
            int levelIndex = tagLevelJComboBox.getSelectedIndex();

            String parts[] = level.split("::");

            if(parts.length == 2)
                tagLevelMap.addProperty(tag, level);
            else if(parts.length == 1)
                tagLevelMap.addProperty(tag, (levelIndex + 1) + parts[0]);
        }
        
        try {
            tagLevelMap.save(tagLevelMapPath, charset);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HierarchicalTagsJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(HierarchicalTagsJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static PropertyTokens getTags(String type, CorpusType corpusType, String path, String langEnc, String cs)
    {
        HierarchicalTagsJPanel hierarchicalTagsJPanel = new HierarchicalTagsJPanel(langEnc, cs, true);

        hierarchicalTagsJPanel.loadTags(true, corpusType, path, type);

        KeyValueProperties tagLevelMap = hierarchicalTagsJPanel.tagLevelMap;

        PropertyTokens tagsPT = new PropertyTokens(tagLevelMap.countProperties());

        if(tagLevelMap == null || tagLevelMap.countProperties() != hierarchicalTagsJPanel.tags.countTokens())
            tagsPT = hierarchicalTagsJPanel.tags;

        Iterator<String> itr = tagLevelMap.getPropertyKeys();

        while(itr.hasNext())
        {
            String tag = itr.next();
            String levels[] = tag.split(HIERARCHICAL_TAGS_SEPARATOR);

            String level = tagLevelMap.getPropertyValue(tag);

            String parts[] = level.split("::");

            if(parts.length == 2)
            {
                int levelIndex = Integer.parseInt(parts[0]);

                String htag = "";

                for (int i = 0; i < levelIndex; i++) {

                    if(i == levelIndex - 1)
                        htag += levels[i];
                    else
                        htag += levels[i] + HIERARCHICAL_TAGS_SEPARATOR;
                }

                tagsPT.addToken(htag);
            }
        }

        return tagsPT;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelJButton;
    private javax.swing.JPanel commandsJPanel;
    private javax.swing.JButton okJButton;
    private javax.swing.JComboBox tagTypeJComboBox;
    private javax.swing.JLabel tagTypeJLabel;
    private javax.swing.JPanel tagTypeJPanel;
    private javax.swing.JPanel tagsJPanel;
    private javax.swing.JScrollPane tagsJScrollPane;
    // End of variables declaration//GEN-END:variables

    public void setDialog(JDialog dialog) {
        this.dialog = dialog;
    }

}
