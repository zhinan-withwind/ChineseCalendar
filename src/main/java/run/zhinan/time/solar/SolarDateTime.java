package run.zhinan.time.solar;

import run.zhinan.time.base.BaseDateTime;
import run.zhinan.time.base.DateTimeHolder;
import run.zhinan.time.format.SolarDateTimeParser;
import run.zhinan.time.ganzhi.GanZhiDateTime;
import run.zhinan.time.lunar.LunarDateTime;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;

public class SolarDateTime extends BaseDateTime implements DateTimeHolder, TemporalAccessor {
    SolarYear year;
    SolarMonth month;
    Day   day;
    int   hour;
    int   minute;

    private SolarDateTime(LocalDateTime dateTime) {
        super(dateTime);
        this.dateTime = dateTime;

        this.year   = SolarYear.of(dateTime.getYear());
        this.month  = SolarMonth.of(dateTime.getYear(), dateTime.getMonthValue());
        this.day    = Day  .of(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth());
        this.hour   = dateTime.getHour();
        this.minute = dateTime.getMinute();
    }

    public static SolarDateTime of(LocalDateTime dateTime) {
        return new SolarDateTime(dateTime);
    }

    public static SolarDateTime of(int year, int month, int day, int hour, int minute) {
        return of(LocalDateTime.of(year, month, day, hour, minute));
    }

    @Override
    public int getYear() {
        return dateTime.getYear();
    }

    @Override
    public int getMonth() {
        return dateTime.getMonthValue();
    }

    @Override
    public int getDay() {
        return dateTime.getDayOfMonth();
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
        return SolarYear.of(dateTime.getYear()).isLeap();
    }

    @Override
    public LocalDateTime toLocalDateTime() {
        return dateTime;
    }

    @Override
    public SolarDateTime toSolarDateTime() {
        return this;
    }

    @Override
    public LunarDateTime toLunarDateTime() {
        return LunarDateTime.of(toLocalDateTime());
    }

    @Override
    public GanZhiDateTime toGanZhiDateTime() {
        return GanZhiDateTime.of(toLocalDateTime());
    }

    @Override
    public boolean isSupported(TemporalField field) {
        return true;
    }

    @Override
    public long getLong(TemporalField field) {
        return toLocalDateTime().get(field);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof SolarDateTime && dateTime.equals(((SolarDateTime) obj).dateTime);
    }

    @Override
    public String toString() {
        return SolarDateTimeParser.DEFAULT.format(this);
    }
}
