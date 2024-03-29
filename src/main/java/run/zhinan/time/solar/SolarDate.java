package run.zhinan.time.solar;

import run.zhinan.time.base.BaseDate;
import run.zhinan.time.base.DateHolder;
import run.zhinan.time.format.SolarDateParser;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;

public class SolarDate extends BaseDate implements TemporalAccessor, DateHolder {
    public SolarDate(LocalDate date) {
        super(date);
    }

    public static SolarDate of(LocalDate date) {
        return new SolarDate(date);
    }

    public static SolarDate of(int year, int month, int day) {
        return of(LocalDate.of(year, month, day));
    }

    public int getYear() {
        return toLocalDate().getYear();
    }

    public int getMonth() {
        return toLocalDate().getMonthValue();
    }

    public int getDay() {
        return toLocalDate().getDayOfMonth();
    }

    @Override
    public boolean isLeap() {
        return false;
    }

    public double toJulianDate() {
        int y = getYear();
        int m = getMonth();
        int d = getDay();
        if (m <= 2) {
            m += 12;
            y--;
        }

        double b;
        if (10000 * y + 100 * m + d <= 15821004) {
            // Julian calendar
            b = -2 + Math.floor((y + 4716.0) / 4) - 1179;
        } else {
            // Gregorian calendar
            b = Math.floor(y / 400.0) - Math.floor(y / 100.0) + Math.floor(y / 4.0);
        }
        return 365 * y - 679004 + b + Math.floor(30.6001 * (m + 1)) + d + 2400000.5;
    }

    public SolarDateTime atTime(int hour, int minute) {
        return SolarDateTime.of(toLocalDate().atTime(hour, minute));
    }

    @Override
    public boolean isSupported(TemporalField field) {
        return toLocalDate().isSupported(field);
    }

    @Override
    public long getLong(TemporalField field) {
        return toLocalDate().getLong(field);
    }

    @Override
    public String toString() {
        return SolarDateParser.DEFAULT.format(this);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SolarDate && date.equals(((SolarDate) obj).date);
    }
}
