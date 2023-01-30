package com.zhinan.time.solar;

public class Day {
    private final Year  year;
    private final Month month;
    private final int value;

    public Day(int year, int month, int value) {
        this.year  = Year.of(year);
        this.month = Month.of(month).at(year);
        this.value = value;
    }

    public static Day of(int year, int month, int day) {
        return new Day(year, month, day);
    }
}
