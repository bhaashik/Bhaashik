/*
 * DSFWorkJPanel.java
 *
 * Created on October 8, 2006, 7:52 PM
 */

package bhaashik.resources.simpledsf.gui;

/**
 *
 * @author  anil
 */
public class SimpleDSFWorkJPanel extends javax.swing.JPanel {
    
    /** Creates new form DSFWorkJPanel */
    public SimpleDSFWorkJPanel() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        workJPanel = new javax.swing.JPanel();
        bottomJPanel = new javax.swing.JPanel();
        positionJPanel = new javax.swing.JPanel();
        senNumJPanel = new javax.swing.JPanel();
        positionJLabel = new javax.swing.JLabel();
        positionJComboBox = new javax.swing.JComboBox();
        textEditCheckBox = new javax.swing.JCheckBox();
        nestedFSJCheckBox = new javax.swing.JCheckBox();
        buttonsJPanel = new javax.swing.JPanel();
        zoomInJButton = new javax.swing.JButton();
        zoomOutJButton = new javax.swing.JButton();
        showUTF8JButton = new javax.swing.JButton();
        commandsJPanel = new javax.swing.JPanel();
        navigateJPanel = new javax.swing.JPanel();
        firstJButton = new javax.swing.JButton();
        prevJButton = new javax.swing.JButton();
        nextJButton = new javax.swing.JButton();
        lastJButton = new javax.swing.JButton();
        mainCommandsJPanel = new javax.swing.JPanel();
        resetJButton = new javax.swing.JButton();
        saveAsJButton = new javax.swing.JButton();
        saveJButton = new javax.swing.JButton();
        moreJButton = new javax.swing.JButton();
        extraCommandsJPanel = new javax.swing.JPanel();
        clearAllJButton = new javax.swing.JButton();
        clearJButton = new javax.swing.JButton();
        resetAllJButton = new javax.swing.JButton();
        editTextJButton = new javax.swing.JButton();
        sentenceJoinJButton = new javax.swing.JButton();
        sentenceSplitJButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout workJPanelLayout = new javax.swing.GroupLayout(workJPanel);
        workJPanel.setLayout(workJPanelLayout);
        workJPanelLayout.setHorizontalGroup(
            workJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1498, Short.MAX_VALUE)
        );
        workJPanelLayout.setVerticalGroup(
            workJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );

        add(workJPanel, java.awt.BorderLayout.CENTER);

        bottomJPanel.setLayout(new java.awt.BorderLayout());

        positionJPanel.setPreferredSize(new java.awt.Dimension(195, 25));
        positionJPanel.setLayout(new java.awt.BorderLayout());

        senNumJPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bhaashik"); // NOI18N
        positionJLabel.setText(bundle.getString("Go_to_sentence_number:")); // NOI18N
        senNumJPanel.add(positionJLabel);

        positionJComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                positionJComboBoxActionPerformed(evt);
            }
        });
        senNumJPanel.add(positionJComboBox);

        textEditCheckBox.setText(bundle.getString("Edit_Node_Text")); // NOI18N
        textEditCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        textEditCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        textEditCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textEditCheckBoxActionPerformed(evt);
            }
        });
        senNumJPanel.add(textEditCheckBox);

        nestedFSJCheckBox.setText(bundle.getString("Nested_FS")); // NOI18N
        nestedFSJCheckBox.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        nestedFSJCheckBox.setEnabled(false);
        nestedFSJCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        nestedFSJCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nestedFSJCheckBoxActionPerformed(evt);
            }
        });
        senNumJPanel.add(nestedFSJCheckBox);

        positionJPanel.add(senNumJPanel, java.awt.BorderLayout.CENTER);

        zoomInJButton.setText("+");
        zoomInJButton.setToolTipText(bundle.getString("Zoom_In")); // NOI18N
        zoomInJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomInJButtonActionPerformed(evt);
            }
        });
        buttonsJPanel.add(zoomInJButton);

        zoomOutJButton.setText("-");
        zoomOutJButton.setToolTipText(bundle.getString("Zoom_out")); // NOI18N
        zoomOutJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoomOutJButtonActionPerformed(evt);
            }
        });
        buttonsJPanel.add(zoomOutJButton);

        showUTF8JButton.setText(bundle.getString("Show_UTF8")); // NOI18N
        showUTF8JButton.setEnabled(false);
        showUTF8JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showUTF8JButtonActionPerformed(evt);
            }
        });
        buttonsJPanel.add(showUTF8JButton);

        positionJPanel.add(buttonsJPanel, java.awt.BorderLayout.EAST);

        bottomJPanel.add(positionJPanel, java.awt.BorderLayout.NORTH);

        commandsJPanel.setLayout(new javax.swing.BoxLayout(commandsJPanel, javax.swing.BoxLayout.Y_AXIS));

        navigateJPanel.setLayout(new java.awt.GridLayout(1, 0, 4, 0));

        firstJButton.setText(bundle.getString("First")); // NOI18N
        firstJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstJButtonActionPerformed(evt);
            }
        });
        navigateJPanel.add(firstJButton);

        prevJButton.setText(bundle.getString("Previous")); // NOI18N
        prevJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevJButtonActionPerformed(evt);
            }
        });
        navigateJPanel.add(prevJButton);

        nextJButton.setText(bundle.getString("Next")); // NOI18N
        nextJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextJButtonActionPerformed(evt);
            }
        });
        navigateJPanel.add(nextJButton);

        lastJButton.setText(bundle.getString("Last")); // NOI18N
        lastJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastJButtonActionPerformed(evt);
            }
        });
        navigateJPanel.add(lastJButton);

        commandsJPanel.add(navigateJPanel);

        mainCommandsJPanel.setLayout(new java.awt.GridLayout(1, 0, 4, 0));

        resetJButton.setText(bundle.getString("Reset")); // NOI18N
        resetJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetJButtonActionPerformed(evt);
            }
        });
        mainCommandsJPanel.add(resetJButton);

        saveAsJButton.setText(bundle.getString("Save_As_...")); // NOI18N
        saveAsJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsJButtonActionPerformed(evt);
            }
        });
        mainCommandsJPanel.add(saveAsJButton);

        saveJButton.setText(bundle.getString("Save")); // NOI18N
        saveJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveJButtonActionPerformed(evt);
            }
        });
        mainCommandsJPanel.add(saveJButton);

        moreJButton.setText(bundle.getString("More...")); // NOI18N
        moreJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moreJButtonActionPerformed(evt);
            }
        });
        mainCommandsJPanel.add(moreJButton);

        commandsJPanel.add(mainCommandsJPanel);

        extraCommandsJPanel.setLayout(new java.awt.GridLayout(1, 0, 4, 0));

        clearAllJButton.setText(bundle.getString("Clear_All")); // NOI18N
        clearAllJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllJButtonActionPerformed(evt);
            }
        });
        extraCommandsJPanel.add(clearAllJButton);

        clearJButton.setText(bundle.getString("Clear")); // NOI18N
        clearJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearJButtonActionPerformed(evt);
            }
        });
        extraCommandsJPanel.add(clearJButton);

        resetAllJButton.setText(bundle.getString("Reset_All")); // NOI18N
        resetAllJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetAllJButtonActionPerformed(evt);
            }
        });
        extraCommandsJPanel.add(resetAllJButton);

        editTextJButton.setText(bundle.getString("Edit_SSF_Text")); // NOI18N
        editTextJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editTextJButtonActionPerformed(evt);
            }
        });
        extraCommandsJPanel.add(editTextJButton);

        sentenceJoinJButton.setText(bundle.getString("Join_Sentence")); // NOI18N
        sentenceJoinJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sentenceJoinJButtonActionPerformed(evt);
            }
        });
        extraCommandsJPanel.add(sentenceJoinJButton);

        sentenceSplitJButton.setText(bundle.getString("Split_Sentence")); // NOI18N
        sentenceSplitJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sentenceSplitJButtonActionPerformed(evt);
            }
        });
        extraCommandsJPanel.add(sentenceSplitJButton);

        commandsJPanel.add(extraCommandsJPanel);

        bottomJPanel.add(commandsJPanel, java.awt.BorderLayout.SOUTH);

        add(bottomJPanel, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void sentenceSplitJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sentenceSplitJButtonActionPerformed
// TODO add your handling code here:
//        TreePath currentSelection = ssfPhraseJPanel.getJTree().getSelectionPath();
//        
//        if (currentSelection != null) {
//            BhaashikMutableTreeNode currentNode = (BhaashikMutableTreeNode) (currentSelection.getLastPathComponent());
//            
//            if((currentNode.getParent() != null && currentNode.getParent().getParent() == null) == false) {
//                JOptionPane.showMessageDialog(this, "Wrong selection for splitting a sentence.", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            
//            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
//            
//            int splitAt = parent.getIndex(currentNode);
//            
//            SSFSentence currentSentence = ssfStory.getSentence(currentPosition);
//            
//            SSFSentence nextSentence = new SSFSentenceImpl();
//            
//            try {
//                nextSentence.setRoot(currentSentence.getRoot().splitPhrase(splitAt));
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            
//            ssfStory.insertSentence(nextSentence, currentPosition + 1);
//            commentsPT.insertToken("", currentPosition + 1);
//            
//            ssfStory.reallocateNodeIDs();
//            ssfStory.reallocateSentenceIDs();
//            
//            int count = positionJComboBox.getModel().getSize();
//            String lastPos = "" + (count + 1);
//            positionJComboBox.addItem(lastPos);
//            
//            resetCurrentPosition();
//            
//            save();
//        }
    }//GEN-LAST:event_sentenceSplitJButtonActionPerformed

    private void sentenceJoinJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sentenceJoinJButtonActionPerformed
// TODO add your handling code here:
//        if(currentPosition == ssfStory.countSentences() - 1) {
//            JOptionPane.showMessageDialog(this, "A sentence can only be joined with the next sentence. You are at the last sentence.", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        
//        SSFSentence currentSentence = ssfStory.getSentence(currentPosition);
//        SSFSentence nextSentence = ssfStory.getSentence(currentPosition + 1);
//        
//        SSFPhrase currentRoot = currentSentence.getRoot();
//        SSFPhrase nextRoot = nextSentence.getRoot();
//        
//        String currentComment = commentsPT.getToken(currentPosition);
//        String nextComment = commentsPT.getToken(currentPosition + 1);
//        
//        currentComment = currentComment + ". " + nextComment;
//        commentsPT.modifyToken(currentComment, currentPosition);
//        commentsPT.removeToken(currentPosition + 1);
//        
//        currentRoot.concat(nextRoot);
//        
//        ssfStory.removeSentence(currentPosition + 1);
//        
//        ssfStory.reallocateSentenceIDs();
//        
//        positionJComboBox.removeItemAt(positionJComboBox.getModel().getSize() - 1);
//        
//        resetCurrentPosition();
        
//	save();
    }//GEN-LAST:event_sentenceJoinJButtonActionPerformed

    private void editTextJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editTextJButtonActionPerformed
// TODO add your handling code here:
//        save();
//        
//        if(editSSFText(true) == true)
//            resetAll();
    }//GEN-LAST:event_editTextJButtonActionPerformed

    private void resetAllJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetAllJButtonActionPerformed
// TODO add your handling code here:
//        int ret = JOptionPane.showConfirmDialog(this, "Are you sure you want to reset everything?", "Reset All", JOptionPane.YES_NO_OPTION);
//        
//        if(ret == JOptionPane.NO_OPTION)
//            return;
//        
//        resetAll();
    }//GEN-LAST:event_resetAllJButtonActionPerformed

    private void clearJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearJButtonActionPerformed
// TODO add your handling code here:
//        int ret = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear some annotation this sentence?", "Clear Annotation in Sentence?", JOptionPane.YES_NO_OPTION);
//        
//        if(ret == JOptionPane.NO_OPTION)
//            return;
//        
//        JDialog alDialog = null;
//        
//        if(owner != null)
//            alDialog = SSFAnnotationLevelsJPanel.showDialog(owner, "SSF Annotation Levels", true);
//        else if(dialog != null)
//            alDialog = SSFAnnotationLevelsJPanel.showDialog(dialog, "SSF Annotation Levels", true);
//        
//        long annotationLevelsFlag = ((SSFAnnotationLevelsJPanel.LocalDialog) alDialog).getAnnotationLevelsFlag();
//        
//        clearCurrentPosition(annotationLevelsFlag);
//        
//        displayCurrentPosition();
    }//GEN-LAST:event_clearJButtonActionPerformed

    private void clearAllJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllJButtonActionPerformed
// TODO add your handling code here:
//        int ret = JOptionPane.showConfirmDialog(this, "Are you sure you want to clear some annotation this task?", "Clear Annotation in Task?", JOptionPane.YES_NO_OPTION);
//        
//        if(ret == JOptionPane.NO_OPTION)
//            return;
//        
//        JDialog alDialog = null;
//        
//        if(dialog != null)
//            alDialog = SSFAnnotationLevelsJPanel.showDialog(dialog, "SSF Annotation Levels", true);
//        else if(owner != null)
//            alDialog = SSFAnnotationLevelsJPanel.showDialog(owner, "SSF Annotation Levels", true);
//        
//        long annotationLevelsFlag = ((SSFAnnotationLevelsJPanel.LocalDialog) alDialog).getAnnotationLevelsFlag();
//        
//        if(annotationLevelsFlag == SSFCorpus.NONE)
//            return;
//        
//        if(UtilityFunctions.flagOn(annotationLevelsFlag, SSFCorpus.COMMENTS)) {
//            for (int i = 0; i < commentsPT.countTokens(); i++)
//                commentsPT.modifyToken("", i);
//        }
//        
//        ssfStory.clearAnnotation(annotationLevelsFlag);
//        resetCurrentPosition();
    }//GEN-LAST:event_clearAllJButtonActionPerformed

    private void moreJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moreJButtonActionPerformed
// TODO add your handling code here:
//        if(extraCommandsJPanel.isVisible())
//            extraCommandsJPanel.setVisible(false);
//        else
//            extraCommandsJPanel.setVisible(true);
    }//GEN-LAST:event_moreJButtonActionPerformed

    private void saveJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveJButtonActionPerformed
// TODO add your handling code here:
//        save();
    }//GEN-LAST:event_saveJButtonActionPerformed

    private void saveAsJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsJButtonActionPerformed
// TODO add your handling code here:
//        Enumeration enm = CorpusType.elements();
//        Vector corpusTypes = new Vector(CorpusType.size());
//        
//        while(enm.hasMoreElements()) {
//            CorpusType ctype = (CorpusType) enm.nextElement();
//            corpusTypes.add(ctype);
//        }
//        
//        CorpusType selectedCorpusType = (CorpusType) JOptionPane.showInputDialog(this,
//                "Select corpus type for saving", "Corpus Type", JOptionPane.INFORMATION_MESSAGE, null,
//                corpusTypes.toArray(), CorpusType.SSF_TAGGED);
//        
//        saveAs(selectedCorpusType);
    }//GEN-LAST:event_saveAsJButtonActionPerformed

    private void resetJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetJButtonActionPerformed
// TODO add your handling code here:
//        resetCurrentPosition();
    }//GEN-LAST:event_resetJButtonActionPerformed

    private void lastJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastJButtonActionPerformed
// TODO add your handling code here:
//        setCurrentPosition(ssfStory.countSentences() - 1);
    }//GEN-LAST:event_lastJButtonActionPerformed

    private void nextJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextJButtonActionPerformed
// TODO add your handling code here:
//        String pos = (String) positionJComboBox.getSelectedItem();
//        try {
//            int cp = Integer.parseInt(pos);
//            setCurrentPosition(cp);
//        } catch(NumberFormatException e) {
//            displayCurrentPosition();
////            JOptionPane.showMessageDialog(this, "Wrong sentence number: " + pos, "Error", JOptionPane.ERROR_MESSAGE);
////            e.printStackTrace();
//        }
    }//GEN-LAST:event_nextJButtonActionPerformed

    private void prevJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevJButtonActionPerformed
// TODO add your handling code here:
//        String pos = (String) positionJComboBox.getSelectedItem();
//        try {
//            int cp = Integer.parseInt(pos);
//            setCurrentPosition(cp - 2);
//        } catch(NumberFormatException e) {
//            displayCurrentPosition();
////            JOptionPane.showMessageDialog(this, "Wrong sentence number: " + pos, "Error", JOptionPane.ERROR_MESSAGE);
////            e.printStackTrace();
//        }
    }//GEN-LAST:event_prevJButtonActionPerformed

    private void firstJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstJButtonActionPerformed
// TODO add your handling code here:
//        setCurrentPosition(0);
    }//GEN-LAST:event_firstJButtonActionPerformed

    private void showUTF8JButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showUTF8JButtonActionPerformed
// TODO add your handling code here:
//        if(utf8Shown == true)
//            utf8Shown = false;
//        else
//            utf8Shown = true;
//        
//        toggleTgtUTF8(utf8Shown);
    }//GEN-LAST:event_showUTF8JButtonActionPerformed

    private void zoomOutJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomOutJButtonActionPerformed
// TODO add your handling code here:
////	BhaashikLanguages.decreaseFontSize();
//        ssfPhraseJPanel.decreaseFontSizes();
//        setVisible(false);
//        setVisible(true);
//        BhaashikMutableTreeNode root = (BhaashikMutableTreeNode) ssfPhraseJPanel.getJTree().getModel().getRoot();
//        ((BhaashikTreeModel) ssfPhraseJPanel.getJTree().getModel()).nodeChanged(root);
//        ssfPhraseJPanel.expandAll(null);
////	ssfPhraseJPanel.getJTree().repaint();
////	ssfPhraseJPanel.getJTree().setVisible(false);
////	ssfPhraseJPanel.getJTree().setVisible(true);
    }//GEN-LAST:event_zoomOutJButtonActionPerformed

    private void zoomInJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoomInJButtonActionPerformed
// TODO add your handling code here:
////	BhaashikLanguages.increaseFontSize();
//        ssfPhraseJPanel.increaseFontSizes();
//        setVisible(false);
//        setVisible(true);
//        BhaashikMutableTreeNode root = (BhaashikMutableTreeNode) ssfPhraseJPanel.getJTree().getModel().getRoot();
//        ((BhaashikTreeModel) ssfPhraseJPanel.getJTree().getModel()).nodeStructureChanged(root);
//        ssfPhraseJPanel.expandAll(null);
////	ssfPhraseJPanel.getJTree().repaint();
////	ssfPhraseJPanel.getJTree().setVisible(false);
////	ssfPhraseJPanel.getJTree().setVisible(true);
    }//GEN-LAST:event_zoomInJButtonActionPerformed

    private void nestedFSJCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nestedFSJCheckBoxActionPerformed
// TODO add your handling code here:
//        if(sentence.getRoot().allowsNestedFS() == true)
//            sentence.getRoot().allowNestedFS(false);
//        else
//            sentence.getRoot().allowNestedFS(true);
//        
//        ssfPhraseJPanel.editTreeNode(null);
    }//GEN-LAST:event_nestedFSJCheckBoxActionPerformed

    private void textEditCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textEditCheckBoxActionPerformed
// TODO add your handling code here:
//        if(textEditCheckBox.isSelected())
//            ssfPhraseJPanel.setNodeTextEditable(true);
////	    ssfPhraseJPanel.getJTree().setEditable(true);
//        else
//            ssfPhraseJPanel.setNodeTextEditable(false);
////	    ssfPhraseJPanel.getJTree().setEditable(false);
    }//GEN-LAST:event_textEditCheckBoxActionPerformed

    private void positionJComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_positionJComboBoxActionPerformed
// TODO add your handling code here:
//        String pos = (String) positionJComboBox.getSelectedItem();
//        try {
//            int cp = Integer.parseInt(pos);
//            setCurrentPosition(cp - 1);
//        } catch(NumberFormatException e) {
//            displayCurrentPosition();
////            JOptionPane.showMessageDialog(this, "Wrong sentence number: " + pos, "Error", JOptionPane.ERROR_MESSAGE);
////            e.printStackTrace();
//        }
    }//GEN-LAST:event_positionJComboBoxActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bottomJPanel;
    private javax.swing.JPanel buttonsJPanel;
    private javax.swing.JButton clearAllJButton;
    private javax.swing.JButton clearJButton;
    private javax.swing.JPanel commandsJPanel;
    private javax.swing.JButton editTextJButton;
    private javax.swing.JPanel extraCommandsJPanel;
    private javax.swing.JButton firstJButton;
    private javax.swing.JButton lastJButton;
    private javax.swing.JPanel mainCommandsJPanel;
    private javax.swing.JButton moreJButton;
    private javax.swing.JPanel navigateJPanel;
    private javax.swing.JCheckBox nestedFSJCheckBox;
    private javax.swing.JButton nextJButton;
    private javax.swing.JComboBox positionJComboBox;
    private javax.swing.JLabel positionJLabel;
    private javax.swing.JPanel positionJPanel;
    private javax.swing.JButton prevJButton;
    private javax.swing.JButton resetAllJButton;
    private javax.swing.JButton resetJButton;
    private javax.swing.JButton saveAsJButton;
    private javax.swing.JButton saveJButton;
    private javax.swing.JPanel senNumJPanel;
    private javax.swing.JButton sentenceJoinJButton;
    private javax.swing.JButton sentenceSplitJButton;
    private javax.swing.JButton showUTF8JButton;
    private javax.swing.JCheckBox textEditCheckBox;
    private javax.swing.JPanel workJPanel;
    private javax.swing.JButton zoomInJButton;
    private javax.swing.JButton zoomOutJButton;
    // End of variables declaration//GEN-END:variables
    
}
