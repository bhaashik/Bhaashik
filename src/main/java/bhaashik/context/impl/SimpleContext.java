/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.context.impl;

import java.io.Serializable;

/**
 *
 * @author anil
 */
public interface SimpleContext<K extends Serializable, E extends Serializable, CE extends ContextElement<E>> extends Context<K,E, CE> {

    long countContextElementTypes();
    
    CE getContextElement(K key);

    long addContextElement(K key, CE ce);

    CE removeContextElement(K key);

}
