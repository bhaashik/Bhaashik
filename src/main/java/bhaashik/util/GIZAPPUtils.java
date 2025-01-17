/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bhaashik.util;

import bhaashik.corpus.ssf.SSFSentence;
import bhaashik.corpus.ssf.SSFStory;
import bhaashik.corpus.ssf.impl.SSFStoryImpl;
import bhaashik.corpus.ssf.tree.SSFNode;
import bhaashik.datastr.ConcurrentLinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author User
 */
public class GIZAPPUtils {
    
    public static Pair<SSFStory, SSFStory> symmetrizeBiDirGIZAPPOutput(String gizaFilePathDir1, String csDir1, String gizaFilePathDir2, String csDir2)
    {
        Pair<SSFStory, SSFStory> symmetrizedInputFromGIZAPP = new Pair<>();

        SSFStory srcTgtDir1Src = new SSFStoryImpl(); 
        SSFStory srcTgtDir1Tgt = new SSFStoryImpl();

        SSFStory srcTgtDir2Src = new SSFStoryImpl();
        SSFStory srcTgtDir2Tgt = new SSFStoryImpl();

        SSFStoryImpl.loadGIZAData(gizaFilePathDir1, csDir1, srcTgtDir1Src, srcTgtDir1Tgt);
        SSFStoryImpl.loadGIZAData(gizaFilePathDir2, csDir2, srcTgtDir2Src, srcTgtDir2Tgt);
        
        return symmetrizedInputFromGIZAPP;
    }    

    public static Pair<SSFStory, SSFStory> biDirGIZAPPSymBalStory(Pair<SSFStory, SSFStory> srcTgtDir1, Pair<SSFStory, SSFStory> srcTgtDir2)
    {        
        Pair<SSFStory, SSFStory> symmetrizationGIZAPPInput = new Pair();        

        List<ConcurrentLinkedHashMap<Integer, SSFNode>> biDirGIZAPPSymBalSentence;
        biDirGIZAPPSymBalSentence = new LinkedList();
        
//        Pair<ConcurrentLinkedHashMap<Integer, SSFNode>, ConcurrentLinkedHashMap<Integer, SSFNode>> biDirGIZAPPOSymBalSen = 
//                getBiDirGIZAPPSymBalSentence(srcTgtDir1, srcTgtDir2);
//        
//        Pair<ConcurrentLinkedHashMap<Integer, SSFNode>, ConcurrentLinkedHashMap<Integer, SSFNode>> biDirGIZAPPSymBalSentene = new Pair<>();
        
        return symmetrizationGIZAPPInput;
    }

    public static Pair<ConcurrentLinkedHashMap<Integer, SSFNode>, ConcurrentLinkedHashMap<Integer, SSFNode>> getBiDirGIZAPPSymBalSentence(Pair<SSFSentence, SSFSentence> srcTgtDir1, Pair<SSFSentence, SSFSentence> srcTgtDir2)
    {        
        Pair<ConcurrentLinkedHashMap<Integer, SSFNode>, ConcurrentLinkedHashMap<Integer, SSFNode>> biDirGIZAPPOSymBal = new Pair();        
        
        return biDirGIZAPPOSymBal;
    }
    
}
