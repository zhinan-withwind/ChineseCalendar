package run.zhinan.time.lunar;

import run.zhinan.time.base.BaseDateTime;
import run.zhinan.time.base.DateTimeHolder;
import run.zhinan.time.format.LunarDateTimeParser;
import run.zhinan.time.ganzhi.GanZhiDateTime;
import run.zhinan.time.ganzhi.Zhi;
import run.zhinan.time.solar.SolarDateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Objects;

public class LunarDateTime extends BaseDateTime implements DateTimeHolder, TemporalAccessor {
    int year;
    int month;
    int day;
    int time;

    LunarDateTime(LocalDateTime dateTime, int year, int month, int day, int time) {
        super(dateTime);
        this.year = year;
        this.month = month;
        this.day = day;
        this.time = time;
    }

    LunarDateTime(LocalDateTime dateTime) {
        super(dateTime);
        LunarDate lunarDate = LunarDate.of(dateTime.toLocalDate());
        this.year = lunarDate.year;
        this.month = lunarDate.month;
        this.day = lunarDate.day;
        this.time = dateTime.getHour() / 2;
    }

    public static LunarDateTime of(LocalDateTime dateTime) {
        return new LunarDateTime(dateTime);
    }

    public static LunarDateTime of(int year, int month, int day, int hour) {
        return LunarDate.of(year, month, day).atTime(hour);
    }

    public int getTime() {
        return time;
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getMonth() {
        return month;
    }

    @Override
    public int getDay() {
        return day;
    }

    @Override
    public int getHour() {
        return time * 2;
    }

    @Override
    public int getMinute() {
        return 0;
    }

    @Override
    public boolean isLeap() {
        return LunarYear.isLeap(year);
    }

    @Override
    public LocalDateTime toLocalDateTime() {
        return dateTime;
    }

    @Override
    public SolarDateTime toSolarDateTime() {
        return SolarDateTime.of(dateTime);
    }

    @Override
    public LunarDateTime toLunarDateTime() {
        return this;
    }

    @Override
    public GanZhiDateTime toGanZhiDateTime() {
        return GanZhiDateTime.of(dateTime);
    }

    public LunarDate toLunarDate() {
        return new LunarDate(dateTime.toLocalDate(), year, month, day);
    }

    @Override
    public String toString() {
        return LunarDateTimeParser.DEFAULT.format(this);
    }

    @Override
    public boolean isSupported(TemporalField field) {
        return toLocalDateTime().isSupported(field);
    }

    @Override
    public long getLong(TemporalField field) {
        return toLocalDateTime().get(field);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LunarDateTime)) return false;
        LunarDateTime that = (LunarDateTime) o;
        return getYear() == that.getYear() && getMonth() == that.getMonth()
                && getDay() == that.getDay() && getTime() == that.getTime()
                && getDateTime().toLocalDate().equals(that.getDateTime().toLocalDate())
                && getDateTime().getHour() / 2 == that.getDateTime().getHour() / 2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getYear(), getMonth(), getDay(), getTime());
    }
}
