package run.zhinan.time.lunar;

import run.zhinan.time.base.BaseDate;
import run.zhinan.time.base.DateHolder;
import run.zhinan.time.format.LunarDateParser;
import run.zhinan.time.solar.SolarYear;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;

public class LunarDate extends BaseDate implements TemporalAccessor, DateHolder {
    LunarYear  lunarYear;
    LunarMonth lunarMonth;
    int year;
    int month;
    int day;

    private LunarDate(int year, int month, int day, boolean isLeap) {
        this.year  = year;
        this.month = month;
        this.day   = day;
        this.lunarYear  = LunarYear.of(year);
        this.lunarMonth = LunarMonth.of(year, month, isLeap);
        this.setDate(toLocalDate());
    }

    private LunarDate(LocalDate date) {
        this.date = date;
        this.year = date.getYear();
        this.lunarYear = LunarYear.of(year);
        if (date.isBefore(this.lunarYear.getSpringDay())) {
            this.lunarYear = LunarYear.of(--year);
        }
        int days = new Double(Duration.between(lunarYear.getSpringDay().atTime(0, 0),
                date.atTime(0, 0)).toDays()).intValue() + 1;
        for (LunarMonth month : lunarYear.getMonths()) {
            if (days > month.dayNum) {
                days -= month.dayNum;
            } else {
                this.lunarMonth = month;
                this.month = month.getValue();
                break;
            }
        }
        this.day = days;
    }

    public static LunarDate of(LocalDate date) {
        return new LunarDate(date);
    }

    public static LunarDate of(int year, int month, int day, boolean isLeap) {
        return new LunarDate(year, month, day, isLeap);
    }

    public LunarDateTime atTime(int time) {
        return LunarDateTime.of(year, month, day, time, lunarMonth.isLeap());
    }

    public LocalDate toLocalDate() {
        if (this.date == null) {
            LunarYear lunarYear = LunarYear.of(this.year);
            int days = lunarYear.getSpringDay().getDayOfYear() - 1 + getDayOfYear();
            setDate(SolarYear.of(this.year).atDay(days));
        }
        return this.date;
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

    @Override
    public boolean isLeap() {
        return getLunarMonth().isLeap();
    }

    public LunarYear  getLunarYear() {
        return LunarYear.of(year);
    }

    public LunarMonth getLunarMonth() {
        return lunarMonth;
    }

    public int getDayOfYear() {
        int days = 0;
        LunarYear lunarYear = LunarYear.of(this.year);
        for (int i = 0; i < this.lunarMonth.getIndex() - 1; i++) {
            days += lunarYear.getMonths().get(i).dayNum;
        }
        days += this.day;
        return days;
    }

    @Override
    public String toString() {
        return LunarDateParser.DEFAULT.format(this);
    }

    @Override
    public boolean isSupported(TemporalField field) {
        return toLocalDate().isSupported(field);
    }

    @Override
    public long getLong(TemporalField field) {
        if (field instanceof ChronoField) {
            switch ((ChronoField) field) {
                case DAY_OF_MONTH:
                    return day;
                case DAY_OF_YEAR:
                    return getDayOfYear();
                case MONTH_OF_YEAR:
                    return month;
                case YEAR:
                    return year;
            }
        }
        return toLocalDate().getLong(field);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LunarDate)) return false;
        LunarDate lunarDate = (LunarDate) o;
        return date.equals(lunarDate.date);
    }
}
