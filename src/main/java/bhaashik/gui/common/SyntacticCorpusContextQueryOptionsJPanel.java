/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SyntacticCorpusContextQueryOptionsJPanel.java
 *
 * Created on 8 Aug, 2009, 7:46:58 PM
 */

package bhaashik.gui.common;

/**
 *
 * @author Anil Kumar Singh
 */
public class SyntacticCorpusContextQueryOptionsJPanel extends javax.swing.JPanel {

    /** Creates new form SyntacticCorpusContextQueryOptionsJPanel */
    public SyntacticCorpusContextQueryOptionsJPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        optionsJPanel = new javax.swing.JPanel();
        prevNodeOptionsJPanel = new javax.swing.JPanel();
        prev1JPanel = new javax.swing.JPanel();
        textPrevJLabel = new javax.swing.JLabel();
        textPrevJTextField = new javax.swing.JTextField();
        prev2JPanel = new javax.swing.JPanel();
        andOr1PrevJComboBox = new javax.swing.JComboBox();
        tagPrevJLabel = new javax.swing.JLabel();
        tagPrevJComboBox = new javax.swing.JComboBox();
        prev3JPanel = new javax.swing.JPanel();
        andOr2PrevJComboBox = new javax.swing.JComboBox();
        featurePrevJLabel = new javax.swing.JLabel();
        featurePrevJComboBox = new javax.swing.JComboBox();
        prev4JPanel = new javax.swing.JPanel();
        andOr3PrevJComboBox = new javax.swing.JComboBox();
        featureValuePrevJLabel = new javax.swing.JLabel();
        featureValuePrevJComboBox = new javax.swing.JComboBox();
        parentNodeOptionsJPanel = new javax.swing.JPanel();
        textParentJLabel = new javax.swing.JLabel();
        textParentJTextField = new javax.swing.JTextField();
        andOr1ParentJComboBox = new javax.swing.JComboBox();
        tagParentJLabel = new javax.swing.JLabel();
        tagParentJComboBox = new javax.swing.JComboBox();
        andOr2ParentJComboBox = new javax.swing.JComboBox();
        featureParentJLabel = new javax.swing.JLabel();
        featureParentJComboBox = new javax.swing.JComboBox();
        andOr3ParentJComboBox = new javax.swing.JComboBox();
        featureValueParentJLabel = new javax.swing.JLabel();
        featureValueParentJComboBox = new javax.swing.JComboBox();
        thisNodeOptionsJPanel = new javax.swing.JPanel();
        this1JPanel = new javax.swing.JPanel();
        textPrevJLabel1 = new javax.swing.JLabel();
        textPrevJTextField1 = new javax.swing.JTextField();
        this2JPanel = new javax.swing.JPanel();
        andOr1ThisJComboBox = new javax.swing.JComboBox();
        tagThisJLabel = new javax.swing.JLabel();
        tagThisJComboBox = new javax.swing.JComboBox();
        this3JPanel = new javax.swing.JPanel();
        andOr2ThisJComboBox = new javax.swing.JComboBox();
        featureThisJLabel = new javax.swing.JLabel();
        featureThisJComboBox = new javax.swing.JComboBox();
        this4JPanel = new javax.swing.JPanel();
        andOr3ThisJComboBox = new javax.swing.JComboBox();
        featureValueThisJLabel = new javax.swing.JLabel();
        featureValueThisJComboBox = new javax.swing.JComboBox();
        nextNodeOptionsJPanel = new javax.swing.JPanel();
        next1JPanel = new javax.swing.JPanel();
        textNextJLabel = new javax.swing.JLabel();
        textNextJTextField = new javax.swing.JTextField();
        next2JPanel = new javax.swing.JPanel();
        andOr1NextJComboBox = new javax.swing.JComboBox();
        tagNextJLabel = new javax.swing.JLabel();
        tagNextJComboBox = new javax.swing.JComboBox();
        next3JPanel = new javax.swing.JPanel();
        andOr2NextJComboBox = new javax.swing.JComboBox();
        featureNextJLabel = new javax.swing.JLabel();
        featureNextJComboBox = new javax.swing.JComboBox();
        next4JPanel = new javax.swing.JPanel();
        andOr3NextJComboBox = new javax.swing.JComboBox();
        featureValueNextJLabel = new javax.swing.JLabel();
        featureValueNextJComboBox = new javax.swing.JComboBox();
        childNodeOptionsJPanel = new javax.swing.JPanel();
        textChildJLabel = new javax.swing.JLabel();
        textChildJTextField = new javax.swing.JTextField();
        andOr1ChildJComboBox = new javax.swing.JComboBox();
        tagChildJLabel = new javax.swing.JLabel();
        tagChildJComboBox = new javax.swing.JComboBox();
        andOr2ChildJComboBox = new javax.swing.JComboBox();
        featureChildJLabel = new javax.swing.JLabel();
        featureChildJComboBox = new javax.swing.JComboBox();
        andOr3ChildJComboBox = new javax.swing.JComboBox();
        featureValueChildJLabel = new javax.swing.JLabel();
        featureValueChildJComboBox = new javax.swing.JComboBox();
        typedQueryJPanel = new javax.swing.JPanel();
        typedQueryJLabel = new javax.swing.JLabel();
        typedQueryJScrollPane = new javax.swing.JScrollPane();
        typedQueryJTextArea = new javax.swing.JTextArea();
        commandsJPanel = new javax.swing.JPanel();
        OKJButton = new javax.swing.JButton();
        CancelJButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        optionsJPanel.setLayout(new java.awt.BorderLayout());

        prevNodeOptionsJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Previous Node Options"));
        prevNodeOptionsJPanel.setLayout(new javax.swing.BoxLayout(prevNodeOptionsJPanel, javax.swing.BoxLayout.Y_AXIS));

        prev1JPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("bhaashik"); // NOI18N
        textPrevJLabel.setText(bundle.getString("Text:")); // NOI18N
        textPrevJLabel.setToolTipText(bundle.getString("Text_to_be_searched")); // NOI18N
        prev1JPanel.add(textPrevJLabel);

        textPrevJTextField.setPreferredSize(new java.awt.Dimension(150, 19));
        prev1JPanel.add(textPrevJTextField);

        prevNodeOptionsJPanel.add(prev1JPanel);

        prev2JPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        andOr1PrevJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Or", "And" }));
        andOr1PrevJComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        prev2JPanel.add(andOr1PrevJComboBox);

        tagPrevJLabel.setText(bundle.getString("_Tag:_")); // NOI18N
        tagPrevJLabel.setToolTipText(bundle.getString("Tag_to_be_searched")); // NOI18N
        tagPrevJLabel.setPreferredSize(new java.awt.Dimension(40, 14));
        prev2JPanel.add(tagPrevJLabel);

        tagPrevJComboBox.setEditable(true);
        tagPrevJComboBox.setPreferredSize(new java.awt.Dimension(100, 20));
        prev2JPanel.add(tagPrevJComboBox);

        prevNodeOptionsJPanel.add(prev2JPanel);

        prev3JPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        andOr2PrevJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Or", "And" }));
        andOr2PrevJComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        prev3JPanel.add(andOr2PrevJComboBox);

        featurePrevJLabel.setText(bundle.getString("__Feature:_")); // NOI18N
        featurePrevJLabel.setToolTipText(bundle.getString("Feature_to_be_searched")); // NOI18N
        featurePrevJLabel.setPreferredSize(new java.awt.Dimension(70, 14));
        prev3JPanel.add(featurePrevJLabel);

        featurePrevJComboBox.setEditable(true);
        featurePrevJComboBox.setPreferredSize(new java.awt.Dimension(100, 20));
        prev3JPanel.add(featurePrevJComboBox);

        prevNodeOptionsJPanel.add(prev3JPanel);

        prev4JPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        andOr3PrevJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Or", "And" }));
        andOr3PrevJComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        prev4JPanel.add(andOr3PrevJComboBox);

        featureValuePrevJLabel.setText(bundle.getString("_Value:_")); // NOI18N
        featureValuePrevJLabel.setToolTipText(bundle.getString("Feature_value_to_be_searched")); // NOI18N
        featureValuePrevJLabel.setPreferredSize(new java.awt.Dimension(50, 14));
        prev4JPanel.add(featureValuePrevJLabel);

        featureValuePrevJComboBox.setEditable(true);
        featureValuePrevJComboBox.setPreferredSize(new java.awt.Dimension(100, 20));
        prev4JPanel.add(featureValuePrevJComboBox);

        prevNodeOptionsJPanel.add(prev4JPanel);

        optionsJPanel.add(prevNodeOptionsJPanel, java.awt.BorderLayout.WEST);

        parentNodeOptionsJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Previous Node Options"));

        textParentJLabel.setText(bundle.getString("Text:")); // NOI18N
        textParentJLabel.setToolTipText(bundle.getString("Text_to_be_searched")); // NOI18N
        parentNodeOptionsJPanel.add(textParentJLabel);

        textParentJTextField.setPreferredSize(new java.awt.Dimension(150, 19));
        parentNodeOptionsJPanel.add(textParentJTextField);

        andOr1ParentJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Or", "And" }));
        andOr1ParentJComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        parentNodeOptionsJPanel.add(andOr1ParentJComboBox);

        tagParentJLabel.setText(bundle.getString("_Tag:_")); // NOI18N
        tagParentJLabel.setToolTipText(bundle.getString("Tag_to_be_searched")); // NOI18N
        tagParentJLabel.setPreferredSize(new java.awt.Dimension(40, 14));
        parentNodeOptionsJPanel.add(tagParentJLabel);

        tagParentJComboBox.setEditable(true);
        tagParentJComboBox.setPreferredSize(new java.awt.Dimension(100, 20));
        parentNodeOptionsJPanel.add(tagParentJComboBox);

        andOr2ParentJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Or", "And" }));
        andOr2ParentJComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        parentNodeOptionsJPanel.add(andOr2ParentJComboBox);

        featureParentJLabel.setText(bundle.getString("__Feature:_")); // NOI18N
        featureParentJLabel.setToolTipText(bundle.getString("Feature_to_be_searched")); // NOI18N
        featureParentJLabel.setPreferredSize(new java.awt.Dimension(70, 14));
        parentNodeOptionsJPanel.add(featureParentJLabel);

        featureParentJComboBox.setEditable(true);
        featureParentJComboBox.setPreferredSize(new java.awt.Dimension(100, 20));
        parentNodeOptionsJPanel.add(featureParentJComboBox);

        andOr3ParentJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Or", "And" }));
        andOr3ParentJComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        parentNodeOptionsJPanel.add(andOr3ParentJComboBox);

        featureValueParentJLabel.setText(bundle.getString("_Value:_")); // NOI18N
        featureValueParentJLabel.setToolTipText(bundle.getString("Feature_value_to_be_searched")); // NOI18N
        featureValueParentJLabel.setPreferredSize(new java.awt.Dimension(50, 14));
        parentNodeOptionsJPanel.add(featureValueParentJLabel);

        featureValueParentJComboBox.setEditable(true);
        featureValueParentJComboBox.setPreferredSize(new java.awt.Dimension(100, 20));
        parentNodeOptionsJPanel.add(featureValueParentJComboBox);

        optionsJPanel.add(parentNodeOptionsJPanel, java.awt.BorderLayout.NORTH);

        thisNodeOptionsJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Current Node Options"));
        thisNodeOptionsJPanel.setLayout(new javax.swing.BoxLayout(thisNodeOptionsJPanel, javax.swing.BoxLayout.Y_AXIS));

        textPrevJLabel1.setText(bundle.getString("Text:")); // NOI18N
        textPrevJLabel1.setToolTipText(bundle.getString("Text_to_be_searched")); // NOI18N
        this1JPanel.add(textPrevJLabel1);

        textPrevJTextField1.setPreferredSize(new java.awt.Dimension(150, 19));
        this1JPanel.add(textPrevJTextField1);

        thisNodeOptionsJPanel.add(this1JPanel);

        andOr1ThisJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Or", "And" }));
        andOr1ThisJComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        this2JPanel.add(andOr1ThisJComboBox);

        tagThisJLabel.setText(bundle.getString("_Tag:_")); // NOI18N
        tagThisJLabel.setToolTipText(bundle.getString("Tag_to_be_searched")); // NOI18N
        tagThisJLabel.setPreferredSize(new java.awt.Dimension(40, 14));
        this2JPanel.add(tagThisJLabel);

        tagThisJComboBox.setEditable(true);
        tagThisJComboBox.setPreferredSize(new java.awt.Dimension(100, 20));
        this2JPanel.add(tagThisJComboBox);

        thisNodeOptionsJPanel.add(this2JPanel);

        andOr2ThisJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Or", "And" }));
        andOr2ThisJComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        this3JPanel.add(andOr2ThisJComboBox);

        featureThisJLabel.setText(bundle.getString("__Feature:_")); // NOI18N
        featureThisJLabel.setToolTipText(bundle.getString("Feature_to_be_searched")); // NOI18N
        featureThisJLabel.setPreferredSize(new java.awt.Dimension(70, 14));
        this3JPanel.add(featureThisJLabel);

        featureThisJComboBox.setEditable(true);
        featureThisJComboBox.setPreferredSize(new java.awt.Dimension(100, 20));
        this3JPanel.add(featureThisJComboBox);

        thisNodeOptionsJPanel.add(this3JPanel);

        andOr3ThisJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Or", "And" }));
        andOr3ThisJComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        this4JPanel.add(andOr3ThisJComboBox);

        featureValueThisJLabel.setText(bundle.getString("_Value:_")); // NOI18N
        featureValueThisJLabel.setToolTipText(bundle.getString("Feature_value_to_be_searched")); // NOI18N
        featureValueThisJLabel.setPreferredSize(new java.awt.Dimension(50, 14));
        this4JPanel.add(featureValueThisJLabel);

        featureValueThisJComboBox.setEditable(true);
        featureValueThisJComboBox.setPreferredSize(new java.awt.Dimension(100, 20));
        this4JPanel.add(featureValueThisJComboBox);

        thisNodeOptionsJPanel.add(this4JPanel);

        optionsJPanel.add(thisNodeOptionsJPanel, java.awt.BorderLayout.CENTER);

        nextNodeOptionsJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Previous Node Options"));
        nextNodeOptionsJPanel.setLayout(new javax.swing.BoxLayout(nextNodeOptionsJPanel, javax.swing.BoxLayout.Y_AXIS));

        next1JPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        textNextJLabel.setText(bundle.getString("Text:")); // NOI18N
        textNextJLabel.setToolTipText(bundle.getString("Text_to_be_searched")); // NOI18N
        next1JPanel.add(textNextJLabel);

        textNextJTextField.setPreferredSize(new java.awt.Dimension(150, 19));
        next1JPanel.add(textNextJTextField);

        nextNodeOptionsJPanel.add(next1JPanel);

        next2JPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        andOr1NextJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Or", "And" }));
        andOr1NextJComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        next2JPanel.add(andOr1NextJComboBox);

        tagNextJLabel.setText(bundle.getString("_Tag:_")); // NOI18N
        tagNextJLabel.setToolTipText(bundle.getString("Tag_to_be_searched")); // NOI18N
        tagNextJLabel.setPreferredSize(new java.awt.Dimension(40, 14));
        next2JPanel.add(tagNextJLabel);

        tagNextJComboBox.setEditable(true);
        tagNextJComboBox.setPreferredSize(new java.awt.Dimension(100, 20));
        next2JPanel.add(tagNextJComboBox);

        nextNodeOptionsJPanel.add(next2JPanel);

        next3JPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        andOr2NextJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Or", "And" }));
        andOr2NextJComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        next3JPanel.add(andOr2NextJComboBox);

        featureNextJLabel.setText(bundle.getString("__Feature:_")); // NOI18N
        featureNextJLabel.setToolTipText(bundle.getString("Feature_to_be_searched")); // NOI18N
        featureNextJLabel.setPreferredSize(new java.awt.Dimension(70, 14));
        next3JPanel.add(featureNextJLabel);

        featureNextJComboBox.setEditable(true);
        featureNextJComboBox.setPreferredSize(new java.awt.Dimension(100, 20));
        next3JPanel.add(featureNextJComboBox);

        nextNodeOptionsJPanel.add(next3JPanel);

        next4JPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        andOr3NextJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Or", "And" }));
        andOr3NextJComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        next4JPanel.add(andOr3NextJComboBox);

        featureValueNextJLabel.setText(bundle.getString("_Value:_")); // NOI18N
        featureValueNextJLabel.setToolTipText(bundle.getString("Feature_value_to_be_searched")); // NOI18N
        featureValueNextJLabel.setPreferredSize(new java.awt.Dimension(50, 14));
        next4JPanel.add(featureValueNextJLabel);

        featureValueNextJComboBox.setEditable(true);
        featureValueNextJComboBox.setPreferredSize(new java.awt.Dimension(100, 20));
        next4JPanel.add(featureValueNextJComboBox);

        nextNodeOptionsJPanel.add(next4JPanel);

        optionsJPanel.add(nextNodeOptionsJPanel, java.awt.BorderLayout.EAST);

        childNodeOptionsJPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Previous Node Options"));

        textChildJLabel.setText(bundle.getString("Text:")); // NOI18N
        textChildJLabel.setToolTipText(bundle.getString("Text_to_be_searched")); // NOI18N
        childNodeOptionsJPanel.add(textChildJLabel);

        textChildJTextField.setPreferredSize(new java.awt.Dimension(150, 19));
        childNodeOptionsJPanel.add(textChildJTextField);

        andOr1ChildJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Or", "And" }));
        andOr1ChildJComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        childNodeOptionsJPanel.add(andOr1ChildJComboBox);

        tagChildJLabel.setText(bundle.getString("_Tag:_")); // NOI18N
        tagChildJLabel.setToolTipText(bundle.getString("Tag_to_be_searched")); // NOI18N
        tagChildJLabel.setPreferredSize(new java.awt.Dimension(40, 14));
        childNodeOptionsJPanel.add(tagChildJLabel);

        tagChildJComboBox.setEditable(true);
        tagChildJComboBox.setPreferredSize(new java.awt.Dimension(100, 20));
        childNodeOptionsJPanel.add(tagChildJComboBox);

        andOr2ChildJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Or", "And" }));
        andOr2ChildJComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        childNodeOptionsJPanel.add(andOr2ChildJComboBox);

        featureChildJLabel.setText(bundle.getString("__Feature:_")); // NOI18N
        featureChildJLabel.setToolTipText(bundle.getString("Feature_to_be_searched")); // NOI18N
        featureChildJLabel.setPreferredSize(new java.awt.Dimension(70, 14));
        childNodeOptionsJPanel.add(featureChildJLabel);

        featureChildJComboBox.setEditable(true);
        featureChildJComboBox.setPreferredSize(new java.awt.Dimension(100, 20));
        childNodeOptionsJPanel.add(featureChildJComboBox);

        andOr3ChildJComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Or", "And" }));
        andOr3ChildJComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        childNodeOptionsJPanel.add(andOr3ChildJComboBox);

        featureValueChildJLabel.setText(bundle.getString("_Value:_")); // NOI18N
        featureValueChildJLabel.setToolTipText(bundle.getString("Feature_value_to_be_searched")); // NOI18N
        featureValueChildJLabel.setPreferredSize(new java.awt.Dimension(50, 14));
        childNodeOptionsJPanel.add(featureValueChildJLabel);

        featureValueChildJComboBox.setEditable(true);
        featureValueChildJComboBox.setPreferredSize(new java.awt.Dimension(100, 20));
        childNodeOptionsJPanel.add(featureValueChildJComboBox);

        optionsJPanel.add(childNodeOptionsJPanel, java.awt.BorderLayout.SOUTH);

        add(optionsJPanel, java.awt.BorderLayout.CENTER);

        typedQueryJPanel.setLayout(new java.awt.BorderLayout(5, 0));

        typedQueryJLabel.setText(bundle.getString("Query:_")); // NOI18N
        typedQueryJLabel.setToolTipText(bundle.getString("You_can_also_type_the_query_here_if_you_know_the_syntax")); // NOI18N
        typedQueryJPanel.add(typedQueryJLabel, java.awt.BorderLayout.WEST);

        typedQueryJTextArea.setColumns(20);
        typedQueryJTextArea.setRows(5);
        typedQueryJScrollPane.setViewportView(typedQueryJTextArea);

        typedQueryJPanel.add(typedQueryJScrollPane, java.awt.BorderLayout.CENTER);

        commandsJPanel.setLayout(new java.awt.GridLayout(1, 0, 4, 0));

        OKJButton.setText(bundle.getString("OK")); // NOI18N
        commandsJPanel.add(OKJButton);

        CancelJButton.setText(bundle.getString("Cancel")); // NOI18N
        commandsJPanel.add(CancelJButton);

        typedQueryJPanel.add(commandsJPanel, java.awt.BorderLayout.SOUTH);

        add(typedQueryJPanel, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelJButton;
    private javax.swing.JButton OKJButton;
    private javax.swing.JComboBox andOr1ChildJComboBox;
    private javax.swing.JComboBox andOr1NextJComboBox;
    private javax.swing.JComboBox andOr1ParentJComboBox;
    private javax.swing.JComboBox andOr1PrevJComboBox;
    private javax.swing.JComboBox andOr1ThisJComboBox;
    private javax.swing.JComboBox andOr2ChildJComboBox;
    private javax.swing.JComboBox andOr2NextJComboBox;
    private javax.swing.JComboBox andOr2ParentJComboBox;
    private javax.swing.JComboBox andOr2PrevJComboBox;
    private javax.swing.JComboBox andOr2ThisJComboBox;
    private javax.swing.JComboBox andOr3ChildJComboBox;
    private javax.swing.JComboBox andOr3NextJComboBox;
    private javax.swing.JComboBox andOr3ParentJComboBox;
    private javax.swing.JComboBox andOr3PrevJComboBox;
    private javax.swing.JComboBox andOr3ThisJComboBox;
    private javax.swing.JPanel childNodeOptionsJPanel;
    private javax.swing.JPanel commandsJPanel;
    private javax.swing.JComboBox featureChildJComboBox;
    private javax.swing.JLabel featureChildJLabel;
    private javax.swing.JComboBox featureNextJComboBox;
    private javax.swing.JLabel featureNextJLabel;
    private javax.swing.JComboBox featureParentJComboBox;
    private javax.swing.JLabel featureParentJLabel;
    private javax.swing.JComboBox featurePrevJComboBox;
    private javax.swing.JLabel featurePrevJLabel;
    private javax.swing.JComboBox featureThisJComboBox;
    private javax.swing.JLabel featureThisJLabel;
    private javax.swing.JComboBox featureValueChildJComboBox;
    private javax.swing.JLabel featureValueChildJLabel;
    private javax.swing.JComboBox featureValueNextJComboBox;
    private javax.swing.JLabel featureValueNextJLabel;
    private javax.swing.JComboBox featureValueParentJComboBox;
    private javax.swing.JLabel featureValueParentJLabel;
    private javax.swing.JComboBox featureValuePrevJComboBox;
    private javax.swing.JLabel featureValuePrevJLabel;
    private javax.swing.JComboBox featureValueThisJComboBox;
    private javax.swing.JLabel featureValueThisJLabel;
    private javax.swing.JPanel next1JPanel;
    private javax.swing.JPanel next2JPanel;
    private javax.swing.JPanel next3JPanel;
    private javax.swing.JPanel next4JPanel;
    private javax.swing.JPanel nextNodeOptionsJPanel;
    private javax.swing.JPanel optionsJPanel;
    private javax.swing.JPanel parentNodeOptionsJPanel;
    private javax.swing.JPanel prev1JPanel;
    private javax.swing.JPanel prev2JPanel;
    private javax.swing.JPanel prev3JPanel;
    private javax.swing.JPanel prev4JPanel;
    private javax.swing.JPanel prevNodeOptionsJPanel;
    private javax.swing.JComboBox tagChildJComboBox;
    private javax.swing.JLabel tagChildJLabel;
    private javax.swing.JComboBox tagNextJComboBox;
    private javax.swing.JLabel tagNextJLabel;
    private javax.swing.JComboBox tagParentJComboBox;
    private javax.swing.JLabel tagParentJLabel;
    private javax.swing.JComboBox tagPrevJComboBox;
    private javax.swing.JLabel tagPrevJLabel;
    private javax.swing.JComboBox tagThisJComboBox;
    private javax.swing.JLabel tagThisJLabel;
    private javax.swing.JLabel textChildJLabel;
    private javax.swing.JTextField textChildJTextField;
    private javax.swing.JLabel textNextJLabel;
    private javax.swing.JTextField textNextJTextField;
    private javax.swing.JLabel textParentJLabel;
    private javax.swing.JTextField textParentJTextField;
    private javax.swing.JLabel textPrevJLabel;
    private javax.swing.JLabel textPrevJLabel1;
    private javax.swing.JTextField textPrevJTextField;
    private javax.swing.JTextField textPrevJTextField1;
    private javax.swing.JPanel this1JPanel;
    private javax.swing.JPanel this2JPanel;
    private javax.swing.JPanel this3JPanel;
    private javax.swing.JPanel this4JPanel;
    private javax.swing.JPanel thisNodeOptionsJPanel;
    private javax.swing.JLabel typedQueryJLabel;
    private javax.swing.JPanel typedQueryJPanel;
    private javax.swing.JScrollPane typedQueryJScrollPane;
    private javax.swing.JTextArea typedQueryJTextArea;
    // End of variables declaration//GEN-END:variables

}
