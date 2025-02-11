/*
 * Created on Aug 15, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.corpus.ssf.features;

import java.io.*;
import java.util.List;
import javax.swing.tree.*;

import bhaashik.corpus.ssf.tree.SSFNode;
import bhaashik.tree.BhaashikMutableTreeNode;
import bhaashik.corpus.parallel.AlignmentUnit;

/**
 *  @author Anil Kumar Singh Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface FeatureStructures extends MutableTreeNode {
    int addAltFSValue(FeatureStructure f);
    int addMultiFSValue(FeatureStructure f);

    void clear();

    int countAltFSValues();
    int countMultiFSValues();

    int findAltFSValue(FeatureStructure fs);
    int findMultiFSValue(FeatureStructure fs);

    FeatureStructure getAltFSValue(int num);
    FeatureStructure getMultiFSValue(int num);

    BhaashikMutableTreeNode getCopy() throws Exception;

    boolean isDeep();

    String makeString();

    String makeStringFV();

    String makeStringForRendering();

    void modifyAltFSValue(FeatureStructure fs, int index);
    void modifyMultiFSValue(FeatureStructure fs, int index);

    void print(PrintStream ps);

    int readString(String fs_str) throws Exception;

    int readStringFV(String fs_str) throws Exception;

    FeatureStructure removeAltFSValue(int num);
    FeatureStructure removeMultiFSValue(int num);

    void clearAnnotation(long annoLevelFlags, SSFNode containingNode);

    void setAltAttribsToEmpty();
    void setMultiAttribsToEmpty();

    List<String> getAltAttributeNames();
    List<String> getMultiAttributeNames();

    String getAltAttributeValueString(String attibName);
    String getMultiAttributeValueString(String attibName);

    List<String> getAltAttributeValues();
    List<String> getMultiAttributeValues();

    List<String> getAltAttributeValuePairs();
   List<String> getMultiAttributeValuePairs();

    String[] getOneOfAltAttributeValues(String attibNames[]);
    String[] getOneOfMultiAttributeValues(String attibNames[]);

    void setAltAttributeValue(String attibName, String val);
    void setMultiAttributeValue(String attibName, String val);

    void concatenateAltAttributeValue(String attibName, String val, String sep);
    void concatenateMultiAttributeValue(String attibName, String val, String sep);

    void setAllAltAttributeValues(String attibName, String val);
    void setAllMultiAttributeValues(String attibName, String val);

    void hideAltAttribute(String aname);
    void hideMultiAttribute(String aname);
    void unhideAltAttribute(String aname);
    void unhideMultAttribute(String aname);

    FeatureAttribute getAltAttribute(String attibName);
    FeatureAttribute getMultiAttribute(String attibName);

    FeatureStructures getAltFeatureStructures(FeatureStructures fss, AlignmentUnit alignmentUnit);
    FeatureStructures getMultiFeatureStructures(FeatureStructures fss, AlignmentUnit alignmentUnit);

    void setAlignmentUnit(AlignmentUnit alignmentUnit);

    AlignmentUnit loadAlignmentUnit(Object srcAlignmentObject, Object srcAlignmentObjectContainer, Object tgtAlignmentObjectContainer, int parallelIndex);

    Object clone();
    
    boolean equals(Object fa);
}