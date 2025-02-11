/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bhaashik.util;

import bhaashik.corpus.parallel.AlignmentBlock;
import bhaashik.corpus.parallel.AlignmentUnit;
import bhaashik.corpus.parallel.gui.SentenceAlignmentInterfaceJPanel;
import bhaashik.corpus.parallel.gui.WordAlignmentInterfaceJPanel;
import bhaashik.corpus.ssf.SSFSentence;
import bhaashik.corpus.ssf.SSFStory;
import bhaashik.corpus.ssf.impl.SSFStoryImpl;
import bhaashik.corpus.ssf.tree.SSFNode;
import bhaashik.corpus.ssf.tree.SSFPhrase;
import bhaashik.datastr.ConcurrentLinkedHashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class GIZAPPUtils {
    
//    public static Pair<SSFStory, SSFStory> symmetrizeBiDirGIZAPPOutput(String gizaFilePathDir1, String csDir1, String gizaFilePathDir2, String csDir2)
//    {
//        Pair<SSFStory, SSFStory> symmetrizedInputFromGIZAPP = new Pair<>();
//
//        SSFStory srcTgtDir1Src = new SSFStoryImpl(); 
//        SSFStory srcTgtDir1Tgt = new SSFStoryImpl();
//
//        SSFStory srcTgtDir2Src = new SSFStoryImpl();
//        SSFStory srcTgtDir2Tgt = new SSFStoryImpl();
//
//        SSFStoryImpl.loadGIZAData(gizaFilePathDir1, csDir1, srcTgtDir1Src, srcTgtDir1Tgt);
//        SSFStoryImpl.loadGIZAData(gizaFilePathDir2, csDir2, srcTgtDir2Src, srcTgtDir2Tgt);
//        
//        symmetrizedInputFromGIZAPP = biDirGIZAPPSymBalStory()
//        
//        return symmetrizedInputFromGIZAPP;
//    }    

    public static Pair<SSFStory, SSFStory> symmetrizeBiDirGIZAPPOutput(String gizaFilePathSrcToTgt, String csSrcToTgt, String gizaFilePathTgtToSrc, String csTgtToSrc)
    {
        Pair<SSFStory, SSFStory> symmetrizedGIZAPPOutput = null;

        SSFStory srcToTgtSSFStorySrc = new SSFStoryImpl(); 
        SSFStory srcToTgtSSFStoryTgt = new SSFStoryImpl();

        SSFStory tgtToSrcSSFStorySrc = new SSFStoryImpl();
        SSFStory tgtToSrcSSFStoryTgt = new SSFStoryImpl();

        SSFStoryImpl.loadGIZAData(gizaFilePathSrcToTgt, csSrcToTgt, srcToTgtSSFStorySrc, srcToTgtSSFStoryTgt);

        System.out.println("Loaded srcToTgtSSFStories");
        
        SSFStoryImpl.loadGIZAData(gizaFilePathTgtToSrc, csTgtToSrc, tgtToSrcSSFStoryTgt, tgtToSrcSSFStorySrc);

        System.out.println("Loaded tgtToSrcSSFStories");
        
        Pair<SSFStory, SSFStory> srcToTgtSSFStoryPair = new Pair<>(srcToTgtSSFStorySrc, srcToTgtSSFStoryTgt);

        Pair<SSFStory, SSFStory> tgtToSrcSSFStoryPair = new Pair<>(tgtToSrcSSFStorySrc, tgtToSrcSSFStoryTgt);
        
        symmetrizedGIZAPPOutput = GIZAPPUtils.biDirGIZAPPSymBalStory(srcToTgtSSFStoryPair, tgtToSrcSSFStoryPair);

        if(symmetrizedGIZAPPOutput != null)
        {
            System.out.println("Symmetrization completed.");
        }
        else
        {
            System.out.println("Symmetrization failed.");            
        }
        
        return symmetrizedGIZAPPOutput;
    }    

    public static Pair<SSFStory, SSFStory> biDirGIZAPPSymBalStory(Pair<SSFStory, SSFStory> srcToTgtSSFStoryPair, Pair<SSFStory, SSFStory> tgtToSrcSSFStoryPair)
    {        
        if(srcToTgtSSFStoryPair == null && tgtToSrcSSFStoryPair == null)
        {
            System.out.println("Null story pair");
            return null;
        }
        
        int numSrcToTgtSSFStorySrc = srcToTgtSSFStoryPair.getFirst().countSentences();
        int numSrcToTgtSSFStoryTgt = srcToTgtSSFStoryPair.getSecond().countSentences();
        
        if(numSrcToTgtSSFStorySrc != numSrcToTgtSSFStoryTgt)
        {
            System.out.println("srcToTgtSSFStorySrc length " + numSrcToTgtSSFStorySrc + " srcToTgtSSFStoryTgt length " + numSrcToTgtSSFStoryTgt + " is not equal.");
            return null;
        }
        
        int numTgtToSrcSSFStorySrc = tgtToSrcSSFStoryPair.getFirst().countSentences();
        int numTgtToSrcSSFStoryTgt = tgtToSrcSSFStoryPair.getSecond().countSentences();
        
        if(numTgtToSrcSSFStorySrc != numTgtToSrcSSFStoryTgt)
        {
            System.out.println("tgtToSrcSSFStorySrc  length " + numTgtToSrcSSFStorySrc + " tgtToSrcSSFStoryTgt length " + numTgtToSrcSSFStoryTgt + " is not equal.");
            return null;
        }
        
        if(numSrcToTgtSSFStorySrc != numTgtToSrcSSFStorySrc)
        {
            System.out.println("srcToTgtSSFStorySrc  length " + numTgtToSrcSSFStorySrc + " tgtToSrcSSFStorySrc lengths " + numTgtToSrcSSFStorySrc + " is not equal.");
            return null;
        }
        
        SSFStory symmetrizedSSFStorySrc = new SSFStoryImpl();
        SSFStory symmetrizedSSFStoryTgt = new SSFStoryImpl();
        
        for (int i = 0; i < numSrcToTgtSSFStorySrc; i++) {
            
            SSFSentence srcToTgtSSFSentenceSrc = srcToTgtSSFStoryPair.getFirst().getSentence(i);
            SSFSentence srcToTgtSSFSentenceTgt = srcToTgtSSFStoryPair.getSecond().getSentence(i);
            
            Pair<SSFSentence, SSFSentence> srcToTgtSSFSentencePair = new Pair<>(srcToTgtSSFSentenceSrc, srcToTgtSSFSentenceTgt);

            SSFSentence tgtToSrcSSFSentenceSrc = tgtToSrcSSFStoryPair.getFirst().getSentence(i);
            SSFSentence tgtToSrcSSFSentenceTgt = tgtToSrcSSFStoryPair.getSecond().getSentence(i);

            Pair<SSFSentence, SSFSentence> tgtToSrcSSFSentencePair = new Pair<>(tgtToSrcSSFSentenceSrc, tgtToSrcSSFSentenceTgt);            
            
//            Pair<SSFSentence, SSFSentence> symmetrizedSentencePair = biDirSymmetrizedSSFSentencePair(srcToTgtSSFSentencePair, tgtToSrcSSFSentencePair);
            Pair<SSFSentence, SSFSentence> symmetrizedSentencePair = doDirSymmetrizedSSFSentencePair(srcToTgtSSFSentencePair, tgtToSrcSSFSentencePair);
            
            symmetrizedSSFStorySrc.addSentence(symmetrizedSentencePair.getFirst());
            symmetrizedSSFStoryTgt.addSentence(symmetrizedSentencePair.getSecond());
        }
        
        Pair<SSFStory, SSFStory> symmetrizationSSFStoryPair = new Pair<>(symmetrizedSSFStorySrc, symmetrizedSSFStoryTgt);        

        System.out.println("Symmetrization at the the story-level complete.");

//        List<LinkedHashMap<Integer, SSFNode>> biDirGIZAPPSymBalSentence;
//        biDirGIZAPPSymBalSentence = new ArrayList();
////        biDirGIZAPPSymBalSentence = new LinkedList();
//        
//        biDirGIZAPPSymBalSentence.
        
//        Pair<ConcurrentLinkedHashMap<Integer, SSFNode>, ConcurrentLinkedHashMap<Integer, SSFNode>> biDirGIZAPPOSymBalSen = 
//                getBiDirGIZAPPSymBalSentence(srcTgtDir1, srcTgtDir2);
//        
//        Pair<ConcurrentLinkedHashMap<Integer, SSFNode>, ConcurrentLinkedHashMap<Integer, SSFNode>> biDirGIZAPPSymBalSentene = new Pair<>();
        
//        return symmetrizationGIZAPPInput;
        return symmetrizationSSFStoryPair;
    }
//    
//    public static Pair<SSFSentence, SSFSentence> biDirSymmetrizedSSFSentencePair(Pair<SSFSentence, SSFSentence> srcToTgtSSFSentencePair, 
//            Pair<SSFSentence, SSFSentence> tgtToSrcSSFSentencePair)
//    {        
//        Pair<SSFSentence, SSFSentence> biDirGIZAPPOSymBalSSFSentencePair = new Pair();        
//
//        Pair<LinkedHashMap<Integer, SSFNode>, LinkedHashMap<Integer, SSFNode>> biDirGIZAPPForSymBal = null;        
//        
//        biDirGIZAPPForSymBal = getBiDirSymmetrizedlIndexedSentencePair(srcToTgtSSFSentencePair, tgtToSrcSSFSentencePair);
//        
//        return biDirGIZAPPOSymBalSSFSentencePair;
//    }
    
//    /**
//     * The int value in each LinkedHashMap denotes the index of an SSFNode in a source language SSFSentece.
//     * The SSFNode denotes the SSFNode in target language SSFSentence which is aligned to the above int index from the source language.
//     * 
//     * @param srcToTgtSSFSentencePairSrc
//     * @param srcToTgtSSFSentencePairTgt
//     * @return 
//     */    
//    public static Pair<LinkedHashMap<Integer, SSFNode>, LinkedHashMap<Integer, SSFNode>> getBiDirSymmetrizedlIndexedSentencePair(Pair<SSFSentence, SSFSentence> srcToTgtSSFSentencePairSrc, Pair<SSFSentence, SSFSentence> srcToTgtSSFSentencePairTgt)
//    {        
//        Pair<LinkedHashMap<Integer, SSFNode>, LinkedHashMap<Integer, SSFNode>> biDirGIZAPPOSymBal = new Pair();     
//        
//        biDirGIZAPPOSymBal = doBiDirBalSentPairSymmetrization(biDirGIZAPPOSymBal);
//        
//        return biDirGIZAPPOSymBal;
//    }
    
//    public static Pair<LinkedHashMap<Integer, SSFNode>, LinkedHashMap<Integer, SSFNode>> doBiDirBalSentPairSymmetrization(Pair<LinkedHashMap<Integer, SSFNode>, LinkedHashMap<Integer, SSFNode>> biDirIndexedSentencePair)
//    public static Pair<SSFSentence, SSFSentence> doBiDirBalSentPairSymmetrization(Pair<LinkedHashMap<Integer, SSFNode>, LinkedHashMap<Integer, SSFNode>> biDirIndexedSentencePair)
     public static Pair<SSFSentence, SSFSentence> doDirSymmetrizedSSFSentencePair(Pair<SSFSentence, SSFSentence> srcToTgtSSFSentencePair, 
            Pair<SSFSentence, SSFSentence> tgtToSrcSSFSentencePair)
    {   
        Pair<SSFSentence, SSFSentence> symmetrizedSentencePair = new Pair<>();
        
//        srcToTgtSSFSentencePair.getFirst().getRoot().getChild(2).getNext()
        
//        Pair<LinkedHashMap<Integer, SSFNode>, LinkedHashMap<Integer, SSFNode>> biDirGIZAPPOSymBal = new Pair();        

        LinkedHashSet<Pair<Integer, Integer>> srcToTgtAlignmentIndices = getAlignedIndicesForSentencePair(srcToTgtSSFSentencePair);
        LinkedHashSet<Pair<Integer, Integer>> tgtToSrcAlignmentIndices = getAlignedIndicesForSentencePair(tgtToSrcSSFSentencePair);
        
        // Symmetrize alignments using GDFA
        LinkedHashSet<Pair<Integer, Integer>> symmetrizedAlignments = symmetrizeAlignmentIndicesGDFA(
                srcToTgtAlignmentIndices, tgtToSrcAlignmentIndices);

        // Print the symmetrized alignments
        System.out.println("Symmetrized Alignments for sentence " + srcToTgtSSFSentencePair.getFirst().convertToRawText());
        System.out.println("\t" + symmetrizedAlignments);
        
        try {
            // To be returned after symmetrization
            SSFSentence symmetrizedSenteenceSrc = (SSFSentence) srcToTgtSSFSentencePair.getFirst().getCopy();
            SSFSentence symmetrizedSenteenceTgt = (SSFSentence) srcToTgtSSFSentencePair.getSecond().getCopy();

            SSFPhrase srcSentenceRoot = symmetrizedSenteenceSrc.getRoot();
            SSFPhrase tgtSentenceRoot = symmetrizedSenteenceTgt.getRoot();
            
            symmetrizedSenteenceSrc.clearAlignments();
            srcSentenceRoot.reallocateNames(null, null);
            
            symmetrizedSenteenceTgt.clearAlignments();
            tgtSentenceRoot.reallocateNames(null, null);
            
            symmetrizedAlignments.stream().forEach(alignmment -> {
                int srcNodeIndex = alignmment.getFirst();
                int tgtNodeIndex = alignmment.getSecond();

                SSFNode srcNode = (SSFNode) srcSentenceRoot.getChildAt(srcNodeIndex);
                SSFNode tgtNode = (SSFNode) tgtSentenceRoot.getChildAt(tgtNodeIndex);
                
                String srcNodeUniqueName = srcNode.getAttributeValue("name");
                String tgtNodeUniqueName = tgtNode.getAttributeValue("name");
                                
                srcNode.getFeatureStructures().getAltFSValue(0).getAttributeValue("alignedTo").setMultiValue(tgtNodeUniqueName);
                tgtNode.getFeatureStructures().getAltFSValue(0).getAttributeValue("alignedTo").setMultiValue(srcNodeUniqueName);
                
                // AlignmentUnits to be added
//                srcNode.getFeatureStructures().getAltFSValue(0).AttributeValue("alignedTo", "" + (tgtNodeIndex + 1) );
//                tgtNode.getFeatureStructures().getAltFSValue(0).addsetAttributeValue("alignedTo", "" + (srcNodeIndex + 1) );

//                    AlignmentUnit srcNodeAlignmentUnit = srcNode.getAlignmentUnit(); // REPLACED BY ABOVE TWO LINES
//                    AlignmentUnit tgtNodeAlignmentUnit = tgtNode.getAlignmentUnit(); // REPLACED BY ABOVE TWO LINES
//
//                    srcNodeAlignmentUnit.addAlignedUnit(tgtNodeAlignmentUnit); // - do -
//                    tgtNodeAlignmentUnit.addAlignedUnit(srcNodeAlignmentUnit); // - do -
                
            });

            symmetrizedSentencePair.setFirst(symmetrizedSenteenceSrc);
            symmetrizedSentencePair.setFirst(symmetrizedSenteenceTgt);
            
        } catch (Exception ex) {
            Logger.getLogger(GIZAPPUtils.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Symmetrization at the setence-level for the above sentence complete.");
                        
        return symmetrizedSentencePair;
    }
    
    public static LinkedHashSet <Pair<Integer, Integer>> getAlignedIndicesForSentencePair(Pair<SSFSentence, SSFSentence> senPair)
    {
        LinkedHashSet<Pair<Integer, Integer>> alignmentIndices = new LinkedHashSet<>();
        
        SSFNode firstSSFSentRoot = senPair.getFirst().getRoot();
        SSFNode secondSSFSentRoot = senPair.getSecond().getRoot();

        int numNodes = firstSSFSentRoot.countChildren();
        
        for (int i = 0; i < numNodes; i++) {
            
            SSFNode ssfNodeFirst = (SSFNode) firstSSFSentRoot.getChildAt(i);
            
            List<SSFNode> alignedNodes = ssfNodeFirst.getAlignedObjects();

            if(alignedNodes == null)
            {
                continue;
            }
            
            for (SSFNode alignedNode : alignedNodes) {
                
                if(alignedNode == null)
                {
                    continue;
                }
                
                int j = secondSSFSentRoot.getIndex(alignedNode);                
             
                Pair<Integer, Integer> alignmentPair = new Pair<>();

                alignmentPair.setFirst(i);
                alignmentPair.setSecond(j);                
            
                alignmentIndices.add(alignmentPair);
            }
        }

        System.out.println("Symmetrization at the index-level for the above sentence complete.");

        return alignmentIndices;
    }
    
//    // Uses the gow_diag_final_and (GDFA) heuristic
//    public static LinkedHashSet<Pair<Integer, Integer>> symmetrizeAlignmentIndicesGDFA(LinkedHashSet<Pair<Integer, Integer>> srcToTgtAlignmentIndices,
//            LinkedHashSet<Pair<Integer, Integer>> tgtToSrcAlignmentIndices)
//    {
//        LinkedHashSet<Pair<Integer, Integer>> symAlignmentIndices = new LinkedHashSet<>();
//        
//        
//        
//        return symAlignmentIndices;
//    }

    /**
     * Symmetrizes alignments using the Grow-Diag-And-Final (GDFA) heuristic.
     *
     * @param srcToTgtAlignmentIndices Direct alignment indices (source-to-target).
     * @param tgtToSrcAlignmentIndices Inverse alignment indices (target-to-source).
     * @return Symmetrized alignment indices as a LinkedHashSet of (source, target) pairs.
     */
    public static LinkedHashSet<Pair<Integer, Integer>> symmetrizeAlignmentIndicesGDFA(
            LinkedHashSet<Pair<Integer, Integer>> srcToTgtAlignmentIndices,
            LinkedHashSet<Pair<Integer, Integer>> tgtToSrcAlignmentIndices) {

        // Initialize the symmetrized alignment with the intersection of the two sets
        LinkedHashSet<Pair<Integer, Integer>> symAlignmentIndices = new LinkedHashSet<>(srcToTgtAlignmentIndices);
        symAlignmentIndices.retainAll(tgtToSrcAlignmentIndices); // Intersection of direct and inverse alignments

        // Add union of direct and inverse alignments
        LinkedHashSet<Pair<Integer, Integer>> unionAlignmentIndices = new LinkedHashSet<>(srcToTgtAlignmentIndices);
        unionAlignmentIndices.addAll(tgtToSrcAlignmentIndices);

        boolean added;
        do {
            added = false;

            // Iterate over each alignment in the current symmetrized alignment
            for (Pair<Integer, Integer> point : new LinkedHashSet<>(symAlignmentIndices)) {
                int src = point.getFirst();
                int tgt = point.getSecond();

                // Check all 8 neighboring cells around the current point
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (dx == 0 && dy == 0) continue; // Skip the current point

                        int neighborSrc = src + dx;
                        int neighborTgt = tgt + dy;

                        Pair<Integer, Integer> neighbor = new Pair<>(neighborSrc, neighborTgt);

                        // Add neighbor if it's in the union of alignments and not already in the symmetrized set
                        if (unionAlignmentIndices.contains(neighbor) && !symAlignmentIndices.contains(neighbor)) {
                            symAlignmentIndices.add(neighbor);
                            added = true; // Mark that a new alignment was added
                        }
                    }
                }
            }
        } while (added); // Repeat until no new alignments are added

        // Final pass: Add any remaining direct or inverse alignments not already in the symmetrized set
        symAlignmentIndices.addAll(srcToTgtAlignmentIndices);
        symAlignmentIndices.addAll(tgtToSrcAlignmentIndices);

        System.out.println("GDFA for the above sentence complete.");

        return symAlignmentIndices;
    }
    
//    public static void prepareSentenceLevelAlignments(AlignmentBlock<SSFStory> alignmentBlock, Pair<SSFSentence, SSFSentence> ssfSentencePair)
//    public static void prepareSentenceLevelAlignments(Pair<SSFSentence, SSFSentence> ssfSentencePair)
//    {
//            SSFSentence srcSentence = ssfSentencePair.getFirst();
//            SSFSentence tgtSentence = ssfSentencePair.getSecond();
//
//            SSFPhrase srcRoot = srcSentence.getRoot();
//            SSFPhrase tgtRoot = tgtSentence.getRoot();
//
//            if(srcRoot == null) {
//                srcRoot = new SSFPhrase();
//            }
//
//            if(tgtRoot == null) {
//                tgtRoot = new SSFPhrase();
//            }

//            srcRoot.reallocateNames(null, null);
//            tgtRoot.reallocateNames(null, null);
//
//            List<SSFNode> srcChildren = srcRoot.getAllChildren();
//            List<SSFNode> tgtChildren = tgtRoot.getAllChildren();
//
//            int scount = srcChildren.size();
//            int tcount = tgtChildren.size();
//
//            for (int i = 0; i < scount; i++)
//            {
//                SSFNode node = (SSFNode) srcChildren.get(i);
//
//                AlignmentUnit alignableUnit = node.getAlignmentUnit();
////                addEdges(alignableUnit, 0, i, false);
//
//                alignableUnit.setAlignmentObject(node);
//
//                alignableUnit.setIndex(i);
//                alignableUnit.setParallelIndex(0);
////                srcSentence.addSrcAlignedUnit(alignableUnit);
////                tgtAlignedUnits.add(p);
//            }
//
//            for (int i = 0; i < tcount; i++)
//            {
//                SSFNode node = (SSFNode) tgtChildren.get(i);
//
//                AlignmentUnit alignableUnit = node.getAlignmentUnit();
////                addEdges(alignableUnit, 2, i, true);
//
//                alignableUnit.setAlignmentObject(node);
//
//                alignableUnit.setIndex(i);
//                alignableUnit.setParallelIndex(2);
////                addTgtAlignedUnit(alignableUnit);
//            }
//        
//    }

    public static void saveData(Pair<SSFStory, SSFStory> symmetrizedGIZAOutput, String gizaFilePath, String cs) {
//        storeCurrentPosition();

        SSFStory srcSSFStory = symmetrizedGIZAOutput.getFirst();
        SSFStory tgtSSFStory = symmetrizedGIZAOutput.getSecond();
        
        File gizaFile = new File(gizaFilePath);
        String gizaFileName = gizaFile.getName();

        try {
            srcSSFStory.save(gizaFileName + ".src.ssf.txt", cs);
            tgtSSFStory.save(gizaFileName + ".src.ssf.txt", cs);

//            srcTextPT.save(srcFilePath + ".pt.txt", cs);
//            tgtTextPT.save(tgtFilePath + ".pt.txt", cs);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SentenceAlignmentInterfaceJPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SentenceAlignmentInterfaceJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void saveGIZAFormat(Pair<SSFStory, SSFStory> symmetrizedGIZAOutput, String gizaFilePath, String cs)
    {
        SSFStory srcSSFStory = symmetrizedGIZAOutput.getFirst();
        SSFStory tgtSSFStory = symmetrizedGIZAOutput.getSecond();
        
        SSFStoryImpl.saveGIZAData(gizaFilePath, cs, srcSSFStory, tgtSSFStory);

//        SSFSentence srcSen = null;
//        SSFSentence tgtSen = null;
//
//        int scount = srcSSFStory.countSentences();
//        int tcount = tgtSSFStory.countSentences();
//
//        if (scount != tcount) {
//            
//            System.out.println("Number of sentences in the srcSSFStroy is not equal to the number in tgtSSFStroy");
//            System.out.println("Number of sentences in srcSSFStroy: " +  scount);
//            System.out.println("Number of sentences in tgtSSFStroy: " +  tcount);
//            
//            return;
//        }
//
//        PrintStream ps = null;
//
//        try {
//            ps = new PrintStream(gizaFilePath, cs);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(GIZAPPUtils.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(WordAlignmentInterfaceJPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        for (int i = 0; i < scount; i++) {
//            srcSen = srcSSFStory.getSentence(i);
//            tgtSen = tgtSSFStory.getSentence(i);
//
////            srcSen.loadAlignments(tgtSen, 0);
////            tgtSen.loadAlignments(srcSen, 2);
//
//            int srcAUCount = srcSen.getRoot().countChildren();
//            int tgtAUCount = tgtSen.getRoot().countChildren();
//
//            ps.println("# Sentence pair (" + (i + 1) + ") source length " + srcAUCount + " target length " + tgtAUCount + " alignment score : ");
//
//            for (int j = 0; j < srcAUCount; j++) {
//                SSFNode node = srcSen.getRoot().getChild(j);
//
//                ps.print(node.getLexData() + " ");
//            }
//
//            ps.println("");
//
//            String nullString = "NULL ({ ";
//            String tgtString = "";
//
//            ConcurrentLinkedHashMap<Integer, Integer> nonNullMap = new ConcurrentLinkedHashMap<Integer, Integer>();
//
//            for (int j = 0; j < tgtAUCount; j++) {
//                SSFNode node = tgtSen.getRoot().getChild(j);
//
//                tgtString += node.getLexData() + " ({ ";
//                                
//                srcNode.getFeatureStructures().getAltFSValue(0).getAttributeValue("alignedTo").setMultiValue(tgtNodeUniqueName);
//                tgtNode.getFeatureStructures().getAltFSValue(0).getAttributeValue("alignedTo").setMultiValue(srcNodeUniqueName);
//
////                AlignmentUnit aunit = node.getAlignmentUnit();
////
////                if (aunit.countAlignedUnits() == 0)
////                {
////                }
////                else
////                {
////                    Iterator<String> itr = aunit.getAlignedUnitKeys();
////
////                    while(itr.hasNext())
////                    {
////                        String key = itr.next();
////
////                        AlignmentUnit alignedUnit = aunit.getAlignedUnit(key);
////
////                        tgtString += (alignedUnit.getIndex() + 1) + " ";
////
////                        nonNullMap.put(alignedUnit.getIndex(), j);
////                    }
////                }
//
//                tgtString += "}) ";
//            }
//
//            tgtString = tgtString.replaceAll("\\(\\{\\}\\)", "({ })");
//
//            for (int j = 0; j < srcAUCount; j++) {
//
//                if(nonNullMap.get(j) == null)
//                {
//                    nullString += (j + 1) + " ";
//                }
//            }
//
//            nullString += "}) ";
//
//            ps.println(nullString + tgtString);
//        }
//
//        ps.close();
    }

    public static void main(String args[])
    {
        String gizaFilePathSrcToTgt = "D:\\projects\\Bhaashik\\data-and-resources\\Word-Alignment\\hindi-treebank\\bhojpuri-hindi\\parallel-corrected\\ver-0.1\\word-aligned\\hin-bho.A3.final";
        String csSrcToTgt = "UTF-8";
        String gizaFilePathTgtToSrc = "D:\\projects\\Bhaashik\\data-and-resources\\Word-Alignment\\hindi-treebank\\bhojpuri-hindi\\parallel-corrected\\ver-0.1\\word-aligned\\bho-hin.A3.final";
        String csTgtToSrc = "UTF-8";
        
        String gizaFilePath = "D:\\projects\\Bhaashik\\data-and-resources\\Word-Alignment\\hindi-treebank\\bhojpuri-hindi\\parallel-corrected\\ver-0.1\\word-aligned\\symmetrized.bho-hin.A3.final";
        String cs = "UTF-8";

//        Pair<SSFStory, SSFStory> gIZAPPUtils.symmetrizeBiDirGIZAPPOutput(String gizaFilePathSrcToTgt, String csSrcToTgt, String gizaFilePathTgtToSrc, String csTgtToSrc);
        Pair<SSFStory, SSFStory> symmetrizedGIZAOutput = GIZAPPUtils.symmetrizeBiDirGIZAPPOutput(gizaFilePathSrcToTgt, csSrcToTgt, gizaFilePathTgtToSrc, csTgtToSrc);
        
        GIZAPPUtils.saveGIZAFormat(symmetrizedGIZAOutput, gizaFilePath, cs);

//        SSFStoryImpl.loadGIZAData(gizaFilePath, cs, srcSSFStory, tgtSSFStory);
//        File sfile = new File(gizaFilePath);

//        if (sfile.exists() && sfile.getParentFile() != null) {
//            path = sfile.getParent();
//        } else {
//            path = stateKVProps.getPropertyValue("GIZADir");
//        }        
    }
}
