/*
 * Created on Aug 15, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package bhaashik.corpus.ssf.features;

import java.io.PrintStream;
import java.io.Serializable;
import javax.swing.tree.*;


/**
 *  @author Anil Kumar Singh Kumar Singh
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface FeatureAttribute extends MutableTreeNode, Serializable, Cloneable {
    public static final int SORT_BY_NAME = 0;

    /**
     *
     * @param v
     * @return
     */
    int addNestedAltValue(FeatureValue v);

    int addNestedMultiValue(FeatureValue v);

    void clear();

    int countNestedAltValues();

    int countNestedMultiValues();

    int findNestedAltValue(FeatureValue v);

    int findNestedMultiValue(FeatureValue v);

    FeatureValue getNestedAltValue(int index);

    FeatureValue getNestedMultiValue(int index);

    String getName();

    String makeString(boolean mandatory);

    void modifyNestedAltValue(FeatureValue v, int index);

    void modifyNestedMultiValue(FeatureValue v, int index);

    void print(PrintStream ps, boolean mandatory);

    void removeAllNestedAltValues();

    void removeAllNestedMultiValues();

    FeatureValue removeNestedAltValue(int index);

    FeatureValue removeNestedMultiValue(int index);

    void hideAttribute();
    void unhideAttribute();
    boolean isHiddenAttribute();

    void setName(String n);

    // other methods
    public Object clone();

    @Override
    public boolean equals(Object obj);
}