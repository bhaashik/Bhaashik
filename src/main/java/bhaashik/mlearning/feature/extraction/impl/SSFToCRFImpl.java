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
public class SSFToCRFImpl extends WindowFeaturesImpl{
    
    /** Creates a new instance of SSFToCRFImpl */
    public SSFToCRFImpl() {
    }
    
    public SSFToCRFImpl(String path, PrintStream ps, boolean whetherTest)
    {
        printFeatureToSSFDir(path, ps, whetherTest);
    }
    
    
    
    //Only this format has implemented the other features.... other formats have to be upgraded
    
    public void printFeatureTrain(SSFStory story, PrintStream ps) {
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
                String lexdata = ((SSFNode) words.get(j)).getLexData();

                List wordFeatures = getWordWindow(sen, j, 3, WINDOW_DIRECTION_BOTH, 1);

                int wcount = wordFeatures.size();
                // to Print the word window
                
                //System.out.println(wcount);
                 System.out.println(((SSFNode) words.get(j)).getLexData() + " ");
                 ps.print(((SSFNode) words.get(j)).getLexData() + " ");
//                for (int k = 0; k < wcount; k++)
//                {
//                    String temp = ((SSFNode) wordFeatures.get(k)).getLexData();
//                    if(k==0)
//                    System.out.println(temp + " ");
//                    ps.print(temp + " ");
//                }

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
                       
                       
                       
                  // Printing other features like word length, is start of sentence, is number etc.     
                       OtherFeaturesImpl oft = new OtherFeaturesImpl();
                       List otherFeatures = new ArrayList();
                       if (oft.isSentenceStart((SSFNode) words.get(j)))
                           otherFeatures.add("true");
                       else     
                           otherFeatures.add("false");
                       
                       if (oft.isNumber((SSFNode) words.get(j)))
                           otherFeatures.add("true");
                       else     
                           otherFeatures.add("false");    
                       
                       if (oft.isFourNumbers((SSFNode) words.get(j)))
                           otherFeatures.add("true");
                       else     
                           otherFeatures.add("false");
                       
                       otherFeatures.add(oft.characterCount((SSFNode) words.get(j)));
                  //     otherFeatures.add(oft.wordClass((SSFNode) words.get(j)));
                  //     otherFeatures.add(oft.briefWordClass((SSFNode) words.get(j)));
                       for (int f=0; f < otherFeatures.size(); f++)
                       {
                           ps.print((otherFeatures.get(f).toString()) + " ");
                       }

                       
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
                String lexdata = ((SSFNode) words.get(j)).getLexData();

                List wordFeatures = getWordWindow(sen, j, 3, WINDOW_DIRECTION_BOTH, 1);

                int wcount = wordFeatures.size();
                // to Print the word window
                
                //System.out.println(wcount);
                 System.out.println(((SSFNode) words.get(j)).getLexData() + " ");
                 ps.print(((SSFNode) words.get(j)).getLexData() + " ");

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

        whetherTest = Boolean.parseBoolean(JOptionPane.showInputDialog(GlobalProperties.getIntlString("Enter_0_for_Train_1_for_Test,_Then_enter_any_file_in_the_input_directory_and_then_enter_output_file")));

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
            psLibsvm = new PrintStream(outputFile, GlobalProperties.getIntlString("UTF-8"));
            obj = new SSFToCRFImpl(inputDir, psLibsvm, whetherTest);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
    }
}
