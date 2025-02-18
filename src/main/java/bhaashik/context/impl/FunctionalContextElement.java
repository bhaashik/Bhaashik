/*
 * FunctionalContextElement.java
 *
 * Created on January 18, 2009, 5:01 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.context.impl;

import java.io.Serializable;

/**
 *
 * @author Anil Kumar Singh
 */
public interface FunctionalContextElement<E extends Serializable> extends ContextElement<E> {

    short getDistance();
    
    void setDistance(short distance);
}
