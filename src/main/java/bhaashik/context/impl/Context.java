/*
 * Context.java
 *
 * Created on October 12, 2008, 7:53 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.context.impl;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Iterator;

/**
 *
 * @author Anil Kumar Singh
 */
public interface Context<K extends Serializable, E extends Serializable, CE extends ContextElement<E>> extends Serializable  {
    
    Iterator<K> getContextElementKeys();

    long countContextElementTokens(boolean recount);

    void print(PrintStream ps) throws IOException, Exception;
    
    void pruneTopN(int n, boolean ascending);
}
