/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bhaashik.util;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author anil
 */
public class Pair<F extends Object & Serializable, S extends Object & Serializable> implements Serializable{
    private F first;
    private S second;

    public Pair() {
//        this.first = getDefaultFirst();
//        this.second = getDefaultSecond();
        
        init();
    }
    
    private void init()
    {
        this.first = getDefaultFirst();
        this.second = getDefaultSecond();        
    }

    public Pair(F f, S s)
    {
        this.first = f;
        this.second = s;
    }
//    
//    @Override
//    public String toString()
//    {
//        return first + "=>" + second;
//    }

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
//    
//    public boolean equals(Pair pair)
//    {
//        if((first == null && pair.first != null)
//                || (first != null && pair.first == null))
//        {
//            return false;
//        }
//        else if((second == null && pair.second != null)
//                || (second != null && pair.second == null))
//        {
//            return false;
//        }
//
//        if((first != null && pair.first != null)
//                && (second != null && pair.second != null))
//        {
//            if(first.equals(pair.first) && second.equals(pair.second))
//            {
//                return true;
//            }
//        }
//        
//        return false;
//    }
    
    @Override
     public boolean equals(Object obj) {

         if (this == obj) return true;

         if (obj == null || getClass() != obj.getClass()) return false;

         Pair<?, ?> pair;
         pair = (Pair<?, ?>) obj;

         return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }    
    
    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
    
    @Override
    public Pair clone() throws CloneNotSupportedException
    {
        Pair pair = (Pair) super.clone();
        
        pair.first = first;
        pair.second = second;
        
        return pair;
    }
//
//    @Override
//    public int compareTo(Pair<F, S> other) {
//        int cmpFirst = this.first.compareTo(other.first);
//        return (cmpFirst != 0) ? cmpFirst : this.second.compareTo(other.second);
//    }
}
