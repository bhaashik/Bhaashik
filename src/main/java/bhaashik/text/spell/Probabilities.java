/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.text.spell;

import java.util.Iterator;
import bhaashik.datastr.ConcurrentLinkedHashMap;
import java.io.Serializable;

/**
 *
 * @author anil
 */
public class Probabilities implements Serializable {

    protected ConcurrentLinkedHashMap<Serializable,Probability> priorProbabilities;

    public Probabilities()
    {
        priorProbabilities = new ConcurrentLinkedHashMap(0, 100);
    }

    public int countPriors()
    {
        if(priorProbabilities == null)
            return 0;

        return priorProbabilities.size();
    }

    public Iterator getPriors()
    {
        if(priorProbabilities == null)
            return null;

        return priorProbabilities.keySet().iterator();
    }

    public Probability getPriorProb(String prior)
    {
        return priorProbabilities.get(prior);
    }

    public void addPriorProb(String prior, Probability Probs)
    {
        priorProbabilities.put(prior, Probs);
    }

    public void removePriorProb(String prior)
    {
        priorProbabilities.remove(prior);
    }
}
