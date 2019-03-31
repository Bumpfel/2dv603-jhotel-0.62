package backend;

public class CalendarCreator {
    
	public boolean isLeapYear(int year) {
		boolean isLY = false;
		
		if ((year % 4)!=0) {
			isLY = false; 
		}
		else if (((year % 4) == 0) && ((year % 100) == 0) && (year % 1000) == 0) {
			isLY = true;
		}
		else if ((year % 4) == 0 && ((year % 100) == 0)) {
			isLY = false;
		}
		else if ((year % 4) == 0) {
			isLY = true;
		}
		
		return isLY;
	}

    public int createCal(String date) {
		int day, month, year;
		
		day = Integer.parseInt(date.substring(0, 2));
		month = Integer.parseInt(date.substring(3, 5));
		
		try {
			year = Integer.parseInt(date.substring(6, 10));
		}
		catch (StringIndexOutOfBoundsException sioob) {
			year = Integer.parseInt(("20") + date.substring(6, 8));
		}

		int days = 0;

		for (int i=2004; i<year; ++i) {
			if (isLeapYear(i)) {
				days = days + 366;
			}
			else {
				days = days + 365;
			}
		}
		
		for (int i=1; i<month; ++i) {
			if (i==1) {
				days = days + 31;
			}
			else if (i==2) {
				if (isLeapYear(year)) {
					days = days + 29;
				}
				else {
					days = days + 28;
				}
			}
			else if (i==3) {
				days = days + 31;
			}
			else if (i==4) {
				days = days + 30;
			}
			else if (i==5) {
				days = days + 31;
			}
			else if (i==6) {
				days = days + 30;
			}
			else if (i==7) {
				days = days + 31;
			}
			else if (i==8) {
				days = days +  31;
			}
			else if (i==9) {
				days = days + 30;
			}
			else if (i==10) {
				days = days + 31;
			}
			else if (i==11) {
				days = days + 30;
			}
			else if (i==12) {
				days = days + 31;
			}
		}
		
		days = days + day;
		
		return days;
	}
}