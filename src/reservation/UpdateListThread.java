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

import functions.Action;
import functions.Language;
import functions.Observable;
import functions.Observer;

import java.util.ArrayList;

public class UpdateListThread extends Thread implements Observable {
	// private ReservationManagement rm;
	private String[] language;
	private ArrayList<Observer> subscribers = new ArrayList<>();
	
	public UpdateListThread() {
		Language lang = new Language();
		language = lang.getLanguage();
		// this.rm = rm;
	}

	public void run() {
		notifySubscribers();
		// rm.setThreadRunning(language[66]);  
		// rm.run();
		// rm.setThreadEnded();
	}

	@Override
	public void addSubscriber(Observer o) {
		subscribers.add(o);
	}

	private void notifySubscribers() {
		System.out.println(subscribers.size());
		for(Observer o : subscribers) {
			o.update(null, null, Action.UPDATE_LIST);
		}
	}

}
