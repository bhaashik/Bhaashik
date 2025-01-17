/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik;

import bhaashik.datastr.ConcurrentLinkedHashMap;
import bhaashik.gui.shell.commands.CommandEngine;
import java.io.Serializable;

/**
 *
 * @author anil
 */
public class BhaashikInterpreter implements Serializable {
    
    private static final long serialVersionUID = 1L;

    ConcurrentLinkedHashMap<String, CommandEngine> commandEngines;
}
