/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bhaashik.mlearning.lm.ngram.impl;

import bhaashik.GlobalProperties;
import bhaashik.factory.Factory;
import bhaashik.mlearning.lm.ngram.NGramCount;
import bhaashik.mlearning.lm.ngram.NGramCounts;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anil
 */
public class NGramCountImpl implements NGramCount {
    protected ArrayList<Integer> indices;
    protected long freq;

    public static class NGramCountFactory implements Factory<NGramCount> {
        @Override
        public NGramCount createInstance() {
            return new NGramCountImpl();
        }

    }
    
    public static Factory getFactory()
    {
        return new NGramCountFactory();
    }
    
    public NGramCountImpl()
    {
        freq = 1;
    }

    public NGramCountImpl(long f)
    {
        freq = f;
    }

    @Override
    public ArrayList<Integer> getIndices()
    {
        return indices;
    }

    @Override
    public void setIndices(ArrayList<Integer> wdIndices)
    {
        indices = wdIndices;
    }

    @Override
    public String getString(NGramCounts ngramLM)
    {
        return getString(ngramLM, indices);
    }

    @Override
    public void setString(NGramCounts ngramLM, String s)
    {
        indices = getIndices(ngramLM, s, true);
    }
   
    @Override
    public long getFreq()
    {
        return freq;
    }

    @Override
    public void setFreq(long f)
    {
        freq = f;
    }

    @Override
    public Object clone()
    {
        try
        {
            NGramCountImpl obj = (NGramCountImpl) super.clone();
            return obj;
        }
        catch (CloneNotSupportedException e)
        {
            throw new InternalError(GlobalProperties.getIntlString("But_the_class_is_Cloneable!!!"));
        }
    }
    
    public static ArrayList<Integer> getIndices(NGramCounts ngramLM, String wds, boolean add)
    {        
        String parts[] = wds.split("@#&");
        
        ArrayList<Integer> indices = new ArrayList<>(parts.length);
        
        for (int i = 0; i < parts.length; i++) {
            int wi = ngramLM.getVocabIndex().indexOf(parts[i], add);
            
            indices.add(wi);
        }
        
        return indices;
    }
    
    public static ArrayList<Integer> getIndicesPlain(NGramCounts ngramLM, String wdsPlain, boolean add)
    {
        String parts[] = wdsPlain.trim().split("[\\s+]");
        
        ArrayList<Integer> indices = new ArrayList<>(parts.length);
        
        for (int i = 0; i < parts.length; i++) {
            int wi = ngramLM.getVocabIndex().indexOf(parts[i], add);
            
            indices.add(wi);
        }
        
        return indices;
    }

    public static String getString(NGramCounts ngramLM, List<Integer> wdIndices)
    {
        String str = "";
        
        int i = 0;
        for (Integer wi : wdIndices) {
            if(i == 0)
            {
                str = (String) ngramLM.getVocabIndex().get(wi);
            }
            else
            {
                str += "@#&" + ngramLM.getVocabIndex().get(wi);
            }
            
            i++;
        }
        
        return str;
    }

    public static String getPlainString(NGramCounts ngramLM, List<Integer> wdIndices)
    {
        String str = "";

        int i = 0;
        
        for (Integer wi : wdIndices) {
            if(i == 0)
            {
                str = (String) ngramLM.getVocabIndex().get(wi);
            }
            else
            {
                str += " " + ngramLM.getVocabIndex().get(wi);                
            }
            
            i++;
        }
        
        return str;
    }
        
    @Override
    public String toString()
    {
        return indices.toString();
//        return getString(indices);
    }
}
