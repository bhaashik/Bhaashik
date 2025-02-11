/*
 * Created on Jun 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.corpus.ssf.features.impl;

import edu.stanford.nlp.util.HashIndex;
import edu.stanford.nlp.util.Index;
import java.io.PrintStream;
import java.util.Comparator;

import bhaashik.corpus.ssf.features.FeatureAttribute;
import bhaashik.corpus.ssf.features.FeatureValue;
import bhaashik.tree.BhaashikMutableTreeNode;


/**
 *  @author Anil Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FeatureAttributeImpl extends BhaashikMutableTreeNode
        implements FeatureAttribute {

    /**
     * 
     */

    // Children could be FeatureValueImpl or FeatureStructureImpl
    private boolean hide = false;

    private int nameIndex;

    static Index<String> namesIndex = new HashIndex<>();    

    public FeatureAttributeImpl() {
        super();
    }

    public FeatureAttributeImpl(Object userObject) {
        super();
        nameIndex = getIndex((String) userObject, true);
    }
    
    public FeatureAttributeImpl(Object userObject, boolean allowsChildren) {
        super(null, allowsChildren);
        nameIndex = getIndex((String) userObject, true);
    }

    @Override
    public String getName() 
    {
        return getString(nameIndex);
    }

    @Override
    public void setName(String n) 
    {
        nameIndex = getIndex(n, true);
    }

    public long getNameVocabularySize()
    {
        long vocabularySize = namesIndex.size();
        
        return vocabularySize;
    }
    
    public static int getIndex(String wd, boolean add)
    {
        int wi = namesIndex.indexOf(wd, add);
        
        return wi;
    }

    public static String getString(Integer wdIndex)
    {
        return namesIndex.get(wdIndex);
    }

    @Override
    public Object getUserObject()
    {
        return getName();
    }
 
    @Override
    public int countNestedAltValues()
    {
        return -1;
    }
 
    @Override
    public int countNestedMultiValues()
    {
        return getChildCount();
    }

    @Override
    public int addNestedAltValue(FeatureValue v)
    {
        System.err.println("Not currently implemented.");
        return -1;
    }

    @Override
    public int addNestedMultiValue(FeatureValue v)
    {
        add((BhaashikMutableTreeNode) v);
        return getChildCount();
    }

    @Override
    public int findNestedAltValue(FeatureValue v) 
    {
        System.err.println("Not currently implemented.");
        return -1;
    }

    @Override
    public int findNestedMultiValue(FeatureValue v) 
    {
        return getIndex((FeatureValueImpl) v);
    }

    @Override
    public FeatureValue getNestedAltValue(int index) 
    {
        System.err.println("Not currently implemented.");
        return null;
    }

    @Override
    public FeatureValue getNestedMultiValue(int index) 
    {
        return (FeatureValue) getChildAt(index);
    }

    @Override
    public void modifyNestedAltValue(FeatureValue v, int index)
    {
        System.err.println("Not currently implemented.");
    }

    @Override
    public void modifyNestedMultiValue(FeatureValue v, int index)
    {
        insert((FeatureValueImpl) v, index);
        remove(index + 1);
    }

    @Override
    public FeatureValue removeNestedAltValue(int index) 
    {
        System.err.println("Not currently implemented.");
        return null;
    }

    @Override
    public FeatureValue removeNestedMultiValue(int index) 
    {
        FeatureValue rem = getNestedAltValue(index);
        remove(index);
        
        return rem;
    }

    @Override
    public void removeAllNestedAltValues() 
    {
        System.err.println("Not currently implemented.");
    }

    @Override
    public void removeAllNestedMultiValues() 
    {
        int count = countNestedMultiValues();
        
        for (int i = 0; i < count; i++) {
            removeNestedMultiValue(i);
        }
    }

    @Override
    public void hideAttribute()
    {
        hide = true;
    }

    @Override
    public void unhideAttribute()
    {
        hide = false;
    }

    @Override
    public boolean isHiddenAttribute()
    {
        return hide;
    }

    @Override
    public String makeString(boolean mandatory)
    {
        String str = "";
        FSProperties fsp = FeatureStructuresImpl.getFSProperties();

        if(mandatory == true)
        {
            str += getNestedAltValue(0).makeString();

            for(int i = 1; i < countNestedAltValues(); i++) {
                str += fsp.getProperties().getPropertyValueForPrint("attribOR") + getNestedAltValue(i).makeString();
            }

            return str;
        }
        else
        {
            String valStr = getNestedAltValue(0).makeString();

            if(valStr == null) {
                valStr = "";
            }
            
            if(valStr.equals("'") || valStr.equals("''") || (valStr.startsWith("'") == false || valStr.endsWith("'") == false)) {
                str +=  getName() + fsp.getProperties().getPropertyValueForPrint("attribEquate") + "'" + valStr + "'";
            }
            else {
                str +=  getName() + fsp.getProperties().getPropertyValueForPrint("attribEquate") + valStr;
            }

            for(int i = 1; i < countNestedAltValues(); i++)
            {
                valStr = getNestedAltValue(i).makeString();
                
                if(valStr.equals("'") || valStr.equals("''") || (valStr.startsWith("'") == false || valStr.endsWith("'") == false)) {
                    str += fsp.getProperties().getPropertyValueForPrint("attribOR") + "'" + valStr + "'";
                }
                else {
                    str += fsp.getProperties().getPropertyValueForPrint("attribOR") + valStr;
                }
            }
        }
        return str;
    }

    // other methods

    @Override
    public void print(PrintStream ps, boolean mandatory)
    {
        ps.println(makeString(mandatory));
    }

    @Override
    public void clear()
    {
        nameIndex = -1;
        removeAllChildren();
    }
    
    public static Comparator getAttributeComparator(int sortType)
    {
	switch(sortType)
	{
	    case SORT_BY_NAME:
		return new Comparator()
		{
                    @Override
		    public int compare(Object one, Object two)
		    {
			return ( ((FeatureAttribute) one).getName().compareToIgnoreCase( ((FeatureAttribute) two).getName() ) );
		    }
		};
	}
	
	return null;
    }

    // Equal if the name as well as all the alt values are equal.
    @Override
    public boolean equals(Object obj)
    {
	if(obj == null || !(obj instanceof FeatureAttributeImpl)) {
            return false;
        }
	
	FeatureAttribute aobj = (FeatureAttribute) obj;
	
	if(getName().equalsIgnoreCase(aobj.getName()) == false) {
            return false;
        }
	
	int count = countNestedAltValues();

        if(count != aobj.countNestedAltValues()) {
            return false;
        }

	for (int i = 0; i < count; i++)
	{
	    if(getNestedAltValue(i).equals(aobj.getNestedAltValue(i)) == false) {
                return false;
            }
	}
	
	return true;
    }

    public static void main(String[] args) {
        FeatureAttribute attrib = new FeatureAttributeImpl();
    }
}
