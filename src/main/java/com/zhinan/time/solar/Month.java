package com.zhinan.time.solar;

public class Month {
    private final Year year;
    private final int value;

    public Month(int value) {
        this.year  = null;
        this.value = value;
    }

    public Month(int year, int value) {
        this.year  = Year.of(year);
        this.value = value;
    }

    public static Month of(int month) {
        return new Month(month);
    }

    public Month at(int year) {
        return new Month(year, getValue());
    }

    public int getValue() {
        return value;
    }

    public Year getYear() {
        return year;
    }
}
