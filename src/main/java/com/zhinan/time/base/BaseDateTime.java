package com.zhinan.time.base;

public class BaseDateTime {
    Date date;
    Time time;

    public BaseDateTime(int year, int month, int day, int hour, int minute, int second) {
        this.date = new Date(year, month, day);
        this.time = new Time(hour, minute, second);
    }

    public int getYear() {
        return date.year;
    }

    public int getMonth() {
        return date.month;
    }

    public int getDay() {
        return date.day;
    }

    public int getHour() {
        return time.hour;
    }

    public int getMinute() {
        return time.minute;
    }

    public int getSecond() {
        return time.second;
    }
}
