/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.context.impl;

import java.io.Serializable;
import java.util.Iterator;

/**
 *
 * @author anil
 * @param <K>
 * @param <E>
 * @param <CE>
 */
public interface FunctionalContext<K extends Serializable, E extends Serializable, CE extends FunctionalContextElement<E>> extends Context<K, E, CE> {

    long countContextElementTypes(K key);

    long countContextElementTokens(K key);

    Iterator<Integer> getContextElementKeys(K key);

    CE getContextElement(K key, int distance);

    long addContextElement(K key, int distance, CE ce);

    CE removeContextElement(K key, int distance);
}
