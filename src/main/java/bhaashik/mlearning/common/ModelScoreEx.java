/*
 * ModelScoreEx.java
 *
 * Created on January 25, 2009, 9:07 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.mlearning.common;

import java.io.Serializable;

/**
 *
 * @author Anil Kumar Singh
 */
public class ModelScoreEx<K extends Serializable, S extends Comparable & Serializable, O extends Serializable> extends ModelScore<K, S> {
    
    public O modelObject;
    
    /** Creates a new instance of ModelScoreEx */
    public ModelScoreEx(K modelKey, S modelScore, O modelObject) {
        super(modelKey, modelScore);
        
        this.modelObject = modelObject;
    }

    public String toString()
    {
        return modelKey + " ||| " + modelObject + " ||| " + modelScore;
    }
}
