/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.corpus.parallel;

import java.awt.dnd.DnDConstants;
import java.io.Serializable;
import java.util.*;

import bhaashik.corpus.ssf.tree.SSFNode;
import bhaashik.corpus.ssf.SSFSentence;
import bhaashik.corpus.ssf.tree.SSFLexItem;
import bhaashik.corpus.ssf.tree.SSFPhrase;
import bhaashik.datastr.ConcurrentLinkedHashMap;
 
/**
 *
 * @author anil
 */
public class AlignmentUnit<T extends Serializable> implements Cloneable, Serializable {

    protected T alignmentObject;
    protected int index;
    protected int parallelIndex;

    protected int actionType = DnDConstants.ACTION_MOVE;

    protected transient ConcurrentLinkedHashMap<String, AlignmentUnit<T>> alignedUnits;

    public AlignmentUnit() {

         alignedUnits = new ConcurrentLinkedHashMap<>();
    }

    public AlignmentUnit(int atype)
    {
        this();

        actionType = atype;
    }

    public int getActionType()
    {
        return actionType;
    }

    public void setActionType(int atype)
    {
        actionType = atype;
    }

    /**
     * @return the alignmentObject
     */
    public T getAlignmentObject()
    {
        return alignmentObject;
    }

    /**
     * @param alignmentObject the alignmentObject to set
     */
    public void setAlignmentObject(T alignmentObject)
    {
        this.alignmentObject = alignmentObject;
    }

    /**
     * @return the index
     */
    public int getIndex()
    {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index)
    {
        this.index = index;
    }

    /**
     * @return the parallelIndex
     */
    public int getParallelIndex()
    {
        return parallelIndex;
    }

    /**
     * @param parallelIndex the parallelIndex to set
     */
    public void setParallelIndex(int parallelIndex)
    {
        this.parallelIndex = parallelIndex;
    }

    public int countAlignedUnits()
    {
        return alignedUnits.size();
    }

    public String getAlignmentKey()
    {
        if(alignmentObject instanceof SSFNode)
        {
            SSFNode node = (SSFNode) alignmentObject;

            return node.getAttributeValue("name");
        }
        else if(alignmentObject instanceof SSFSentence)
        {
            SSFSentence sentence = (SSFSentence) alignmentObject;

            return sentence.getId();
        }

        return null;
    }

    public Iterator<String> getAlignedUnitKeys()
    {
        return alignedUnits.keySet().iterator();
    }

    public AlignmentUnit<T> getAlignedUnit(String key)
    {
        return alignedUnits.get(key);
    }

    public T getAlignedObject(String key)
    {
        return getAlignedUnit(key).getAlignmentObject();
    }

    public List<AlignmentUnit<T>> getAlignedUnits()
    {
        Collection<AlignmentUnit<T>> alignedCollection = alignedUnits.values();
        
        return Collections.list(Collections.enumeration(alignedCollection));
    }

    public List<T> getAlignedObjects()
    {
        Collection<AlignmentUnit<T>> alignedCollection = alignedUnits.values();
        
        List<T> alignedArrayList = new ArrayList();
        
        Iterator<AlignmentUnit<T>> itr = alignedCollection.iterator();
        
        while(itr.hasNext())
        {
            AlignmentUnit<T> aunit = itr.next();
            
            alignedArrayList.add(aunit.getAlignmentObject());
        }
        
        if(alignedArrayList.isEmpty()) {
            return null;
        }

        return alignedArrayList;
    }

    public T getFirstAlignedObject()
    {
        List<T> alignedObjects = getAlignedObjects();

        if(alignedObjects == null || alignedObjects.isEmpty()) {
            return null;
        }

        return alignedObjects.get(0);
    }

    public T getAlignedObject(int i)
    {
        List<T> alignedObjects = getAlignedObjects();

        if(alignedObjects == null || alignedObjects.isEmpty()
                || i >= alignedObjects.size() || i < 0) {
            return null;
        }

        return alignedObjects.get(i);        
    }

    public T getLastAlignedObject()
    {
        List<T> alignedObjects = getAlignedObjects();

        if(alignedObjects == null || alignedObjects.isEmpty()) {
            return null;
        }

        return alignedObjects.get(alignedObjects.size() - 1);                
    }

    public int addAlignedUnit(AlignmentUnit<T> p)
    {
        String key = p.getAlignmentKey();

        if(key != null) {
            alignedUnits.put(key, p);
        }
        
        return alignedUnits.size();
    }
    
    public void addUniqueName()
    {
        
    }

//    public void insertAlignmentUnit(int mode, Alignable<T> alignableBoject, int index, int granularity)
//    {
//        if(mode == AlignmentBlock.DOCUMENT_ALIGNMENT_MODE)
//        {
//        }
//        else if(mode == AlignmentBlock.PARAGRAPH_ALIGNMENT_MODE)
//        {
//        }
//        else if(mode == AlignmentBlock.SENTENCE_ALIGNMENT_MODE)
//        {
//        }
//        else if(mode == AlignmentBlock.PHRASE_ALIGNMENT_MODE)
//        {
//            SSFNode parent = (SSFNode) ((SSFNode) getAlignmentObject()).getRoot();
//            
//            SSFNode ssfNode = null;
//
//            parent.insert(parent, index);
//
//            if(granularity == AlignmentBlock.PHRASE_GRANULARITY)
//            {
//                ssfNode = new SSFPhrase();
//                
//            }
//            else if(granularity == AlignmentBlock.LEXITEM_GRANULARITY)
//            {
//                ssfNode = new SSFLexItem();
//                
//                SSFNode parent = (SSFNode) ssfNode.getRoot();
//
//                parent.insert(parent, index);
//                
//                addUniqueName();                
//            }
//
//            addUniqueName();
//        }
//
//        assignAlignmentReferences();
//        synchronizeIndices(false);
//    }

    public AlignmentUnit<T> removeAlignedUnit(String key)
    {
        return alignedUnits.remove(key);
    }

    public void removeAlignedUnit(AlignmentUnit<T> p)
    {
        if(p == null) {
            return;
        }

        String key = p.getAlignmentKey();
        
        removeAlignedUnit(key);

        saveAlignments();
    }

    public void removeAllAlignments()
    {
        alignedUnits.clear();
    }

    public void clearAlignments()
    {
        Iterator<String> itr = getAlignedUnitKeys();

        while(itr.hasNext())
        {
            String key = itr.next();

            AlignmentUnit<T> alignedUnit = getAlignedUnit(key);
            
            alignedUnit.removeAlignedUnit(this);
        }

        removeAllAlignments();

        if(alignmentObject instanceof SSFNode)
        {
            SSFNode node = (SSFNode) alignmentObject;

            node.saveAlignments();
        }
        else if(alignmentObject instanceof SSFSentence)
        {
            SSFSentence sentence = (SSFSentence) alignmentObject;

            sentence.getFeatureStructure().setAlignmentUnit(sentence.getAlignmentUnit());
        }
    }

    public void saveAlignments()
    {
        if(alignmentObject instanceof SSFNode)
        {
            SSFNode node = (SSFNode) alignmentObject;

            node.saveAlignments();
        }
        else if(alignmentObject instanceof SSFSentence)
        {
            SSFSentence sentence = (SSFSentence) alignmentObject;
            sentence.getFeatureStructure().setAlignmentUnit(sentence.getAlignmentUnit());
        }
    }

    @Override
    public String toString()
    {
        if(alignmentObject == null) {
            return super.toString();
        }

        if (alignmentObject instanceof SSFSentence)
        {
            SSFSentence sentence = (SSFSentence) alignmentObject;

            return sentence.getRoot().makeRawSentence();
        }
        else if (alignmentObject instanceof SSFNode)
        {
            SSFNode node = (SSFNode) alignmentObject;
            return node.convertToBracketForm(1);
        }

        return super.toString();
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        AlignmentUnit clone = (AlignmentUnit) super.clone();
        
        clone.alignmentObject = alignmentObject;
        
        clone.index = index;
        
        clone.parallelIndex = parallelIndex;
        
        clone.actionType = actionType;
        
        clone.alignedUnits = (ConcurrentLinkedHashMap) alignedUnits.clone();
        
        return clone;
    }
}
