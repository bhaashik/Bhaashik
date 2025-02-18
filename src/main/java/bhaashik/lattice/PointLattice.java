/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.lattice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

import bhaashik.GlobalProperties;
import bhaashik.corpus.ssf.features.FeatureAttribute;
import bhaashik.corpus.ssf.features.FeatureStructure;
import bhaashik.properties.PropertyTokens;
import bhaashik.text.spell.PhoneticModelOfScripts;
import bhaashik.tree.BhaashikEdge;
import bhaashik.tree.BhaashikEdges;
import bhaashik.tree.BhaashikMutableTreeNode;
import bhaashik.table.BhaashikTableModel;
import bhaashik.util.UtilityFunctions;

/**
 *
 * @author Anil Kumar Singh
 */
public class PointLattice implements Serializable {

    protected BhaashikTableModel grid;
    protected BhaashikEdges edges;

    protected String langEnc = GlobalProperties.getIntlString("hin::utf8");
    protected String charset = GlobalProperties.getIntlString("UTF-8");

    public static final int PHONETIC_SPACE_MODE = 0;

    public PointLattice()
    {
        edges = new BhaashikEdges();
    }

    /**
     * @return the grid
     */
    public BhaashikTableModel getGrid()
    {
        return grid;
    }

    /**
     * @param grid the grid to set
     */
    public void setGrid(BhaashikTableModel grid)
    {
        this.grid = grid;
    }

    /**
     * @return the edges
     */
    public BhaashikEdges getEdges()
    {
        return edges;
    }

    /**
     * @param edges the edges to set
     */
    public void setEdges(BhaashikEdges edges)
    {
        this.edges = edges;
    }

    /**
     * @return the langEnc
     */
    public String getLangEnc()
    {
        return langEnc;
    }

    /**
     * @param langEnc the langEnc to set
     */
    public void setLangEnc(String langEnc)
    {
        this.langEnc = langEnc;
    }

    /**
     * @return the charset
     */
    public String getCharset()
    {
        return charset;
    }

    /**
     * @param charset the charset to set
     */
    public void setCharset(String charset)
    {
        this.charset = charset;
    }

    public void printGrid(PrintStream ps)
    {
        grid.print(ps);
    }

    @SuppressWarnings("empty-statement")
    public void makeLattice(String string, int mode)
    {
        if(mode == PHONETIC_SPACE_MODE)
        {
            try {
                PhoneticModelOfScripts cpms = new PhoneticModelOfScripts(GlobalProperties.resolveRelativePath("props/spell-checker/spell-checker-propman.txt"), charset, langEnc);

                PropertyTokens featureList = cpms.getFeatureList();

                featureList.print(System.out);

                int count = featureList.countTokens();

//                System.out.println("Total number of feature: " + count);

                Hashtable featureIndices = new Hashtable(count);

                for (int i = 0; i < count; i++)
                {
                    featureIndices.put(featureList.getToken(i), Integer.valueOf(i));
                }

                Vector tableHeader = new Vector(count + 1);
                
                tableHeader.add(GlobalProperties.getIntlString("Features"));

                count = string.length();

                for (int i = 0; i < count; i++)
                {
                    tableHeader.add(string.charAt(i) + "");
                }

                count = featureList.countTokens();

                grid = new BhaashikTableModel(tableHeader.toArray(new String[tableHeader.size()]), count);

                for (int i = 0; i < count; i++)
                {
                    grid.setValueAt(featureList.getToken(i), i, 0);
                }

                count = string.length();

                FeatureStructure prevFS = null;

                for (int i = 0; i < count; i++)
                {
                    char c = string.charAt(i);

                    FeatureStructure fs = cpms.getFeatureStructure(c, langEnc);

                    int fcount = fs.countAttributes();

                    for (int j = 0; j < fcount; j++)
                    {
                        FeatureAttribute fa = fs.getAttribute(j);
                        int ind = ((Integer) featureIndices.get(fa.getName())).intValue();
                        
                        BhaashikMutableTreeNode node = new BhaashikMutableTreeNode(fa.getNestedAltValue(0).getMultiValue());

//                        System.out.println(fa.getName() + "=" + fa.getMultiValue(0).getValue());
                        grid.setValueAt(fa.getNestedAltValue(0).getMultiValue(), ind, i + 1);

                        if(prevFS != null)
                        {
                            int pcount = prevFS.countAttributes();

                            for (int k = 0; k < pcount; k++)
                            {
                                FeatureAttribute pfa = prevFS.getAttribute(k);
                                int indPrev = ((Integer) featureIndices.get(pfa.getName())).intValue();

                                BhaashikMutableTreeNode prevNode = new BhaashikMutableTreeNode(pfa.getNestedAltValue(0).getMultiValue());

                                BhaashikEdge edge = new BhaashikEdge(prevNode, indPrev, i, node, ind, i + 1);

                                edges.addEdge(edge);
                            }
                        }
                    }

                    prevFS = fs;
                }

                pruneLattice();

                count = grid.getRowCount();

                Hashtable prunedFeatureIndices = new Hashtable(count);
                Hashtable reverseFeatureIndices = (Hashtable) UtilityFunctions.getReverseMap(featureIndices);

                for (int i = 0; i < count; i++)
                {
                    Object feature = grid.getValueAt(i, 0);
                    prunedFeatureIndices.put(feature, Integer.valueOf(i));
                }

                count = edges.countEdges();

                for (int i = 0; i < count; i++)
                {
                    BhaashikEdge edge = edges.getEdge(i);

                    int cells[] = edge.getCells();

                    int newCells[] = new int[4];

                    int oldRow1 = cells[0];
                    int oldRow2 = cells[2];

                    int newRow1 = ((Integer) prunedFeatureIndices.get(reverseFeatureIndices.get(Integer.valueOf(oldRow1)))).intValue();
                    int newRow2 = ((Integer) prunedFeatureIndices.get(reverseFeatureIndices.get(Integer.valueOf(oldRow2)))).intValue();

                    newCells = new int[] {newRow1, cells[1], newRow2, cells[3]};

                    edge.setCells(newCells);
                }

//                grid.print(System.out);
                
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void pruneLattice()
    {
        int rcount = grid.getRowCount();

        for (int i = 0; i < rcount; i++)
        {
            if(grid.isRowEmpty(i, true))
            {
                grid.removeRow(i--);
                rcount--;
            }
        }
    }

    public static void main(String[] args)
    {
        PointLattice pointLattice = new PointLattice();

        pointLattice.makeLattice("गहरा", PHONETIC_SPACE_MODE);
    }
}
