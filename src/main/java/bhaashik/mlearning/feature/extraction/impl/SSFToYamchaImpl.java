/*
 * SSFToCRFImpl.java
 *
 * Created on September 3, 2008, 6:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.mlearning.feature.extraction.impl;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;

import bhaashik.corpus.ssf.SSFProperties;
import bhaashik.corpus.ssf.SSFSentence;
import bhaashik.corpus.ssf.SSFStory;
import bhaashik.corpus.ssf.features.FeatureAttribute;
import bhaashik.corpus.ssf.features.FeatureStructure;
import bhaashik.corpus.ssf.features.FeatureStructures;
import bhaashik.corpus.ssf.features.impl.FSProperties;
import bhaashik.corpus.ssf.impl.SSFStoryImpl;
import bhaashik.corpus.ssf.tree.SSFNode;
import bhaashik.corpus.ssf.tree.SSFPhrase;
import bhaashik.tree.BhaashikMutableTreeNode;

/**
 *
 * @author Anil Kumar Singh
 */
public class SSFToYamchaImpl extends WindowFeaturesImpl{
    
    /** Creates a new instance of SSFToCRFImpl */
    
    public SSFToYamchaImpl(String path, PrintStream ps, boolean whetherTest)
    {
        printFeatureToSSFDir(path, ps, whetherTest);
    }
    
    public void printFeatureTrain(SSFStory story, PrintStream ps) {
        int countNE = 0;
        int scount = story.countSentences();
        String subst = "nil";

        System.out.println("in train");

        int prefixNum = 3;
        int suffixNum = 3;

        //this section prints the header information in the arff file 

        for (int i = 0; i < scount; i++) {
            int flagSentence = 0;
            SSFSentence sen = story.getSentence(i);

            List chunkVector = getChunkInformation(sen);//this is the chunk information of the word of a sentence

            List<BhaashikMutableTreeNode> words = ((SSFPhrase) sen.getRoot()).getAllLeaves();

            int ccount = words.size();

            String classWord = new String();
            for (int j = 0; j < ccount; j++)
            {
                String lexdata = ((SSFNode) words.get(j)).getLexData();

                List wordFeatures = getWordWindow(sen, j, 3, WINDOW_DIRECTION_BOTH, 1);

                int wcount = wordFeatures.size();
                // to Print the word window
                
//                 System.out.println(((SSFNode) words.get(j)).getLexData() + " ");
                 ps.print(((SSFNode) words.get(j)).getLexData() + " ");
//                for (int k = 0; k < wcount; k++)
//                {
//                    String temp = ((SSFNode) wordFeatures.get(k)).getLexData();
//                    if(k==0)
//                    System.out.println(temp + " ");
//                    ps.print(temp + " ");
//                }
//
                       List charFeatures = new ArrayList();

                       for (int m = 1; m <= prefixNum; m++) {
                           if(m > lexdata.length())
                               charFeatures.add(subst);
                           else
                               charFeatures.add(lexdata.substring(0, m));
                       }

                       for (int n = 1; n <= suffixNum; n++) {
                           if(n > lexdata.length())
                               charFeatures.add(subst);
                           else
                               charFeatures.add(lexdata.substring(lexdata.length() - n, lexdata.length()));
                       }

                       for (int n = 0; n < charFeatures.size() ; n++)
                                 ps.print(((String) charFeatures.get(n)) + " ");

                //this is to  print the chunk attributes of the given window of the word       
//                Vector chunkFeatures = getChunkAttributeWindow(chunkVector, j , 3 , WINDOW_DIRECTION_BOTH);
//
//                for (int k = 0; k < chunkFeatures.size(); k++) {
//                     ps.print((String) chunkFeatures.get(k));
//                }
//
//                ps.print((String) chunkVector.get(j) + " ");
//
//
//               Vector parentChunks = getParentChunkAttribute((SSFNode)words.get(j));
//               int pcount = parentChunks.size();
//               for (int k = 0; k < pcount; k++) {
//                     ps.print((String) parentChunks.get(k) + " ");
//                }
//                       
                 classWord = (String) chunkVector.get(j)+ "-";

                SSFNode parent = (SSFNode) ((SSFNode) words.get(j)).getParent();

                FeatureStructures fs = parent.getFeatureStructures();
                if(fs != null && fs.countAltFSValues() > 0)
                {
                    FeatureStructure fs1 = fs.getAltFSValue(0);
                    FeatureAttribute fa =  fs1.getAttribute("ne");
                    if(fa != null && fa.countNestedAltValues() > 0)
                    {
                        String wordFeature = (String) fa.getNestedAltValue(0).getMultiValue();

                        if(wordFeature != null && (isValidSymbol(wordFeature) == true) ){
                            classWord += wordFeature;
                        }
                        else
                            classWord += "NOT";

                        if(wordFeature != null && (isContainingNE(wordFeature) == true))
                            flagSentence = 1;
                    }
                    else
                    {
                        classWord += "NOT";
                    }
                }                   
                 
                else
                {
                    classWord += "NOT";
                }
                
                ps.println(classWord);
            }
            
            ps.println();
            countNE++;
        }
    }
    
    public void printFeatureTest(SSFStory story, PrintStream ps) {
//        
//        int countNE = 0;
//        int scount = story.countSentences();
//        String subst = "nil";
//
//        int prefixNum = 3;
//        int suffixNum = 3;
//
//        //this section prints the header information in the arff file 
//
//        for (int i = 0; i < scount; i++) {
//            int flagSentence = 0;
//            SSFSentence sen = story.getSentence(i);
//
//            Vector chunkVector = getChunkInformation(sen);//this is the chunk information of the word of a sentence
//
//            Vector words = ((SSFPhrase) sen.getRoot()).getAllLeaves();
//
//            int ccount = words.size();
//
//            String classWord = new String();
//            for (int j = 0; j < ccount; j++)
//            {
//                 classWord = (String) chunkVector.get(j)+ "-";
//
//                SSFNode parent = (SSFNode) ((SSFNode) words.get(j)).getParent();
//
//                FeatureStructures fs = parent.getFeatureStructures();
//                if(fs != null && fs.countAltFSValues() > 0)
//                {
//                    FeatureStructure fs1 = fs.getAltFSValue(0);
//                    FeatureAttribute fa =  fs1.getAttribute("ne");
//                    if(fa != null && fa.countAltValues() > 0)
//                    {
//                        String wordFeature = (String) fa.getMultiValue(0).getValue();
//
//                        if(wordFeature != null && (isValidSymbol(wordFeature) == true) ){
//                            classWord += wordFeature;
//                        }
//                        else
//                            classWord += "NOT";
//
//                        if(wordFeature != null && (isContainingNE(wordFeature) == true))
//                            flagSentence = 1;
//                    }
//                    else
//                    {
//                        classWord += "NOT";
//                    }
//                }                   
//                 
//                else
//                {
//                    classWord += "NOT";
//                }
//                
//                
//                String lexdata = ((SSFNode) words.get(j)).getLexData();
//
//                Vector wordFeatures = getWordWindow(sen, j, 3, WINDOW_DIRECTION_BOTH, 1);
//
//                int wcount = wordFeatures.size();
//                // to Print the word window
//                 ps.print(((SSFNode) words.get(j)).getLexData() + " ");
//                for (int k = 0; k < wcount; k++)
//                {
//                        String temp = ((SSFNode) wordFeatures.get(k)).getLexData();
//                            ps.print(temp + " ");
//                }
//
//                       Vector charFeatures = new Vector();
//
//                       for (int m = 1; m <= prefixNum; m++) {
//                           if(m > lexdata.length())
//                               charFeatures.add(subst);
//                           else
//                               charFeatures.add(lexdata.substring(0, m));
//                       }
//
//                       for (int n = 1; n <= suffixNum; n++) {
//                           if(n > lexdata.length())
//                               charFeatures.add(subst);
//                           else
//                               charFeatures.add(lexdata.substring(lexdata.length() - n, lexdata.length()));
//                       }
//
//                       for (int n = 0; n < charFeatures.size() ; n++)
//                                 ps.print(((String) charFeatures.get(n)) + " ");
//
//                //this is to  print the chunk attributes of the given window of the word       
////                Vector chunkFeatures = getChunkAttributeWindow(chunkVector, j , 3 , WINDOW_DIRECTION_BOTH);
////
////                for (int k = 0; k < chunkFeatures.size(); k++) {
////                     ps.print((String) chunkFeatures.get(k));
////                }
////
////                ps.print((String) chunkVector.get(j));
////
////
////               Vector parentChunks = getParentChunkAttribute((SSFNode)words.get(j));
////               int pcount = parentChunks.size();
////               for (int k = 0; k < pcount; k++) {
////                     ps.print((String) parentChunks.get(k));
////                }
//                ps.println(classWord);
//            }
//            countNE++;
//        }
        
        int countNE = 0;
        int scount = story.countSentences();
        String subst = "nil";

        System.out.println("in test");

        int prefixNum = 3;
        int suffixNum = 3;

        //this section prints the header information in the arff file 

        for (int i = 0; i < scount; i++) {
            int flagSentence = 0;
            SSFSentence sen = story.getSentence(i);

            List chunkVector = getChunkInformation(sen);//this is the chunk information of the word of a sentence

            List<BhaashikMutableTreeNode> words = ((SSFPhrase) sen.getRoot()).getAllLeaves();

            int ccount = words.size();

            String classWord = new String();
            for (int j = 0; j < ccount; j++)
            {
                String lexdata = ((SSFNode) words.get(j)).getLexData();

                List wordFeatures = getWordWindow(sen, j, 3, WINDOW_DIRECTION_BOTH, 1);

                int wcount = wordFeatures.size();
                // to Print the word window
                
                //System.out.println(wcount);
                 //System.out.println(((SSFNode) words.get(j)).getLexData() + " ");
                 ps.print(((SSFNode) words.get(j)).getLexData() + " ");
//                for (int k = 0; k < wcount; k++)
//                {
//                    String temp = ((SSFNode) wordFeatures.get(k)).getLexData();
//                    if(k==0)
//                    System.out.println(temp + " ");
//                    ps.print(temp + " ");
//                }
//
                       List charFeatures = new ArrayList();

                       for (int m = 1; m <= prefixNum; m++) {
                           if(m > lexdata.length())
                               charFeatures.add(subst);
                           else
                               charFeatures.add(lexdata.substring(0, m));
                       }

                       for (int n = 1; n <= suffixNum; n++) {
                           if(n > lexdata.length())
                               charFeatures.add(subst);
                           else
                               charFeatures.add(lexdata.substring(lexdata.length() - n, lexdata.length()));
                       }

                       for (int n = 0; n < charFeatures.size() ; n++)
                                 ps.print(((String) charFeatures.get(n)) + " ");

                //this is to  print the chunk attributes of the given window of the word       
//                Vector chunkFeatures = getChunkAttributeWindow(chunkVector, j , 3 , WINDOW_DIRECTION_BOTH);
//
//                for (int k = 0; k < chunkFeatures.size(); k++) {
//                     ps.print((String) chunkFeatures.get(k));
//                }
//
//                ps.print((String) chunkVector.get(j));
//
//
//               Vector parentChunks = getParentChunkAttribute((SSFNode)words.get(j));
//               int pcount = parentChunks.size();
//               for (int k = 0; k < pcount; k++) {
//                     ps.print((String) parentChunks.get(k) + " ");
//                }
//                       
                 classWord = (String) chunkVector.get(j)+ "-";

                SSFNode parent = (SSFNode) ((SSFNode) words.get(j)).getParent();

                FeatureStructures fs = parent.getFeatureStructures();
                if(fs != null && fs.countAltFSValues() > 0)
                {
                    FeatureStructure fs1 = fs.getAltFSValue(0);
                    FeatureAttribute fa =  fs1.getAttribute("ne");
                    if(fa != null && fa.countNestedAltValues() > 0)
                    {
                        String wordFeature = (String) fa.getNestedAltValue(0).getMultiValue();

                        if(wordFeature != null && (isValidSymbol(wordFeature) == true) ){
                            classWord += wordFeature;
                        }
                        else
                            classWord += "NOT";

                        if(wordFeature != null && (isContainingNE(wordFeature) == true))
                            flagSentence = 1;
                    }
                    else
                    {
                        classWord += "NOT";
                    }
                }                   
                 
                else
                {
                    classWord += "NOT";
                }
                
                ps.println(classWord);
            }

            ps.println();
            countNE++;
        }
    }
    
    
    public static void main(String[] args) throws Exception {
        FSProperties fsp = new FSProperties();
        SSFProperties ssfp = new SSFProperties();
        SSFStory testStory = new SSFStoryImpl();
        SSFStory trainStory = new SSFStoryImpl();
        PrintStream psLibsvm;
        SSFToCRFImpl obj;
        boolean whetherTest=false;

//        whetherTest = Boolean.parseBoolean(JOptionPane.showInputDialog("Enter 0 for Train 1 for Test, Then enter any file in the input directory and then enter output file"));

        JFileChooser jfc = new JFileChooser();
        
        jfc.showOpenDialog(null);
        
        String inputDir = jfc.getSelectedFile().getAbsolutePath();

        jfc.showOpenDialog(null);
        
        String outputFile = jfc.getSelectedFile().getAbsolutePath();
        
        int index = inputDir.lastIndexOf('/');
        //System.out.println(index);
        inputDir = inputDir.substring(0, index);
        //System.out.println(inputDir);
            try {
                psLibsvm = new PrintStream(outputFile, "UTF-8");
                SSFToYamchaImpl ssfToYamchaImpl = new SSFToYamchaImpl(inputDir, psLibsvm, whetherTest);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
    }
}
