/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.text.spell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import bhaashik.datastr.ConcurrentLinkedHashMap;
import java.util.List;

import bhaashik.corpus.ssf.features.FeatureAttribute;
import bhaashik.corpus.ssf.features.FeatureStructure;
import bhaashik.mlearning.lm.ngram.NGram;
import bhaashik.mlearning.lm.ngram.NGramLM;
import bhaashik.mlearning.lm.ngram.impl.NGramImpl;
import bhaashik.properties.PropertyTokens;
import bhaashik.table.BhaashikTableModel;
import java.io.Serializable;

/**
 *
 * @author anil
 */
public class ConditionalProbabilities {

    ConcurrentLinkedHashMap<Serializable,ConcurrentLinkedHashMap> conditionalProbabilities;

    public ConditionalProbabilities()
    {
        conditionalProbabilities = new ConcurrentLinkedHashMap(0, 100);
    }

    public int countSucceedingFeatures()
    {
        if(conditionalProbabilities == null)
            return 0;

        return conditionalProbabilities.size();
    }

    public int countPrecedingFeatures(String succeedingFeature)
    {
        if(conditionalProbabilities == null)
            return 0;

        ConcurrentLinkedHashMap precedingProbs = (ConcurrentLinkedHashMap) conditionalProbabilities.get(succeedingFeature);

        if(precedingProbs == null)
            return 0;

        return precedingProbs.size();
    }

    public int countTotalFeatures()
    {
        if(conditionalProbabilities == null)
            return 0;

        Iterator succeedingItr = getSucceedingFeatures();

        int total = 0;

        while(succeedingItr.hasNext())
        {
            String succeedingFeature = (String) succeedingItr.next();
            total += countPrecedingFeatures(succeedingFeature);
        }

        return total;
    }

    public Iterator getSucceedingFeatures()
    {
        if(conditionalProbabilities == null)
            return null;

        return conditionalProbabilities.keySet().iterator();
    }

    public Iterator getPrecedingFeatures(String succeedingFeature)
    {
        if(conditionalProbabilities == null)
            return null;

        ConcurrentLinkedHashMap precedingProbs = (ConcurrentLinkedHashMap) conditionalProbabilities.get(succeedingFeature);

        if(precedingProbs == null)
            return null;

        return precedingProbs.keySet().iterator();
    }

    public Probability getFeatureProb(String succeedingFeature, String precedingFeature)
    {
        ConcurrentLinkedHashMap precedingProbs = (ConcurrentLinkedHashMap) conditionalProbabilities.get(succeedingFeature);

        if(precedingProbs == null)
            return null;

        return (Probability) precedingProbs.get(precedingFeature);
    }

    public void addFeatureProb(String succeedingFeature, String precedingFeature, Probability Probs)
    {
        ConcurrentLinkedHashMap precedingProbs = (ConcurrentLinkedHashMap) conditionalProbabilities.get(succeedingFeature);

        if(precedingProbs == null)
        {
            precedingProbs = new ConcurrentLinkedHashMap(0, 100);

            conditionalProbabilities.put(succeedingFeature, precedingProbs);
        }

        precedingProbs.put(precedingFeature, Probs);
    }

    public void removeFeatureProbs(String succeedingFeature)
    {
        if(conditionalProbabilities == null)
            return;

        conditionalProbabilities.remove(succeedingFeature);
    }

    public void removeFeatureProb(String succeedingFeature, String precedingFeature)
    {
        ConcurrentLinkedHashMap precedingProbs = (ConcurrentLinkedHashMap) conditionalProbabilities.get(succeedingFeature);

        if(precedingProbs == null)
            return;

        precedingProbs.remove(precedingFeature);
    }

    public void read(String path, String charset) throws FileNotFoundException, IOException
    {
        BhaashikTableModel probsTable = new BhaashikTableModel();

        int rcount = probsTable.getRowCount();

        for (int i = 0; i < rcount; i++)
        {
            String succeedingFeature = (String) probsTable.getValueAt(i, 0);
            String preceedingFeature = (String) probsTable.getValueAt(i, 1);
            String freqStr = (String) probsTable.getValueAt(i, 2);
            String probStr = (String) probsTable.getValueAt(i, 3);

            Probability probability = new Probability();

            probability.setFrequency(Long.parseLong(freqStr));
            probability.setProbability(Long.parseLong(probStr));

            addFeatureProb(succeedingFeature, preceedingFeature, probability);
        }

        probsTable.read(path, charset);
    }

    public void save(String path, String charset) throws FileNotFoundException, IOException
    {
        BhaashikTableModel probsTable = new BhaashikTableModel(countTotalFeatures(), 4);

        Iterator succeedingItr = getSucceedingFeatures();

        int row = 0;

        while(succeedingItr.hasNext())
        {
            String succeedingFeature = (String) succeedingItr.next();

            Iterator precedingItr = getPrecedingFeatures(succeedingFeature);

            while(precedingItr.hasNext())
            {
                String precedingFeature = (String) precedingItr.next();

                Probability probability = getFeatureProb(succeedingFeature, precedingFeature);

                probsTable.setValueAt(succeedingFeature, row, 0);
                probsTable.setValueAt(precedingFeature, row, 1);
                probsTable.setValueAt(probability.getFrequency(), row, 2);
                probsTable.setValueAt(probability.getProbability(), row, 3);

                row++;
            }
        }

        probsTable.save(path, charset);
    }

    public void compile(NGramLM charNGramLM, PhoneticModelOfScripts phoneticModelOfScripts,
                        PropertyTokens featurePrecedence, String langEnc) throws FileNotFoundException, IOException
    {
        phoneticModelOfScripts.setNgramLM(charNGramLM);
        
        Iterator<List<Integer>> itr = charNGramLM.getNGramKeys(1);

        while(itr.hasNext())
        {
            List<Integer> chIndices = itr.next();
            
            String ch = NGramImpl.getString(charNGramLM, chIndices);

            NGram ngram = (NGram) charNGramLM.getNGram(ch, 1);

            long freq = ngram.getFreq();

            FeatureStructure fs = phoneticModelOfScripts.getFeatureStructure(ch.charAt(0), langEnc);

            if(fs == null)
                continue;

            int count = featurePrecedence.countTokens();

            FeatureAttribute prevFA = null;

            for (int i = 0; i < count; i++)
            {
                String fname = featurePrecedence.getToken(i);

                FeatureAttribute fa = fs.getAttribute(fname);

                if(fa == null)
                    continue;

                String fv = fa.getName() + "=" + (String) fa.getNestedAltValue(i).getMultiValue();
                String prevFV = "=";

                if(prevFA != null)
                    prevFV = prevFA.getName() + "=" + (String) prevFA.getNestedAltValue(i).getMultiValue();

                Probability probability = getFeatureProb(prevFV, fv);

                if(probability == null)
                {
                    probability = new Probability();
                    addFeatureProb(prevFV, fv, probability);
                }

                long featureFreq = probability.getFrequency();

                probability.setProbability(featureFreq + freq);
                
                prevFA = fa;
            }
        }

        calculateProbabilities();
    }

    public void calculateProbabilities()
    {
        Iterator succeedingItr = getSucceedingFeatures();

        while(succeedingItr.hasNext())
        {
            String succeedingFeature = (String) succeedingItr.next();

            Iterator precedingItr = getPrecedingFeatures(succeedingFeature);

            long totalFreq = 0;

            while(precedingItr.hasNext())
            {
                String precedingFeature = (String) precedingItr.next();

                Probability featureProbability = getFeatureProb(succeedingFeature, precedingFeature);

                totalFreq += featureProbability.getFrequency();
            }

            double prob = 0.0;

            while(precedingItr.hasNext())
            {
                String precedingFeature = (String) precedingItr.next();

                Probability featureProbability = getFeatureProb(succeedingFeature, precedingFeature);

                prob = featureProbability.getFrequency() / totalFreq;

                featureProbability.setProbability(prob);
            }
        }
    }
}
