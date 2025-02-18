/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.tree;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author anil
 */
public class BhaashikEdges implements Serializable {

    protected Vector<BhaashikEdge> edges;

    public BhaashikEdges()
    {
        edges = new Vector<BhaashikEdge>(0, 100);
    }

    public int countEdges()
    {
        if(edges == null)
            return 0;

        return edges.size();
    }

    public BhaashikEdge getEdge(int n)
    {
        if(edges == null)
            return null;

        return edges.get(n);
    }

    public int addEdge(BhaashikEdge e)
    {
        if(edges == null)
            return -1;

        edges.add(e);

        return edges.size();
    }

    public int removeEdge(BhaashikEdge e)
    {
        if(edges == null)
            return -1;

        edges.remove(e);

        return edges.size();
    }

    public int removeEdge(int e)
    {
        if(edges == null)
            return -1;

        edges.remove(e);

        return edges.size();
    }

    public int removeEdge(int row1, int col1, int row2, int col2)
    {
        if(edges == null)
            return -1;

        int ecount = edges.size();

        for (int i = 0; i < ecount; i++)
        {
            BhaashikEdge e = getEdge(i);

            int cells[] = e.getCells();

            if(cells != null && cells[0] == row1 && cells[1] == col1
                     && cells[2] == row2 && cells[3] == col2)
            {
                edges.remove(e);
                break;
            }
        }
        
        return edges.size();
    }

    public void removeAllEdges()
    {
        if(edges == null)
            return;

        edges.removeAllElements();
    }

    public int modifyEdge(BhaashikEdge e, BhaashikEdge eNew)
    {
        if(edges == null)
            return -1;

        int ind = edges.indexOf(e);

        if(ind == -1)
            return -1;

        edges.setElementAt(eNew, ind);

        return edges.size();
    }

    public int modifyEdge(int e, BhaashikEdge eNew)
    {
        if(edges == null)
            return -1;

        if(e < 0 || e >= edges.size())
            return -1;

        edges.setElementAt(eNew, e);

        return edges.size();
    }

}
