/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.corpus.ssf.query;

import java.io.Serializable;

/**
 *
 * @author anil
 */
public interface QueryValue extends Serializable {

    Object getQueryReturnValue();
    void setQueryReturnValue(Object rv);

    Object getQueryReturnObject();
    void setQueryReturnObject(Object rv);
}
