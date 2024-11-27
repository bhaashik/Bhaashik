/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.table.gui;

import bhaashik.gui.common.BhaashikEvent;

/**
 *
 * @author anil
 */
public class DisplayEvent extends BhaashikEvent {

    protected String filePath;
    protected String charset;

    public static final int DISPLAY_FILE = 0;

    /** Creates a new instance of TreeViewerEvent */
    public DisplayEvent(Object source, int id, String filePath, String charset) {
        super(source, id);

        this.filePath = filePath;
        this.charset = charset;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public String getCharset()
    {
        return charset;
    }
}
