/*
 * SSFToSVMImpl.java
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
import java.util.HashMap;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import bhaashik.GlobalProperties;
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
public class SSFToSVMImpl extends WindowFeaturesImpl{
    
    /** Creates a new instance of SSFToSVMImpl */
    public SSFToSVMImpl() {
    }
    
    public SSFToSVMImpl(String path, PrintStream ps, boolean whetherTest)
    {
        printFeatureToSSFDir(path, ps, whetherTest);
    }
    
    HashMap hMap;
    static int staticInt;
    public void printFeatureTrain(SSFStory story, PrintStream ps) {
        if(staticInt == 0)
        {
            hMap = new HashMap();

            hMap.put("B-NEP", 1);
            hMap.put("I-NEP", 2);
            hMap.put("O-NEP", 3);
            hMap.put("B-NETE", 4);
            hMap.put("I-NETE", 5);
            hMap.put("O-NETE", 6);
            hMap.put("B-NETO", 7);
            hMap.put("I-NETO", 8);
            hMap.put("O-NETO", 9);
            hMap.put("B-NETI", 10);
            hMap.put("I-NETI", 11);
            hMap.put("O-NETI", 12);
            hMap.put("B-NED", 13);
            hMap.put("I-NED", 14);
            hMap.put("O-NED", 15);
            hMap.put("B-NEL", 16);
            hMap.put("I-NEL", 17);
            hMap.put("O-NEL", 18);
            hMap.put("B-NEN", 19);
            hMap.put("I-NEN", 20);
            hMap.put("O-NEN", 21);
            hMap.put("B-NET", 22);
            hMap.put("I-NET", 23);
            hMap.put("O-NET", 24);
            hMap.put("B-NEO", 25);
            hMap.put("I-NEO", 26);
            hMap.put("O-NEO", 27);
            hMap.put("B-NEM", 28);
            hMap.put("I-NEM", 29);
            hMap.put("O-NEM", 30);
            hMap.put("B-NEA", 31);
            hMap.put("I-NEA", 32);
            hMap.put("O-NEA", 33);
            hMap.put("B-NETP",34);
            hMap.put("I-NETP",35);
            hMap.put("O-NETP", 36);
            hMap.put("B-NOT", 37);
            hMap.put("I-NOT", 38);
            hMap.put("O-NOT", 39);
            staticInt++;
        }
        int countNE = 0;
        int scount = story.countSentences();
        String subst = "nil";

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
                
                ps.print(hMap.get(classWord) + " ");
                String lexdata = ((SSFNode) words.get(j)).getLexData();

                List wordFeatures = getWordWindow(sen, j, 3, WINDOW_DIRECTION_BOTH, 1);

                int wcount = wordFeatures.size();
                // to Print the word window
                 ps.print(((SSFNode) words.get(j)).getLexData());
                for (int k = 0; k < wcount; k++)
                {
                        String temp = ((SSFNode) wordFeatures.get(k)).getLexData();
                            ps.print(temp);
                }

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
                                 ps.print(((String) charFeatures.get(n)));

                //this is to  print the chunk attributes of the given window of the word       
                List chunkFeatures = getChunkAttributeWindow(chunkVector, j , 3 , WINDOW_DIRECTION_BOTH);

                for (int k = 0; k < chunkFeatures.size(); k++) {
                     ps.print((String) chunkFeatures.get(k));
                }

                ps.print((String) chunkVector.get(j));


               List parentChunks = getParentChunkAttribute((SSFNode)words.get(j));
               int pcount = parentChunks.size();
               for (int k = 0; k < pcount; k++) {
                     ps.print((String) parentChunks.get(k));
                }
                ps.println();
            }
            countNE++;
        }
    }
    
    public void printFeatureTest(SSFStory story, PrintStream ps) {
        if(staticInt == 0)
        {
            hMap = new HashMap();

            hMap.put("B-NEP", 1);
            hMap.put("I-NEP", 2);
            hMap.put("O-NEP", 3);
            hMap.put("B-NETE", 4);
            hMap.put("I-NETE", 5);
            hMap.put("O-NETE", 6);
            hMap.put("B-NETO", 7);
            hMap.put("I-NETO", 8);
            hMap.put("O-NETO", 9);
            hMap.put("B-NETI", 10);
            hMap.put("I-NETI", 11);
            hMap.put("O-NETI", 12);
            hMap.put("B-NED", 13);
            hMap.put("I-NED", 14);
            hMap.put("O-NED", 15);
            hMap.put("B-NEL", 16);
            hMap.put("I-NEL", 17);
            hMap.put("O-NEL", 18);
            hMap.put("B-NEN", 19);
            hMap.put("I-NEN", 20);
            hMap.put("O-NEN", 21);
            hMap.put("B-NET", 22);
            hMap.put("I-NET", 23);
            hMap.put("O-NET", 24);
            hMap.put("B-NEO", 25);
            hMap.put("I-NEO", 26);
            hMap.put("O-NEO", 27);
            hMap.put("B-NEM", 28);
            hMap.put("I-NEM", 29);
            hMap.put("O-NEM", 30);
            hMap.put("B-NEA", 31);
            hMap.put("I-NEA", 32);
            hMap.put("O-NEA", 33);
            hMap.put("B-NETP",34);
            hMap.put("I-NETP",35);
            hMap.put("O-NETP", 36);
            hMap.put("B-NOT", 37);
            hMap.put("I-NOT", 38);
            hMap.put("O-NOT", 39);
            staticInt++;
        }
        int countNE = 0;
        int scount = story.countSentences();
        String subst = "nil";

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
                
                ps.print(hMap.get(classWord) + " ");
                String lexdata = ((SSFNode) words.get(j)).getLexData();

                List wordFeatures = getWordWindow(sen, j, 3, WINDOW_DIRECTION_BOTH, 1);

                int wcount = wordFeatures.size();
                // to Print the word window
                 ps.print(((SSFNode) words.get(j)).getLexData());
                for (int k = 0; k < wcount; k++)
                {
                        String temp = ((SSFNode) wordFeatures.get(k)).getLexData();
                            ps.print(temp);
                }

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
                                 ps.print(((String) charFeatures.get(n)));

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
//                     ps.print((String) parentChunks.get(k));
//                }
                ps.println();
            }
            countNE++;
        }
    }
    
    public static void main(String[] args) throws Exception {
        FSProperties fsp = new FSProperties();
        SSFProperties ssfp = new SSFProperties();
        SSFStory testStory = new SSFStoryImpl();
        SSFStory trainStory = new SSFStoryImpl();
        PrintStream psLibsvm;
        SSFToArff_LSVM obj;
        boolean whetherTest=false;

        whetherTest = Boolean.parseBoolean(JOptionPane.showInputDialog(GlobalProperties.getIntlString("Enter_0_for_Train_1_for_Test,_Then_enter_any_file_in_the_input_directory_and_then_enter_output_file")));

        JFileChooser jfc = new JFileChooser();
        
        jfc.showOpenDialog(null);
        
        String inputDir = jfc.getSelectedFile().getAbsolutePath();

        jfc.showOpenDialog(null);
        
        String outputFile = jfc.getSelectedFile().getAbsolutePath();
        
        int index = inputDir.lastIndexOf('/');
        System.out.println(index);
        inputDir = inputDir.substring(0, index);
        System.out.println(inputDir);
        if(inputDir.equalsIgnoreCase("0"))
        {
            try {
                psLibsvm = new PrintStream(outputFile, GlobalProperties.getIntlString("UTF-8"));
                obj = new SSFToArff_LSVM(inputDir, psLibsvm, whetherTest);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }
        
        else
        {
            try {
                whetherTest = true;
                psLibsvm = new PrintStream(outputFile, GlobalProperties.getIntlString("UTF-8"));
                obj = new SSFToArff_LSVM(inputDir, psLibsvm, whetherTest);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }
    }
}
