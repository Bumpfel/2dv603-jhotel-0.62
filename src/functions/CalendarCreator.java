package functions;

import java.util.Calendar;

public class CalendarCreator {

    public boolean isLeapYear(int year) {
        boolean isLY = false;

        if ((year % 4) != 0) {
            isLY = false;
        } else if (((year % 4) == 0) && ((year % 100) == 0) && (year % 1000) == 0) {
            isLY = true;
        } else if ((year % 4) == 0 && ((year % 100) == 0)) {
            isLY = false;
        } else if ((year % 4) == 0) {
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
        } catch (StringIndexOutOfBoundsException sioob) {
            year = Integer.parseInt(("20") + date.substring(6, 8));
        }

        int days = 0;

        for (int i = 2004; i < year; ++i) {
            if (isLeapYear(i)) {
                days = days + 366;
            } else {
                days = days + 365;
            }
        }

        for (int i = 1; i < month; ++i) {
            if (i == 1) {
                days = days + 31;
            } else if (i == 2) {
                if (isLeapYear(year)) {
                    days = days + 29;
                } else {
                    days = days + 28;
                }
            } else if (i == 3) {
                days = days + 31;
            } else if (i == 4) {
                days = days + 30;
            } else if (i == 5) {
                days = days + 31;
            } else if (i == 6) {
                days = days + 30;
            } else if (i == 7) {
                days = days + 31;
            } else if (i == 8) {
                days = days + 31;
            } else if (i == 9) {
                days = days + 30;
            } else if (i == 10) {
                days = days + 31;
            } else if (i == 11) {
                days = days + 30;
            } else if (i == 12) {
                days = days + 31;
            }
        }

        days = days + day;

        return days;
    }

    public boolean correctDate(String date) {
        boolean correctDate = false;
        int day, month, year;

        day = Integer.parseInt(date.substring(0, 2));
        month = Integer.parseInt(date.substring(3, 5));

        try {
            year = Integer.parseInt(date.substring(6, 10));
        } catch (StringIndexOutOfBoundsException sioob) {
            year = Integer.parseInt(("20") + date.substring(6, 8));
        }

        if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10)
                || (month == 12)) {
            if (day > 31) {
                correctDate = false;
            } else {
                correctDate = true;
            }
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 30) {
                correctDate = false;
            } else {
                correctDate = true;
            }
        } else if (month == 2) {
            if (isLeapYear(year)) {
                if (day > 29) {
                    correctDate = false;
                } else {
                    correctDate = true;
                }
            } else {
                if (day > 28) {
                    correctDate = false;
                } else {
                    correctDate = true;
                }
            }
        }
        return correctDate;
    }

    public int[] calcDate() {
        int[] days = new int[2];
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int daysInMonth;
        String f;
        String l;

        // jComboBox.setSelectedItem(Integer.toString(year));

        if ((month == 0) || (month == 2) || (month == 4) || (month == 6) || (month == 7) || (month == 9)
                || (month == 11)) {
            daysInMonth = 31;
        } else if (month == 1) {
            if (isLeapYear(year)) {
                daysInMonth = 29;
            } else {
                daysInMonth = 28;
            }
        } else {
            daysInMonth = 30;
        }

        if (month < 9) {
            f = "01.0" + (month + 1) + "." + year;
            l = daysInMonth + ".0" + (month + 1) + "." + year;
        } else {
            f = "01." + (month + 1) + "." + year;
            l = daysInMonth + "." + (month + 1) + "." + year;
        }

        days[0] = createCal(f);
        days[1] = createCal(l);
        return days;
    }

    public int[] calcDate(int month, int year) {
        int[] days = new int[2];
        Calendar cal = Calendar.getInstance();
        int daysInMonth;
        String f;
        String l;

        if ((month == 0) || (month == 2) || (month == 4) || (month == 6) || (month == 7) || (month == 9)
                || (month == 11)) {
            daysInMonth = 31;
        } else if (month == 1) {
            if (isLeapYear(year)) {
                daysInMonth = 29;
            } else {
                daysInMonth = 28;
            }
        } else {
            daysInMonth = 30;
        }

        if (month < 9) {
            f = "01.0" + (month + 1) + "." + year;
            l = daysInMonth + ".0" + (month + 1) + "." + year;
        } else {
            f = "01." + (month + 1) + "." + year;
            l = daysInMonth + "." + (month + 1) + "." + year;
        }

        days[0] = createCal(f);
        days[1] = createCal(l);
        return days;
    }

}