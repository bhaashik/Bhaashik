/*
 * MultiPropertiesManager.java
 *
 * Created on April 2, 2006, 4:52 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.properties;

import java.io.*;
import java.util.*;

import bhaashik.GlobalProperties;
import bhaashik.datastr.ConcurrentLinkedHashMap;
import bhaashik.resources.aggregate.AggregateResourceImpl;
import bhaashik.xml.dom.BhaashikDOMElement;
import org.dom4j.dom.DOMAttribute;
import org.dom4j.dom.DOMElement;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author anil
 */
public class MultiPropertiesManager extends AggregateResourceImpl implements Serializable, BhaashikProperties, BhaashikDOMElement
{
    protected ConcurrentLinkedHashMap multiprops;
    
    /** Creates a new instance of MultiKeyValueProperties */
    public MultiPropertiesManager()
    {
	multiprops = new ConcurrentLinkedHashMap(0, 3);
    }

    public MultiPropertiesManager(String propFile, String cs) throws FileNotFoundException, IOException
    {
	read(propFile, cs);
    }

    public MultiPropertiesManager(ConcurrentLinkedHashMap multiprops)
    {
	this.multiprops = multiprops;
    }

    public void print(java.io.PrintStream ps)
    {
	PropertiesManager.printMany(multiprops, ps);
    }

    public int read(String f, String charset) throws FileNotFoundException, IOException
    {
	multiprops = (ConcurrentLinkedHashMap) PropertiesManager.readMany(f, charset);

	return multiprops.size();
    }
        
    public int save() throws FileNotFoundException, IOException
    {
        PropertiesManager.saveMany(multiprops, filePath, charset);
        
        return 0;
    }

    public int save(String f, String cs) throws FileNotFoundException, IOException, UnsupportedEncodingException
    {
        PropertiesManager.saveMany(multiprops, f, cs);
        
        return 0;
    }

    public ConcurrentLinkedHashMap getMultiPropertiesMap()
    {
	return multiprops;
    }

    public int countProperties()
    {
        return multiprops.size();   
    }

    public Iterator getPropertiesKeys()
    {
        return multiprops.keySet().iterator();
    }

    public PropertiesManager getPropertiesValue(String p /* Properties key */)
    {
        return (PropertiesManager) multiprops.get(p);   
    }

    public int addProperties(String p /* properties key */, PropertiesManager v /*properties value */)
    {
        multiprops.put(p, v);
        
        return multiprops.size();   
    }

    public PropertiesManager removeProperties(String p /* properties key */)
    {
        return (PropertiesManager) multiprops.remove(p);   
    }

    public DOMElement getDOMElement() {
        DOMElement domElement = new DOMElement(GlobalProperties.getIntlString("MultiPropertiesManager"));
        Iterator itr = multiprops.keySet().iterator();

        while(itr.hasNext())
        {
            String key = (String) itr.next();
            KeyValueProperties val = (KeyValueProperties) multiprops.get(key);
            
            DOMElement kvpElement = val.getDOMElement();
            DOMAttribute attribName = new DOMAttribute(kvpElement, new org.dom4j.QName(GlobalProperties.getIntlString("name")), key);
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

    public void readXML(Element domElement) {
        NodeList elements = domElement.getElementsByTagName(GlobalProperties.getIntlString("PropertiesManager"));

        int count = elements.getLength();
        
        for (int i = 0; i < count; i++)
        {
            org.w3c.dom.Element propsElement = (org.w3c.dom.Element) elements.item(i);
            String key = propsElement.getAttribute(GlobalProperties.getIntlString("name"));
            
            PropertiesManager props = new PropertiesManager();
            props.readXML(propsElement);

            multiprops.put(key, props);
        }        
    }
}
