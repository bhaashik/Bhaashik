/*
 * Created on Aug 13, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.corpus.ssf.features.impl;

import bhaashik.GlobalProperties;
import bhaashik.corpus.ssf.features.FeatureStructures;
import edu.stanford.nlp.util.HashIndex;
import edu.stanford.nlp.util.Index;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import bhaashik.corpus.ssf.features.FeatureValue;
import bhaashik.tree.BhaashikMutableTreeNode;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *  @author Anil Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FeatureValueImpl extends BhaashikMutableTreeNode
        implements FeatureValue
{
    transient java.util.ResourceBundle bundle = GlobalProperties.getResourceBundle(); // NOI18N
//    transient FeatureValueUserObject ;
    /**featureValueUserObject
     * 
     */
    
    // Leaf node with a String (value) as the user object
//    protected List<Integer> altAalueIndices;
    
    public static String ALT_VALUE_SEPARATOR = "::";
    public static String MULTI_VALUE_SEPARATOR = ";";
    public static String ALT_MULTI_VALUES_SEPARATOR = ":;:";

    public FeatureValueImpl() {
        super();
        
        userObject = new FeatureValueUserObject();
    }

    public FeatureValueImpl(Object userObject) {
        super(userObject);
//        featureValueUserObject = getAltValueIndices((String) userObject, true);
    }
    
    public FeatureValueImpl(Object userObject, boolean allowsChildren) {
        super(userObject, allowsChildren);
    }
    
    @Override
    public Object getAltValue()
    {
        return ((FeatureValueUserObject) getUserObject());
    }
    
    @Override
    public Object getMultiValue()
    {
        return getMultiValueString(multipleValueIndices);
    }
    
    @Override
    public void setAltValue(Object v)
    {
        altValueIndices = getAltValueIndices(v.toString(), true);
    }
    
    @Override
    public void setMultiValue(Object v)
    {
        multipleValueIndices = getMultiValueIndices(v.toString(), true);
    }

    @Override
    public Object getUserObject() 
    {
//        String str = "";
//
//        if(altValueIndices == null || altValueIndices.isEmpty())
//        {
//            str = getMultiValueString(altValueIndices);
//        }
//        else if(multipleValueIndices == null || multipleValueIndices.isEmpty())
//        {
//            str = getAltValueString(altValueIndices);
//        }
//        else{
//            if( (altValueIndices != null || !altValueIndices.isEmpty()) && (multipleValueIndices != null || !multipleValueIndices.isEmpty()) )
//            {
//                str = getAltValueString(altValueIndices);
//                
//                str += ALT_MULTI_VALUES_SEPARATOR + getMultiValueString(altValueIndices);                
//            }
//        }
//        
//        return str;
        
        return userObject;
    }

    @Override
    public void setUserObject(Object userObject) 
    {                    
        this.userObject = userObject;
//        if(userObject instanceof FeatureValueUserObject)
//        {
//            this.userObject = userObject;
//        }
//        else if(userObject instanceof String)
//        {        
//            String altValStr = "";
//            String multiValStr = "";
//
//            String parts[] = userObject.toString().split(ALT_MULTI_VALUES_SEPARATOR);
//
//            if(parts.length == 1)
//            {
//                if(parts[0].contains(ALT_VALUE_SEPARATOR))
//                {
//                    altValStr = parts[0];
//                    altValueIndices = getAltValueIndices(altValStr, true);
//                }
//                else if(parts[0].contains(MULTI_VALUE_SEPARATOR))
//                {
//                    multiValStr = parts[0];
//                    multipleValueIndices = getMultiValueIndices(multiValStr, true);
//                }
//            }
//            else if(parts.length == 2)
//            {
//                altValStr = parts[0];            
//                multiValStr = parts[1];
//
//                altValueIndices = getAltValueIndices(altValStr, true);
//                multipleValueIndices = getMultiValueIndices(multiValStr, true);
//            }
//
//    //        altValueIndices = getAltValueIndices(userObject.toString(), true);
//    //        multipleValueIndices = getMultiValueIndices(userObject.toString(), true);
//        }
    }

    @Override
    public long getAltValueVocabularySize()
    {
        long vocabularySize = ((FeatureValueUserObject)getUserObject()).altValueIndices.size();
        
        return vocabularySize;
    }

    @Override
    public long getMultiValueVocabularySize()
    {
        long vocabularySize = ((FeatureValueUserObject)getUserObject()).multipleValueIndices.size();
        
        return vocabularySize;
    }

    @Override
    public List<Integer> getAltValueIndices(String wds, boolean add)
    {
        String parts[] = wds.split(ALT_VALUE_SEPARATOR);
        
        List<Integer> indices = new ArrayList<Integer>(parts.length);
        
        for (int i = 0; i < parts.length; i++) {
            int wi = altValueIndices.indexOf((Object) parts[i]);
            
            indices.add(wi);
        }
        
        return indices;
    }

    @Override
    public List<Integer> getMultiValueIndices(String wds, boolean add)
    {
        String parts[] = wds.split(MULTI_VALUE_SEPARATOR);
        
        List<Integer> indices = new ArrayList<Integer>(parts.length);
        
        for (int i = 0; i < parts.length; i++) {
            int wi = multipleValueIndices.indexOf((Object) parts[i]);
            
            indices.add(wi);
        }
        
        return indices;
    }

    public static String getAltValueString(List<Integer> wdIndices)
    {
        if(wdIndices == null || wdIndices.isEmpty())
        {
            return "";
        }
        
        String str = "";
        
        int i = 0;
        for (Integer wi : wdIndices) {
            
            if(wi == -1)
            {
                continue;
            }
            
            if(i == 0)
            {
//                str = altValueIndices.get(wi);
                str = altValueIndex.get(wi);
            }
            else
            {
//                str += ALT_VALUE_SEPARATOR + altValueIndices.get(wi);
                str += ALT_VALUE_SEPARATOR + altValueIndex.get(wi);
            }
            
            i++;
        }
        
        return str;
    }

    public static String getMultiValueString(List<Integer> wdIndices)
    {
        if(wdIndices == null || wdIndices.isEmpty())
        {
            return "";
        }
        
        String str = "";
        
        int i = 0;
        for (Integer wi : wdIndices) {
            
            if(wi == -1)
            {
                continue;
            }
            
            if(i == 0)
            {
//                str = altValueIndices.get(wi);
                str = altValueIndex.get(wi);
            }
            else
            {
//                str += ALT_VALUE_SEPARATOR + altValueIndices.get(wi);
                str += ALT_VALUE_SEPARATOR + altValueIndex.get(wi);
            }
            
            i++;
        }
        
        return str;
    }

    public static List<String> getAltValueStrings(List<Integer> wdIndices)
    {        
        if(wdIndices == null || wdIndices.isEmpty())
        {
            return null;
        }
        
        String altValuesStr = getAltValueString(wdIndices);
        
        String parts[] = altValuesStr.split(ALT_VALUE_SEPARATOR);
        
        if(parts.length < 1)
        {
            return null;
        }
        
        List<String> altValStrings = Arrays.asList(parts);
        
        return altValStrings;
    }

    public static List<String> getMultiValueStrings(List<Integer> wdIndices)
    {
        if(wdIndices == null || wdIndices.isEmpty())
        {
            return null;
        }
        
        String multiValuesStr = getMultiValueString(wdIndices);
        
        String parts[] = multiValuesStr.split(MULTI_VALUE_SEPARATOR);
        
        if(parts.length < 1)
        {
            return null;
        }
        
        List<String> multiValStrings = Arrays.asList(parts);
        
        return multiValStrings;
    }

    @Override
    public boolean isFeatureStructure()
    {
	return false;
    }
    
    @Override
    public int readAltValueString(String str) throws Exception
    {
	setAltValue(str);
	return 0;
    }
    
    @Override
    public int readMultiValueString(String str) throws Exception
    {
	setMultiValue(str);
	return 0;
    }
	
    @Override
    public String makeStringAlt()
    {
        if(getUserObject() == null) {
            return null;
        }
        
        return getUserObject().toString();
    }
	
    @Override
    public String makeStringMulti()
    {
        if(getUserObject() == null) {
            return null;
        }
        
        return getUserObject().toString();
    }

    @Override
    public String makeStringAltForRendering()
    {
        return makeStringAlt();
    }

    @Override
    public String makeStringMultiForRendering()
    {
        return makeStringMulti();
    }
	
    @Override
    public String toString()
    {
//    {
//        if(altValueIndices != null && altValueIndices.isEmpty() == false)
//        {
//            System.err.println("toString() not implemented for alt values. Only for multi values.");
//            
////            makeStringAlt();
//        }
//        
//        return (String) makeStringMulti();
            
        return getUserObject().toString();
    }

    @Override
    public Object clone()
    {
        FeatureValueImpl obj = (FeatureValueImpl) super.clone();

        return obj;
    }

    @Override
    public void print(PrintStream ps)
    {
//        if(altValueIndices != null && altValueIndices.isEmpty() == false)
//        {
//            System.err.println("print(PrintStream ps) not implemented for alt values. Only for multi values.");
//            
////            ps.(makeStringAlt());
//        }
//        ps.println(makeStringMulti());
        
        ((FeatureValueUserObject) getUserObject()).print(ps);
    }

    @Override
    public void clearNestedAltValues()
    {
        clearAltValues();
    }

    @Override
    public void clearNestedMultiValues()
    {
        clearMultiValues();
    }

    @Override
    public void clearAltValues()
    {
//        altValueIndices.clear();
//        
//        String valueString = makeStringAlt();
//        
//        setUserObject(valueString);
        
        ((FeatureValueUserObject) getUserObject()).getAltValueIndices().clear();
    }

    @Override
    public void clearMultiValues()
    {
//        multipleValueIndices.clear();
//        
//        String valueString = makeString();
//        
//        setUserObject(valueString);

        ((FeatureValueUserObject) getUserObject()).getMultiValueIndices().clear();
    }

    @Override
    public boolean equals(Object obj)
    {
        if(!(obj instanceof FeatureValueImpl)) {
            return false;
        }
        
        String valueString = makeString();
//	if(obj == null && valueString.equals("")) {
//            return true;
//        }
//
//        if(obj == null || getAltValue() == null) {
//            return false;
//        }
//	
//	if(((String) getAltValue()).equalsIgnoreCase((String) ((FeatureValue) obj).getAltValue())) {
//            return true;
//        }
        
	
	return valueString.equals(obj.toString());
    }
    
    @Override
    public String toString()
    {
        String str = "";
        
        if()
    }
    
    class FeatureValueUserObject
    {
        private transient Object altUserObject;
        private transient Object multiUserObject;

        private transient List<Integer> multipleValueIndices = new LinkedList<>();
        private transient List<Integer> altValueIndices = new LinkedList<>();    

        static Index<String> multipleValueIndex = new HashIndex<>();
        static Index<String> altValueIndex = new HashIndex<>();    

        public FeatureValueUserObject() {
        }
        
        public FeatureValueUserObject(Object altObject, List<Integer> altIndices, Object multiObject, List<Integer> multiIndices)
        {
            this.altUserObject = altObject;
            this.multiUserObject = multiObject;
            this.altValueIndices = altIndices;
            this.multipleValueIndices = multiIndices;
        }

        /**
         * @return the altUserObject
         */
        Object getAltUserObject() {
            return altUserObject;
        }

        /**
         * @param altUserObject the altUserObject to set
         */
        void setAltUserObject(Object altUserObject) {
            java.lang.Object oldAltUserObject = this.altUserObject;
            this.altUserObject = altUserObject;
            propertyChangeSupport.firePropertyChange(PROP_ALTUSEROBJECT, oldAltUserObject, altUserObject);
        }

        /**
         * @return the multiUserObject
         */
        Object getMultiUserObject() {
            return multiUserObject;
        }

        /**
         * @param multiUserObject the multiUserObject to set
         */
        void setMultiUserObject(Object multiUserObject) {
            java.lang.Object oldMultiUserObject = this.multiUserObject;
            this.multiUserObject = multiUserObject;
            propertyChangeSupport.firePropertyChange(PROP_MULTIUSEROBJECT, oldMultiUserObject, multiUserObject);
        }

        /**
         * @return the multipleValueIndices
         */
        List<Integer> getMultipleValueIndices() {
            return multipleValueIndices;
        }

        /**
         * @param multipleValueIndices the multipleValueIndices to set
         */
        void setMultipleValueIndices(List<Integer> multipleValueIndices) {
            java.util.List<java.lang.Integer> oldMultipleValueIndices = this.multipleValueIndices;
            this.multipleValueIndices = multipleValueIndices;
            propertyChangeSupport.firePropertyChange(PROP_MULTIPLEVALUEINDICES, oldMultipleValueIndices, multipleValueIndices);
        }

        /**
         * @return the altValueIndices
         */
        List<Integer> getAltValueIndices() {
            return altValueIndices;
        }

        /**
         * @param altValueIndices the altValueIndices to set
         */
        void setAltValueIndices(List<Integer> altValueIndices) {
            java.util.List<java.lang.Integer> oldAltValueIndices = this.altValueIndices;
            this.altValueIndices = altValueIndices;
            propertyChangeSupport.firePropertyChange(PROP_ALTVALUEINDICES, oldAltValueIndices, altValueIndices);
        }

//        /**
//         * @return the multipleValueIndex
//         */
//        static Index<String> getMultipleValueIndex() {
//            return multipleValueIndex;
//        }
//
//        /**
//         * @param aMultipleValueIndex the multipleValueIndex to set
//         */
//        static void setMultipleValueIndex(Index<String> aMultipleValueIndex) {
//            edu.stanford.nlp.util.Index<java.lang.String> oldMultipleValueIndex = multipleValueIndex;
//            multipleValueIndex = aMultipleValueIndex;
//            propertyChangeSupport.firePropertyChange(PROP_MULTIPLEVALUEINDEX, oldMultipleValueIndex, multipleValueIndex);
//        }
//
//        /**
//         * @return the altValueIndex
//         */
//        static Index<String> getAltValueIndex() {
//            return altValueIndex;
//        }
//
//        /**
//         * @param aAltValueIndex the altValueIndex to set
//         */
//        static void setAltValueIndex(Index<String> aAltValueIndex) {
//            edu.stanford.nlp.util.Index<java.lang.String> oldAltValueIndex = altValueIndex;
//            altValueIndex = aAltValueIndex;
//            propertyChangeSupport.firePropertyChange(PROP_ALTVALUEINDEX, oldAltValueIndex, altValueIndex);
//        }
        List<Integer> getAltValueIndices(String wds, boolean add)
        {
            List<Integer> indices = null;
            
            if(wds != null && wds.equals("") == false)
            {
                String parts[] = wds.split(ALT_VALUE_SEPARATOR);

                indices = new ArrayList<>(parts.length);

                for (int i = 0; i < parts.length; i++) {
                    int wi = altValueIndices.indexOf((Object) parts[i]);

                    indices.add(wi);
                }
            }
            else
            {
                indices = new ArrayList<>(0);
            }

            return indices;
        }

        List<Integer> getMultiValueIndices(String wds, boolean add)
        {
            List<Integer> indices = null;
            
            if(wds != null && wds.equals("") == false)
            {
                String parts[] = wds.split(MULTI_VALUE_SEPARATOR);

                indices = new ArrayList<>(parts.length);

                for (int i = 0; i < parts.length; i++) {
                    int wi = multipleValueIndices.indexOf((Object) parts[i]);

                    indices.add(wi);
                }
            }
            else
            {
                indices = new ArrayList<>(0);
            }

            return indices;
        }

        @Override
        public String toString()
        {
            String str = "";
            
            if(altUserObject == null || altUserObject.toString().equals(""))
            {
                str += multiUserObject.toString();
            }
            else if(multiUserObject == null || multiUserObject.toString().equals(""))
            {
                str += altUserObject.toString();
            }
            else
            {
                str += altUserObject.toString() + ALT_MULTI_VALUES_SEPARATOR + multiUserObject.toString();
            }
            
            return str;
        }

        public int readString(String str) throws Exception
        {
            int ret = -1;

            String parts[] = null;
            
            if(str != null && str.equals("") == false)
            {
                
                if(getUserObject() instanceof FeatureValue)
                {
                    if()
                }
                else if(getUserObject() instanceof FeatureStructures)
                {
                    ((FeatureStructures) getUserObject()).readString(str);
                }
            }
            
            return ret;
        }

        public int readStringAlt(String str) throws Exception
        {
            int ret = -1;
            
            String altStr = "";
            String multiStr = "";
            
            if(str != null && str.equals("") == false)
            {
                
            }
            
            if(str != null && str.equals("") == false && getUserObject() instanceof FeatureValueUserObject)
            {                
                FeatureValueUserObject fvObject = ((FeatureValueUserObject) getUserObject());
                
                if(getUserObject() instanceof FeatureValue)
                {
                    if()
                }
                else if(getUserObject() instanceof FeatureStructures featureStructures)
                {
                    featureStructures.readString(str);
                }
            }
            
            return ret;
        }
    
        String makeStringMulti()
        {
            return altUserObject.toString();            
        }

        String makeStringAltForRendering()
        {
            return makeStringAlt();
        }

        String makeStringMulti()
        {
            return multiUserObject.toString();            
        }

        String makeStringMultiForRendering()
        {
            return makeStringMulti();
        }

        void print(PrintStream ps)
        {
            ps.print(toString());
        }

        int readAltValueString(String str) throws Exception;
        int readMultiValueString(String str) throws Exception;

        boolean equals(Object obj);
        
        private final transient PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);
        
        public static final String PROP_ALTUSEROBJECT = "altUserObject";
        public static final String PROP_MULTIUSEROBJECT = "multiUserObject";
        public static final String PROP_MULTIPLEVALUEINDICES = "multipleValueIndices";
        public static final String PROP_ALTVALUEINDICES = "altValueIndices";
        public static final String PROP_MULTIPLEVALUEINDEX = "multipleValueIndex";
        public static final String PROP_ALTVALUEINDEX = "altValueIndex";
    }
    
    public static void main(String[] args) {
    }
}
