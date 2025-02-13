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
package main;

import java.awt.Frame;
import java.io.*;
import javax.swing.*;

import functions.Language;
import functions.Options;

import java.util.*;

public class ImportWindow extends Frame {

	private javax.swing.JLabel jLabel = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JCheckBox jCheckBox = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JButton jButton1 = null;
	private javax.swing.JButton jButton2 = null;
	
	String[] language;
	String dbname;
	
	/**
	 * This is the default constructor
	 */
	public ImportWindow() {
		Language lang = new Language();
		language = lang.getLanguage();
		Options options = new Options();
		dbname = options.getFileName();
		
		
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(null);
		this.add(getJLabel(), null);
		this.add(getJTextField(), null);
		this.add(getJCheckBox(), null);
		this.add(getJButton(), null);
		this.add(getJButton1(), null);
		this.add(getJButton2(), null);
		this.setSize(300, 200);
		this.setTitle(language[4]);
		this.addWindowListener(new java.awt.event.WindowAdapter() { 
			public void windowClosing(java.awt.event.WindowEvent e) {    
				dispose();
			}
		});
	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if(jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setBounds(17, 39, 87, 19);
			jLabel.setText(language[42]);
		}
		return jLabel;
	}
	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField() {
		if(jTextField == null) {
			jTextField = new javax.swing.JTextField();
			jTextField.setBounds(17, 57, 266, 17);
		}
		return jTextField;
	}
	/**
	 * This method initializes jCheckBox
	 * 
	 * @return javax.swing.JCheckBox
	 */
	private javax.swing.JCheckBox getJCheckBox() {
		if(jCheckBox == null) {
			jCheckBox = new javax.swing.JCheckBox();
			jCheckBox.setBounds(17, 83, 266, 18);
			//jCheckBox.setText("Bereinigen und neu Sortieren");
			jCheckBox.setBackground(java.awt.SystemColor.window);
			jCheckBox.setEnabled(false);
		}
		return jCheckBox;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if(jButton == null) {
			jButton = new javax.swing.JButton();
			jButton.setBounds(192, 153, 91, 22);
			jButton.setText(language[21]);
			jButton.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					
					/*try {
						RandomAccessFile file = new RandomAccessFile(dbname, "rw");
						RandomAccessFile newfile = new RandomAccessFile(jTextField.getText(), "r");
						file.seek(file.length()-5);
						newfile.seek(0);
						
						
						loop1:
						while (true) {
							String t;
							String new_t = "";
	
							while (!(new_t.equals("EOF"))) {
								new_t = newfile.readUTF();
								file.writeUTF(new_t);
							}
								
							//file.writeUTF("EOF");
							break loop1;
						}
							
						file.close();
						newfile.close();
						dispose();
					}*/

					try {
						ArrayList db_old = new ArrayList();
						ArrayList db_new = new ArrayList();
						String[] currentGuest;
						
						
						FileInputStream fis = new FileInputStream(jTextField.getText());
						ObjectInputStream ois = new ObjectInputStream(fis);
						FileInputStream fos = new FileInputStream(dbname);
						ObjectInputStream oos = new ObjectInputStream(fos);
						
						db_new = (ArrayList) ois.readObject();
						db_old = (ArrayList) oos.readObject();
						ois.close();
						oos.close();
						
						for (int i=0; i<db_new.size(); ++i) {
							currentGuest = (String[]) db_new.get(i);
							db_old.add(currentGuest);
						}
						
						FileOutputStream fos2 = new FileOutputStream(dbname);
						ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
						
						oos2.writeObject(db_old);
						oos2.flush();
						oos2.close();
						
						dispose();
						
					}
					catch (ClassNotFoundException cnf) {
						System.out.println(cnf + " import");
					}
					catch (IOException io1) {
					// nada 	
					}
				}
			});
		}
		return jButton;
	}
	/**
	 * This method initializes jButton1
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton1() {
		if(jButton1 == null) {
			jButton1 = new javax.swing.JButton();
			jButton1.setBounds(145, 123, 138, 22);
			jButton1.setText(language[23]);
			jButton1.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					JFileChooser chooser = new JFileChooser();
					int returnVal = chooser.showOpenDialog(ImportWindow.this);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						jTextField.setText(chooser.getSelectedFile().getPath());
					}
					
				}
			});
		}
		return jButton1;
	}
	
	
	private javax.swing.JButton getJButton2() {
		if(jButton2 == null) {
			jButton2 = new javax.swing.JButton();
			jButton2.setBounds(17, 153, 100, 22);
			jButton2.setText(language[22]);
			jButton2.addActionListener(new java.awt.event.ActionListener() { 
				public void actionPerformed(java.awt.event.ActionEvent e) {    
					dispose();
				}
			});
		}
		return jButton2;
	}
	

	
}
