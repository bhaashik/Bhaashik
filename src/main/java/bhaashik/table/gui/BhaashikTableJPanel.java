/*
 * BhaashikJTablePanel.java
 *
 * Created on September 18, 2005, 10:38 PM
 */

package bhaashik.table.gui;

import java.io.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.awt.Frame;
import javax.swing.*;
import javax.swing.table.*;

import bhaashik.GlobalProperties;
import bhaashik.common.types.ClientType;
import bhaashik.corpus.ssf.features.impl.FeatureStructuresImpl;
import bhaashik.db.DBConnection;
import bhaashik.db.DatabaseUtils;
import bhaashik.db.gui.ConnectToDBJPanel;
import bhaashik.gui.clients.BhaashikClient;
import bhaashik.gui.common.JPanelDialog;
import bhaashik.gui.common.PopupListener;
import bhaashik.gui.common.BhaashikLanguages;
import bhaashik.gui.common.BhaashikStringDataTransfer;

import bhaashik.table.BhaashikTableModel;
import bhaashik.util.UtilityFunctions;

/**
 *
 * @author  anil
 */
public class BhaashikTableJPanel extends javax.swing.JPanel implements WindowListener, JPanelDialog, BhaashikClient {

    protected ClientType clientType = ClientType.TABLE_EDITOR;

    protected JFrame owner;
    protected JDialog dialog;
    protected Component parentComponent;
    
    protected DBConnection connection;
    
    protected int mode;
    protected String langEnc;
    
    protected String name;

    protected String title = GlobalProperties.getIntlString("Untitled");

    protected BhaashikTableModel tableModel;
    protected boolean sortable;

    public static final int DEFAULT_MODE = 1000;
    public static final int CUSTOM_MODE = 1001;
    public static final int FS_MODE = 1002;
    public static final int MINIMAL_MODE = 1003;
    public static final int DATABASE_SCHEMA_MODE = 1004;
    public static final int DATABASE_MODE = 1005;

    protected boolean allCommands[];
    protected BhaashikTableAction actions[];

    protected javax.swing.JScrollPane tableJScrollPane;
    protected javax.swing.JTable tableJTable;

    protected boolean commandButtonsShown;
    
    /** Creates new form BhaashikJTablePanel */
    public BhaashikTableJPanel(String f, String charset, boolean sortable,
            int appliedCommands[], int mode, String lang) throws FileNotFoundException, IOException
    {
        initComponents();
        initJTable();

        parentComponent = this;
        
        this.mode = mode;
	langEnc = lang;
//	UtilityFunctions.setComponentFont(this, langEnc);
	UtilityFunctions.setComponentFont(tableJTable, langEnc);
	
        tableModel = new BhaashikTableModel(f, charset);
//        tableJTable.setModel(table);
        setModel(tableModel);

        setSortable(sortable);

        tableJTable.setPreferredScrollableViewportSize(new Dimension(500, 120));
        
        MouseListener popupListener = new PopupListener(tableJPopupMenu);
        tableJTable.addMouseListener(popupListener);
        prepareCommands(appliedCommands, mode);
        
        tableJTable.setDefaultEditor(String.class, new BhaashikTableCellEditor(langEnc));
        resize();
    }

    public BhaashikTableJPanel(BhaashikTableModel table, boolean sortable,
            int appliedCommands[], int mode, String lang)
    {
        initComponents();
        initJTable();

        parentComponent = this;
        
        this.mode = mode;
	langEnc = lang;
//	UtilityFunctions.setComponentFont(this, language);
	UtilityFunctions.setComponentFont(tableJTable, langEnc);

        setModel(table);
//	tableJTable.setModel(table);

        setSortable(sortable);

        tableJTable.setPreferredScrollableViewportSize(new Dimension(500, 120));
        
        MouseListener popupListener = new PopupListener(tableJPopupMenu);
        tableJTable.addMouseListener(popupListener);
        prepareCommands(appliedCommands, mode);
        
        tableJTable.setDefaultEditor(String.class, new BhaashikTableCellEditor(langEnc));
        resize();
    }

    public BhaashikTableJPanel(boolean sortable, int appliedCommands[], int mode, String lang)
    {
        initComponents();
        initJTable();

        parentComponent = this;

        this.mode = mode;
	langEnc = lang;

//	UtilityFunctions.setComponentFont(this, language);
	UtilityFunctions.setComponentFont(tableJTable, langEnc);

	tableModel = new BhaashikTableModel();
//        tableJTable.setModel(table);
        setModel(tableModel);

        setSortable(sortable);
        
        tableJTable.setPreferredScrollableViewportSize(new Dimension(500, 120));

        MouseListener popupListener = new PopupListener(tableJPopupMenu);
        tableJTable.addMouseListener(popupListener);
        prepareCommands(appliedCommands, mode);
        
        tableJTable.setDefaultEditor(String.class, new BhaashikTableCellEditor(langEnc));
        resize();
    }

    public BhaashikTableJPanel(boolean sortable, int mode, String lang)
    {
        initComponents();
        initJTable();

        parentComponent = this;

        this.mode = mode;
	langEnc = lang;
//	UtilityFunctions.setComponentFont(this, language);
	UtilityFunctions.setComponentFont(tableJTable, langEnc);

	tableModel = new BhaashikTableModel(1, 1);
//        tableJTable.setModel(table);
        setModel(tableModel);

        setSortable(sortable);
        
        tableJTable.setPreferredScrollableViewportSize(new Dimension(500, 120));
        
        MouseListener popupListener = new PopupListener(tableJPopupMenu);
        tableJTable.addMouseListener(popupListener);
        prepareCommands(null, mode);
        
        tableJTable.setDefaultEditor(String.class, new BhaashikTableCellEditor(langEnc));
        resize();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tableJPopupMenu = new javax.swing.JPopupMenu();
        commandsJPanel = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        commandsJPanel.setLayout(new java.awt.GridLayout(0, 1, 0, 2));
        add(commandsJPanel, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    public ClientType getClientType()
    {
        return clientType;
    }

    public DBConnection getConnection() {
        return connection;
    }

    public void setConnection(DBConnection connection, boolean databaseMode, boolean databaseSchemaMode) {
        this.connection = connection;

        if(databaseMode)
        {
            tableModel.setDatabaseMode(true);
        }
        else if(databaseSchemaMode)
        {
            tableModel.setDatabaseSchemaMode(true);
        }
        
        tableModel.setConnection(connection);
    }
    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
        
        if(mode == DATABASE_MODE)
        {
            tableModel.setDatabaseMode(true);
        }
        else
        {
            tableModel.setDatabaseMode(false);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        tableModel.setName(name);
    }
    
    private void initJTable()
    {
        tableJTable = new BhaashikDefaultJTable();
        tableJScrollPane = new JScrollPane();

        tableJScrollPane.setPreferredSize(new java.awt.Dimension(453, 150));
        tableJScrollPane.setViewport(null);
        tableJTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String []
            {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));

        tableJTable.setCellSelectionEnabled(true);
        tableJTable.setDoubleBuffered(true);
        tableJScrollPane.setViewportView(tableJTable);

        add(tableJScrollPane, java.awt.BorderLayout.CENTER);
    }

    private void resize()
    {
        increaseFontSize(null);
//        increaseFontSize(null);
        
        increaseRowSize(null);
        increaseRowSize(null);
        increaseRowSize(null);
        increaseRowSize(null);
        increaseRowSize(null);
//        increaseRowSize(null);
//        increaseRowSize(null);
//        increaseRowSize(null);
    }
    
    public boolean getSortable()
    {
        return sortable;
    }
    
    public void setSortable(boolean s)
    {
        if(getSortable() == false && s)
        {
//            tableJTable.setAutoCreateRowSorter(true);
            TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableJTable.getModel());
            tableJTable.setRowSorter(sorter);            
            
//            tableModel = (BhaashikTableModel) tableJTable.getModel();
//            TableSorter sorter = new TableSorter(tableModel);
////            tableJTable.setModel(sorter);
//            setModel(tableModel);
//            sorter.setTableHeader(tableJTable.getTableHeader());
//            //Set up tool tips for column headers.
//            tableJTable.getTableHeader().setToolTipText(
//                    "Click to specify sorting; Control-Click to specify secondary sorting");
        }
        else if(getSortable() && s == false)
        {
            tableJTable.setAutoCreateRowSorter(false);            
//            TableSorter sorter = (TableSorter) tableJTable.getModel();
//            tableModel = (BhaashikTableModel) sorter.getTableModel();
////            tableJTable.setModel(table);
//            setModel(tableModel);
        }

        sortable = s;
    }

    private void prepareCommands(int appliedCommands[], int mode)
    {
        allCommands = new boolean[BhaashikTableAction._TOTAL_ACTIONS_];
        actions = new BhaashikTableAction[BhaashikTableAction._TOTAL_ACTIONS_];
        
        for(int i = 0; i < allCommands.length; i++)
        {
            allCommands[i] = true;
	    actions[i] = BhaashikTableAction.createAction(this, i);
        }

        if(appliedCommands != null)
        {
            for(int i = 0; i < allCommands.length; i++)
                allCommands[i] = false;
            
            for(int i = 0; i < appliedCommands.length; i++)
            {
                int cmd = appliedCommands[i];
                allCommands[cmd] = true;

		JMenuItem mi = new JMenuItem();
		mi.setAction(actions[cmd]);
		tableJPopupMenu.add(mi);

		JButton jb = new JButton(actions[cmd]);
		jb.setAction(actions[cmd]);
		commandsJPanel.add(jb);
            }
            
            ((GridLayout) commandsJPanel.getLayout()).setRows(appliedCommands.length);
            ((GridLayout) commandsJPanel.getLayout()).setVgap(4);
        }
        else
        {
            for(int i = 0; i < allCommands.length; i++)
            {
                JMenuItem mi = new JMenuItem();
                mi.setAction(actions[i]);
                tableJPopupMenu.add(mi);

                JButton jb = new JButton(actions[i]);
                jb.setAction(actions[i]);
                commandsJPanel.add(jb);
            }
            
            ((GridLayout) commandsJPanel.getLayout()).setRows(allCommands.length);
            ((GridLayout) commandsJPanel.getLayout()).setVgap(4);
        }

        commandButtonsShown = true;
    }
    
    public void setModel(BhaashikTableModel tbl)
    {
        tableJTable.setModel(tbl);
        
        tableModel = tbl;
        
//        setSortable(sortable);
//        
//        if(getSortable())
//        {
//            TableSorter sorter = new TableSorter(tableModel);
//            ((TableSorter) tableJTable.getModel()).setTableModel(sorter);
//        }
//        else
//        {
//            tableJTable.setModel(tbl);
//        }
//        
//        BhaashikTableModel sch = tbl.getSchema();
//        
//        if(sch != null && sch.getColumnIndex("DataType") >= 0)
//        {
//            int dtCol = sch.getColumnIndex("DataType");
//
//            if(dtCol >= 0)
//            {
//                int rcount = sch.getRowCount();
//                int dvCol = sch.getColumnIndex("EnumValues");
//
//                for (int i = 0; i < rcount; i++)
//                {
//                    String dtype = (String) sch.getValueAt(i, dtCol);
//                    
//                    if(dtype.equals("Enum"))
//                    {
//                        String colName = (String) sch.getValueAt(i, 0);
//                        int colIndex = tbl.getColumnIndex(colName);
//                        
//                        JComboBox comboBox = new JComboBox();
//                        comboBox.setEditable(true);
//                        
//                        TableColumn tcol = getJTable().getColumnModel().getColumn(colIndex);
//                        
//                        String dvals = (String) sch.getValueAt(i, dvCol);
//                        
//                        String enumVals[] = dvals.split("\\|\\|");
//                        
//                        for (int j = 0; j < enumVals.length; j++)
//                        {
//                            comboBox.addItem(enumVals[j]);
//                        }
//                        
//                        comboBox.setSelectedIndex(0);
//                        
//                        tcol.setCellEditor(new DefaultCellEditor(comboBox));
//
//                        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
//                        renderer.setToolTipText("Click for selecting a value");
//                        tcol.setCellRenderer(renderer);
//                    }
//                }
//            }
//        }
    }

    public BhaashikTableModel getModel()
    {
        return (BhaashikTableModel) tableJTable.getModel();

//        BhaashikTableModel ret = null;
//        
//        if(getSortable())
//        {
//            ret = (BhaashikTableModel) ((TableSorter) tableJTable.getModel()).getTableModel();
//        }
//        else
//        {
//            ret = (BhaashikTableModel) tableJTable.getModel();
//        }
//        
//        return ret;
    }

    public void changeModel(BhaashikTableModel tbl)
    {
	UtilityFunctions.setComponentFont(tableJTable, langEnc);

//        tableJTable.setModel(table);
        setModel(tbl);

        setSortable(sortable);
            
        MouseListener popupListener = new PopupListener(tableJPopupMenu);
        tableJTable.addMouseListener(popupListener);
        
        tableJTable.setDefaultEditor(String.class, new BhaashikTableCellEditor(langEnc));

        doLayout();
    }
    
    public JTable getJTable()
    {
        return tableJTable;
    }
    
    public void showCommandButtons(boolean b)
    {
        commandsJPanel.setVisible(b);
    }
    
    /**
     * Action implementations which are called from Actions
     */

    public void connectToDB(EventObject e)
    {
        ConnectToDBJPanel dbConnPanel = DatabaseUtils.connectToDatabase(owner);
        
        connection = dbConnPanel.getConnection();

        String tableName = dbConnPanel.getTableName();
        
        if(tableName != null && tableName.equals("") == false)
        {
            tableModel = new BhaashikTableModel(connection, tableName);
            tableJTable.setModel(tableModel);
            tableJTable.setVisible(false);
            tableJTable.setVisible(true);
        }
    }

    public void openTable(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        
        if(stm.isInDatabaseMode())
        {
        }
        else
        {
            JFileChooser chooser = new JFileChooser();
            int returnVal = chooser.showOpenDialog(this);
            
            if(returnVal == JFileChooser.APPROVE_OPTION)
            {
                String tableFile = chooser.getSelectedFile().getAbsolutePath();

                String charset = JOptionPane.showInputDialog(parentComponent, GlobalProperties.getIntlString("Please_enter_the_charset:"), GlobalProperties.getIntlString("UTF-8"));

                try
                {
                    tableModel = new BhaashikTableModel(tableFile, charset);
                    changeModel(tableModel);
              }
                catch(Exception ex)
                {
                    JOptionPane.showMessageDialog(parentComponent, GlobalProperties.getIntlString("Error_opening_file:_") + tableFile, GlobalProperties.getIntlString("Error"), JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }            
            }
        }
    }

    public void setSortable(EventObject e)
    {
        setSortable(!sortable);
    }
    
    public void addTableColumn(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        
        if(stm != null)
        {
            JTable jt = getJTable();
            
            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();
            
            String colName = JOptionPane.showInputDialog(parentComponent, GlobalProperties.getIntlString("Please_enter_the_column_name:"));
            stm.addColumn(colName);
        }
    }
    
    public void addTableRow(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        
        if(stm != null)
        {
            JTable jt = getJTable();
            
            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();
            
	    stm.addRow();
        }
    }
    
    public void copyTableData(EventObject e)
    {
        BhaashikTableModel stm = getModel();

        JTable jt = getJTable();

        if(jt.getRowSelectionAllowed() == false && jt.getColumnSelectionAllowed() == false)
            return;
        
        if(stm != null)
        {
            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();
            
            String str = "";

            if(jt.getRowSelectionAllowed() && jt.getColumnSelectionAllowed() == false)
            {
                int rows[] = jt.getSelectedRows();

                for(int i = 0; i < rows.length; i++)
                {
                    str += stm.rowToString(rows[i], "\t") + "\n";
                }
            }
            else if(jt.getRowSelectionAllowed() == false && jt.getColumnSelectionAllowed())
            {
                int cols[] = jt.getSelectedColumns();
                
                if(cols.length <= 0)
                    return;
                
                int rcount = stm.getRowCount();

                Vector cvecs = new Vector(cols.length);
                
                for(int i = 0; i < cols.length; i++)
                {
                    cvecs.add(stm.getColumn(cols[i]));
                }

                for(int i = 0; i < rcount; i++)
                {
                    for(int j = 0; j < cols.length; j++)
                    {
                        if(j < cols.length - 1)
                            str += ((Vector) cvecs.get(j)).get(i) + "\t";
                        else
                            str += ((Vector) cvecs.get(j)).get(i);
                    }
                    
                    str += "\n";
                }
            }
            else
            {
                int rfrom = jt.getSelectedRow();
                int cfrom = jt.getSelectedColumn();
                
                if(rfrom == -1 || cfrom == -1)
                    return;

                int rows[] = jt.getSelectedRows();
                int cols[] = jt.getSelectedColumns();
                
                for(int i = 0; i < rows.length; i++)
                {
                    for(int j = 0; j < cols.length; j++)
                    {
                        if(j < cols.length - 1)
                            str += stm.getValueAt(rfrom + i, cfrom + j) + "\t";
                        else
                            str += stm.getValueAt(rfrom + i, cfrom + j);
                    }
                    
                    str += "\n";
                }
            }
            
            BhaashikStringDataTransfer tableTransfer = new BhaashikStringDataTransfer();
            tableTransfer.setClipboardContents(str);
        }
    }
    
    public void cutTableData(EventObject e)
    {
        BhaashikTableModel stm = getModel();

        JTable jt = getJTable();

        if(jt.getRowSelectionAllowed() == false && jt.getColumnSelectionAllowed() == false)
            return;
        
        if(stm != null)
        {
            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();
            
            String str = "";

            if(jt.getRowSelectionAllowed() && jt.getColumnSelectionAllowed() == false)
            {
                int rows[] = jt.getSelectedRows();
                
                if(rows.length <= 0)
                    return;

                for(int i = 0; i < rows.length; i++)
                {
                    str += stm.rowToString(rows[i], "\t") + "\n";
                    stm.clearRowData(rows[i]);
                }
            }
            else if(jt.getRowSelectionAllowed() == false && jt.getColumnSelectionAllowed())
            {
                int cols[] = jt.getSelectedColumns();
                
                if(cols.length <= 0)
                    return;

                int rcount = stm.getRowCount();

                Vector cvecs = new Vector(cols.length);
                
                for(int i = 0; i < cols.length; i++)
                {
                    cvecs.add(stm.getColumn(cols[i]));
                }

                for(int i = 0; i < rcount; i++)
                {
                    for(int j = 0; j < cols.length; j++)
                    {
                        if(j < cols.length - 1)
                            str += ((Vector) cvecs.get(j)).get(i) + "\t";
                        else
                            str += ((Vector) cvecs.get(j)).get(i);
                    }
                    
                    str += "\n";
                }

                for(int i = 0; i < cols.length; i++)
                    stm.clearColData(cols[i]);
            }
            else
            {
                int rfrom = jt.getSelectedRow();
                int cfrom = jt.getSelectedColumn();
                
                if(rfrom == -1 || cfrom == -1)
                    return;

                int rows[] = jt.getSelectedRows();
                int cols[] = jt.getSelectedColumns();
                
                for(int i = 0; i < rows.length; i++)
                {
                    for(int j = 0; j < cols.length; j++)
                    {
                        if(j < cols.length - 1)
                            str += stm.getValueAt(rfrom + i, cfrom + j) + "\t";
                        else
                            str += stm.getValueAt(rfrom + i, cfrom + j);
                        
                        stm.setValueAt("", rfrom + i, cfrom + j);
                    }
                    
                    str += "\n";
                }
            }
            
            BhaashikStringDataTransfer tableTransfer = new BhaashikStringDataTransfer();
            tableTransfer.setClipboardContents(str);
        }
    }
    
    public void deleteTableColumn(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        
        if(stm != null)
        {
            JTable jt = getJTable();
            
            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();
            
            int cols[] = jt.getSelectedColumns();
            
            if(cols.length <= 0)
                return;
            
            TableColumn tcols[] = new TableColumn[cols.length];
            
            for(int i = 0; i < cols.length; i++)
            {
                tcols[i] = jt.getColumnModel().getColumn(cols[i]);
            }
            
            for(int i = 0; i < cols.length; i++)
            {
                TableColumn tc = tcols[i];
                int columnModelIndex = tc.getModelIndex();

                if(columnModelIndex >= 0 && jt.getColumnCount() > 1)
                {
                    jt.removeColumn(tc);
                    jt.setAutoCreateColumnsFromModel(false);
                    
                    // Correct the model indices in the TableColumn objects
                    // by decrementing those indices that follow the deleted column
                    Enumeration enm = jt.getColumnModel().getColumns();
                    for (; enm.hasMoreElements(); )
                    {
                        tc = (TableColumn) enm.nextElement();
                        
                        if (tc.getModelIndex() >= columnModelIndex) {
                            tc.setModelIndex(tc.getModelIndex() - 1);
                        }
                    }
                    
                    stm.removeColumn(columnModelIndex);
                    
//                    if(getSortable())
//                    {
//                        ((TableSorter) jt.getModel()).clearSortingState();
//                        ((TableSorter) jt.getModel()).fireTableDataChanged();
//                    }

                    jt.setAutoCreateColumnsFromModel(true);
                }
            }
        }
    }
    
    public void deleteTableRow(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        
        if(stm != null)
        {
            JTable jt = getJTable();
            int rows = jt.getSelectedRowCount();
            
            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();
            
            for(int i = 0; i < rows; i++)
            {
                int r = jt.getSelectedRow();

//                if(r >= 0 && jt.getRowCount() > 1)
                if(r >= 0 && jt.getRowCount() > 0)
                {
		    if(mode == FS_MODE)
		    {
			if(FeatureStructuresImpl.getFSProperties().isMandatory((String) stm.getValueAt(r, 0)) == false)
	                    stm.removeRow(r);
			else
			    JOptionPane.showMessageDialog(parentComponent, GlobalProperties.getIntlString("A_mandatory_attribute_cannot_be_deleted."), GlobalProperties.getIntlString("Error"), JOptionPane.ERROR_MESSAGE);
		    }
		    else
                        stm.removeRow(r);
                }
            }
        }
    }
    
    public void editTable(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        
        if(stm != null)
        {
            JTable jt = getJTable();
            
	    Action a = ((AbstractButton) e.getSource()).getAction();
	    
            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();
            
            if(stm.getEditable())
            {
                a.putValue(Action.NAME, GlobalProperties.getIntlString("Edit_On"));
                stm.setEditable(false);
            }
            else
            {
                a.putValue(Action.NAME, GlobalProperties.getIntlString("Edit_Off"));
                stm.setEditable(true);
            }
        }
    }
    
    public void insertTableColumn(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        
        if(stm != null)
        {
            JTable jt = getJTable();
            
            int col = jt.getSelectedColumn();
            
            if(col == -1)
                return;

            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();

            String colName = JOptionPane.showInputDialog(parentComponent, GlobalProperties.getIntlString("Please_enter_the_column_name:"));

            jt.setAutoCreateColumnsFromModel(false);
            
            jt.getColumnModel().addColumn(new TableColumn());
            jt.getColumnModel().moveColumn(jt.getColumnModel().getColumnCount() - 1, col);
            jt.getColumnModel().getColumn(col).setModelIndex(col);

            // Correct the model indices in the TableColumn objects
            // by decrementing those indices that follow the deleted column
            Enumeration enm = jt.getColumnModel().getColumns();
            for (; enm.hasMoreElements(); )
            {
                TableColumn tc = (TableColumn) enm.nextElement();

                if (tc.getModelIndex() > col) {
                    tc.setModelIndex(tc.getModelIndex() + 1);
                }
            }

            stm.insertColumn(col, colName);

//            if(getSortable())
//            {
//                ((TableSorter) jt.getModel()).clearSortingState();
//                ((TableSorter) jt.getModel()).fireTableDataChanged();
//            }

            jt.setAutoCreateColumnsFromModel(true);
        }
    }
    
    public void insertTableRow(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        
        if(stm != null)
        {
            JTable jt = getJTable();
            int r = jt.getSelectedRow();
            
            if(r == -1)
                return;
            
            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();
            
            
	    if(mode == FS_MODE)
	    {
		if(FeatureStructuresImpl.getFSProperties().isMandatory((String) stm.getValueAt(r, 0)) == false)
                    stm.insertRow(r);
		else
		    addTableRow(e);
	    }
	    else
                stm.insertRow(r);
        }
    }
    
    public void renameTableColumn(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        
        if(stm != null)
        {
            JTable jt = getJTable();
            
            int col = jt.getSelectedColumn();
            
            if(col == -1)
                return;

            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();

            String colName = JOptionPane.showInputDialog(parentComponent, GlobalProperties.getIntlString("Please_enter_the_column_name:"));

            stm.setColumnIdentifier(col, colName);
            
            stm.fireTableStructureChanged();
            
//            JTableHeader th = jt.getTableHeader();
//            
//            TableColumnModel tcm = th.getColumnModel();
//            
//            TableColumn tc = tcm.getColumn(col);
//            
//            tc.setHeaderValue( colName );
//            th.repaint();
        }
    }    
    public void pasteTableData(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        
        JTable jt = getJTable();

        if(jt.getRowSelectionAllowed() == false && jt.getColumnSelectionAllowed() == false)
            return;
        
        if(stm != null)
        {
            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();

            BhaashikStringDataTransfer tableTransfer = new BhaashikStringDataTransfer();
            String str = tableTransfer.getClipboardContents();

            if(jt.getRowSelectionAllowed() && jt.getColumnSelectionAllowed() == false)
            {
                int r = jt.getSelectedRow();
                
                if(r == -1)
                    return;
                
                int rcount = stm.getRowCount();

                String srows[] = str.split("\n");

                for(int i = 0; i < srows.length && r < rcount; i++)
                {
                    stm.stringToRow(r++, srows[i], "\t");
                }
            }
            else if(jt.getRowSelectionAllowed() == false && jt.getColumnSelectionAllowed())
            {
                int c = jt.getSelectedColumn();
                
                if(c == -1)
                    return;
                
                int rcount = stm.getRowCount();
                int ccount = stm.getColumnCount();

                String srows[] = str.split("\n");

                for(int i = 0; i < srows.length && i < rcount; i++)
                {
                    String scols[] = srows[i].split("\t");
                    
                    for(int j = 0; j < scols.length && c + j < ccount; j++)
                    {
                        stm.setValueAt(scols[j], i, c + j);
                    }
                }
            }
            else
            {
                int r = jt.getSelectedRow();
                int c = jt.getSelectedColumn();
                
                if(r == -1 || c == -1)
                    return;

                int rcount = stm.getRowCount();
                int ccount = stm.getColumnCount();

                String srows[] = str.split("\n");

                for(int i = 0; i < srows.length && r + i < rcount; i++)
                {
                    String scols[] = srows[i].split("\t");
                    
                    for(int j = 0; j < scols.length && c + j < ccount; j++)
                    {
                        stm.setValueAt(scols[j], r + i, c + j);
                    }
                }
            }
        }
    }
    
    public void printTable(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        
        if(stm != null)
        {
            JTable jt = getJTable();
            
            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();
            
            try {
                jt.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void saveTable(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        
        if(stm != null)
        {
            JTable jt = getJTable();
            
            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();
            
            try {
                stm.save();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(parentComponent, GlobalProperties.getIntlString("Error_resetting_from_file._Perhaps_the_file_name_and_the_charset_are_not_defined."), GlobalProperties.getIntlString("Error"), JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void saveTableAs(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        
        if(stm != null)
        {
            JTable jt = getJTable();
            
            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();
            
            try {
                JFileChooser chooser = new JFileChooser();
                int returnVal = chooser.showSaveDialog(this);
                if(returnVal == JFileChooser.APPROVE_OPTION)
                {
                    String tableFile = chooser.getSelectedFile().getAbsolutePath();

                    String charset = JOptionPane.showInputDialog(parentComponent, GlobalProperties.getIntlString("Please_enter_the_charset:"), GlobalProperties.getIntlString("UTF-8"));
                    stm.save(tableFile, charset);
                }
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(parentComponent, GlobalProperties.getIntlString("Error_resetting_from_file._Perhaps_the_file_name_and_the_charset_are_not_defined."), GlobalProperties.getIntlString("Error"), JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void markForSaving(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        stm.isToBeSaved(!stm.isToBeSaved());
    }
    
    public void setTableSelectionMode(EventObject e)
    {
        Object[] possibleValues = { GlobalProperties.getIntlString("Row_Selection"), GlobalProperties.getIntlString("Column_Selection"), GlobalProperties.getIntlString("Cell_Selection")};
        Object selectedValue = JOptionPane.showInputDialog(parentComponent,
                GlobalProperties.getIntlString("Select_the_table_selection_mode:"), GlobalProperties.getIntlString("Selection_Mode"), JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, possibleValues[0]);

        if(selectedValue == null || selectedValue .equals("") == true)
            return;
        
        if(selectedValue.equals(GlobalProperties.getIntlString("Row_Selection")))
	    setTableSelectionMode(0);
        else if(selectedValue.equals(GlobalProperties.getIntlString("Column_Selection")))
	    setTableSelectionMode(1);
        else if(selectedValue.equals(GlobalProperties.getIntlString("Cell_Selection")))
	    setTableSelectionMode(2);
    }
    
    public void setTableSelectionMode(int m)
    {
        JTable jt = getJTable();
        
        if(m < 0 || m > 2)
            return;
        
        if(m == 0)
        {
            jt.setCellSelectionEnabled(false);
            jt.firePropertyChange("cellSelectionEnabled", true, false);
            jt.setColumnSelectionAllowed(false);
            jt.firePropertyChange("columnSelectionAllowed", true, false);
            jt.setRowSelectionAllowed(true);
            jt.firePropertyChange("rowSelectionAllowed", false, true);
        }
        else if(m == 1)
        {
            jt.setCellSelectionEnabled(false);
            jt.firePropertyChange("cellSelectionEnabled", true, false);
            jt.setRowSelectionAllowed(false);
            jt.firePropertyChange("rowSelectionAllowed", true, false);
            jt.setColumnSelectionAllowed(true);
            jt.firePropertyChange("columnSelectionAllowed", false, true);
        }
        else if(m == 2)
        {
            jt.setCellSelectionEnabled(true);
            jt.firePropertyChange("cellSelectionEnabled", false, true);
            jt.setRowSelectionAllowed(true);
            jt.firePropertyChange("rowSelectionAllowed", false, true);
            jt.setColumnSelectionAllowed(true);
            jt.firePropertyChange(GlobalProperties.getIntlString("columnSelectionAllowed"), false, true);
        }
    }
    
    public void tableClearAll(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        
        if(stm != null)
        {
            JTable jt = getJTable();
            
            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();

            stm.clearData();
        }
    }
    
    public void tableResetAll(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        
        if(stm != null)
        {
            JTable jt = getJTable();
            
            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();
            
            stm.clear();
            try {
                stm.read();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(parentComponent, "Error resetting from file. Perhaps the file name and the charset are not defined.", GlobalProperties.getIntlString("Error"), JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void tableColumnClear(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        
        if(stm != null)
        {
            JTable jt = getJTable();
            
            int c = jt.getSelectedColumn();
            
            if(c == -1)
                return;
            
            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();

            stm.clearColData(c);
        }
    }
    
    public void tableRowClear(EventObject e)
    {
        BhaashikTableModel stm = getModel();
        
        if(stm != null)
        {
            JTable jt = getJTable();
            
            int r = jt.getSelectedRow();
            
            if(r == -1)
                return;
            
            if(jt.isEditing() == true)
                jt.getCellEditor().stopCellEditing();

            stm.clearRowData(r);
        }
    }
      
    public void selectLanguage(EventObject e)
    {
        langEnc = BhaashikLanguages.selectLanguage(this, langEnc);
	UtilityFunctions.setComponentFont(tableJTable, langEnc);
        tableJTable.setDefaultEditor(String.class, new BhaashikTableCellEditor(langEnc));
    }
    
    public void selectEncoding(EventObject e)
    {
        langEnc = BhaashikLanguages.selectEncoding(this, langEnc);
	UtilityFunctions.setComponentFont(tableJTable, langEnc);
        tableJTable.setDefaultEditor(String.class, new BhaashikTableCellEditor(langEnc));
    }
  
    public void selectInputMethod(EventObject e)
    {
        String im = BhaashikLanguages.selectInputMethod(this);
        
        if(owner != null)
            BhaashikLanguages.changeInputMethod(owner, im);
        else if(dialog != null)
            BhaashikLanguages.changeInputMethod(dialog, im);
    }
    
    public void showKBMap(EventObject e)
    {
        BhaashikLanguages.showKBMap(this);
    }
    
    public void increaseFontSize(EventObject e)
    {
        UtilityFunctions.increaseFontSize(tableJTable);
    }
    
    public void decreaseFontSize(EventObject e)
    {
        UtilityFunctions.decreaseFontSize(tableJTable);
    }
    
    public void increaseRowSize(EventObject e)
    {
        tableJTable.setRowHeight(tableJTable.getRowHeight() + 2);
    }
    
    public void decreaseRowSize(EventObject e)
    {
        if(tableJTable.getRowHeight() > 2)
            tableJTable.setRowHeight(tableJTable.getRowHeight() - 2);
    }
    
    public void showCommandButtons(EventObject e)
    {
        if(commandButtonsShown)
        {
            showCommandButtons(false);
            commandButtonsShown = false;
        }
        else
        {
            showCommandButtons(true);
            commandButtonsShown = true;
        }
    }
    
    /*
     * Operations for creating table panels for specific purposes.
     */
    public static BhaashikTableJPanel createTableDefaultJPanel(BhaashikTableModel ft, String lang)
    {
        BhaashikTableJPanel bhaashikTableJPanel = new BhaashikTableJPanel(ft, false, null, BhaashikTableJPanel.DEFAULT_MODE, lang);
	bhaashikTableJPanel.setTableSelectionMode(0);

	return bhaashikTableJPanel;
    }

    public static BhaashikTableJPanel createTableDisplayJPanel(BhaashikTableModel ft, String lang, boolean sortable)
    {
        int cmds[] = new int[8];
        cmds[0] = BhaashikTableAction.SAVE_TABLE_AS;
        cmds[1] = BhaashikTableAction.TABLE_COPY;
	cmds[2] = BhaashikTableAction.PRINT_TABLE;
	cmds[3] = BhaashikTableAction.SET_TABLE_SEL_MODE;
	cmds[4] = BhaashikTableAction.INCREASE_FONT_SIZE;
	cmds[5] = BhaashikTableAction.DECREASE_FONT_SIZE;
	cmds[6] = BhaashikTableAction.INCREASE_ROW_SIZE;
	cmds[7] = BhaashikTableAction.DECREASE_ROW_SIZE;

        BhaashikTableJPanel bhaashikTableJPanel = new BhaashikTableJPanel(ft, sortable, cmds, BhaashikTableJPanel.CUSTOM_MODE, lang);
	bhaashikTableJPanel.setTableSelectionMode(0);
        ((BhaashikTableJPanel) bhaashikTableJPanel).showCommandButtons(false);

	return bhaashikTableJPanel;        
    }

    public static BhaashikTableJPanel createDatabaseSchemaTableJPanel(BhaashikTableModel ft, String lang, boolean sortable)
    {
        int cmds[] = new int[7];
        cmds[0] = BhaashikTableAction.SAVE_TABLE;
//        cmds[1] = BhaashikTableAction.SAVE_TABLE_AS;
        cmds[1] = BhaashikTableAction.ADD_ROW;
        cmds[2] = BhaashikTableAction.ADD_COL;
        cmds[3] = BhaashikTableAction.RENAME_COL;
        cmds[4] = BhaashikTableAction.DEL_ROW;
        cmds[5] = BhaashikTableAction.DEL_COL;
        cmds[6] = BhaashikTableAction.TABLE_COPY;
//	cmds[8] = BhaashikTableAction.PRINT_TABLE;
//	cmds[9] = BhaashikTableAction.SET_TABLE_SEL_MODE;
//	cmds[10] = BhaashikTableAction.INCREASE_FONT_SIZE;
//	cmds[11] = BhaashikTableAction.DECREASE_FONT_SIZE;
//	cmds[12] = BhaashikTableAction.INCREASE_ROW_SIZE;
//	cmds[13] = BhaashikTableAction.DECREASE_ROW_SIZE;
//        columnName.add("Column Name");
//        columnName.add("Data Type");
//        columnName.add("Primary Key");
//        columnName.add("Not NULL");
//        columnName.add("Unique");
//        columnName.add("Default");
//        columnName.add("Check");        

        String[] colNames = new String[]{"Column Name", "Data Type", "Primary Key", "Not NULL", "Unique", "Default", "Check"};

//        if(replace)
//            colNames = new String[]{GlobalProperties.getIntlString("Tag"), GlobalProperties.getIntlString("New_Tag"), GlobalProperties.getIntlString("Text"), GlobalProperties.getIntlString("New_Text"), GlobalProperties.getIntlString("Attribute_Name"), GlobalProperties.getIntlString("New_Name"), GlobalProperties.getIntlString("Create_Attribute"), GlobalProperties.getIntlString("Attribute_Value"), GlobalProperties.getIntlString("New_Value")};

    	ft.setColumnIdentifiers(colNames);

//        BhaashikTableJPanel bhaashikTableJPanel = new BhaashikTableJPanel(ft, sortable, cmds, BhaashikTableJPanel.DATABASE_MODE, lang);
        BhaashikTableJPanel bhaashikTableJPanel = new BhaashikTableJPanel(ft, false, cmds, BhaashikTableJPanel.MINIMAL_MODE, lang);
//	bhaashikTableJPanel.setTableSelectionMode(0);
        ((BhaashikTableJPanel) bhaashikTableJPanel).showCommandButtons(true);

	return bhaashikTableJPanel;        
    }

    public static BhaashikTableJPanel createDatabaseTableJPanel(BhaashikTableModel ft, String lang, boolean sortable)
    {
        int cmds[] = new int[7];
        cmds[0] = BhaashikTableAction.SAVE_TABLE;
//        cmds[1] = BhaashikTableAction.SAVE_TABLE_AS;
        cmds[1] = BhaashikTableAction.ADD_ROW;
        cmds[2] = BhaashikTableAction.ADD_COL;
        cmds[3] = BhaashikTableAction.RENAME_COL;
        cmds[4] = BhaashikTableAction.DEL_ROW;
        cmds[5] = BhaashikTableAction.DEL_COL;
        cmds[6] = BhaashikTableAction.TABLE_COPY;
//	cmds[8] = BhaashikTableAction.PRINT_TABLE;
//	cmds[9] = BhaashikTableAction.SET_TABLE_SEL_MODE;
//	cmds[10] = BhaashikTableAction.INCREASE_FONT_SIZE;
//	cmds[11] = BhaashikTableAction.DECREASE_FONT_SIZE;
//	cmds[12] = BhaashikTableAction.INCREASE_ROW_SIZE;
//	cmds[13] = BhaashikTableAction.DECREASE_ROW_SIZE;

        BhaashikTableJPanel bhaashikTableJPanel = new BhaashikTableJPanel(ft, sortable, cmds, BhaashikTableJPanel.DATABASE_MODE, lang);
	bhaashikTableJPanel.setTableSelectionMode(0);
        ((BhaashikTableJPanel) bhaashikTableJPanel).showCommandButtons(true);

	return bhaashikTableJPanel;        
    }

    public static BhaashikTableJPanel createTableDisplayJPanel(BhaashikTableModel ft, String lang)
    {
        return createTableDisplayJPanel(ft, lang, false);
    }

    public static BhaashikTableJPanel createUserManagerJPanel(BhaashikTableModel ft, String lang)
    {
        int cmds[] = new int[8];
        cmds[0] = BhaashikTableAction.SAVE_TABLE;
        cmds[1] = BhaashikTableAction.SAVE_TABLE_AS;
        cmds[2] = BhaashikTableAction.ADD_ROW;
        cmds[3] = BhaashikTableAction.ADD_COL;
        cmds[4] = BhaashikTableAction.RENAME_COL;
        cmds[5] = BhaashikTableAction.DEL_ROW;
        cmds[6] = BhaashikTableAction.DEL_COL;
        cmds[7] = BhaashikTableAction.TABLE_COPY;
//	cmds[8] = BhaashikTableAction.PRINT_TABLE;
//	cmds[9] = BhaashikTableAction.SET_TABLE_SEL_MODE;
//	cmds[10] = BhaashikTableAction.INCREASE_FONT_SIZE;
//	cmds[11] = BhaashikTableAction.DECREASE_FONT_SIZE;
//	cmds[12] = BhaashikTableAction.INCREASE_ROW_SIZE;
//	cmds[13] = BhaashikTableAction.DECREASE_ROW_SIZE;
//        columnName.add("Column Name");
//        columnName.add("Data Type");
//        columnName.add("Primary Key");
//        columnName.add("Not NULL");
//        columnName.add("Unique");
//        columnName.add("Default");
//        columnName.add("Check");        

        String[] colNames = new String[]{"First Name", "Last Name", "Username", "E-mail Address",
            "Phone Number", "Organisation", "Language(s)", "Academic Backgroud",};

//        if(replace)
//            colNames = new String[]{GlobalProperties.getIntlString("Tag"), GlobalProperties.getIntlString("New_Tag"), GlobalProperties.getIntlString("Text"), GlobalProperties.getIntlString("New_Text"), GlobalProperties.getIntlString("Attribute_Name"), GlobalProperties.getIntlString("New_Name"), GlobalProperties.getIntlString("Create_Attribute"), GlobalProperties.getIntlString("Attribute_Value"), GlobalProperties.getIntlString("New_Value")};

    	ft.setColumnIdentifiers(colNames);

//        BhaashikTableJPanel bhaashikTableJPanel = new BhaashikTableJPanel(ft, sortable, cmds, BhaashikTableJPanel.DATABASE_MODE, lang);
        BhaashikTableJPanel bhaashikTableJPanel = new BhaashikTableJPanel(ft, false, cmds, BhaashikTableJPanel.MINIMAL_MODE, lang);
//	bhaashikTableJPanel.setTableSelectionMode(0);
        ((BhaashikTableJPanel) bhaashikTableJPanel).showCommandButtons(true);

	return bhaashikTableJPanel;        
    }

    public static BhaashikTableJPanel createPermissionsJPanel()
    {
	return null;
    }

    public static BhaashikTableJPanel createResourceManagerJPanel()
    {
	return null;
    }

    public static BhaashikTableJPanel createFeatureTableJPanel(BhaashikTableModel ft, String lang)
    {
        int cmds[] = new int[2];
        cmds[0] = BhaashikTableAction.DEL_ROW;
        cmds[1] = BhaashikTableAction.ADD_ROW;
//        cmds[2] = TableAction.INSERT_ROW;

	ft.setColumnIdentifiers(new String[] {GlobalProperties.getIntlString("Feature"), GlobalProperties.getIntlString("Value")});
        BhaashikTableJPanel bhaashikTableJPanel = new BhaashikTableJPanel(ft, false, cmds, BhaashikTableJPanel.FS_MODE, lang);
//        bhaashikTableJPanel.setLayout(new javax.swing.BoxLayout(bhaashikTableJPanel, javax.swing.BoxLayout.X_AXIS));
//        bhaashikTableJPanel.setVisible(true);
        ((BhaashikTableJPanel) bhaashikTableJPanel).showCommandButtons(true);

	return bhaashikTableJPanel;
    }

    public static BhaashikTableJPanel createFindOptionsTableJPanel(BhaashikTableModel ft, String lang, boolean replace)
    {
        int cmds[] = new int[7];
        cmds[0] = BhaashikTableAction.MARK_FOR_SAVING;
        cmds[1] = BhaashikTableAction.ADD_ROW;
        cmds[2] = BhaashikTableAction.DEL_ROW;
        cmds[3] = BhaashikTableAction.INCREASE_FONT_SIZE;
        cmds[4] = BhaashikTableAction.DECREASE_FONT_SIZE;
        cmds[5] = BhaashikTableAction.INCREASE_ROW_SIZE;
        cmds[6] = BhaashikTableAction.DECREASE_ROW_SIZE;

        String[] colNames = new String[]{GlobalProperties.getIntlString("Tag"), GlobalProperties.getIntlString("Text"), GlobalProperties.getIntlString("Attribute_Name"), GlobalProperties.getIntlString("Attribute_Value")};

        if(replace)
            colNames = new String[]{GlobalProperties.getIntlString("Tag"), GlobalProperties.getIntlString("New_Tag"), GlobalProperties.getIntlString("Text"), GlobalProperties.getIntlString("New_Text"), GlobalProperties.getIntlString("Attribute_Name"), GlobalProperties.getIntlString("New_Name"), GlobalProperties.getIntlString("Create_Attribute"), GlobalProperties.getIntlString("Attribute_Value"), GlobalProperties.getIntlString("New_Value")};

    	ft.setColumnIdentifiers(colNames);
        
        BhaashikTableJPanel bhaashikTableJPanel = new BhaashikTableJPanel(ft, false, cmds, BhaashikTableJPanel.MINIMAL_MODE, lang);
//        bhaashikTableJPanel.setLayout(new javax.swing.BoxLayout(bhaashikTableJPanel, javax.swing.BoxLayout.X_AXIS));
//        bhaashikTableJPanel.setVisible(true);
        ((BhaashikTableJPanel) bhaashikTableJPanel).showCommandButtons(true);

	return bhaashikTableJPanel;
    }

    public static BhaashikTableJPanel createPropertyTokensJPanel()
    {
	return null;
    }

    public static BhaashikTableJPanel createKVPropertiesJPanel()
    {
	return null;
    }

    public static BhaashikTableJPanel createWordTypeTableJPanel()
    {
	return null;
    }

    public static BhaashikTableJPanel createSentenceAlignmentJPanel()
    {
	return null;
    }

    public static BhaashikTableJPanel createNGramLMJPanel()
    {
	return null;
    }

    public static BhaashikTableJPanel createKwikJPanel(BhaashikTableModel kwikTable, String langEnc)
    {
        kwikTable.setColumnIdentifiers(new String[]{GlobalProperties.getIntlString("Left_Context"), GlobalProperties.getIntlString("Keyword"), GlobalProperties.getIntlString("Right_Context")});

        BhaashikTableJPanel bhaashikTableJPanel = createTableDisplayJPanel(kwikTable, langEnc, true);
        
	return bhaashikTableJPanel;
    }
    
    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        JFrame frame = new JFrame(GlobalProperties.getIntlString("Bhaashik_Table_Editor"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BhaashikTableJPanel newContentPane = new BhaashikTableJPanel(new BhaashikTableModel(1, 1), false, null, BhaashikTableJPanel.DEFAULT_MODE, GlobalProperties.getIntlString("hin::utf8"));

//        JFileChooser chooser = new JFileChooser();
//        int returnVal = chooser.showOpenDialog(frame);
//        if(returnVal == JFileChooser.APPROVE_OPTION)
//        {
//            String tableFile = chooser.getSelectedFile().getAbsolutePath();
//            
//            String charset = JOptionPane.showInputDialog(frame, "Please enter the charset:", "UTF-8");
//           
//            //Create and set up the content pane.
//            BhaashikTableJPanel newContentPane = null;
//
//            try
//            {
//                newContentPane = new BhaashikTableJPanel(tableFile, charset, false, null, BhaashikTableJPanel.DEFAULT_MODE, "hin::utf8");
//            }
//            catch(Exception e)
//            {
//                JOptionPane.showMessageDialog(frame, "Error opening file.", "Error", JOptionPane.ERROR_MESSAGE);
//                e.printStackTrace();
//            }
//
//            newContentPane.owner = frame;
//            newContentPane.setOpaque(true); //content panes must be opaque
//            frame.setContentPane(newContentPane);
////            frame.addWindowListener(newContentPane);
//
////            newContentPane.setTitle(newContentPane.getTitle());
//
//            //Display the window.
//            frame.pack();
//
//            int inset = 5;
//            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//            frame.setBounds(inset, inset,
//                    screenSize.width  - inset*2,
//                    screenSize.height - inset*9);
//
//            frame.setVisible(true);
//        }
//        else
//        {
//            System.exit(0);
//        }
        
            newContentPane.owner = frame;
            newContentPane.setOpaque(true); //content panes must be opaque
            frame.setContentPane(newContentPane);
//            frame.addWindowListener(newContentPane);

//            newContentPane.setTitle(newContentPane.getTitle());

            //Display the window.
            frame.pack();

            int inset = 5;
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setBounds(inset, inset,
                    screenSize.width  - inset*2,
                    screenSize.height - inset*9);

            frame.setVisible(true);
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
        BhaashikTableModel stm = getModel();
        
        if(stm.isInDatabaseMode())
        {
            try {
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public String getTableName()
    {
        return tableModel.getName();
    }
    
    public void setTableName(String name)
    {
        tableModel.setName(name);
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
    public javax.swing.JPanel commandsJPanel;
    public javax.swing.JPopupMenu tableJPopupMenu;
    // End of variables declaration//GEN-END:variables
    
}
