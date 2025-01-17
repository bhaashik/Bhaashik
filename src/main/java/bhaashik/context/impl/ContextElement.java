/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.context.impl;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;

/**
 *
 * @author anil
 */
public interface ContextElement<E extends Serializable> extends Serializable {

    E getContextElement();

    void setContextElement(E contextElement);

    long getFreq();

    void setFreq(long freq);

    void print(PrintStream ps) throws IOException, Exception;

}
