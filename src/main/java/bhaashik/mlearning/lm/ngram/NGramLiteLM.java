/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bhaashik.mlearning.lm.ngram;

import bhaashik.datastr.ConcurrentLinkedHashMap;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anil
 */
public interface NGramLiteLM <NG extends NGramLite> extends NGramCounts<NG> {

    @Override
    ConcurrentLinkedHashMap<ArrayList<Integer>, Long> getCumulativeFrequencies(int whichGram);

    @Override
    ArrayList<ConcurrentLinkedHashMap<ArrayList<Integer>, Long>> getCumulativeFrequenciesList();
    
}
