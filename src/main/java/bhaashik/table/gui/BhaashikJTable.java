/*
 * BhaashikJTable.java
 *
 * Created on February 4, 2006, 6:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package bhaashik.table.gui;

import java.awt.event.MouseListener;
import java.util.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.*;
import java.awt.geom.*;
import java.util.List;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import bhaashik.GlobalProperties;
import bhaashik.corpus.parallel.Alignable;
import bhaashik.corpus.parallel.AlignmentBlock;
import bhaashik.corpus.parallel.AlignmentUnit;
import bhaashik.corpus.parallel.gui.AlignmentEvent;
import bhaashik.corpus.parallel.gui.AlignmentEventListener;
import bhaashik.corpus.parallel.gui.BhaashikAlignableAction;
import bhaashik.corpus.parallel.gui.BhaashikAlignableDataTransfer;
import bhaashik.corpus.ssf.SSFProperties;
import bhaashik.corpus.ssf.features.FeatureStructures;
import bhaashik.corpus.ssf.features.impl.FSProperties;
import bhaashik.corpus.ssf.features.impl.FeatureStructureImpl;
import bhaashik.corpus.ssf.features.impl.FeatureStructuresImpl;
import bhaashik.corpus.ssf.tree.SSFLexItem;
import bhaashik.corpus.ssf.tree.SSFNode;
import bhaashik.corpus.ssf.tree.SSFPhrase;
import bhaashik.datastr.ConcurrentLinkedHashMap;
import bhaashik.gui.common.BhaashikEvent;
import bhaashik.tree.gui.TreeDrawingEventListener;
import bhaashik.tree.gui.TreeViewerEvent;
import bhaashik.lattice.PointLattice;
import bhaashik.table.BhaashikTableModel;
import bhaashik.tree.BhaashikEdge;
import bhaashik.tree.BhaashikEdges;
import bhaashik.tree.BhaashikMutableTreeNode;
import bhaashik.tree.BhaashikTreeModel;
import bhaashik.tree.TreeViewerEventListener;
import bhaashik.util.Pair;
import bhaashik.util.UtilityFunctions;
import java.awt.event.MouseAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.tree.MutableTreeNode;

/**
 *
 * @author anil
 */
public class BhaashikJTable extends JTable implements Scrollable, MouseMotionListener, MouseListener
{
    protected BhaashikMutableTreeNode treeRoot;
    protected BhaashikTreeModel treeModel;
    protected Hashtable nodeHashtable;
    protected MouseInputAdapter rowResizer,  columnResizer = null;
    protected BhaashikEdges edges;
    protected int maxUnitIncrement = 5;
    protected boolean missingModel = false;
    protected TableDragSource ds;
    protected TableDropTarget dt;
    int mode = DEFAULT_MODE;
    public static final int DEFAULT_MODE = 0;
    public static final int TREE_MODE = 1;
    public static final int PHONETIC_SPACE_MODE = 2;
    public static final int ALIGNMENT_MODE = 3;

    protected boolean leafDependencies = false;

    protected JComponent editor;

    protected transient RowEditorModel rowEditorModel;
    protected transient RowRendererModel rowRendererModel;
    
    protected transient javax.swing.event.EventListenerList listenerListLocal = new javax.swing.event.EventListenerList();

    protected PointLattice pointLattice;
    protected AlignmentBlock alignmentBlock;

    protected BhaashikAlignableAction copyAction;
    protected BhaashikAlignableAction cutAction;
    protected BhaashikAlignableAction pasteAction;
    protected BhaashikAlignableAction labelAction;

//    protected static ConcurrentLinkedHashMap<String,KeyStroke> actionsKeyMap;
    protected static ActionMap actionsKeyMap;

    protected static InputMap customInputMap;
    protected static ActionMap customActionMap;

    protected ConcurrentLinkedHashMap cfgToDepTreeMapping;

//    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private final PropertyChangeHelper propertyChangeHelper;

    protected final Pair<Integer, Integer> savedSelectedCell = new Pair<>(0, 0);

    public Pair<Integer, Integer> getSavedSelectedCell() {
        return savedSelectedCell;
    }

    public void setSavedSelectedCell(int savedSelectedRow, int savedSelectedColumn) {
        this.savedSelectedCell.setFirst(savedSelectedRow);
        this.savedSelectedCell.setSecond(savedSelectedColumn);
    }

    /** Creates a new instance of BhaashikJTable */
    public BhaashikJTable(BhaashikTableModel mdl, int m)
    {
        super(mdl);
        this.propertyChangeHelper = new PropertyChangeHelper(this);

        // Add a mouse listener to detect clicks
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isAltDown() && e.getClickCount() == 2) {
                    propertyChangeHelper.firePropertyChange("NodeInsertedWI", null, e);
                }
            }
        });

        maxUnitIncrement = m;
//        propertyChangeSupport = new PropertyChangeSupport(this);

//        // Add a mouse listener to detect clicks
//        this.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                // Fire a property change event on mouse click
////                propertyChangeSupport.firePropertyChange("mouseClick", null, e);
//
//                if(e.isAltDown() && e.getClickCount() == 2)
//                {
////                    propertyChangeSupport.firePropertyChange("NodeInsertedWI", null, e);
//                    propertyChangeHelper.firePropertyChange("NodeInsertedWI", null, e);                
//                }
//            }
//        });
    }
    
    public BhaashikJTable(BhaashikTableModel mdl, int m, BhaashikMutableTreeNode treeRoot, boolean dndOn)
    {
        this(mdl, m);

        mode = m;
        this.treeRoot = treeRoot;
        treeModel = new BhaashikTreeModel(treeRoot);

        initDND(dndOn);
        init(dndOn);
    }

//    public BhaashikJTable(BhaashikTableModel mdl, int m, BhaashikMutableTreeNode treeRoot)
//    {
//        this(mdl, m, treeRoot, false);
//    }

    public BhaashikJTable(BhaashikTableModel mdl, int m, PointLattice pointLattice)
    {
        this(mdl, m);

        mode = PHONETIC_SPACE_MODE;
        this.pointLattice = pointLattice;
    }

    public BhaashikJTable(BhaashikTableModel mdl, int m, AlignmentBlock alignmentBlock, boolean dndOn)
    {
        this(mdl, m);

        mode = ALIGNMENT_MODE;
        this.alignmentBlock = alignmentBlock;

        initDND(dndOn);
        init(dndOn);
    }

//    public BhaashikJTable(BhaashikTableModel mdl, int m, AlignmentBlock alignmentBlock)
//    {
//        this(mdl, m, alignmentBlock, false);
//    }

    public BhaashikJTable(BhaashikTableModel mdl, int m, boolean dndOn)
    {
//        super(mdl);
        this(mdl, m);

//        maxUnitIncrement = m;

        initDND(dndOn);
//        init(true);
    }

    protected void init(boolean dndOn)
    {
        //Let the user scroll by dragging to outside the window.
        setAutoscrolls(true); //enable synthetic drag events

        if(dndOn)
            addMouseMotionListener(this);//handle mouse drags

////    Added
        addMouseListener(this);
//
    }

    protected void initDND(boolean dndOn)
    {

        edges = new BhaashikEdges();

        if(dndOn)
        {
            ds = new TableDragSource(this, DnDConstants.ACTION_COPY_OR_MOVE, mode);
            dt = new TableDropTarget(this, mode);
        }

        nodeHashtable = new Hashtable(0, 10);

//        setDropMode(DropMode.USE_SELECTION);
    }
//    // Method to add a PropertyChangeListener
//    public void addPropertyChangeListener(PropertyChangeListener listener) {
//        propertyChangeSupport.addPropertyChangeListener(listener);
//    }
//
//    // Method to remove a PropertyChangeListener
//    public void removePropertyChangeListener(PropertyChangeListener listener) {
//        propertyChangeSupport.removePropertyChangeListener(listener);
//    }


//    public void addPropertyChangeListener(PropertyChangeListener listener) {
//        propertyChangeHelper.addPropertyChangeListener(listener);
//    }
//
//    public void removePropertyChangeListener(PropertyChangeListener listener) {
//        propertyChangeHelper.removePropertyChangeListener(listener);
//    }

    public PropertyChangeHelper getPropertyChangeHelper() {
        return propertyChangeHelper;
    }
    
    public void prepareCommands()
    {
        copyAction = BhaashikAlignableAction.createAction(this, BhaashikAlignableAction.TABLE_COPY);
        cutAction = BhaashikAlignableAction.createAction(this, BhaashikAlignableAction.TABLE_CUT);
        pasteAction = BhaashikAlignableAction.createAction(this, BhaashikAlignableAction.TABLE_PASTE);
        labelAction = BhaashikAlignableAction.createAction(this, BhaashikAlignableAction.TABLE_SET_LABEL);

//        customInputMap = new InputMap();
//        customActionMap = new ActionMap();

        customInputMap = getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        customActionMap = getActionMap();

//        actionsKeyMap = new ConcurrentLinkedHashMap<String,KeyStroke>();
        actionsKeyMap = getActionMap();

        KeyStroke copyKS = KeyStroke.getKeyStroke("C");
        KeyStroke cutKS = KeyStroke.getKeyStroke("X");
        KeyStroke pasteKS = KeyStroke.getKeyStroke("V");
        KeyStroke labelKS = KeyStroke.getKeyStroke("G");
//        KeyStroke ks = KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_UP);

        customInputMap.put(copyKS, "Copy");
        customInputMap.put(cutKS, "Cut");
        customInputMap.put(pasteKS, "Paste");
        customInputMap.put(labelKS, "Label");

        customActionMap.put("Copy", copyAction);
        customActionMap.put("Cut", cutAction);
        customActionMap.put("Paste", pasteAction);
        customActionMap.put("Label", labelAction);

//        actionsKeyMap.put(copyKS, copyAction);
//        actionsKeyMap.put(pasteKS, pasteAction);

//        actionsKeyMap.put("Copy", KeyStroke.getKeyStroke("ctrl pressed C"));
//        actionsKeyMap.put("Paste", KeyStroke.getKeyStroke("ctrl pressed V"));
//
//        ks = KeyStroke.getKeyStroke("ctrl pressed C");
//        ((JPanel) editor).registerKeyboardAction(copyAction, "Copy", ks, JComponent.WHEN_FOCUSED);
//        ks = KeyStroke.getKeyStroke("ctrl pressed V");
//        ((JPanel) editor).registerKeyboardAction(pasteAction, "Paste", ks, JComponent.WHEN_FOCUSED);

//        BhaashikShortcutsData.registerShortcuts(editor, customActionMap, actionsKeyMap);
    }

    public void setEditor(JComponent ed)
    {
        editor = ed;
    }

    public void setCellObject(int r, int c, Object obj)
    {
        if(edges == null)
            return;

        String key = r + "::" + c;
        nodeHashtable.put(key, obj);
    }

    public void clearCellObjects()
    {
        nodeHashtable = new Hashtable(0, 10);
    }

    public void setEdges(BhaashikEdges edges)
    {
        this.edges = edges;
    }

    public BhaashikEdges getEdges()
    {
        return edges;
    }

    public int addEdge(BhaashikEdge e)
    {
        if(edges == null)
            return -1;

        edges.addEdge(e);

        return edges.countEdges();
    }

    public void clearEdges()
    {
        if(edges == null)
            return;

        edges.removeAllEdges();
    }

    public int countEdges()
    {
        return edges.countEdges();
    }

    public BhaashikEdge getEdge(int i)
    {
        return edges.getEdge(i);
    }

    public Object getCellObject(int r, int c)
    {
        if (mode == TREE_MODE)
        {
            String key = r + "::" + c;
            return nodeHashtable.get(key);
        }

        return this.getValueAt(r, c);
    }

    public Object getSelectedCellObject()
    {
        int r = this.getSelectedRow();
        int c = this.getSelectedColumn();

        if (mode == TREE_MODE)
        {
            String key = r + "::" + c;
            return nodeHashtable.get(key);
        }

        return this.getValueAt(r, c);
    }

    public Object getObjectModel()
    {
        if (mode == DEFAULT_MODE)
        {
            return getModel();
        } else if (mode == TREE_MODE)
        {
            return treeModel;
        }

        return null;
    }

    public BhaashikMutableTreeNode getTreeRoot()
    {
        return treeRoot;
    }

    public PointLattice getPointLattice()
    {
        return pointLattice;
    }

    public AlignmentBlock getAlignmentBlock()
    {
        return alignmentBlock;
    }

    public Cell findCellObject(Object obj)
    {
        int rcount = getRowCount();
        int ccount = getRowCount();

        for (int i = 0; i < rcount; i++)
        {
            for (int j = 0; j < ccount; j++)
            {
                Object val = getCellObject(i, j);

                if(val instanceof String)
                {
                    if(val.equals(obj))
                        return new Cell(i, j);
                }
                else
                {
                    if(val == obj)
                        return new Cell(i, j);
                }
            }
        }

        return null;
    }

    public void paintComponent(Graphics g)
    {
        clear(g);
        Graphics2D g2d = (Graphics2D) g;

        BasicStroke wideStroke = new BasicStroke(4.0f);

        float dash1[] =
        {
            10.0f
        };
        BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);

        g2d.setBackground(Color.CYAN);
        g2d.setColor(Color.RED);
        g2d.setStroke(wideStroke);
        //    g2d.setStroke(dashed);

        if(edges != null)
        {
            int ecount = edges.countEdges();

            for (int i = 0; i < ecount; i++)
            {
                BhaashikEdge edge = edges.getEdge(i);
                int cells[] = edge.getCells();

                FontMetrics fm = g2d.getFontMetrics();
                int stringHeight = fm.getHeight();

                Rectangle rect = getCellRect(cells[0], cells[1], true);

                int gap = 4;

                int x1 = rect.x + rect.width / 2;
                int y1 = rect.y + (rect.height + stringHeight) / 2 + gap;
                //	    int y1 = rect.y + (int) (0.6f * (float) rect.height);

                if(mode == ALIGNMENT_MODE
                        && (alignmentBlock.getMode() == AlignmentBlock.SENTENCE_ALIGNMENT_MODE
                            || alignmentBlock.getMode() == AlignmentBlock.PARAGRAPH_ALIGNMENT_MODE))
                {
                    y1 = rect.y + (rect.height) / 2;
                }

                rect = getCellRect(cells[2], cells[3], true);

                int x2 = rect.x + rect.width / 2;
                int y2 = rect.y + (rect.height - stringHeight) / 2 - gap;
                //	    int y2 = rect.y + (int) (0.4f * (float) rect.height);

                if(mode == ALIGNMENT_MODE
                        && (alignmentBlock.getMode() == AlignmentBlock.SENTENCE_ALIGNMENT_MODE
                            || alignmentBlock.getMode() == AlignmentBlock.PARAGRAPH_ALIGNMENT_MODE))
                {
                    y2 = rect.y + (rect.height) / 2;
                }

                Stroke prevStroke = g2d.getStroke();
                Color prevColor = g2d.getColor();

                Stroke stroke = edge.getStroke();

                if (stroke != null)
                {
                    g2d.setStroke(stroke);
                }

                Color color = edge.getColor();

                if (color != null)
                {
                    g2d.setColor(color);
                }

                int xx = Math.abs(x1 + x2) / 2;
                int yy = Math.abs(y1 + y2) / 2;
                int controlx = xx + (int) (0.3 * (double) xx);
                int controly = yy - (int) (0.3 * (double) yy);

                if (edge.isCurved())
                {
                    //            int w = Math.max(25, x2 - x1);
                    //            int h = Math.max(25, y2 - y1);
                    //            g2d.drawArc(x1, y1, w, h, 45, -45);
                    QuadCurve2D curve = new QuadCurve2D.Double(x1, y1, controlx, controly, x2, y2);
                    //            QuadCurve2D curve = new QuadCurve2D.Double(x1, y1, Math.abs(x1 + x2)/2 + depth, Math.abs(y1 + y2)/2 + depth, x2, y2);

                    g2d.draw(curve);

    //                Ellipse2D ellipse2D = new Ellipse2D.Double(controlx, controly, 2, 2);
    //                g2d.draw(ellipse2D);
                }
                else if (edge.isTriangle())
                {
                    x2 = x2 - (int) (0.40 * (double) rect.width);
                    int x3 = x2 + (int) (0.50 * (double) rect.width);

                    Line2D line = new Line2D.Double(x1, y1, x2, y2);
                    g2d.draw(line);

                    line = new Line2D.Double(x2, y2, x3, y2);
                    g2d.draw(line);

                    line = new Line2D.Double(x3, y2, x1, y1);
                    g2d.draw(line);
                }
                else
                {
                    Line2D line = new Line2D.Double(x1, y1, x2, y2);
                    g2d.draw(line);
                }

                if (stroke != null)
                {
                    g2d.setStroke(prevStroke);
                }

                if (color != null)
                {
                    g2d.setColor(prevColor);
                }

                String label = edge.getLabel();

                if (label != null && label.equals("") == false)
                {
                    int stringWidth = fm.stringWidth(label);

                    g2d.setColor(new Color(0, 0, 255));

                    int sx = Math.abs(x1 + x2 - stringWidth) / 2;
                    int sy = Math.abs(y1 + y2 - stringHeight) / 2 + fm.getMaxAscent();

                    if (edge.isCurved())
                    {
                        g2d.drawString(edge.getLabel(), Math.abs(sx + controlx) / 2, Math.abs(sy + controly) / 2);
                    } else
                    {
                        g2d.drawString(edge.getLabel(), sx, sy);
                    }
    //                    g2d.drawString(edge.getLabel(), Math.abs(x1 + x2 - stringWidth)/2, Math.abs(y1 + y2 - stringHeight)/2 + fm.getMaxAscent());

                    g2d.setColor(Color.RED);
                }
            }
//	    if(edge.getStartNode().isLeaf() == true)
//		setValueAt(edge.getStartNode().getLexData(), cells[0], cells[1]);
//	    else
//		setValueAt(edge.getStartNode().getName(), cells[0], cells[1]);
//
//	    if(edge.getEndNode().isLeaf() == true)
//		setValueAt(edge.getEndNode().getLexData(), cells[2], cells[3]);
//	    else
//		setValueAt(edge.getEndNode().getName(), cells[2], cells[3]);
        }

        if(mode == TREE_MODE &&
                ((treeRoot instanceof SSFPhrase && leafDependencies == false) || leafDependencies))
        {
            int rcount = getRowCount();
            int ccount = getColumnCount();

            for (int i = 0; i < rcount; i++)
            {
                for (int j = 0; j < ccount; j++)
                {
                    SSFNode cellObject = (SSFNode) getCellObject(i, j);

                    if(cellObject != null)
                    {
                        SSFNode node = (SSFNode) cellObject;

                        String attribs[] = FSProperties.getSemanticAttributes();

                        FeatureStructures fss = node.getFeatureStructures();

                        if(fss != null)
                        {
                            String values[] = fss.getOneOfAttributeValues(attribs);

                            if(values != null && values.length == 2)
                            {
                                FontMetrics fm = g2d.getFontMetrics();
                                int stringHeight = fm.getHeight();

                                Rectangle rect = getCellRect(i, j, true);

                                int gap = 4;

                                int x = rect.x + rect.width / 2;
                                int y = rect.y + (rect.height + stringHeight) / 2 + gap;

                                int stringWidth = fm.stringWidth(values[1]);

                                Color color = UtilityFunctions.getColor(FSProperties.getSemanticTreeAttributeProperties(values[0])[0]);
                                g2d.setColor(color);

    //                            int sx = Math.abs(x - stringWidth) / 2;
    //                            int sy = Math.abs(y - stringHeight) / 2 + fm.getMaxAscent();

                                g2d.drawString(values[1], x + gap, y + gap);

                                g2d.setColor(Color.RED);
                            }
                        }
                    }
                }
            }
        }
    }

    protected void clear(Graphics g)
    {
        try
        {
            super.paintComponent(g);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

// turn resizing on/of
    public void setResizable(boolean row, boolean column)
    {
        if (row)
        {
            if (rowResizer == null)
            {
                rowResizer = new TableRowResizer(this);
            }
        } else if (rowResizer != null)
        {
            removeMouseListener(rowResizer);
            removeMouseMotionListener(rowResizer);
            rowResizer = null;
        }
        if (column)
        {
            if (columnResizer == null)
            {
                columnResizer = new TableColumnResizer(this);
            }
        } else if (columnResizer != null)
        {
            removeMouseListener(columnResizer);
            removeMouseMotionListener(columnResizer);
            columnResizer = null;
        }
    }

    // mouse press intended for resize shouldn't change row/col/cell celection
    public void changeSelection(int row, int column, boolean toggle, boolean extend)
    {
        if (getCursor() == TableColumnResizer.resizeCursor || getCursor() == TableRowResizer.resizeCursor)
        {
            return;
        }
        super.changeSelection(row, column, toggle, extend);
    }

    public String convertValueToText(Object value, int row, int column)
    {
        if (value != null)
        {
            if(value instanceof SSFLexItem || value instanceof SSFPhrase)
            {
//                FeatureStructures fss = ((SSFNode) value).getFeatureStructures();
                String sValue = ((SSFNode) value).makeSummaryString();

//                if(fss != null)
//                {
//                    sValue += fss.makeString();

                    if (sValue != null)
                    {
                        return sValue;
                    }
//                }
            }
            else
            {
                String sValue = value.toString();
                if (sValue != null)
                {
                    return sValue;
                }
            }
        }
        
        return "";
    }

    public String getToolTipText(MouseEvent event)
    {
        String tip = null;
        Point p = event.getPoint();

        // Locate the renderer under the event location 
        int hitColumnIndex = columnAtPoint(p);
        int hitRowIndex = rowAtPoint(p);

        if ((hitColumnIndex != -1) && (hitRowIndex != -1))
        {
            TableCellRenderer renderer = getCellRenderer(hitRowIndex, hitColumnIndex);
            Component component = prepareRenderer(renderer, hitRowIndex, hitColumnIndex);

            // Now have to see if the component is a JComponent before 
            // getting the tip 
            if (component instanceof JComponent)
            {
                // Convert the event to the renderer's coordinate system 
                Rectangle cellRect = getCellRect(hitRowIndex, hitColumnIndex, false);
//                if (cellRect.width >= component.getPreferredSize().width)
//                {
//                    return null;
//                }
                p.translate(-cellRect.x, -cellRect.y);
                MouseEvent newEvent = new MouseEvent(component, event.getID(),
                        event.getWhen(), event.getModifiers(),
                        p.x, p.y, event.getClickCount(),
                        event.isPopupTrigger());

                tip = ((JComponent) component).getToolTipText(newEvent);
            }
        }

        // No tip from the renderer, see whether any tooltip is set on JTable 
        if (tip == null)
        {
            tip = getToolTipText();
        }

        // calculate tooltip from cell value 
        if (tip == null && hitRowIndex >= 0 && hitColumnIndex >= 0)
        {
            Object value = getValueAt(hitRowIndex, hitColumnIndex);

            Object cellObject = getCellObject(hitRowIndex, hitColumnIndex);

            if(cellObject != null)
                value = cellObject;

            tip = convertValueToText(value, hitRowIndex, hitColumnIndex);
            if (tip.length() == 0)
            {
                tip = null; // don't show empty tooltips
            }
        }

        return tip;
    }

    // makes the tooltip's location to match table cell location 
    // also avoids showing empty tooltips 
    public Point getToolTipLocation(MouseEvent event)
    {
        int row = rowAtPoint(event.getPoint());
        if (row == -1)
        {
            return null;
        }
        int col = columnAtPoint(event.getPoint());
        if (col == -1)
        {
            return null;
        }

        // to avoid empty tooltips - return null location 
        boolean hasTooltip = getToolTipText() == null
                ? getToolTipText(event) != null
                : true;

        return hasTooltip
                ? getCellRect(row, col, false).getLocation()
                : null;
    }

    //Methods required by the MouseMotionListener interface:
    public void mouseMoved(MouseEvent e)
    {
    }

    public void mouseDragged(MouseEvent e)
    {
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //The user is dragging us, so scroll!
        Rectangle r = new Rectangle(e.getX(), e.getY(), 5, 5);
        scrollRectToVisible(r);
    }

    public void mousePressed(MouseEvent e)
    {
    }

    public void assigning(SSFNode tempRoot, Hashtable hash)
    {
        int count = tempRoot.getChildCount();
        if (tempRoot.getAttributeValue(GlobalProperties.getIntlString("name")) != null && !tempRoot.getAttributeValue(GlobalProperties.getIntlString("name")).equals(""))
        {
            hash.put(tempRoot.getAttributeValue(GlobalProperties.getIntlString("name")), tempRoot.getAttributeValue(GlobalProperties.getIntlString("name")));
        } else
        {
        }
        for (int i = 0; i < count; i++)
        {
            if (tempRoot.getChildAt(i) instanceof SSFPhrase)
            {
                assigning((SSFNode) tempRoot.getChildAt(i), hash);
            }
        }
    }

    public void assign(SSFNode tempRoot, int value)
    {
        int tempcount = 0, check = 0;
        tempcount = tempRoot.getChildCount();

        for (int j = 0; j < tempcount; j++)
        {
            if (tempRoot.getChildAt(j) instanceof SSFPhrase)
            {
                check++;
            }
        }

        if ((tempRoot.getAttributeValue(GlobalProperties.getIntlString("name")) == null || tempRoot.getAttributeValue(GlobalProperties.getIntlString("name")).equals("")) && tempRoot.getChildCount() != 0)
        {
            for (int j = 0; j < tempcount; j++)
            {
                if (tempRoot.getChildAt(j) instanceof SSFPhrase)
                {
                    check++;
                }
            }
            if (check != 0)
            {
                tempRoot.setAttributeValue(GlobalProperties.getIntlString("name"), value + "");
            }
        }
        value++;
        int count = tempRoot.getChildCount();
        for (int i = 0; i < count; i++)
        {
            SSFNode temp = (SSFNode) tempRoot.getChildAt(i);
            if (!temp.getLexData().equals(""))
            {
                continue;
            } else
            {
                assign(temp, value);
            }
        }
    }

    private void setNodeLabel(EventObject e)
    {
        if(treeRoot == null || mode != TREE_MODE || treeRoot.getChildCount() == 0)
            return;

        MouseEvent me = null;

        int r = this.getSelectedRow();
        int c = this.getSelectedColumn();

        if(e instanceof MouseEvent)
        {
            me = (MouseEvent) e;

            Point p = getMousePosition();

            if(p == null)
                return;

            r = this.rowAtPoint(p);
            c = this.columnAtPoint(p);
        }

        SSFNode tempRoot = (SSFNode) treeRoot.getChildAt(0);
        SSFNode treeNode = (SSFNode) this.getCellObject(r, c);

        SSFProperties ssfp = SSFNode.getSSFProperties();

        String rootName = ssfp.getProperties().getPropertyValueForPrint("rootName");

//        if(treeRoot instanceof SSFNode && ((SSFNode) treeRoot).getName().equals(rootName) == false)
//              return;

//        if (treeNode != tempRoot && treeNode instanceof SSFPhrase)
        if(treeNode != tempRoot && (treeNode instanceof SSFPhrase || allowsLeafDependencies()))
        {
            String attribs[] = null;

            if (e instanceof MouseEvent && me.isControlDown())
            {
                attribs = FSProperties.getDependencyGraphAttributes();
            } else
            {
                attribs = FSProperties.getDependencyTreeAttributes();
            }

            String atrribName = (String) JOptionPane.showInputDialog(this,
                    GlobalProperties.getIntlString("Select_the_relation"), GlobalProperties.getIntlString("Relation"), JOptionPane.INFORMATION_MESSAGE, null, attribs, "drel");

            if(atrribName == null || atrribName.equals(""))
                return;

            String attribVals[] = FeatureStructuresImpl.getFSProperties().getNonMandatoryAttributeValues(atrribName);

            String atrribVal = (String) JOptionPane.showInputDialog(this,
                    GlobalProperties.getIntlString("Select_the_relation_value"), GlobalProperties.getIntlString("Relation_Value"), JOptionPane.INFORMATION_MESSAGE, null, attribVals, attribVals[0]);
//            String atrribVal = JOptionPane.showInputDialog("Please enter the relation value", "");

            if (atrribVal != null && !atrribVal.equals(""))
            {
                if(atrribVal.equalsIgnoreCase(GlobalProperties.getIntlString("other")))
                {
                    atrribVal = JOptionPane.showInputDialog(GlobalProperties.getIntlString("Please_enter_the_relation_value"), "");
                }

                int name = 1;

                if (((SSFPhrase) treeNode.getParent()).getAttributeValue(GlobalProperties.getIntlString("name")) == null || (((SSFPhrase) treeNode.getParent()).getAttributeValue(GlobalProperties.getIntlString("name")).equals("")))
                {
                    List<SSFNode> nodes = ((SSFPhrase) tempRoot).getNodesForAttribVal(GlobalProperties.getIntlString("name"), "" + name, true);


                    while (nodes != null && nodes.size() > 0)
                    {
                        name++;
                        nodes = ((SSFPhrase) tempRoot).getNodesForAttribVal(GlobalProperties.getIntlString("name"), "" + name, true);
                    }

                    ((SSFPhrase) treeNode.getParent()).setAttributeValue(GlobalProperties.getIntlString("name"), "" + name);
//                    treeNode.setAttributeValue("drel", atrribVal+":"+name);
                    treeNode.setAttributeValue(atrribName, atrribVal + ":" + name);
                    fireTreeViewerEvent(new TreeViewerEvent(this, TreeViewerEvent.TREE_CHANGED_EVENT));
                } else
                {
//                  treeNode.setAttributeValue("drel", atrribVal+":"+((SSFPhrase) treeNode.getParent()).getAttributeValueString("name"));
                    treeNode.setAttributeValue(atrribName, atrribVal + ":" + ((SSFPhrase) treeNode.getParent()).getAttributeValue(GlobalProperties.getIntlString("name")));
                    fireTreeViewerEvent(new TreeViewerEvent(this, TreeViewerEvent.TREE_CHANGED_EVENT));
                }
            }

            setRowSelectionInterval(r, r);
            setColumnSelectionInterval(c, c);
        }
    }

    public void mouseClicked(MouseEvent e)
    {
        if(e.isPopupTrigger())
            return;

//        if(ds.dragStarted() == false && e.getClickCount() == 2)
//        {
//            ds.dragStarted(true);
//
//            BhaashikDragGestureEvent dge = null;
//            LinkedList  elist = new LinkedList();
//            elist.add(e);
//
//            if(e.isControlDown())
//            {
//                dge = new BhaashikDragGestureEvent(ds.getRecognizer(), DnDConstants.ACTION_COPY, e.getPoint(), elist);
//            }
//            else
//            {
//                dge = new BhaashikDragGestureEvent(ds.getRecognizer(), DnDConstants.ACTION_MOVE, e.getPoint(), elist);
//            }
//
////            dge.setTriggerEvent(e);
////            ds.dragGestureRecognized(dge);
//        }
//        else
////            if(ds.dragStarted() == true)
//        {
//            dt.dropEnded(false);
//
//            DropTargetDragEvent dtre = null;
//            DropTargetDropEvent dtde = null;
//
//            if(e.isControlDown())
//            {
//                dtde = new DropTargetDropEvent(dt.getDropTarget().getDropTargetContext(), e.getPoint(), DnDConstants.ACTION_COPY, DnDConstants.ACTION_COPY);
//            }
//            else
//            {
//                dtde = new DropTargetDropEvent(dt.getDropTarget().getDropTargetContext(), e.getPoint(), DnDConstants.ACTION_MOVE, DnDConstants.ACTION_MOVE);
//            }
//
////            dt.dragEnter(dtde);
////            dt.drop(dtde);
//
////                DropTargetDragEvent dtde = null;
////
////                if(e.isControlDown())
////                {
////                    dtde = new DropTargetDragEvent(dt.getDropTarget().getDropTargetContext(), e.getPoint(), DnDConstants.ACTION_COPY, DnDConstants.ACTION_COPY);
////                }
////                else
////                {
////                    dtde = new DropTargetDragEvent(dt.getDropTarget().getDropTargetContext(), e.getPoint(), DnDConstants.ACTION_MOVE, DnDConstants.ACTION_MOVE);
////                }
////
////                dt.dragEnter(dtde);
//         }
//         else
//        {
//            int i = 0;
//         }

        if(mode == TREE_MODE)
            setNodeLabel(e);
        else if(mode == ALIGNMENT_MODE)
        {
//            if( (e.isAltDown() && e.isControlDown() == false) || e.getClickCount() == 2)
//            {
//                AlignmentUnit alignmentUnit = (AlignmentUnit) getSelectedCellObject();
//                SSFNode ssfNode = null;
//                if(alignmentUnit == null)
//                {
//                    if(getSelectedRow() == 0 || getSelectedRow() == 2)
//                    {
//                        alignmentUnit = (AlignmentUnit) getValueAt(getSelectedRow(), 0);
//                        ssfNode = (SSFNode) alignmentUnit.getAlignmentObject();
//                        MutableTreeNode parent = (MutableTreeNode) ssfNode.getParent();
//
//                        if (parent != null) {
//                            int selNodeIndex = parent.getIndex(ssfNode);
//
//                            FeatureStructures featureStructures = new FeatureStructuresImpl();
//                            featureStructures = new FeatureStructuresImpl();
//                            featureStructures.setToEmpty();
//
//                            featureStructures.addAltFSValue(new FeatureStructureImpl());
//
//                            SSFLexItem ssfLexItem = new SSFLexItem("0", "word?", "",featureStructures);
//                            ((SSFNode) parent).add(ssfLexItem);
//                            
////                            ((SSFNode) parent).insert(ssfLexItem, selNodeIndex);
//                        }
//                    }
//                }
//                else
//                {
//                    ssfNode = (SSFNode) alignmentUnit.getAlignmentObject();
//                }
//
//                if(ssfNode instanceof SSFLexItem)
//                {
//                    String editedword = JOptionPane.showInputDialog("Please edit/enter the word:", ssfNode.getLexData());
//
//                    ssfNode.setLexData(editedword);
//                }
        }
//    }
        
//      Point p = getMousePosition();
//      int r = this.rowAtPoint(p);
//      int c = this.columnAtPoint(p);
//
//      SSFNode tempRoot = (SSFNode) treeRoot.getChildAt(0);
//      SSFNode  treeNode = (SSFNode) this.getCellObject(r, c);
//
//      if(treeNode != tempRoot && treeNode instanceof SSFPhrase)
//      {
//          String depAttribs[] = null;
//
//            if(e.isControlDown())
//                depAttribs = FSProperties.getAnnCorraDependencyGraphAttributes();
//            else
//                depAttribs = FSProperties.getAnnCorraDependencyTreeAttributes();
//
//          String atrribName = (String) JOptionPane.showInputDialog(this,
//                "Select the relation", "Relation", JOptionPane.INFORMATION_MESSAGE, null, depAttribs, "drel");
//
//         String atrribVal = JOptionPane.showInputDialog("Please enter the relation value", "");
//
//         if(atrribVal != null && !atrribVal.equals(""))
//         {
//              int name = 1;
//
//              if(((SSFPhrase) treeNode.getParent()).getAttributeValueString("name")==null || (((SSFPhrase) treeNode.getParent()).getAttributeValueString("name").equals("")))
//              {
//                  Vector nodes = ((SSFPhrase) tempRoot).getNodesForAttribVal("name", "" + name);
//
//
//                  while(nodes != null && nodes.size()>0)
//                  {
//                        name++;
//                        nodes = ((SSFPhrase) tempRoot).getNodesForAttribVal("name","" + name);
//                  }
//
//                    ((SSFPhrase) treeNode.getParent()).setAttributeValue("name", "" + name);
////                    treeNode.setAttributeValue("drel", atrribVal+":"+name);
//                    treeNode.setAttributeValue(atrribName, atrribVal+":"+name);
//                    fireTreeViewerEvent(new TreeViewerEvent(this, TreeViewerEvent.TREE_CHANGED_EVENT));
//              }
//              else{
////                  treeNode.setAttributeValue("drel", atrribVal+":"+((SSFPhrase) treeNode.getParent()).getAttributeValueString("name"));
//                    treeNode.setAttributeValue(atrribName, atrribVal+":"+((SSFPhrase) treeNode.getParent()).getAttributeValueString("name"));
//                    fireTreeViewerEvent(new TreeViewerEvent(this, TreeViewerEvent.TREE_CHANGED_EVENT));
//              }
//          }
//      }
//      Hashtable nodename = new Hashtable(0,20);
//      SSFNode tempRoot = (SSFNode) treeRoot.getChildAt(0);
//      assigning(tempRoot,nodename);
//      SSFNode  treeNode = (SSFNode) this.getCellObject(r, c);
//       if(treeNode != null && !((SSFNode) treeNode.getParent()).isRoot() && treeNode.getLexData().equals(""))
//      {
//          String charset = JOptionPane.showInputDialog("Please enter the edge value", "");
//          if(charset!="")
//          {
//             if(((SSFNode) (treeNode.getParent())).getAttributeValueString("name")==null ||((SSFNode) (treeNode.getParent())).getAttributeValueString("name").equals(""))
//            {
//                 int value=1;
//                 int i=0,j=0;
//                 //int count = treeRoot.getChildAt(0).getChildCount();
//                 while(nodename.get(value)!=null)
//                 {
//                     
//                    value++;
//                 }
//                 nodename.put(value,value);
//                 ((SSFNode) treeNode.getParent()).setAttributeValue("name",value+"");
//          //       assign(tempRoot,value);
//                 
//                 treeNode.setAttributeValue("drel",charset+":"+((SSFNode) (treeNode.getParent())).getAttributeValueString("name"));
//            }
//             else
//             {
//                 treeNode.setAttributeValue("drel",charset+":"+((SSFNode) (treeNode.getParent())).getAttributeValueString("name"));
//             }
//          }
//      }
    }

    public void mouseReleased(MouseEvent e)
    {
        int i = 0;
        //  dt=new TableDropTarget(this);
    }

    public void mouseEntered(MouseEvent e)
    {
        int i = 0;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseExited(MouseEvent e)
    {
        int i = 0;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public Dimension getPreferredSize()
    {
        if (missingModel)
        {
            return new Dimension(320, 480);
        } else
        {
            return super.getPreferredSize();
        }
    }

    public Dimension getPreferredScrollableViewportSize()
    {
        return getPreferredSize();
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect,
            int orientation,
            int direction)
    {
        //Get the current position.
        int currentPosition = 0;
        if (orientation == SwingConstants.HORIZONTAL)
        {
            currentPosition = visibleRect.x;
        } else
        {
            currentPosition = visibleRect.y;
        }

        //Return the number of pixels between currentPosition
        //and the nearest tick mark in the indicated direction.
        if (direction < 0)
        {
            int newPosition = currentPosition -
                    (currentPosition / maxUnitIncrement) * maxUnitIncrement;
            return (newPosition == 0) ? maxUnitIncrement : newPosition;
        } else
        {
            return ((currentPosition / maxUnitIncrement) + 1) * maxUnitIncrement - currentPosition;
        }
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect,
            int orientation,
            int direction)
    {
        if (orientation == SwingConstants.HORIZONTAL)
        {
            return visibleRect.width - maxUnitIncrement;
        } else
        {
            return visibleRect.height - maxUnitIncrement;
        }
    }

    public boolean getScrollableTracksViewportWidth()
    {
        return false;
    }

    public boolean getScrollableTracksViewportHeight()
    {
        return false;
    }

    public void setMaxUnitIncrement(int pixels)
    {
        maxUnitIncrement = pixels;
     }

     public void setRowEditorModel(RowEditorModel rm)
     {
         this.rowEditorModel = rm;
     }

     public RowEditorModel getRowEditorModel()
     {
         return rowEditorModel;
     }

     public void setRowRendererRendererModel(RowRendererModel rm)
     {
         this.rowRendererModel = rm;
     }

     public RowRendererModel getRowRendererModel()
     {
         return rowRendererModel;
     }

     public TableCellEditor getCellEditor(int row, int col)
     {
         TableCellEditor tmpEditor = null;

         if (rowEditorModel != null)
             tmpEditor = rowEditorModel.getEditor(row);

         if (tmpEditor!=null)
             return tmpEditor;
         
         return super.getCellEditor(row,col);
     }

     public TableCellRenderer getCellRenderer(int row, int col)
     {
         TableCellRenderer tmpRenderer = null;

         if (rowRendererModel != null)
             tmpRenderer = rowRendererModel.getRenderer(row);

         if (tmpRenderer != null)
             return tmpRenderer;

         return super.getCellRenderer(row,col);
     }

    // This methods allows classes to register for MyEvents
    public void addEventListener(EventListener listener)
    {
        listenerListLocal.add(EventListener.class, listener);
    }

    // This methods allows classes to unregister for MyEvents
    public void removeEventListener(EventListener listener)
    {
        listenerListLocal.remove(EventListener.class, listener);
    }

    // This private class is used to fire BhaashikEvent
    public void fireTreeViewerEvent(BhaashikEvent evt)
    {
        Object[] listeners = listenerListLocal.getListenerList();
        // Each listener occupies two elements - the first is the listener class
        // and the second is the listener instance
        for (int i = 0; i < listeners.length; i++)
        {
            if (listeners[i] instanceof TreeViewerEventListener)
            {
                ((TreeViewerEventListener) listeners[i]).treeChanged((TreeViewerEvent) evt);
            }
            if (listeners[i] instanceof TreeDrawingEventListener)
            {
                ((TreeDrawingEventListener) listeners[i]).treeChanged((TreeViewerEvent) evt);
            }
            if (listeners[i] instanceof AlignmentEventListener)
            {
                ((AlignmentEventListener) listeners[i]).alignmentChanged((AlignmentEvent) evt);
            }
        }
    }

    public String getCellPosition(Object obj) {
        //        if (mode == TREE_MODE)
//        {
            Hashtable revNodeHashTable = (Hashtable) UtilityFunctions.getReverseMap(nodeHashtable);
            String key = (String) revNodeHashTable.get(obj);
            if(key!=null){
                return key;
            }
            else{
                BhaashikTableModel stm = (BhaashikTableModel) this.getModel();
                for (int i = 0; i < this.getRowCount(); i++) {
                    for (int j = 0; j < this.getColumnCount(); j++) {

                        System.out.print(stm.getValueAt(i, j)+" X " + this.getCellObject(i, j).getClass());
                        System.out.println(i+"::"+j);
                        if(stm.getValueAt(i, j).equals(obj))
                        {
                            System.out.println(i+"::"+j);
                            return String.valueOf(i)+"::"+String.valueOf(j);
                        }
                    }

                }
            }
        //}
        return null;
    }
    
    public void copyTableData(EventObject e)
    {
        BhaashikTableModel stm = (BhaashikTableModel) getModel();

//        if(getRowSelectionAllowed() == false && getColumnSelectionAllowed() == false)
//            return;

        if(stm != null)
        {
            if(isEditing() == true)
                getCellEditor().stopCellEditing();

            Alignable alignable = null;

            int row = getSelectedRow();
            int col = getSelectedColumn();

            if(mode == TREE_MODE)
            {
                alignable = (Alignable) getCellObject(row, col);
            }
            else if (mode == ALIGNMENT_MODE)
            {
                alignable = (Alignable) ((AlignmentUnit) stm.getValueAt(row, col)).getAlignmentObject();
            }

            alignable.getAlignmentUnit().setActionType(DnDConstants.ACTION_MOVE);

            BhaashikAlignableDataTransfer tableTransfer = new BhaashikAlignableDataTransfer();

            if(mode == TREE_MODE)
            {
                 BhaashikAlignableDataTransfer.startTransfer(this, alignable, mode, alignable.getAlignmentUnit().getActionType(), this, row, col);
                 fireTreeViewerEvent(new TreeViewerEvent(this, TreeViewerEvent.TREE_CHANGED_EVENT));
            }
            else if (mode == ALIGNMENT_MODE)
            {
//                fireTreeViewerEvent(new AlignmentEvent(this, AlignmentEvent.ALIGNMENT_CHANGED_EVENT));
            }

            tableTransfer.setClipboardContents(alignable);
        }
    }

    public void cutTableData(EventObject e)
    {
        BhaashikTableModel stm = (BhaashikTableModel) getModel();

//        if(getRowSelectionAllowed() == false && getColumnSelectionAllowed() == false)
//            return;

        if(stm != null)
        {
            if(isEditing() == true)
                getCellEditor().stopCellEditing();

            Alignable alignable = null;

            int row = getSelectedRow();
            int col = getSelectedColumn();
            Object alignableObject = null;

            if(mode == TREE_MODE)
            {                
                alignableObject = getCellObject(row, col);

                if(alignableObject != null && alignableObject instanceof Alignable)
                {
                    alignable = (Alignable) alignableObject;
                }
            }
            else if (mode == ALIGNMENT_MODE)
            {                                    
                AlignmentUnit alignmentUnit = (AlignmentUnit) stm.getValueAt(row, col);
                
                if(alignmentUnit == null)
                {
                    return;
                }                
                
                alignable = (Alignable) alignmentUnit.getAlignmentObject();
                
                if(alignable instanceof Alignable)
                {
//                    alignable = (Alignable) alignableObject;
                }
            }

            alignable.getAlignmentUnit().setActionType(DnDConstants.ACTION_COPY);

            BhaashikAlignableDataTransfer tableTransfer = new BhaashikAlignableDataTransfer();

            if(mode == TREE_MODE)
            {
                BhaashikAlignableDataTransfer.startTransfer(this, alignable, mode, alignable.getAlignmentUnit().getActionType(), this, row, col);
                fireTreeViewerEvent(new TreeViewerEvent(this, TreeViewerEvent.TREE_CHANGED_EVENT));

                tableTransfer.setClipboardContents(alignable);
            }
            else if (mode == ALIGNMENT_MODE)
            {
//                fireTreeViewerEvent(new AlignmentEvent(this, AlignmentEvent.ALIGNMENT_CHANGED_EVENT));
            }

            tableTransfer.setClipboardContents(alignable);
        }
    }

    public void pasteTableData(EventObject e)
    {
        BhaashikTableModel stm = (BhaashikTableModel) getModel();

//        if(getRowSelectionAllowed() == false && getColumnSelectionAllowed() == false)
//            return;

        if(stm != null)
        {
            if(isEditing() == true)
                getCellEditor().stopCellEditing();

            BhaashikAlignableDataTransfer tableTransfer = new BhaashikAlignableDataTransfer();
            Alignable alignable = tableTransfer.getClipboardContents();

            int row = getSelectedRow();
            int col = getSelectedColumn();

            Alignable alignableTgt = null;

            if(mode == TREE_MODE)
            {
                alignableTgt = (Alignable) getCellObject(row, col);
            }
            else if (mode == ALIGNMENT_MODE)
            {
                alignableTgt = (Alignable) ((AlignmentUnit) stm.getValueAt(row, col)).getAlignmentObject();
            }

            if(alignable.getAlignmentUnit() == null)
                return;

            BhaashikAlignableDataTransfer.transferData(this, alignable, alignableTgt, mode, alignable.getAlignmentUnit().getActionType(), this, row, col);

            if(mode == TREE_MODE)
            {
                fireTreeViewerEvent(new TreeViewerEvent(this, TreeViewerEvent.TREE_CHANGED_EVENT));
            }
            else if (mode == ALIGNMENT_MODE)
            {
                fireTreeViewerEvent(new AlignmentEvent(this, AlignmentEvent.ALIGNMENT_CHANGED_EVENT));
            }
        }
    }

    public void setLabel(EventObject e)
    {
        setNodeLabel(e);
    }

    public boolean allowsLeafDependencies()
    {
        return leafDependencies;
    }

    public void allowsLeafDependencies(boolean ld)
    {
        leafDependencies = ld;
    }

    public ConcurrentLinkedHashMap getCFG2DepTreeMap()
    {
        return cfgToDepTreeMapping;
    }

    public void setCFG2DepTreeMap(ConcurrentLinkedHashMap cfgToDepTreeMapping)
    {
        this.cfgToDepTreeMapping = cfgToDepTreeMapping;
    }

    public class PropertyChangeHelper {
        private final PropertyChangeSupport propertyChangeSupport;

        public PropertyChangeHelper(Object source) {
            this.propertyChangeSupport = new PropertyChangeSupport(source);
        }

        public void addPropertyChangeListener(PropertyChangeListener listener) {
            propertyChangeSupport.addPropertyChangeListener(listener);
        }

        public void removePropertyChangeListener(PropertyChangeListener listener) {
            propertyChangeSupport.removePropertyChangeListener(listener);
        }

        public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
            propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
        }
    }
}