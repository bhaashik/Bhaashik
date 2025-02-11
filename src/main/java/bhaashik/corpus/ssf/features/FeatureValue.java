/*
 * Created on Aug 15, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.corpus.ssf.features;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.List;
import javax.swing.tree.*;

/**
 *  @author Anil Kumar Singh Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface FeatureValue extends MutableTreeNode, Serializable, Cloneable {
    boolean isFeatureStructure();
    
    void clearNestedAltValues();
    void clearNestedMultiValues();

    void clearAltValues();
    void clearMultiValues();

    Object clone();

    Object getAltValue();
    List<Integer> getAltValueIndices(String wds, boolean add);    

    Object getMultiValue();
    List<Integer> getMultiValueIndices(String wds, boolean add);    

    long getMultiValueVocabularySize();
    long getAltValueVocabularySize();
    
    String makeStringAlt();

    String makeStringAltForRendering();

    String makeStringMulti();

    String makeStringMultiForRendering();

    void print(PrintStream ps);

    int readAltValueString(String str) throws Exception;
    int readMultiValueString(String str) throws Exception;

//    @Override
    Object getUserObject();
    
    @Override
    void setUserObject(Object object);
    
    Object getAltUserObject();
    Object getMultiUserObject();
    
    void setAltUserObjeect(Object userObject);
    void setMultiUserObject(Object userObject);

    void setAltValue(Object v);
    void setMultiValue(Object v);

    public boolean equals(Object obj);
}