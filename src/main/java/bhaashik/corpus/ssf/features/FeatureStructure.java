/*
 * Created on Aug 15, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.corpus.ssf.features;

import java.io.PrintStream;
import java.util.*;
import javax.swing.tree.*;

import bhaashik.corpus.ssf.tree.SSFNode;
import bhaashik.corpus.parallel.AlignmentUnit;
import bhaashik.table.BhaashikTableModel;

/**
 *  @author Anil Kumar Singh Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface FeatureStructure extends MutableTreeNode, FeatureValue {
//public interface FeatureStructure extends MutableTreeNode {
    
    int addAltAttribute(FeatureAttribute a);
    
    int addMultiAttribute(FeatureAttribute a);

    int addAltAttribute(FeatureAttribute a, String p);

    int addMultiAttribute(FeatureAttribute a, String p);

    int addAltAttribute(String name, String value);

    int addMultiAttribute(String name, String value);

    void addMandatoryAttributes();

    void clearAlt();

    void clearMulti();

    @Override
    void clearNestedAltValues();

    @Override
    void clearNestedMultiValues();

    int countAltAttributes();

    int countMultiAttributes();

    int findAltAttribute(FeatureAttribute a);

    int findMultiAttribute(FeatureAttribute a);

    FeatureAttribute getAltAttribute(String p);

    FeatureAttribute getMultiAttribute(String p);

    FeatureAttribute getOneOfAltAttributes(String names[]);

    FeatureAttribute getOneOfMultiAttributes(String names[]);

    FeatureAttribute getAltAttribute(int index);

    FeatureAttribute getMultiAttribute(int index);

    List<String> getAltAttributeNames();

    List<String> getMultiAttributeNames();

    List<String> getAltAttributeNames(String p);

    List<String> getMultiAttributeNames(String p);

    FeatureValue getAltAttributeValue(String p);

    FeatureValue getMultiAttributeValue(String p);

    String getAltAttributeValueString(String p);

    String getMultiAttributeValueString(String p);

    void setAltAttributeValue(String attibName, String val);

    void setMultiAttributeValue(String attibName, String val);

    FeatureValue getAltAttributeValueByIndex(String p);

    FeatureValue getMultiAttributeValueByIndex(String p);

    List<String> getAltAttributeValues();

    List<String> getMultiAttributeValues();

    List<String> getAltAttributeValuePairs();

    List<String> getMultiAttributeValuePairs();

    List<FeatureValue> getAltAttributeValues(String p);

    List<FeatureValue> getMultiAttributeValues(String p);

    BhaashikTableModel getAltFeatureTable();

    BhaashikTableModel getMultiFeatureTable();

    String getName();

    List<FeatureAttribute> getAltPaths(String name);

    List<FeatureAttribute> getMultiPaths(String name);

    List<FeatureAttribute> getAltPaths(String name, String p); // get_path_values($attr,$fs)

    List<FeatureAttribute> getMultiPaths(String name, String p); // get_path_values($attr,$fs)

    Object getNestedAltValue();

    Object getNestedMultiValue();

    void clearAnnotation(long annoLevelFlags, SSFNode containingNode);

    boolean hasMandatoryAttribs();

    void hasMandatoryAttribs(boolean m);

    void checkAndSetHasMandatory();

    String makeStringForRendering();

    String makeString();

    String makeStringFV();

    /**
     * Not yet implemented.
     * 
     * @param f1
     * @param f2
     * @return 
     */
    FeatureStructure mergeAlt(FeatureStructure f1, FeatureStructure f2);

    FeatureStructure mergeMulti(FeatureStructure f1, FeatureStructure f2);

    void modifyAltAttribute(FeatureAttribute a, int index);

    void modifyMultiAttribute(FeatureAttribute a, int index);

    void modifyAltAttribute(FeatureAttribute a, int index, String p);

    void modifyMultiAttribute(FeatureAttribute a, int index, String p);

    void modifyAltAttributeValue(FeatureValue fv, String p);

    void modifyMultiAttributeValue(FeatureValue fv, String p);

    void modifyAltAttributeValue(FeatureValue fv, int attribIndex, int altValIndex);

    void modifyMultiAttributeValue(FeatureValue fv, int attribIndex, int altValIndex);

    @Override
    void print(PrintStream ps);

    /**
     * Not yet implemented.
     */
    void pruneAlt();

    void pruneMulti();

    int readAltString(String fs_str) throws Exception;

    int readMultiString(String fs_str) throws Exception;
    
    int readAltStringV2(String fs_str) throws Exception;
    
    int readMultiStringV2(String fs_str) throws Exception;

    int readAltStringFV(String fs_str) throws Exception;

    int readMultiStringFV(String fs_str) throws Exception;

    void removeAllAltAttributes();

    void removeAllMultiAttributes();

    FeatureAttribute removeAltAttribute(int index);

    FeatureAttribute removeMultiAttribute(int index);

    FeatureAttribute removeAltAttribute(String p);

    FeatureAttribute removeMultiAttribute(String p);

    FeatureAttribute removeAltAttribute(int index, String p);

    FeatureAttribute removeMultiAttribute(int index, String p);

    void removeMandatoryAttributes();

    void removeNonMandatoryAltAttributes();

    void removeNonMandatoryMultiAttributes();

    void hideAltAttribute(String aname);

    void hideMultiAttribute(String aname);

    void unhideAltAttribute(String aname);

    void unhideMultiAttribute(String aname);

    FeatureAttribute searchAltAttribute(String name, boolean exactMatch);

    FeatureAttribute searchMultiAttribute(String name, boolean exactMatch);

    FeatureAttribute searchOneOfAltAttributes(String names[], boolean exactMatch);

    FeatureAttribute searchOneOfMultiAttributes(String names[], boolean exactMatch);

    FeatureAttribute searchAltAttributeValue(String name, String val, boolean exactMatch);

    FeatureAttribute searchMultiAttributeValue(String name, String val, boolean exactMatch);

    List<FeatureAttribute> searchAltAttributeValues(String name, String val, boolean exactMatch);

    List<FeatureAttribute> searchMultiAttributeValues(String name, String val, boolean exactMatch);

    List<FeatureAttribute> replaceAltAttributeValues(String name, String val, String nameReplace, String valReplace);

    List<FeatureAttribute> replaceMultiAttributeValues(String name, String val, String nameReplace, String valReplace);

    List<FeatureAttribute> searchAltAttributes(String name, boolean exactMatch);

    List<FeatureAttribute> searchMultiAttributes(String name, boolean exactMatch);

    void setAltFeatureTable(BhaashikTableModel ft);

    void setMultiFeatureTable(BhaashikTableModel ft);

    void setName(String n);

    void setToEmpty();

    void setNestedAltValue(Object v);

    void setNestedMultiValue(Object v);

    /**
     * Not yet implemented.
     * 
     * @param f1
     * @param f2
     * @return 
     */
    FeatureStructure unifyAlt(FeatureStructure f1, FeatureStructure f2);

    FeatureStructure unifyMulti(FeatureStructure f1, FeatureStructure f2);

    void setAlignmentUnit(AlignmentUnit alignmentUnit);

    AlignmentUnit loadAlignmentUnit(Object srcAlignmentObject, Object srcAlignmentObjectContainer, Object tgtAlignmentObjectContainer, int parallelIndex);

    public Object clone();

    public void sortAltAttributes(int sortType);

    public void sortMultiAttributes(int sortType);

    @Override
    public boolean equals(Object fa);
}