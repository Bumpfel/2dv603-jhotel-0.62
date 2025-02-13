/**
 * Copyright 2004, Martin Jungowski
 *
 *	This file is part of JHotel.
 *
 *	JHotel is free software; you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation; either version 2 of the License, or
 *	(at your option) any later version.
 *
 *	JHotel is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with JHotel; if not, write to the Free Software
 *	Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *
**/
package functions;

import java.awt.Frame;
import java.util.ArrayList;

public class YesNoDialog extends ObservableFrame {

	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	String name, firstlabel, thirdlabel, action, firstname;
	String[] guest, oldguest, newguest;
	private javax.swing.JTextArea jTextArea = null;
	String text;
	String[] language;

	/**
	 * This is the default constructor
	 */
	public YesNoDialog(String[] guest, String text, String action) { //TODO shouldnt need guest
		Language lang = new Language();
		language = lang.getLanguage();
		this.guest = guest;
		this.action = action;
		this.text = text;
		
		initialize();
	}
	
	public YesNoDialog(String text, String action) {
		Language lang = new Language();
		language = lang.getLanguage();
		this.action = action;
		this.text = text;
		
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(null);
		this.add(getJButton(), null);
		this.add(getJButton1(), null);
		this.add(getJTextArea(), null);
		this.setBounds(366, 304, 295, 160);
		this.setTitle(language[48]);
		this.setResizable(false);
	}
	/**
	 * This method initializes jButton "OK"
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if(jButton == null) {
			jButton = new javax.swing.JButton();
			jButton.setBounds(158, 118, 116, 26);
			jButton.setText(language[15]);
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (action.equals("deleteEntry")) {
						notifySubscribers(null, null, Action.DELETE_ENTRY);
					}
					else if (action.equals("undoEntry")) {
						notifySubscribers(null, null, Action.CLEAR);
					}
					else if (action.equals("undoAddEntry")) {
						notifySubscribers(null, guest, Action.RESET);
					}
					else if (action.equals("deleteRes")) {
						notifySubscribers(null, null, Action.DELETE_RES);
					}
					else if (action.equals("changeRes")) {
						notifySubscribers(null, null, Action.CHANGE_RES);
					}
					dispose();
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1 "Cancel"
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton1() {
		if(jButton1 == null) {
			jButton1 = new javax.swing.JButton();
			jButton1.setBounds(22, 118, 116, 26);
			jButton1.setText(language[16]);
			jButton1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					if (action.equals("undoEntry")) {
						notifySubscribers(null, null, Action.UNDO);
					}
					dispose();
				}
			});
		}
		return jButton1;
	}
	
	
	/**
	 * This method initializes jTextArea
	 * 
	 * @return javax.swing.JTextArea
	 */
	private javax.swing.JTextArea getJTextArea() {
		if(jTextArea == null) {
			jTextArea = new javax.swing.JTextArea();
			jTextArea.setBounds(23, 28, 250, 84);
			jTextArea.setBackground(java.awt.SystemColor.window);
			jTextArea.setEditable(false);
			jTextArea.setText(text);
			jTextArea.setLineWrap(true);
			jTextArea.setWrapStyleWord(true);
		}
		return jTextArea;
	}

}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"
