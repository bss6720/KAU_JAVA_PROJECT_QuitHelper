package sungsu.quithelper;

import java.util.Calendar;

public class Date implements Comparable<Date>{
    private int year, month, day;

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public boolean setDate(int year, int month, int day) {
        Calendar now = Calendar.getInstance();
        if(year>now.get(Calendar.YEAR) || month<1 || month>12 || day>31 || day<1) {
            return false;
        }
        now.set(year, month-1, day);
        if(now.getActualMaximum(Calendar.DATE)<day) {
            return false;
        }
        this.year = year;
        this.month = month;
        this.day = day;
        return true;
    }

    public boolean setDate(String date) {
        if(date.length()>8) {
            return false;
        }
        return setDate(Integer.parseInt(date.substring(0,4)),
                Integer.parseInt(date.substring(4,6)),
                Integer.parseInt(date.substring(6,8)));
    }

    public void setToday() {
        Calendar now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH)+1;
        day = now.get(Calendar.DAY_OF_MONTH);
    }

    public void next() {
        Calendar cal = Calendar.getInstance();
        cal.set(year,month-1,day);
        int maxday = cal.getActualMaximum(cal.DATE);
        day++;
        if(day > maxday) {
            month=month+1;
            if(month>12) {
                year++;
                month = 1;
            }
            day = 1;
        }
    }

    @Override
    public String toString() {
        return String.format("%04d%02d%02d",year,month,day);
    }

    @Override
    public int compareTo(Date date) {
        if(year>date.year) {
            return 1;
        } else if(year<date.year) {
            return -1;
        } else {
            if(month>date.month) {
                return 1;
            } else if(month<date.month) {
                return -1;
            } else {
                if(day>date.day) {
                    return 1;
                } else if(day<date.day) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }
}
