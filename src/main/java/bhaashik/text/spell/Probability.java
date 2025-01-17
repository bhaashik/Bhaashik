/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.text.spell;

import java.io.Serializable;

/**
 *
 * @author anil
 */
public class Probability implements Serializable {

    protected long frequency;
    protected double probability;

    public Probability()
    {

    }

    /**
     * @return the frequency
     */
    public long getFrequency()
    {
        return frequency;
    }

    /**
     * @param frequency the frequency to set
     */
    public void setFrequency(long frequency)
    {
        this.frequency = frequency;
    }

    /**
     * @return the probability
     */
    public double getProbability()
    {
        return probability;
    }

    /**
     * @param probability the probability to set
     */
    public void setProbability(double probability)
    {
        this.probability = probability;
    }

    
}
