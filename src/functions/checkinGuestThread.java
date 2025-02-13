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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import functions.Language;
import functions.ObservableThread;
import functions.Action;
import functions.CalendarCreator;

public class checkinGuestThread extends ObservableThread {

	private String[] oldguest, newguest;
	String[] language;

	private CalendarCreator calendarCreator = new CalendarCreator();

	public checkinGuestThread(String[] oldguest, String[] newguest) {
		System.out.println("guestThread started");

		Language lang = new Language();
		language = lang.getLanguage();
		this.oldguest = oldguest;
		this.newguest = newguest;
	}

	public void run() {
		String gst = newguest[0] + " - " + newguest[1] + ", " + newguest[2] + ": " + newguest[3] + "; " + newguest[4]
				+ "; " + newguest[5] + ": " + newguest[6] + "# " + newguest[7];
		changeReservation(calendarCreator.createCal(newguest[3]), calendarCreator.createCal(newguest[4]), gst,
				newguest[5], oldguest);
	}

	public void changeReservation(int arrival, int departure, String name, String room, String[] oldguest) {
		// String toDelete = oldguest[0] + " - " + oldguest[1] + ", " + oldguest[2] + ":
		// " + oldguest[3] + "; " + oldguest[4] + "; " + oldguest[5] + ": " +
		// oldguest[6];
		String roomtoDelete = oldguest[5];
		int firstday = calendarCreator.createCal(oldguest[3]);
		int lastday = calendarCreator.createCal(oldguest[4]);

		ArrayList reservations = new ArrayList();
		String[] availableRooms = new String[168];
		String[] tmp = new String[100000];
		int index = 0;

		// cw.setThreadRunning(language[66]);

		try {
			FileInputStream fis = new FileInputStream("./db/restable.jh");
			ObjectInputStream ois = new ObjectInputStream(fis);

			reservations = (ArrayList) ois.readObject();
			ois.close();

			availableRooms = (String[]) reservations.get(reservations.size() - 1);

			// First, let's delete the old entry
			// Determine room - index
			loop1: for (int i = 0; i < availableRooms.length; ++i) {
				if (availableRooms[i].equals(roomtoDelete)) {
					index = i;
					break loop1;
				}
			}
			tmp = (String[]) reservations.get(index);
			for (int i = firstday; i < lastday; ++i) {
				tmp[i] = null;
			}
			reservations.set(index, tmp);
			/*************************************/

			// Now, let's make the new reservation
			// Determine room - index
			loop1: for (int i = 0; i < availableRooms.length; ++i) {
				if (availableRooms[i].equals(room)) {
					index = i;
					break loop1;
				}
			}

			tmp = (String[]) reservations.get(index);
			for (int i = arrival; i < departure; ++i) {
				tmp[i] = name;
			}
			reservations.set(index, tmp);
			/*************************************/

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

			notifySubscribers(null, null, Action.SET_CHECKIN_LIST);
		} catch (IOException io) {
			System.out.println(io);
		}
		notifySubscribers(null, reservations, Action.THREAD_ENDED);
		// cw.setThreadEnded();
	}

}
