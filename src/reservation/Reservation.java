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
package reservation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import functions.Language;
import functions.Observable;
import functions.Observer;
import functions.Action;
import functions.CalendarCreator;
import guest.Guest;

public class Reservation extends Thread implements Observer, Observable {
	private int arrival;
	private int departure;
	private String name;
	private String room;
	private String price;
	private String[] language;
	private String[] oldguest;
	private String[] newguest;
	Guest guest = new Guest();
	private String[] emptyGuest = new String[guest.getEntries()];
	private String arrivalS, departureS;

	private CalendarCreator calendarCreator = new CalendarCreator();
	private ArrayList<Observer> subscribers = new ArrayList<>();

	public Reservation() {
		Language lang = new Language();
		language = lang.getLanguage();
	}


	public Reservation(String arr, String dep, String name, String room, String price) {
		this.arrival = calendarCreator.createCal(arr);
		this.departure = calendarCreator.createCal(dep);
		this.name = name;
		this.room = room;
		this.arrivalS = arr;
		this.departureS = dep;
		if (price.length() < 3) {
			this.price = price + ",00";
		} else {
			this.price = price;
		}

		Language lang = new Language();
		language = lang.getLanguage();
	}

	public void run() {
		// rm.setThreadRunning(language[66]);
		makeReservation(arrival, departure, name, room);
		// rm.run();
		// rm.setThreadEnded();
	}

	// public void deleteReservation(String[] guest) {
	// 	notifySubscribers(Action.DELETE_RES);		
	// 	deleteResThread drt = new deleteResThread(guest);
	// 	drt.addSubscriber(this);
	// 	drt.start();
	// }
	
	// public void changeReservation(String[] oldguest, String[] newguest) {
	// 	changeResThread crt = new changeResThread(oldguest, newguest);
	// 	crt.addSubscriber(this);
	// 	crt.start();
	// }

	// public void checkinGuest(String[] oldguest, String[] newguest) {
	// checkinGuestThread cgt = new checkinGuestThread(cw, oldguest, newguest);
	// cgt.start();
	// }

	public void makeReservation(int arrival, int departure, String name, String room) {
		ArrayList reservations = new ArrayList();
		String[] availableRooms = new String[168];
		String[] tmp = new String[100000];
		int index = 0;

		try {
			FileInputStream fis = new FileInputStream("./db/restable.jh");
			ObjectInputStream ois = new ObjectInputStream(fis);

			reservations = (ArrayList) ois.readObject();
			ois.close();

			availableRooms = (String[]) reservations.get(reservations.size() - 1);

			// Determine room - index
			loop1: for (int i = 0; i < availableRooms.length; ++i) {
				if (availableRooms[i].equals(room)) {
					index = i;
					break loop1;
				}
			}

			tmp = (String[]) reservations.get(index);
			for (int i = arrival; i < departure; ++i) {
				tmp[i] = name + ": " + arrivalS + "; " + departureS + "; " + room + ": " + false + "# " + price;
			}
			reservations.set(index, tmp);
		} catch (ClassNotFoundException cnf) {
			System.out.println(cnf + " Reservation.makeReservation()");
		} catch (IOException io) {
			System.out.println(io + " Reservation.makeReservation()");
		}

		try {
			FileOutputStream fos = new FileOutputStream("./db/restable.jh");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(reservations);
			oos.flush();
			oos.close();
		} catch (IOException io) {
			System.out.println(io);
		}
	}

	public void createResTable() {
		ArrayList al1, al2, al3, al4, al5;
		String[] f1, f2, f3, f4, f5, f6, f7, f8;
		ArrayList reservations = new ArrayList();
		String[] tmp;
		String[] availableRooms = new String[168];
		/*
		 * for (int i=0; i<availableRooms.length; ++i) { availableRooms[i]=""; }
		 */
		int j = 0;

		try {
			FileInputStream sngl = new FileInputStream("./cfg/single.rms");
			FileInputStream dblr = new FileInputStream("./cfg/double.rms");
			FileInputStream trpl = new FileInputStream("./cfg/triple.rms");
			FileInputStream qd = new FileInputStream("./cfg/quad.rms");
			FileInputStream app = new FileInputStream("./cfg/apartment.rms");

			ObjectInputStream single = new ObjectInputStream(sngl);
			ObjectInputStream dbl = new ObjectInputStream(dblr);
			ObjectInputStream triple = new ObjectInputStream(trpl);
			ObjectInputStream quad = new ObjectInputStream(qd);
			ObjectInputStream apartment = new ObjectInputStream(app);

			al1 = (ArrayList) single.readObject(); // Alle Einzelzimmer �ber alle Stockwerke als String[]
			al2 = (ArrayList) dbl.readObject(); // Alle Doppelzimmer �ber alle Stockwerke als String[]
			al3 = (ArrayList) triple.readObject(); // Alle Drei-Bett Zimmer �ber alle Stockwerke als String[]
			al4 = (ArrayList) quad.readObject(); // Alle Vier-Bett Zimmer �ber alle Stockwerke als String[]
			al5 = (ArrayList) apartment.readObject(); // Alle Apartments �ber alle Stockwerke als String[]

			single.close();
			dbl.close();
			triple.close();
			quad.close();
			apartment.close();

			// Einzelzimmer �ber alle Stockwerke
			f1 = (String[]) al1.get(0);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al1.get(1);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al1.get(2);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al1.get(3);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al1.get(4);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al1.get(5);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al1.get(6);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al1.get(7);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}

			// Doppelzimmer �ber alle Stockwerke
			f1 = (String[]) al2.get(0);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al2.get(1);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al2.get(2);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al2.get(3);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al2.get(4);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al2.get(5);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al2.get(6);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al2.get(7);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}

			// Drei-Bett Zimmer �ber alle Stockwerke
			f1 = (String[]) al3.get(0);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al3.get(1);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al3.get(2);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al3.get(3);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al3.get(4);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al3.get(5);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al3.get(6);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al3.get(7);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}

			// Vier-Bett Zimmer �ber alle Stockwerke
			f1 = (String[]) al4.get(0);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al4.get(1);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al4.get(2);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al4.get(3);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al4.get(4);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al4.get(5);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al4.get(6);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al4.get(7);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}

			// Apartments �ber alle Stockwerke
			f1 = (String[]) al5.get(0);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al5.get(1);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al5.get(2);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al5.get(3);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al5.get(4);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al5.get(5);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al5.get(6);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			f1 = (String[]) al5.get(7);
			for (int i = 0; i < f1.length; ++i) {
				availableRooms[j] = f1[i];
				++j;
				reservations.add(new String[100000]);
			}
			reservations.add(availableRooms);
		} catch (ClassNotFoundException cnf) {
			System.out.println(cnf);
		} catch (IOException io) {
			System.out.println(io);
		}

		try {
			FileOutputStream fos = new FileOutputStream("./db/restable.jh");
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(reservations);
			oos.flush();
			oos.close();
		} catch (IOException io) {
			System.out.println(io);
		}
	}

	public boolean checkAvailability(int arrival, int departure, String room) {
		boolean available = true;
		ArrayList reservations = new ArrayList();
		String[] availableRooms = new String[168];
		String[] tmp;
		int index = 0;

		try {
			FileInputStream fis = new FileInputStream("./db/restable.jh");
			ObjectInputStream ois = new ObjectInputStream(fis);

			reservations = (ArrayList) ois.readObject();
			ois.close();

			availableRooms = (String[]) reservations.get(reservations.size() - 1);

			loop1: for (int i = 0; i < availableRooms.length; ++i) {
				if (availableRooms[i].equals(room)) {
					index = i;
					break loop1;
				}
			}

			tmp = (String[]) reservations.get(index);
			loop1: for (int i = arrival; i < departure; ++i) {
				if (tmp[i] != null) {
					available = false;
					break loop1;
				}
			}

		} catch (ClassNotFoundException cnf) {
			System.out.println(cnf + " Reservation.makeReservation()");
		} catch (IOException io) {
			System.out.println(io + " Reservation.makeReservation()");
		}

		return available;
	}

	@Override
	public void addSubscriber(Observer o) {
		subscribers.add(o);
	}

	private void notifySubscribers(Action action) {
		for(Observer o : subscribers) {
			o.update(null, null, action);
		}
	}

	@Override
	public void update(Observer obs, Object args, Action action) {
		if(action == Action.DELETE_RES) {

		}
		else if(action == Action.CHANGE_RES) {

		}
	}
	
	/*public static void main(String[] args) {
		MainWindow mw = new MainWindow();
		mw.setVisible(false);
		ReservationManagement rm = new ReservationManagement(mw);
		rm.setVisible(false);
		Reservation res = new Reservation(rm);
		
		//res.makeReservation(9, 11, "Chris Olbertz", "10");
		//res.checkAvailability(10, 11, "10");
		res.createResTable();
		System.exit(0);
	}*/




}
