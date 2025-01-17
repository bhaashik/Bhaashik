/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bhaashik.util;

import java.io.Serializable;

/**
 *
 * @author anil
 */
public class Pair<F extends Object & Serializable, S extends Object & Serializable> implements Serializable{
    private F first;
    private S second;

    public Pair() {
        this.first = getDefaultFirst();
        this.second = getDefaultSecond();
    }

    public Pair(F f, S s)
    {
        this.first = f;
        this.second = s;
    }
    
    @Override
    public String toString()
    {
        return first + "=>" + second;
    }

    protected F getDefaultFirst() {
        return null; // Override for custom defaults
    }

    protected S getDefaultSecond() {
        return null; // Override for custom defaults
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    } 
    
    public boolean equals(Pair pair)
    {
        if((first == null && pair.first != null)
                || (first != null && pair.first == null))
        {
            return false;
        }
        else if((second == null && pair.second != null)
                || (second != null && pair.second == null))
        {
            return false;
        }

        if((first != null && pair.first != null)
                && (second != null && pair.second != null))
        {
            if(first.equals(pair.first) && second.equals(pair.second))
            {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public Pair clone() throws CloneNotSupportedException
    {
        Pair pair = (Pair) super.clone();
        
        pair.first = first;
        pair.second = second;
        
        return pair;
    }
}
