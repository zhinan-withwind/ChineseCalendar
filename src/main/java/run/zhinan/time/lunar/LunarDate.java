package run.zhinan.time.lunar;

import run.zhinan.time.format.DATE_NAME;
import run.zhinan.time.format.LunarDateParser;
import run.zhinan.time.solar.SolarDate;
import run.zhinan.time.solar.SolarYear;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Objects;

public class LunarDate implements TemporalAccessor {
    LocalDate date;

    int year;
    int month;
    int day;

    LunarDate(LocalDate date, int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.date = date == null ? toLocalDate() : date;
    }

    LunarDate(LocalDate date) {
        this.date = date;
        this.year = date.getYear();
        LunarYear lunarYear = LunarYear.of(year);
        if (date.isBefore(lunarYear.getSpringDay())) {
            lunarYear = LunarYear.of(--year);
        }
        int days = new Double(Duration.between(lunarYear.getSpringDay().atTime(0, 0),
                date.atTime(0, 0)).toDays()).intValue() + 1;
        for (LunarMonth month : lunarYear.getMonths()) {
            if (days > month.dayNum) {
                days -= month.dayNum;
            } else {
                this.month = month.getValue();
                break;
            }
        }
        this.day = days;
    }

    public static LunarDate of(LocalDate date) {
        return new LunarDate(date);
    }

    public static LunarDate of(int year, int month, int day) {
        return new LunarDate(null, year, month, day);
    }

    public LunarDateTime atTime(int time) {
        return new LunarDateTime(date.atTime(time * 2, 0), year, month, day, time);
    }

    public LocalDate toLocalDate() {
        if (this.date == null) {
            LunarYear lunarYear = LunarYear.of(this.year);
            int days = lunarYear.getSpringDay().getDayOfYear() - 1 + getDayOfYear();
            this.date = SolarYear.of(this.year).atDay(days);
        }
        return this.date;
    }

    public SolarDate toSolarDate() {
        return SolarDate.of(toLocalDate());
    }

    public int getYear() {
        return year;
    }

    public LunarYear getLunarYear() {
        return LunarYear.of(year);
    }

    public int getDayOfYear() {
        int days = 0;
        LunarYear lunarYear = LunarYear.of(this.year);
        for (int i = 0; i < this.month - 1; i++) {
            days += lunarYear.getMonths().get(i).dayNum;
        }
        days += this.day;
        return days;
    }

    public int getMonth() {
        return month;
    }

    public LunarMonth getLunarMonth() {
        return LunarMonth.of(year, month);
    }

    public int getDay() {
        return day;
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
        switch ((ChronoField) field) {
            case DAY_OF_MONTH: return day;
            case DAY_OF_YEAR: return getDayOfYear();
            case MONTH_OF_YEAR: return month;
            case YEAR: return year;
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

    @Override
    public int hashCode() {
        return Objects.hash(date, getYear(), getMonth(), getDay());
    }
}
