package bhaashik.properties;

import java.io.*;
import java.util.*;
//import ml.options.OptionData;
//import ml.options.OptionSet;
import bhaashik.GlobalProperties;
import bhaashik.datastr.ConcurrentLinkedHashMap;
import bhaashik.xml.dom.BhaashikDOMElement;
import bhaashik.resources.ResourceImpl;
import bhaashik.table.BhaashikTableModel;
import bhaashik.table.TableSorter;
import org.dom4j.dom.DOMAttribute;
import org.dom4j.dom.DOMElement;
import org.w3c.dom.NodeList;

/**
 *
 * @author surana
 *
 * The class which can be used for completely customizing things. It stores things in Property - Value format.
 */

public class KeyValueProperties extends ResourceImpl implements Serializable, BhaashikProperties, BhaashikDOMElement {
    /**
     *
     * For column headers in table GUI.
     *
     */
    
    protected String keyName;    
    protected String valueName; 
    protected ConcurrentLinkedHashMap properties;
       
    public KeyValueProperties()
    {
        super();
        
        properties = new ConcurrentLinkedHashMap(0, 5);
        setKeyName(GlobalProperties.getIntlString("Property"));
        setValueName(GlobalProperties.getIntlString("Value"));
    }
    
    public KeyValueProperties(int size) {
        super();
        
        // TODO Auto-generated constructor stub        
        properties = new ConcurrentLinkedHashMap(size);

        setKeyName(GlobalProperties.getIntlString("Property"));
        setValueName(GlobalProperties.getIntlString("Value"));
    }
   
    public KeyValueProperties(int size, int growSize) {   
        super();
        
        // TODO Auto-generated constructor stub
        properties = new ConcurrentLinkedHashMap(size, growSize);
                
        setKeyName(GlobalProperties.getIntlString("Property"));
        setValueName(GlobalProperties.getIntlString("Value"));
    }

    public KeyValueProperties(String propFile, String cs) throws FileNotFoundException, IOException            
    {
        super(propFile, cs);
        
        properties = new ConcurrentLinkedHashMap(0, 5);
        setKeyName(GlobalProperties.getIntlString("Property"));
        setValueName(GlobalProperties.getIntlString("Value"));

        read(propFile, cs);        
    }
    
    public String getKeyName()
    {
        return keyName;        
    }
    
    public void setKeyName(String n)
    
    {        
        keyName = n;        
    }

    public String getValueName()
    {
        return valueName;   
    }

    public void setValueName(String n)
    {
        valueName = n;   
    }

    public int countProperties()
    {
        return properties.size();   
    }

    public Iterator getPropertyKeys()
    {
        return properties.keySet().iterator();
    }

    public String getPropertyValue(String p /* Property key */)
    {
        return (String) properties.get(p);   
    }

    public String getPropertyValueForPrint(String p /* Property key */)
    {
        String v = getPropertyValue(p);

        if(v.startsWith("\\") && v.length() == 2)    
        {
            if(v.equals("\\t"))       
                v = "\t";
            else if(v.equals("\\n"))
                v = "\n";
            else if(v.equals("\\r"))
                v = "\r";
            else if(v.equals("\\\\"))
                v = "\\";
            else if(v.equals("\\b"))
                v = "\b";
            else if(v.equals("\\\""))
                v = "\"";
            else if(v.equals("\\\'"))
                v = "\'";
        }

        return v;
    }

    public int addProperty(String p /* property key */, String v /*property value */)
    {
        if(p == null || v == null)
        {
            return -1;
        }
        
        properties.put(p, v);
        
        return properties.size();   
    }

    public int addAllProperties(KeyValueProperties kvp)
    {
        properties.putAll(kvp.properties);
        
        return properties.size();   
    }

    public String removeProperty(String p /* property key */)
    {
        return (String) properties.remove(p);   
    }

    // More useful when both key and value are unique
    
    public KeyValueProperties getReverse()
    {
        KeyValueProperties kvp = new KeyValueProperties(0, 5);

        Iterator itr = getPropertyKeys();

        while(itr.hasNext())
        {
            String key = (String) itr.next();
            String val = getPropertyValue(key);
            
            kvp.addProperty(val, key);
        }

        return kvp;
    }
    
    public int isEmpty()
    {
        Iterator enm = getPropertyKeys();
        int flag = 0;
        
        while(enm.hasNext())
        {
            String key = (String) enm.next();
            String val = getPropertyValue(key);
            
            if(val == null || val.equals("") == false)
            {
                flag=1;
                break;
            }
            
        }

        if(flag == 1)
            return 0;
        
        return 1;
    }

    /**
     *
     * Reads from a file with two columns, separated by a tab.
     *
     */
    
    public int read() throws FileNotFoundException, IOException
    {
        return read(filePath, charset);   
    }
    
    public int read(String f, String cs) throws FileNotFoundException, IOException        
    {
        clear();

        BufferedReader lnReader = null;

        if(!cs.equals(""))       
            lnReader = new BufferedReader(new InputStreamReader(new FileInputStream(f), cs));
        else
            lnReader = new BufferedReader(new InputStreamReader(new FileInputStream(f), GlobalProperties.getIntlString("UTF-8")));

        String line;
        
        String splitstr[] = new String[2];

        while((line = lnReader.readLine()) != null)
        {
            if((!line.startsWith("#")) && line != "")       
            {
                if(line.startsWith(GlobalProperties.getIntlString("Column_Names::")))
                {
                    splitstr = line.split("::", 2);   
                    splitstr = splitstr[1].split("\t");

                    if(splitstr.length == 2 && splitstr[0].equals("") == false
                            && splitstr[1].equals("") == false)
                    {
                        setKeyName(splitstr[0]);   
                        setValueName(splitstr[1]);
                    }
                }
                else   
                {
                    splitstr = line.split("\t", 2);

                    if(splitstr.length == 2)       
                        addProperty(splitstr[0], splitstr[1]);
                }
            }
        }

        return 0;
    }

    public static ConcurrentLinkedHashMap readMany(String f, String charset) throws FileNotFoundException, IOException
    {        
        ConcurrentLinkedHashMap ht = new ConcurrentLinkedHashMap(0, 5);

        BufferedReader lnReader = null;

        if(!charset.equals(""))       
            lnReader = new BufferedReader(new InputStreamReader(new FileInputStream(f), charset));
        else
            lnReader = new BufferedReader(new InputStreamReader(new FileInputStream(f), GlobalProperties.getIntlString("UTF-8")));

        String line;
        String splitstr[] = null;
        KeyValueProperties kvprop = null;
        String kvpName = "";

        while((line = lnReader.readLine()) != null)
        {
            if((!line.startsWith(GlobalProperties.getIntlString("#"))) && line.equals("") == false)
            {
                if(line.startsWith(GlobalProperties.getIntlString("KVPropertiesBegin::")))
                {
                    splitstr = line.split("::");   
                    kvpName = splitstr[1];

                    kvprop = new KeyValueProperties();
                }
                else if(line.startsWith(GlobalProperties.getIntlString("KVPropertiesEnd")))
                {
                    if(kvpName.equals("") == false && kvprop != null)       
                    {
                        ht.put(kvpName, kvprop);
                        kvpName = "";
                        kvprop = null;
                    }
                    else   
                        throw new IOException();
                }
                else if(line.startsWith(GlobalProperties.getIntlString("Column_Names::")))
                {
                    splitstr = line.split(GlobalProperties.getIntlString("::"));
                    
                    if(splitstr.length == 2)
                    {
                        splitstr = splitstr[1].split("\t");
                        
                        if(splitstr.length == 2)
                        {
                            kvprop.keyName = splitstr[0];   
                            kvprop.valueName = splitstr[1];
                        }
                    }
                }
                else   
                {
                    splitstr = line.split("\t");

                    if(kvprop != null && splitstr.length >= 2)
                        kvprop.addProperty(splitstr[0], splitstr[1]);   
                }
            }
        }

        return ht;
    }

    public int save() throws FileNotFoundException, UnsupportedEncodingException
    {
        return save(filePath, charset);   
    }

    public int save(String f, String cs) throws FileNotFoundException, UnsupportedEncodingException
    {
        PrintStream ps = new PrintStream(f, cs);
        
        print(ps);

        return 0;   
    }

    public static void saveMany(ConcurrentLinkedHashMap kvprops, String f, String charset) throws FileNotFoundException, UnsupportedEncodingException
    {        
        PrintStream ps = new PrintStream(f, charset);
        
        printMany(kvprops, ps);   
    }

    public void print(PrintStream ps)
    {
        Iterator enm = getPropertyKeys();

        ps.println(GlobalProperties.getIntlString("Column_Names::") + getKeyName() + "\t" + getValueName());

        while(enm.hasNext())
        {
            String key = (String) enm.next();   
            String val = getPropertyValue(key);
            
            ps.println(key + "\t" + val);
        }
    }

    public static void printMany(ConcurrentLinkedHashMap kvprops, PrintStream ps)
    {
        Vector kvec = new Vector(kvprops.keySet());
        
        Collections.sort(kvec);

        int count = kvec.size();

        for(int i = 0; i < count; i++)       
        {
            String tblName = (String) kvec.get(i);
            
            KeyValueProperties kvprop = (KeyValueProperties) kvprops.get(tblName);

            ps.println(GlobalProperties.getIntlString("KVPropertiesBegin::") + tblName);
            
            kvprop.print(ps);   
            ps.println(GlobalProperties.getIntlString("KVPropertiesEnd"));
        }
    }

    public static void printManyXML(ConcurrentLinkedHashMap kvprops, PrintStream ps)
    {
        Vector kvec = new Vector(kvprops.keySet());
        DOMElement domElementMany = new DOMElement(GlobalProperties.getIntlString("MultiKeyValueProperties"));
        
        Collections.sort(kvec);

        int count = kvec.size();

        for(int i = 0; i < count; i++)       
        {
            String tblName = (String) kvec.get(i);
            
            KeyValueProperties kvprop = (KeyValueProperties) kvprops.get(tblName);
            
            DOMElement domElement = kvprop.getDOMElement();
            DOMAttribute attribKVP = new DOMAttribute(domElement, new org.dom4j.QName(GlobalProperties.getIntlString("name")), tblName);
            
            domElementMany.add(domElement);
        }
        
        ps.print(domElementMany.asXML());
    }

    public KeyValueProperties getCopy()
    {
        KeyValueProperties copy = new KeyValueProperties();
        
        copy.name = name;
        copy.langEnc = langEnc;
        copy.charset = charset;
        copy.filePath = filePath;
        copy.keyName = keyName;
        copy.valueName = valueName;

        copy.properties = new ConcurrentLinkedHashMap(countProperties());
        Iterator enm = properties.keySet().iterator();
        
        while(enm.hasNext())
        {
            String k = (String) enm.next();
            
            copy.addProperty(k, getPropertyValue(k));   
        }

        return copy;
    }

    public Object clone() throws CloneNotSupportedException
    {
        KeyValueProperties obj = (KeyValueProperties) super.clone();

        obj.properties = new ConcurrentLinkedHashMap(countProperties());
        Iterator enm = properties.keySet().iterator();
        
        while(enm.hasNext())
        {
            String k = (String) enm.next();
            
            obj.addProperty(k, getPropertyValue(k));   
        }

        return obj;
    }

    public void clear()
    {
        properties.clear();   
        
        Runtime rt = Runtime.getRuntime();
        rt.gc(); 
    }

    public static BhaashikTableModel convertToBhaashikTableModel(KeyValueProperties kvp)
    {
        BhaashikTableModel table = new BhaashikTableModel(kvp.countProperties(), 2);

        int i = 0;
        
        Iterator keys = kvp.getPropertyKeys();

        while(keys.hasNext())
        {
            String key = (String) keys.next();
            String val = kvp.getPropertyValue(key);
            
            table.setValueAt(key, i, 0);   
            table.setValueAt(val, i++, 1);   
        }
        
        return table;
    }

    public static KeyValueProperties convertToKeyValueProperties(BhaashikTableModel stm)
    {
        KeyValueProperties kvp = new KeyValueProperties();
        kvp.setCharset(stm.getCharset());
        
        int rcount = stm.getRowCount();
        
        for (int i = 0; i < rcount; i++)
        {
            kvp.addProperty((String) stm.getValueAt(i, 0), (String) stm.getValueAt(i, 1));
        }
        
        return kvp;
    }

    public static BhaashikTableModel mergeKVPs(KeyValueProperties kvps[])
    {
        BhaashikTableModel table = new BhaashikTableModel(kvps[0].countProperties(), kvps.length + 1);

        int i = 0;
        
        Iterator keys = kvps[0].getPropertyKeys();

        while(keys.hasNext())
        {
            String key = (String) keys.next();
            
            table.setValueAt(key, i++, 0);   
        }

        for (i = 0; i < kvps.length; i++)
        {
            int rcount = table.getRowCount();

            for (int k = 0; k < rcount; k++)       
            {
                String key = (String) table.getValueAt(k, 0);   
                String val = kvps[i].getPropertyValue(key);

                table.setValueAt(val, k, i + 1);
            }
        }

        return table;
    }

//    public DOMElement getDOMElement() {
//        DOMElement domElement = new DOMElement("KeyValueProperties");
//        
//        DOMAttribute attribKey = new DOMAttribute(domElement, new org.dom4j.QName("keyName"), keyName);
//        DOMAttribute attribval = new DOMAttribute(domElement, new org.dom4j.QName("valueName"), valueName);
//        
//        Enumeration enm = getPropertyKeys();
//
//        while(enm.hasMoreElements())       
//        {
//            String key = (String) enm.nextElement();   
//            String val = getPropertyValue(key);
//            
//            DOMElement kvpElement = new DOMElement("KeyValueProperty");
//            attribKey = new DOMAttribute(kvpElement, new org.dom4j.QName("key"), key);
//            attribval = new DOMAttribute(kvpElement, new org.dom4j.QName("value"), val);
//            domElement.add(kvpElement);
//        }
//        
//        return domElement;
//    }
    public DOMElement getDOMElement() {
        DOMElement domElement = new DOMElement(GlobalProperties.getIntlString("KeyValueProperties"));

        Iterator enm = getPropertyKeys();

        while(enm.hasNext())
        {
            String key = (String) enm.next();   
            String val = getPropertyValue(key);
            
            DOMElement kvpElement = new DOMElement(GlobalProperties.getIntlString("KeyValueProperty"));
//            attribKey = new DOMAttribute(kvpElement, new org.dom4j.QName("key"), key);
//            attribval = new DOMAttribute(kvpElement, new org.dom4j.QName("value"), val);
            kvpElement.addAttribute(GlobalProperties.getIntlString("key"), key);
            kvpElement.addAttribute(GlobalProperties.getIntlString("value"), val);
            domElement.add(kvpElement);
        }
        
        return domElement;
    }

    public String getXML() {
        org.dom4j.dom.DOMElement element = getDOMElement();
        return element.asXML();
    }

    public void printXML(PrintStream ps) {
        ps.println(getXML());
    }

//    public void readXML(org.w3c.dom.Element domElement) {
//        keyName = domElement.getAttribute("keyName");
//        valueName = domElement.getAttribute("valueName");
//        
//        NodeList elements = domElement.getElementsByTagName("KeyValueProperty");
//        int count = elements.getLength();
//        
//        for (int i = 0; i < count; i++)
//        {
//            org.w3c.dom.Element kvElement = (org.w3c.dom.Element) elements.item(i);
//            String key = kvElement.getAttribute("key");
//            String value = kvElement.getAttribute("value");
//            
//            addProperty(key, value);
//        }        
//    }
    public void readXML(org.w3c.dom.Element domElement) {
        
        
        NodeList elements = domElement.getElementsByTagName(GlobalProperties.getIntlString("KeyValueProperty"));
        int count = elements.getLength();
        
        
        for (int i = 0; i < count; i++)
        {
            org.w3c.dom.Element kvElement = (org.w3c.dom.Element) elements.item(i);
            String key = kvElement.getAttribute(GlobalProperties.getIntlString("key"));
            String value = kvElement.getAttribute(GlobalProperties.getIntlString("value"));
       //     System.out.println(key+" = "+value);
            
            addProperty(key, value);
        }        
    }

    public void loadMap(Map map)
    {
        Iterator keys = map.keySet().iterator();

        while(keys.hasNext())
        {
            String key = (String) keys.next();

            String val = map.get(key) + "";

            addProperty(key, val);
        }
    }

    public void fillMap(Map map)
    {
        Iterator keys = getPropertyKeys();

        while(keys.hasNext())
        {
            String key = (String) keys.next();

            String val = getPropertyValue(key);

            map.put(key, val);
        }
    }

    public static KeyValueProperties getIntersectionOfKeys(KeyValueProperties kvp1, KeyValueProperties kvp2)
    {
        KeyValueProperties intersectionKVP = new KeyValueProperties();

        Iterator itr1 = kvp1.getPropertyKeys();

        while(itr1.hasNext())
        {
            String key1 = (String) itr1.next();

            String val1 = kvp2.getPropertyValue(key1);

            if(val1 != null)
                intersectionKVP.addProperty(key1, val1);
        }

        return intersectionKVP;
    }
    
//    public static KeyValueProperties readOptions(OptionSet options)
//    {
//        KeyValueProperties optionsKV = new KeyValueProperties();
//        
//        List<OptionData> optionData = options.getOptionData();
//        
//        for (OptionData option : optionData) {
//            String key = option.getKey();
//            
//            if(key == null)
//            {
//                key = option.getShortKey();
//            }
//            
//            if(options.isSet(key))
//            {
//                optionsKV.addProperty(option.getPrefixString() + key, option.getResultValue(0));
//            }
//        }
//
//        return optionsKV;
//    }

    public static void main(String[] args)
    {
	KeyValueProperties word_list = new KeyValueProperties();   
//	KeyValueProperties fsp = new KeyValueProperties();   
//        KeyValueProperties dev = new KeyValueProperties();
//        KeyValueProperties ben = new KeyValueProperties();
//        KeyValueProperties gur = new KeyValueProperties();
//        KeyValueProperties guj = new KeyValueProperties();
//        KeyValueProperties ori = new KeyValueProperties();
//        KeyValueProperties tam = new KeyValueProperties();
//        KeyValueProperties tel = new KeyValueProperties();
//        KeyValueProperties kan = new KeyValueProperties();
//        KeyValueProperties mal = new KeyValueProperties();
        
        try        
        { 
            String path = "data/word-translation/word-lists/english-wordList.txt";

//            word_list.read(path, "UTF-8");   
//            BhaashikTableModel word_list_table = KeyValueProperties.convertToBhaashikTableModel(word_list);
            BhaashikTableModel word_list_table = new BhaashikTableModel(0, 2);
            word_list_table.read(path, GlobalProperties.getIntlString("UTF-8"));
            TableSorter sorter = new TableSorter(word_list_table);
            
            sorter.setColumnComparator(String.class, TableSorter.STRING_INTEGER_COMPARATOR);
            sorter.setSortingStatus(1, TableSorter.DESCENDING);

            int rcount = word_list_table.getRowCount();            
            int ccount = word_list_table.getColumnCount();

            BhaashikTableModel sorted_word_list_table = new BhaashikTableModel(rcount, ccount);

            for (int i = 0; i < rcount; i++)
            {
//                for (int j = 0; j < ccount; j++)       
//                {
                    sorted_word_list_table.setValueAt(sorter.getValueAt(i, 0), i, 0);   
                    sorted_word_list_table.setValueAt(sorter.getValueAt(i, 1), i, 1);   
//                }
            }

            sorted_word_list_table.setColumnIdentifiers(new String[]{GlobalProperties.getIntlString("Word"), GlobalProperties.getIntlString("Frequency")});

            path = "data/word-translation/word-lists/english-wordList-sorted.txt";
            sorted_word_list_table.save(path, GlobalProperties.getIntlString("UTF-8"));
            
//	    fsp.read("properties.txt", "UTF-8"); //throws java.io.FileNotFoundException;
                        
//            dev.read("/home/anil/tmp/utf8-dev-chars.txt", "UTF-8");   
//            ben.read("/home/anil/tmp/utf8-ben-chars.txt", "UTF-8");
//            gur.read("/home/anil/tmp/utf8-gur-chars.txt", "UTF-8");
//            guj.read("/home/anil/tmp/utf8-guj-chars.txt", "UTF-8");
//            ori.read("/home/anil/tmp/utf8-ori-chars.txt", "UTF-8");
//            tam.read("/home/anil/tmp/utf8-dev-chars.txt", "UTF-8");
//            tel.read("/home/anil/tmp/utf8-tel-chars.txt", "UTF-8");
//            kan.read("/home/anil/tmp/utf8-kan-chars.txt", "UTF-8");
//            mal.read("/home/anil/tmp/utf8-mal-chars.txt", "UTF-8");
//
//            KeyValueProperties kvps[] = {dev, ben, gur, guj, ori, tam, tel, kan, mal};

//            BhaashikTableModel isc_utf8_map = KeyValueProperties.mergeKVPs(kvps);
//
//            TableSorter sorter = new TableSorter(isc_utf8_map);
//            
//            sorter.setSortingStatus(0, TableSorter.ASCENDING);
//
//            int rcount = isc_utf8_map.getRowCount();            
//            int ccount = isc_utf8_map.getColumnCount();
//
//            BhaashikTableModel sorted_isc_utf8_map = new BhaashikTableModel(rcount, ccount);
//
//            for (int i = 0; i < rcount; i++)
//            {
//                for (int j = 0; j < ccount; j++)       
//                {
//                    sorted_isc_utf8_map.setValueAt(sorter.getValueAt(i, j), i, j);   
//                }
//            }
//
//            sorted_isc_utf8_map.setColumnIdentifiers(new String[]{"isc-code", "dev", "ben", "gur", "guj", "ori", "tam", "tel", "kan", "mal"});
//            sorted_isc_utf8_map.save("/home/anil/tmp/isc_utf8_map.txt", "UTF-8");            
        }
        
        catch(Exception e)
        {
            System.out.println(GlobalProperties.getIntlString("Exception!"));
            
            e.printStackTrace();   
        }

//	fsp.print(System.out);
        // problem in taking characters like "\t" i.e. TAB
//	System.out.println(fsp.getPropertyValue("fieldSeparator")+"\t sdkjfk");   
    }
}

