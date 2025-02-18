/*
 * EMCompleteDataCorpusImpl.java
 *
 * Created on June 27, 2006, 12:26 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.mlearning.common.impl;

import java.io.PrintStream;
import java.util.Iterator;
import bhaashik.datastr.ConcurrentLinkedHashMap;
import java.util.Vector;

import bhaashik.GlobalProperties;
import bhaashik.mlearning.common.MLAnalysis;
import bhaashik.mlearning.common.MLCorpusAnalysis;
import bhaashik.mlearning.common.MLFrequency;
import bhaashik.mlearning.common.MLType;

/**
 *
 * @author Anil Kumar Singh
 */
public abstract class MLAbstractCorpusAnalysis extends MLAbstractCorpus
	implements MLCorpusAnalysis
{
    protected Vector allAnalyses;

    /** Creates a new instance of EMCompleteDataCorpusImpl */
    public MLAbstractCorpusAnalysis()
    {
    }

    public long getAnalysisCount(MLType type) {
	return ((ConcurrentLinkedHashMap) data.get(type)).size();
    }

    public MLFrequency getAnalysisFrequency(MLType type, MLAnalysis analysis) {
	return (MLFrequency) ((ConcurrentLinkedHashMap) data.get(type)).get(analysis);
    }

    public long addAnalysis(MLType type, MLAnalysis analysis, MLFrequency freq) {
	if(data.get(type) == null)
	    data.put(type, new ConcurrentLinkedHashMap());
	
	((ConcurrentLinkedHashMap) data.get(type)).put(analysis, freq);
	return data.size();
    }

    public long removeAnalysis(MLType type, MLAnalysis analysis) {
	if(data.get(type) == null)
	    return 0;

	((ConcurrentLinkedHashMap) data.get(type)).remove(analysis);
	return data.size();
    }

    public Iterator getAnalyses() {
	Iterator itrType = getTypes();
	allAnalyses = new Vector();
	
	while(itrType.hasNext())
	{
	    MLType tp = (MLType) itrType.next();
	    allAnalyses.addAll(((ConcurrentLinkedHashMap) data.get(tp)).keySet());
	}

	return allAnalyses.iterator();
    }

    public Iterator getAnalysisFrequencies() {
	Iterator itrType = getTypes();
	Vector allFrequencies = new Vector();
	
	while(itrType.hasNext())
	{
	    MLType tp = (MLType) itrType.next();
	    allFrequencies.addAll(((ConcurrentLinkedHashMap) data.get(tp)).values());
	}

	return allFrequencies.iterator();
    }

    public Iterator getAnalyses(MLType type) {
	return ((ConcurrentLinkedHashMap) data.get(type)).keySet().iterator();
    }

    public Iterator getAnalysisFrequencies(MLType type) {
	return ((ConcurrentLinkedHashMap) data.get(type)).values().iterator();
    }

    public void print(PrintStream ps)
    {
	ps.println(GlobalProperties.getIntlString("Analysis_Frequencies:"));
	
	Iterator itrType = getTypes();
	
	while(itrType.hasNext())
	{
	    MLType tp = (MLType) itrType.next();
	    Iterator itrAn = getAnalyses(tp);
	    
	    ps.println(GlobalProperties.getIntlString("Type:_") + tp);
	    
	    while(itrAn.hasNext())
	    {
		MLAnalysis an = (MLAnalysis) itrAn.next();
		ps.println("\t" + an + "\t" + getAnalysisFrequency(tp, an));
	    }
	}
    }
}
