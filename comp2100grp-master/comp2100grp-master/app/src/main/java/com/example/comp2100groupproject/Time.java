package com.example.comp2100groupproject;

public class Time {
    public int year;
    public int month;
    public int date;
    public int hour;
    public int minute;

    public Time() {
        year = 2020;
        month = 1;
        date = 1;
        hour = 0;
        minute = 0;
    }


    public Time(int hour, int minute) {
        this.year = 0;
        this.month = 0;
        this.minute = 0;
        this.hour = hour;
        this.minute = minute;
    }

    public Time(int y, int month, int d) {
        year = y;
        this.month = month;
        date = d;
    }

    public Time(int y, int month, int d, int h, int m) {
        year = y;
        this.month = month;
        date = d;
        if ( h >= 0 && h <= 23)
            hour = h;
/*        else
            hour = 0;*/
        if ( m >= 0 && m <= 59)
            minute = m;
/*        else
            minute = 0;*/
    }
/*


 */
public String toString()
{
    String s = "";

        if (year == 0 && month == 0 && date == 0) {
            s = s + hour + " : " + minute;
        } else {

            s = s + year;
            if (month < 10)
                s = s + "/" + "0" + month;
            else
                s = s + "/" + month;
            if (date < 10)
                s = s + "/" + "0" + date + "/";
            else
                s = s + "/" + date + "/";

            if (hour < 10)
                s = s + "0" + hour;
            else
                s = s + hour;
            if (minute < 10)
                s = s + ":" + "0" + minute;
            else
                s = s + ":" + minute;
            }
            return s;


}

    public static Time fromString(String s) {
        int year = Integer.parseInt(s.substring(0,4));
        int month = Integer.parseInt(s.substring(5,7));
        int date = Integer.parseInt(s.substring(8,10));
        int hour = Integer.parseInt(s.substring(11,13));
        int minute = Integer.parseInt(s.substring(14,16));
        Time t = new Time(year,month,date,hour,minute);
        return t;
    }

    public static Time fromString_short(String s) {
        int year = Integer.parseInt(s.substring(0,4));
        int month = Integer.parseInt(s.substring(5,7));
        int date = Integer.parseInt(s.substring(8,10));
        Time t = new Time(year,month,date);
        return t;
    }

    public static Boolean earlier_short (Time t1, Time t2) {
        if (t1.year < t2.year)
            return true;
        else if (t1.year > t2.year)
            return false;

        if (t1.month < t2.month)
            return true;
        else if (t1.month > t2.month)
            return false;

        if (t1.date < t2.date)
            return true;
        else if (t1.date > t2.date)
            return false;

        return false;
    }


    public static Boolean earlier (Time t1, Time t2) {
        if (t1.year < t2.year)
            return true;
        else if (t1.year > t2.year)
            return false;

        if (t1.month < t2.month)
            return true;
        else if (t1.month > t2.month)
            return false;

        if (t1.date < t2.date)
            return true;
        else if (t1.date > t2.date)
            return false;

        if (t1.hour < t2.hour)
            return true;
        else if (t1.hour > t2.hour)
            return false;

        if (t1.minute < t2.minute)
            return true;
        else if (t1.minute > t2.minute)
            return false;

        return false;
    }
}
