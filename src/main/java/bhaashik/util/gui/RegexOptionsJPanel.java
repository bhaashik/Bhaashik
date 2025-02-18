/*
 * RegexOptionsJPanel.java
 *
 * Created on May 18, 2006, 2:58 PM
 */

package bhaashik.util.gui;

import java.awt.*;
import javax.swing.*;
import java.util.regex.*;

import bhaashik.util.UtilityFunctions;

/**
 *
 * @author  anil
 */
public class RegexOptionsJPanel extends javax.swing.JPanel {
    
    protected JDialog dialog;
    protected int regexFlags;
    
    /** Creates new form RegexOptionsJPanel */
    public RegexOptionsJPanel() {
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
        literalCaseJCheckBox = new javax.swing.JCheckBox();
        matchCaseJCheckBox = new javax.swing.JCheckBox();
        multilineJCheckBox = new javax.swing.JCheckBox();
        unixLinesJCheckBox = new javax.swing.JCheckBox();
        dotallJCheckBox = new javax.swing.JCheckBox();
        unicodeCaseJCheckBox = new javax.swing.JCheckBox();
        canonicalEqJCheckBox = new javax.swing.JCheckBox();
        dummyJPanel = new javax.swing.JPanel();
        commandsJPanel = new javax.swing.JPanel();
        okJButton = new javax.swing.JButton();
        cancelJButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        optionsJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Java Regular Expression Options", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12))); // NOI18N
        optionsJPanel.setLayout(new java.awt.GridLayout(0, 1));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bhaashik"); // NOI18N
        literalCaseJCheckBox.setText(bundle.getString("Literal_parsing_of_pattern")); // NOI18N
        literalCaseJCheckBox.setToolTipText(bundle.getString("Literal_parsing_of_pattern")); // NOI18N
        literalCaseJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        literalCaseJCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        optionsJPanel.add(literalCaseJCheckBox);

        matchCaseJCheckBox.setSelected(true);
        matchCaseJCheckBox.setText(bundle.getString("Match_case")); // NOI18N
        matchCaseJCheckBox.setToolTipText(bundle.getString("Match_case")); // NOI18N
        matchCaseJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        matchCaseJCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        optionsJPanel.add(matchCaseJCheckBox);

        multilineJCheckBox.setText(bundle.getString("Multiline")); // NOI18N
        multilineJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        multilineJCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        optionsJPanel.add(multilineJCheckBox);

        unixLinesJCheckBox.setText(bundle.getString("Unix_lines")); // NOI18N
        unixLinesJCheckBox.setToolTipText(bundle.getString("Enables_Unix_lines_mode")); // NOI18N
        unixLinesJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        unixLinesJCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        optionsJPanel.add(unixLinesJCheckBox);

        dotallJCheckBox.setText(bundle.getString("Dotall_(any_character_to_include_line_terminator)")); // NOI18N
        dotallJCheckBox.setToolTipText(bundle.getString("Dotall_(any_character_to_include_line_terminator)")); // NOI18N
        dotallJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        dotallJCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        optionsJPanel.add(dotallJCheckBox);

        unicodeCaseJCheckBox.setText(bundle.getString("Unicode_case")); // NOI18N
        unicodeCaseJCheckBox.setToolTipText(bundle.getString("Enables_Unicode-aware_case_folding")); // NOI18N
        unicodeCaseJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        unicodeCaseJCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        optionsJPanel.add(unicodeCaseJCheckBox);

        canonicalEqJCheckBox.setText(bundle.getString("Canonical_equivalence")); // NOI18N
        canonicalEqJCheckBox.setToolTipText(bundle.getString("Enables_canonical_equivalence")); // NOI18N
        canonicalEqJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        canonicalEqJCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        optionsJPanel.add(canonicalEqJCheckBox);

        add(optionsJPanel, java.awt.BorderLayout.NORTH);

        javax.swing.GroupLayout dummyJPanelLayout = new javax.swing.GroupLayout(dummyJPanel);
        dummyJPanel.setLayout(dummyJPanelLayout);
        dummyJPanelLayout.setHorizontalGroup(
            dummyJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );
        dummyJPanelLayout.setVerticalGroup(
            dummyJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        add(dummyJPanel, java.awt.BorderLayout.CENTER);

        commandsJPanel.setLayout(new java.awt.GridLayout(1, 0, 4, 0));

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

    private void cancelJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelJButtonActionPerformed
// TODO add your handling code here:
	regexFlags = 0;
	dialog.setVisible(false);
    }//GEN-LAST:event_cancelJButtonActionPerformed

    private void okJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okJButtonActionPerformed
// TODO add your handling code here:
	regexFlags = 0;
	
	if(literalCaseJCheckBox.isSelected())
	    regexFlags |= Pattern.LITERAL;

	if(matchCaseJCheckBox.isSelected() == false)
	    regexFlags |= Pattern.CASE_INSENSITIVE;

	if(multilineJCheckBox.isSelected())
	    regexFlags |= Pattern.MULTILINE;

	if(unixLinesJCheckBox.isSelected())
	    regexFlags |= Pattern.UNIX_LINES;

	if(dotallJCheckBox.isSelected())
	    regexFlags |= Pattern.DOTALL;

	if(unicodeCaseJCheckBox.isSelected())
	    regexFlags |= Pattern.UNICODE_CASE;

	if(canonicalEqJCheckBox.isSelected())
	    regexFlags |= Pattern.CANON_EQ;
	
	dialog.setVisible(false);	
    }//GEN-LAST:event_okJButtonActionPerformed
    
    public long getRegexFlags()
    {
	return regexFlags;
    }

    public static JDialog showDialog(Frame owner, String title, boolean modal)
    {
	RegexOptionsJPanel panel = new RegexOptionsJPanel();
	JDialog dialog = new LocalDialog(owner, title, modal, panel);
	
	dialog.pack();
	
	UtilityFunctions.centre(dialog);

	dialog.setVisible(true);
	
	return dialog;
    }
    
    public static JDialog showDialog(Dialog owner, String title, boolean modal)
    {
	RegexOptionsJPanel panel = new RegexOptionsJPanel();
	JDialog dialog = new LocalDialog(owner, title, modal, panel);
	
	dialog.pack();
	
	UtilityFunctions.centre(dialog);

	dialog.setVisible(true);
	
	return dialog;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton cancelJButton;
    protected javax.swing.JCheckBox canonicalEqJCheckBox;
    protected javax.swing.JPanel commandsJPanel;
    protected javax.swing.JCheckBox dotallJCheckBox;
    protected javax.swing.JPanel dummyJPanel;
    protected javax.swing.JCheckBox literalCaseJCheckBox;
    protected javax.swing.JCheckBox matchCaseJCheckBox;
    protected javax.swing.JCheckBox multilineJCheckBox;
    protected javax.swing.JButton okJButton;
    protected javax.swing.JPanel optionsJPanel;
    protected javax.swing.JCheckBox unicodeCaseJCheckBox;
    protected javax.swing.JCheckBox unixLinesJCheckBox;
    // End of variables declaration//GEN-END:variables
 
    public static class LocalDialog extends JDialog
    {
	protected RegexOptionsJPanel panel;
	
	public LocalDialog()
	{
	    super();
	}

	public LocalDialog(Dialog owner, String title, boolean modal, RegexOptionsJPanel pnl)
	{
	    super(owner, title, modal);
	    panel = pnl;
	    panel.dialog = this;
	    add(panel);
	}

	public LocalDialog(Frame owner, String title, boolean modal, RegexOptionsJPanel pnl)
	{
	    super(owner, title, modal);
	    panel = pnl;
	    panel.dialog = this;
	    add(panel);
	}
    
	public long getRegexFlags()
	{
	    return panel.getRegexFlags();
	}
    };
}
