/**
 * Copyright 2004, Martin Jungowski
 *
 *  This file is part of JHotel.
 *
 *  JHotel is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  JHotel is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with JHotel; if not, write to the Free Software
 *   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *
 *  */
package album;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Album {
	
	private ArrayList album = new ArrayList();
	private long entries = 0;
	
	public Album() {
	}

	public void init() {
		try {
			FileInputStream fis = new FileInputStream("db/album.jh");
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			album = (ArrayList) ois.readObject();
			ois.close();
			
			entries = album.size();
		}
		catch (IOException io) {
			System.out.println(io);
		}
		catch (ClassNotFoundException cnf) {
			System.out.println(cnf);
		}
		
	}
	
	public int getEntries() {
		return album.size();
	}

	public void saveAlbum(ArrayList newEntries) {
		
		for (int i=0; i<newEntries.size(); ++i) {
			album.add(newEntries.get(i));
		}
		try {
			FileOutputStream fos = new FileOutputStream("db/album.jh");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(album);
			oos.flush();
			oos.close();
		}
		catch (IOException io) {
			System.out.println(io);
		}
		
	}
}
