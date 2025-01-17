/*
 * ModelScore.java
 *
 * Created on January 25, 2009, 7:02 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.mlearning.common;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import bhaashik.datastr.ConcurrentLinkedHashMap;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Anil Kumar Singh
 */
public class ModelScore<K extends Serializable, S extends Comparable & Serializable> implements Comparable, Serializable {
    
    /** Creates a new instance of ModelScore */
    public K modelKey;
    public S modelScore;

    public ModelScore(K modelKey, S modelScore)
    {
        this.modelKey = modelKey;
        this.modelScore = modelScore;
    }    

    public static <K extends Serializable, S extends Comparable & Serializable>
            ConcurrentLinkedHashMap<K, S> sortElementsByScores(ConcurrentLinkedHashMap<K,S> elements, boolean ascending)
    {
        ConcurrentLinkedHashMap<K, S> sortedElements = new ConcurrentLinkedHashMap<>(elements.size());
    	ArrayList<ModelScore> sortedScores = new ArrayList<ModelScore>(elements.size());

        Iterator<K> itr = elements.keySet().iterator();

        while(itr.hasNext())
        {
            K key = itr.next();

            S score = elements.get(key);
            ModelScore<K, S> ms = new ModelScore<K, S>(key, score);
            sortedScores.add(ms);
        }

        if(!ascending)
            Collections.sort(sortedScores, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) o2).compareTo((Comparable) o1);
            }
            });
        else
            Collections.sort(sortedScores, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) o1).compareTo((Comparable) o2);
            }
            });

        for (int i = 0; i < sortedScores.size(); i++)
        {
            ModelScore<K,S> ms = (ModelScore<K,S>) sortedScores.get(i);
            sortedElements.put(ms.modelKey, ms.modelScore);
        }

        return sortedElements;
   }

    public static <K extends Serializable, S extends Comparable & Serializable>
            ConcurrentLinkedHashMap<K, S> getTopN(ConcurrentLinkedHashMap<K,S> elements, int N, boolean ascending)
    {
        ConcurrentLinkedHashMap<K, S> sortedMap = sortElementsByScores(elements, ascending);

        ConcurrentLinkedHashMap<K, S> topN = new ConcurrentLinkedHashMap<>(N);

        Iterator<K> itr = sortedMap.keySet().iterator();

        int i = 0;

        while(itr.hasNext() && i < N)
        {
            K k = itr.next();

            S s = sortedMap.get(k);

            topN.put(k, s);

            i++;
        }

        return topN;
    }

    @Override
    public int compareTo(Object o)
    {
        return modelScore.compareTo(((ModelScore) o).modelScore);
    }

    public static void main(String args[])
    {
        ConcurrentLinkedHashMap<String, Integer> scores =  new ConcurrentLinkedHashMap<String, Integer>();

        scores.put("a", Integer.valueOf(1));
        scores.put("b", Integer.valueOf(3));
        scores.put("c", Integer.valueOf(5));

        ConcurrentLinkedHashMap<String, Integer> sorted =  ModelScore.sortElementsByScores(scores, true);
//        ConcurrentLinkedHashMap<String, Integer> sorted = ModelScore.getTopN(scores, 2, false);

        Iterator<String> itr = sorted.keySet().iterator();

        while(itr.hasNext())
        {
            String k = itr.next();

            Integer s = sorted.get(k);

            System.out.println(k + "\t" + s);
        }
    }
}
