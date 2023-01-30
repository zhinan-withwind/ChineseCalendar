package com.zhinan.time.base;

public class Date {
    int year;
    int month;
    int day;

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public static Date of(int year, int month, int day) {
        return new Date(year, month, day);
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public double toJulianDate() {
        int y = getYear(), m = getMonth(), d = getDay();

        if (m <= 2) {
            m +=12; y--;
        }

        double b;
        if (10000 * y + 100 * m + d <= 15821004) {
            // Julian calendar
            b = -2 + Math.floor((y + 4716.0) / 4) - 1179;
        } else {
            // Gregorian calendar
            b = Math.floor(y / 400.0) - Math.floor(y / 100.0) + Math.floor(y / 4.0);
        }
        return 365*y - 679004 + b + Math.floor(30.6001*(m + 1)) + d + 2400000.5;
    }
}
