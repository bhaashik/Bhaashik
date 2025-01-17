/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.gui.shell.commands;

import java.io.Serializable;

/**
 *
 * @author anil
 */
public interface CommandEngine extends Serializable {

    String executeCommand(String[] args);
}
