/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import bhaashik.datastr.ConcurrentLinkedHashMap;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import bhaashik.GlobalProperties;
import bhaashik.common.types.ShortcutType;
import bhaashik.properties.MultiPropertiesTable;
import bhaashik.properties.PropertiesTable;
import bhaashik.table.BhaashikTableModel;

/**
 *
 * @author anil
 */
public class BhaashikShortcutsData {

    protected static String shortcutsDataPath = GlobalProperties.resolveRelativePath("props/state/shortcuts.txt");
    protected static MultiPropertiesTable shortcutsData;

    /** Creates a new instance of BhaashikShortcutsData */
    public BhaashikShortcutsData() {
    }

    protected static void init() {
        shortcutsData = new MultiPropertiesTable();
        shortcutsData.setFilePath(shortcutsDataPath);
        shortcutsData.setCharset("UTF-8");
        shortcutsData.setLangEnc("hin::utf8");
        shortcutsData.setName("Bhaashik Shortcuts");

        File shortcutsDataFile = new File(shortcutsDataPath);

//        if(shortcutsDataFile.exists() && shortcutsDataFile.length() > 0L)
        if(shortcutsDataFile.exists())
        {
            try {
                shortcutsData.read(shortcutsDataPath, "UTF-8");
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
//        else
//        {
//            try {
//                shortcutsDataFile.createNewFile();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }

        Enumeration enm = ShortcutType.elements();

        while(enm.hasMoreElements())
        {
            ShortcutType shortcutTypeType = (ShortcutType) enm.nextElement();

            if(shortcutsData.getPropertiesValue(shortcutTypeType.toString()) == null)
                shortcutsData.addProperties(shortcutTypeType.toString(), new PropertiesTable());
        }
    }

    public static MultiPropertiesTable getShortcutsData()
    {
        if(shortcutsData == null)
            init();

        return shortcutsData;
    }

    public static BhaashikTableModel getShortcutsTable(Action[] actions, InputMap inputMap, ActionMap actionMap, ConcurrentLinkedHashMap<String,KeyStroke> actionsKeyMap)
    {
        PropertiesTable shortcutTableModel = new PropertiesTable(0, 3);

        for (int i = 0; i < actions.length; i++)
        {
            int row = shortcutTableModel.getRowCount();

            shortcutTableModel.addRow();

            String actionName = (String) actions[i].getValue(Action.NAME);

            shortcutTableModel.setValueAt(actionName, row, 0);
            shortcutTableModel.setValueAt(actions[i].getValue(Action.SHORT_DESCRIPTION), row, 1);

            KeyStroke keyStroke = KeyStroke.getKeyStroke(actionName.charAt(actionName.length() - 1));

            if(actionsKeyMap.get(actionName) != null)
                keyStroke = actionsKeyMap.get(actionName);

            shortcutTableModel.setValueAt(keyStroke, row, 2);

            inputMap.put(keyStroke, actionName);
            actionMap.put(actionName, actions[i]);

//            actionsKeyMap.put(actionName, keyStroke);
        }

        return shortcutTableModel;
    }

    public static void readShortcuts(BhaashikTableModel shortcutTableModel, Action[] actions, InputMap inputMap, ActionMap actionMap, ConcurrentLinkedHashMap<String,KeyStroke> actionsKeyMap)
    {
        for (int i = 0; i < actions.length && i < shortcutTableModel.getRowCount(); i++)
        {
            String actionName = (String) actions[i].getValue(Action.NAME);

            Object keyStrokeObj = shortcutTableModel.getValueAt(i, 2);
            KeyStroke keyStroke = null;

            if(keyStrokeObj instanceof String)
                keyStroke = KeyStroke.getKeyStroke((String) keyStrokeObj);
            else
                keyStroke = (KeyStroke) keyStrokeObj;

            inputMap.put(keyStroke, actionName);
            actionMap.put(actionName, actions[i]);

            actionsKeyMap.put(actionName, keyStroke);
        }
    }

    public static void registerShortcuts(JComponent component, ActionMap actionMap, ConcurrentLinkedHashMap<String,KeyStroke> actionsKeyMap)
    {
        Iterator itr = actionsKeyMap.keySet().iterator();

        while(itr.hasNext())
        {
            String actionName = (String) itr.next();
            
            component.registerKeyboardAction(actionMap.get(actionName), actionName,
                    actionsKeyMap.get(actionName),
                    JComponent.WHEN_IN_FOCUSED_WINDOW);
        }
    }

    public static void reset()
    {
        init();
    }

    public static void save()
    {
        if(shortcutsData == null)
            init();

        try {
            shortcutsData.save();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
