package run.zhinan.time.lunar;

import run.zhinan.time.base.BaseDateTime;
import run.zhinan.time.base.DateTimeHolder;
import run.zhinan.time.format.LunarDateTimeParser;
import run.zhinan.time.ganzhi.GanZhiDateTime;
import run.zhinan.time.solar.SolarDateTime;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.Objects;

public class LunarDateTime extends BaseDateTime implements DateTimeHolder, TemporalAccessor {
    int year;
    int month;
    int day;
    int time;
    LunarDate lunarDate;

    LunarDateTime(int year, int month, int day, int time, boolean isLeap) {
        this.year  = year;
        this.month = month;
        this.day   = day;
        this.time  = time;
        this.lunarDate = LunarDate.of(year, month, day, isLeap);
        this.dateTime  = lunarDate.toLocalDate().atTime(time * 2, 0);
    }

    LunarDateTime(LocalDateTime dateTime) {
        super(dateTime);
        this.lunarDate = LunarDate.of(dateTime.toLocalDate());
        this.year  = this.lunarDate.year;
        this.month = this.lunarDate.month;
        this.day   = this.lunarDate.day;
        this.time  = (dateTime.getHour() + 1) / 2 % 12;
    }

    public static LunarDateTime of(LocalDateTime dateTime) {
        return new LunarDateTime(dateTime);
    }

    public static LunarDateTime of(int year, int month, int day, int hour, boolean isLeap) {
        return new LunarDateTime(year, month, day, hour, isLeap);
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
        return dateTime.getHour();
    }

    @Override
    public int getMinute() {
        return dateTime.getMinute();
    }

    @Override
    public boolean isLeap() {
        return lunarDate.getLunarMonth().isLeap();
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
        return lunarDate;
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
