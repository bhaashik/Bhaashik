/*
 * SSFAnnotationLevelsJPanel.java
 *
 * Created on April 19, 2006, 8:37 PM
 */

package bhaashik.corpus.ssf.gui;

import java.awt.*;
import javax.swing.*;

import bhaashik.corpus.ssf.SSFCorpus;
import bhaashik.util.UtilityFunctions;

/**
 *
 * @author  anil
 */
public class SSFAnnotationLevelsJPanel extends javax.swing.JPanel {

    protected JDialog dialog;
    protected long annotationLevelsFlag;
    
    /** Creates new form SSFAnnotationLevelsJPanel */
    public SSFAnnotationLevelsJPanel() {
	initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        optionsJPanel = new javax.swing.JPanel();
        posTagsJCheckBox = new javax.swing.JCheckBox();
        chunkNamesJCheckBox = new javax.swing.JCheckBox();
        chunksJCheckBox = new javax.swing.JCheckBox();
        lexMFAJCheckBox = new javax.swing.JCheckBox();
        lexEFAJCheckBox = new javax.swing.JCheckBox();
        chunkMFAJCheckBox = new javax.swing.JCheckBox();
        chunkEFAJCheckBox = new javax.swing.JCheckBox();
        allExceptFirstFSJCheckBox = new javax.swing.JCheckBox();
        pruneFSJCheckBox = new javax.swing.JCheckBox();
        commentsJCheckBox = new javax.swing.JCheckBox();
        overallJCheckBox = new javax.swing.JCheckBox();
        commandsJPanel = new javax.swing.JPanel();
        okJButton = new javax.swing.JButton();
        cancelJButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        optionsJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("SSF Annotation Levels"));
        optionsJPanel.setLayout(new java.awt.GridLayout(0, 1, 0, 4));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bhaashik"); // NOI18N
        posTagsJCheckBox.setText(bundle.getString("POS_tags")); // NOI18N
        posTagsJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        optionsJPanel.add(posTagsJCheckBox);

        chunkNamesJCheckBox.setText(bundle.getString("Chunk_names")); // NOI18N
        chunkNamesJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        optionsJPanel.add(chunkNamesJCheckBox);

        chunksJCheckBox.setText(bundle.getString("Chunks")); // NOI18N
        chunksJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        optionsJPanel.add(chunksJCheckBox);

        lexMFAJCheckBox.setText(bundle.getString("Mandatory_attributes_of_word")); // NOI18N
        lexMFAJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        optionsJPanel.add(lexMFAJCheckBox);

        lexEFAJCheckBox.setText(bundle.getString("Non-mandatory_attributes_of_word")); // NOI18N
        lexEFAJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        optionsJPanel.add(lexEFAJCheckBox);

        chunkMFAJCheckBox.setText(bundle.getString("Mandatory_attributes_of_chunks")); // NOI18N
        chunkMFAJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        optionsJPanel.add(chunkMFAJCheckBox);

        chunkEFAJCheckBox.setText(bundle.getString("Non-mandatory_attributes_of_chunks")); // NOI18N
        chunkEFAJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        optionsJPanel.add(chunkEFAJCheckBox);

        allExceptFirstFSJCheckBox.setText(bundle.getString("All_except_the_first_feature_structure")); // NOI18N
        allExceptFirstFSJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        optionsJPanel.add(allExceptFirstFSJCheckBox);

        pruneFSJCheckBox.setText(bundle.getString("Prune_the_feature_structures")); // NOI18N
        pruneFSJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        optionsJPanel.add(pruneFSJCheckBox);

        commentsJCheckBox.setText(bundle.getString("Comments")); // NOI18N
        commentsJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        optionsJPanel.add(commentsJCheckBox);

        overallJCheckBox.setText(bundle.getString("Overall_annotation")); // NOI18N
        overallJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        overallJCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                overallJCheckBoxActionPerformed(evt);
            }
        });
        optionsJPanel.add(overallJCheckBox);

        add(optionsJPanel, java.awt.BorderLayout.CENTER);

        okJButton.setText(bundle.getString("OK")); // NOI18N
        okJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okJButtonActionPerformed(evt);
            }
        });
        commandsJPanel.add(okJButton);

        cancelJButton.setText(bundle.getString("Cancel")); // NOI18N
        cancelJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelJButtonActionPerformed(evt);
            }
        });
        commandsJPanel.add(cancelJButton);

        add(commandsJPanel, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void overallJCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_overallJCheckBoxActionPerformed
// TODO add your handling code here:
	if(overallJCheckBox.isSelected())
	{
	    posTagsJCheckBox.setSelected(true);
	    chunkNamesJCheckBox.setSelected(true);
	    chunksJCheckBox.setSelected(true);
	    lexMFAJCheckBox.setSelected(true);
	    lexEFAJCheckBox.setSelected(true);
	    chunkMFAJCheckBox.setSelected(true);
	    chunkEFAJCheckBox.setSelected(true);
        allExceptFirstFSJCheckBox.setSelected(false);
        pruneFSJCheckBox.setSelected(true);
	    commentsJCheckBox.setSelected(true);
	}
    }//GEN-LAST:event_overallJCheckBoxActionPerformed

    private void cancelJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelJButtonActionPerformed
// TODO add your handling code here:
	annotationLevelsFlag = SSFCorpus.NONE;
	dialog.setVisible(false);
    }//GEN-LAST:event_cancelJButtonActionPerformed

    private void okJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okJButtonActionPerformed
// TODO add your handling code here:
	annotationLevelsFlag = 0;
	
	if(overallJCheckBox.isSelected())
	    annotationLevelsFlag |= SSFCorpus.OVERALL_ANNOTATION;
	else
	{
	    if(posTagsJCheckBox.isSelected())
		annotationLevelsFlag |= SSFCorpus.POS_TAGS;
	    
	    if(chunkNamesJCheckBox.isSelected())
		annotationLevelsFlag |= SSFCorpus.CHUNK_NAMES;
	    
	    if(chunksJCheckBox.isSelected())
		annotationLevelsFlag |= SSFCorpus.CHUNKS;
	    
	    if(lexMFAJCheckBox.isSelected())
		annotationLevelsFlag |= SSFCorpus.LEX_MANDATORY_ATTRIBUTES;
	    
	    if(lexEFAJCheckBox.isSelected())
		annotationLevelsFlag |= SSFCorpus.LEX_EXTRA_ATTRIBUTES;
	    
	    if(chunkMFAJCheckBox.isSelected())
		annotationLevelsFlag |= SSFCorpus.CHUNK_MANDATORY_ATTRIBUTES;
	    
	    if(chunkEFAJCheckBox.isSelected())
		annotationLevelsFlag |= SSFCorpus.CHUNK_EXTRA_ATTRIBUTES;
	    
        if(allExceptFirstFSJCheckBox.isSelected())
		annotationLevelsFlag |= SSFCorpus.ALL_EXCEPT_THE_FIRST_FS;

        if(pruneFSJCheckBox.isSelected())
		annotationLevelsFlag |= SSFCorpus.PRUNE_THE_FS;
	    
	    if(commentsJCheckBox.isSelected())
		annotationLevelsFlag |= SSFCorpus.COMMENTS;
	}
	
	dialog.setVisible(false);
    }//GEN-LAST:event_okJButtonActionPerformed
    
    public long getAnnotationLevelsFlag()
    {
	return annotationLevelsFlag;
    }

    public static JDialog showDialog(Frame owner, String title, boolean modal)
    {
	SSFAnnotationLevelsJPanel panel = new SSFAnnotationLevelsJPanel();
	JDialog dialog = new LocalDialog(owner, title, modal, panel);
	
	dialog.pack();
	
	UtilityFunctions.centre(dialog);

	dialog.setVisible(true);
	
	return dialog;
    }
    
    public static JDialog showDialog(Dialog owner, String title, boolean modal)
    {
	SSFAnnotationLevelsJPanel panel = new SSFAnnotationLevelsJPanel();
	JDialog dialog = new LocalDialog(owner, title, modal, panel);
	
	dialog.pack();
	
	UtilityFunctions.centre(dialog);

	dialog.setVisible(true);
	
	return dialog;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JCheckBox allExceptFirstFSJCheckBox;
    protected javax.swing.JButton cancelJButton;
    protected javax.swing.JCheckBox chunkEFAJCheckBox;
    protected javax.swing.JCheckBox chunkMFAJCheckBox;
    protected javax.swing.JCheckBox chunkNamesJCheckBox;
    protected javax.swing.JCheckBox chunksJCheckBox;
    protected javax.swing.JPanel commandsJPanel;
    protected javax.swing.JCheckBox commentsJCheckBox;
    protected javax.swing.JCheckBox lexEFAJCheckBox;
    protected javax.swing.JCheckBox lexMFAJCheckBox;
    protected javax.swing.JButton okJButton;
    protected javax.swing.JPanel optionsJPanel;
    protected javax.swing.JCheckBox overallJCheckBox;
    protected javax.swing.JCheckBox posTagsJCheckBox;
    protected javax.swing.JCheckBox pruneFSJCheckBox;
    // End of variables declaration//GEN-END:variables
 
    public static class LocalDialog extends JDialog
    {
	protected SSFAnnotationLevelsJPanel panel;
	
	public LocalDialog()
	{
	    super();
	}

	public LocalDialog(Dialog owner, String title, boolean modal, SSFAnnotationLevelsJPanel pnl)
	{
	    super(owner, title, modal);
	    panel = pnl;
	    panel.dialog = this;
	    add(panel);
	}

	public LocalDialog(Frame owner, String title, boolean modal, SSFAnnotationLevelsJPanel pnl)
	{
	    super(owner, title, modal);
	    panel = pnl;
	    panel.dialog = this;
	    add(panel);
	}
    
	public long getAnnotationLevelsFlag()
	{
	    return panel.getAnnotationLevelsFlag();
	}
    };
}
