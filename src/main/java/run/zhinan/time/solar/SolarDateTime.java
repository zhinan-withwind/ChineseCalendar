package run.zhinan.time.solar;

import run.zhinan.time.base.DateTimeHolder;
import run.zhinan.time.ganzhi.GanZhiDateTime;
import run.zhinan.time.lunar.LunarDateTime;

import java.time.LocalDateTime;

public class SolarDateTime implements DateTimeHolder {
    LocalDateTime dateTime;

    public SolarDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static SolarDateTime of(LocalDateTime dateTime) {
        return new SolarDateTime(dateTime);
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
        return Year.of(dateTime.getYear()).isLeap();
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
}
