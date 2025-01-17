/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.context;

import bhaashik.context.impl.FunctionalContextElement;
import java.io.Serializable;

/**
 *
 * @author Anil Kumar Singh
 */
public class KWIKContextElement<E extends Serializable> extends FunctionalContextElementImpl<E> implements FunctionalContextElement<E> {

    private static final long serialVersionUID = 1L;

    public KWIKContextElement()
    {
        super();
    }
}
