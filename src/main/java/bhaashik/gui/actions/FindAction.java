/*
 * FindAction.java
 *
 * Created on June 17, 2006, 2:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package bhaashik.gui.actions;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Position;

import bhaashik.GlobalProperties;

/**
 *
 * @author Anil Kumar Singh
 */
public abstract class FindAction extends AbstractAction implements DocumentListener, KeyListener{
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    protected JTextField searchField = new JTextField();
    JPopupMenu popup = new JPopupMenu();
    
    public FindAction(){
	super("Incremental Search"); //NOI18N
	searchPanel.setBackground(UIManager.getColor("ToolTip.background")); //NOI18N
	searchField.setOpaque(false);
	JLabel label = new JLabel(GlobalProperties.getIntlString("Search_for:"));
	label.setFont(new Font("DialogInput", Font.BOLD, 12)); // for readability
	searchPanel.add(label);
	searchPanel.add(searchField);
	searchField.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
	searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
	popup.setBorder(BorderFactory.createLineBorder(Color.black));
	popup.add(searchPanel);
	searchField.setFont(new Font("DialogInput", Font.PLAIN, 12)); // for readability
	
	// when the window containing the "comp" has registered Esc key
	// then on pressing Esc instead of search popup getting closed
	// the event is sent to the window. to overcome this we
	// register an action for Esc.
	searchField.registerKeyboardAction(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		popup.setVisible(false);
	    }
	}, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_FOCUSED);
    }
    
    protected JComponent comp = null;
    protected boolean ignoreCase;
    
    /*-------------------------------------------------[ ActionListener ]---------------------------------------------------*/
    
    public void actionPerformed(ActionEvent ae){
	if(ae.getSource() == searchField)
	    popup.setVisible(false);
	else{
	    comp = (JComponent)ae.getSource();
	    ignoreCase = (ae.getModifiers() & ActionEvent.SHIFT_MASK)!=0;
	    
	    searchField.removeActionListener(this);
	    searchField.removeKeyListener(this);
	    searchField.getDocument().removeDocumentListener(this);
	    initSearch(ae);
	    searchField.addActionListener(this);
	    searchField.addKeyListener(this);
	    searchField.getDocument().addDocumentListener(this);
	    
	    Rectangle rect = comp.getVisibleRect();
	    popup.show(comp, rect.x, rect.y - popup.getPreferredSize().height - 5);
	    searchField.requestFocus();
	}
    }
    
    // can be overridden by subclasses to change initial search text etc.
    protected void initSearch(ActionEvent ae){
	searchField.setText(""); //NOI18N
	searchField.setForeground(Color.black);
    }
    
    private void changed(Position.Bias bias){
	// note: popup.pack() doesn't work for first character insert
	popup.setVisible(false);
	popup.setVisible(true);
	
	searchField.requestFocus();
	searchField.setForeground(changed(comp, searchField.getText(), bias) ? Color.black : Color.red);
    }
    
    // should search for given text and select item and
    // return true if search is successfull
    protected abstract boolean changed(JComponent comp, String text, Position.Bias bias);
    
    /*-------------------------------------------------[ DocumentListener ]---------------------------------------------------*/
    
    public void insertUpdate(DocumentEvent e){
	changed(null);
    }
    
    public void removeUpdate(DocumentEvent e){
	changed(null);
    }
    
    public void changedUpdate(DocumentEvent e){}
    
    /*-------------------------------------------------[ KeyListener ]---------------------------------------------------*/
    
    protected boolean shiftDown = false;
    protected boolean controlDown = false;
    
    public void keyPressed(KeyEvent ke){
	shiftDown = ke.isShiftDown();
	controlDown = ke.isControlDown();
	
	switch(ke.getKeyCode()){
	    case KeyEvent.VK_UP:
		changed(Position.Bias.Backward);
		break;
	    case KeyEvent.VK_DOWN:
		changed(Position.Bias.Forward);
		break;
	}
    }
    
    public void keyTyped(KeyEvent e){}
    public void keyReleased(KeyEvent e){}
    
    /*-------------------------------------------------[ Installation ]---------------------------------------------------*/
    
    public void install(JComponent comp){
	comp.registerKeyboardAction(this, KeyStroke.getKeyStroke('I', KeyEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
	comp.registerKeyboardAction(this, KeyStroke.getKeyStroke('I', KeyEvent.CTRL_MASK|KeyEvent.SHIFT_MASK), JComponent.WHEN_FOCUSED);
    }
}
