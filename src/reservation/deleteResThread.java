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
import functions.ObservableThread;
import functions.Action;
import functions.CalendarCreator;

public class deleteResThread extends ObservableThread {

	private String[] reservation;
	private String[] language;

	private CalendarCreator calendarCreator = new CalendarCreator();

	public deleteResThread(String[] reservation) {
		Language lang = new Language();
		language = lang.getLanguage();
		this.reservation = reservation;
	}

	public void run() {
		String toDelete = reservation[0] + " - " + reservation[1] + ", " + reservation[2] + ": " + reservation[3] + "; "
				+ reservation[4];
		String roomtoDelete = reservation[5];

		int firstday = calendarCreator.createCal(reservation[3]);
		int lastday = calendarCreator.createCal(reservation[4]);

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

		notifySubscribers(null, reservations, Action.UPDATE_TABLE);

	}
}
