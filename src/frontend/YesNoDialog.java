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
package frontend;

import java.awt.Frame;
import java.util.ArrayList;

import backend.Language;
import backend.Observable;
import backend.Observer;
import backend.Reservation;

public class YesNoDialog extends Frame implements Observable {

	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	SearchWindow sw;
	ReservationManagement rm;
	String name, firstlabel, thirdlabel, action, firstname;
	String[] guest, oldguest, newguest;
	private javax.swing.JTextArea jTextArea = null;
	String text;
	String[] language;
	private ArrayList<Observer> subscribers = new ArrayList<>();

	public enum Action { DELETE, CLEAR, RESET, UNDO };

	/**
	 * This is the default constructor
	 */
	public YesNoDialog(String[] guest, String text, String action) {
		Language lang = new Language();
		language = lang.getLanguage();
		this.guest = guest;
		this.action = action;
		this.text = text;
		
		initialize();
	}
	
	public YesNoDialog(ReservationManagement rm, String[] guest, String text, String action) {
		Language lang = new Language();
		language = lang.getLanguage();
		this.rm = rm;
		this.guest = guest;
		this.action = action;
		this.text = text;
		
		initialize();
	}
	
	public YesNoDialog(ReservationManagement rm, String[] newguest, String[] oldguest, String text, String action) {
		Language lang = new Language();
		language = lang.getLanguage();
		this.rm = rm;
		this.newguest = newguest;
		this.oldguest = oldguest;
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
						notifySubscribers(Action.DELETE);
						// mw.deleteEntry(guest);
					}
					else if (action.equals("undoEntry")) {
						notifySubscribers(Action.CLEAR);
						// mw.clearFields();
					}
					else if (action.equals("undoAddEntry")) {
						notifySubscribers(Action.RESET);
						// mw.addDataWindowReset(guest);
					}
					else if (action.equals("deleteRes")) {
						Reservation res = new Reservation(rm);
						res.deleteReservation(guest);	
					}
					else if (action.equals("changeRes")) {
						Reservation res = new Reservation(rm);
						res.changeReservation(oldguest, newguest);
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
						notifySubscribers(Action.UNDO);
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

	public void addSubscriber(Observer o) {
		subscribers.add(o);
	}

	private void notifySubscribers(Action action) {
		for(Observer o : subscribers) {
			o.update(this, guest, action);
		}
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="10,10"
