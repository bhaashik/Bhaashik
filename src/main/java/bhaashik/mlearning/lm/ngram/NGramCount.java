/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bhaashik.mlearning.lm.ngram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anil
 */
public interface NGramCount extends Serializable {

    long getFreq();

    ArrayList<Integer> getIndices();

    String getString(NGramCounts ngramLM);

    void setFreq(long f);

    void setIndices(ArrayList<Integer> wdIndices);

    void setString(NGramCounts ngramLM, String s);    
    
    Object clone();
}
