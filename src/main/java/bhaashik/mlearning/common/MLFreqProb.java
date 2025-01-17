/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.mlearning.common;

import java.io.Serializable;

/**
 *
 * @author anil
 */
public interface MLFreqProb extends Serializable {

    int getFrequency();

    void setFrequency(int frequency);

    double getProbability();

    void setProbability(double probability);
}
