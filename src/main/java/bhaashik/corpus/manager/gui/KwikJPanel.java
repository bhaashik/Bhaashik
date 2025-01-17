/*
 * KwikJPanel.java
 *
 * Created on September 26, 2005, 9:39 PM
 */

package bhaashik.corpus.manager.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Iterator;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.TableColumn;

import bhaashik.GlobalProperties;
import bhaashik.gui.clients.BhaashikClient;
import bhaashik.gui.common.JPanelDialog;
import bhaashik.table.gui.BhaashikTableJPanel;
import bhaashik.context.KWIKContext;
import bhaashik.context.KWIKContextModels;
import bhaashik.table.BhaashikTableModel;
import bhaashik.util.UtilityFunctions;
import bhaashik.common.types.ClientType;
import java.io.Serializable;

/**
 *
 * @author  anil
 */
public class KwikJPanel extends javax.swing.JPanel implements WindowListener, JPanelDialog, BhaashikClient {

    protected ClientType clientType = ClientType.LANGUAGE_ENCODING_IDENTIFIER;

    protected JFrame owner;
    protected JDialog dialog;
    protected Component parentComponent;
    
    protected BhaashikTableModel kwikTable;
    protected BhaashikTableJPanel kwikTableJPanel;
    
    protected String langEnc = GlobalProperties.getIntlString("hin::utf8");
    protected String title = GlobalProperties.getIntlString("Untitled");
    
    /** Creates new form KwikJPanel */
    public KwikJPanel(String langEnc) {
        initComponents();
        
        this.langEnc = langEnc;
        
        init();
//        addTestData();
//        resize();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        
        setLayout(new java.awt.BorderLayout());
        
    }//GEN-END:initComponents

    public ClientType getClientType()
    {
        return clientType;
    }

    private void addTestData()
    {
        addKwikRow("Phonemic Segmentation as Epiphenomenon", "keyword", "Phonemic Segmentation as Epiphenomenon");
        addKwikRow("John Benjamins, Amsterdam", "keywrd", "Linguistics of Literacy");
        addKwikRow("Linguistics of Literacy", "kword", "Review of Daniels and Bright");
        addKwikRow("The World's Writing Systems (Oxford University Press)", "kywords", "A Mathematical Theory of Communication A Mathematical Theory of Communication");
        addKwikRow("Bell System Tech. J.", "keywords", "Maximum Entropy Models for Natural Language Ambiguity Resolution");
        addKwikRow("University of Pennsylvania University of Pennsylvania University of Pennsylvania", "keywrds", "University of Pennsylvania");
        addKwikRow("Natural Language Processing: A Paninian Perspective", "keeword", "Parameter Estimation");
    }
    
    public void init()
    {
        kwikTable = new BhaashikTableModel(0, 3);
        
        kwikTableJPanel = BhaashikTableJPanel.createKwikJPanel(kwikTable, langEnc);
        
        JTable jtable = kwikTableJPanel.getJTable();
        
        KwikCellRenderer renderer = new KwikCellRenderer(langEnc);

        TableColumn col = jtable.getColumnModel().getColumn(0);
        col.setCellRenderer(renderer);

        col = jtable.getColumnModel().getColumn(1);
        col.setCellRenderer(renderer);

        col = jtable.getColumnModel().getColumn(2);
        col.setCellRenderer(renderer);
        
        add(kwikTableJPanel, BorderLayout.CENTER);        
    }
    
    public void resize()
    {
        if(kwikTableJPanel == null)
            return;
        
        UtilityFunctions.fitColumnsToContent(kwikTableJPanel.getJTable());        
    }
    
    public void addKwikRow(String leftContext, String keyword, String rightContext)
    {
        kwikTable.addRow(new String[]{leftContext, keyword, rightContext});
    }

    public void addKwikContextModels(KWIKContextModels kwikContextModels)
    {
        Iterator itr = kwikContextModels.getContextModelKeys();

        while(itr.hasNext()) {
//            Object key = itr.next();
            Serializable key = (Serializable) itr.next();

            KWIKContext context = (KWIKContext) kwikContextModels.getContextModel(key);
            String leftContext = (String) context.getLeftContext().getContextElement();
            String keyword = (String) context.getKeyword().getContextElement();
            String rightContext = (String) context.getRightContext().getContextElement();

            kwikTable.addRow(new String[]{leftContext, keyword, rightContext});
        }
    }

    public String getLangEnc()
    {
        return langEnc;
    }

    public Frame getOwner() {
        return owner;
    }

    public void setOwner(Frame frame) {
        owner = (JFrame) frame;
    }

    public void setParentComponent(Component parentComponent)
    {
        this.parentComponent = parentComponent;
    }

    public String getTitle() {
        return title;
    }

    public JMenuBar getJMenuBar() {
        return null;
    }

    public JToolBar getJToolBar() {
        return null;
    }

    public JPopupMenu getJPopupMenu()
    {
        return null;
    }    
    
    public void setDialog(JDialog dialog)
    {
        this.dialog = dialog;
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
//        BhaashikTableModel stm = getModel();
//        
//        if(stm.isInDatabaseMode())
//        {
//            try {
//                DriverManager.getConnection("jdbc:derby:;shutdown=true");
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//        }
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
    
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame(GlobalProperties.getIntlString("Bhaashik_Kwik_Panel"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        KwikJPanel newContentPane = new KwikJPanel(GlobalProperties.getIntlString("hin::utf8"));
        
        newContentPane.owner = frame;
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        frame.pack();

        int inset = 5;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(inset, inset,
                screenSize.width  - inset*2,
                screenSize.height - inset*9);

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
    // End of variables declaration//GEN-END:variables
}
