package run.zhinan.time.ganzhi;

import run.zhinan.time.base.DateTimeHolder;
import run.zhinan.time.lunar.LunarDateTime;
import run.zhinan.time.solar.SolarDate;
import run.zhinan.time.solar.SolarDateTime;
import run.zhinan.time.solar.SolarTerm;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GanZhiDateTime implements DateTimeHolder {
    LocalDateTime dateTime;

    GanZhi year;
    GanZhi month;
    GanZhi day;
    GanZhi time;

    public GanZhiDateTime(GanZhi year, GanZhi month, GanZhi day, GanZhi time) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.time = time;
    }

    public static GanZhiDateTime of(GanZhi year, GanZhi month, GanZhi day, GanZhi time) {
        return new GanZhiDateTime(year, month, day, time);
    }

    public static GanZhiDateTime of(LocalDateTime dateTime) {
        // 第一步：计算年份干支
        // 如果在立春前，则为上一年
        LocalDateTime springDay = SolarTerm.J01_LICHUN.of(dateTime.getYear()).getDateTime();
        int currentYear = dateTime.getYear() - (dateTime.isBefore(springDay) ? 1 : 0);
        GanZhi year  = toGanZhi(currentYear);

        // 第二步：计算月份干支
        GanZhi month = toGanZhi(currentYear, SolarTerm.getLastMajorSolarTerm(dateTime).getDateTime().getMonthValue());

        // 计算日期干支
        GanZhi day   = toGanZhi(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth());

        // 计算时间干支
        GanZhi time  = toGanZhi(day, dateTime.getHour());

        GanZhiDateTime ganZhiDateTime = new GanZhiDateTime(year, month, day, time);
        ganZhiDateTime.dateTime = dateTime;

        return ganZhiDateTime;
    }

    public static GanZhi toGanZhi(GanZhi day, int hour) {
        int dayGanValue = ((day.getGan().getValue() % 5) * 2 + (hour + 1) / 2) % 10 + 1;
        int dayZhiValue = (hour + 1) / 2 + 1;
        return GanZhi.of(dayGanValue, dayZhiValue);
    }

    public static GanZhi toGanZhi(int year) {
        int yearGanValue = (year - 3 - 1) % 10 + 1;
        int yearZhiValue = (year - 3 - 1) % 12 + 1;
        return GanZhi.of(yearGanValue, yearZhiValue);
    }

    public static GanZhi toGanZhi(int year, int month) {
        GanZhi ganZhiYear = toGanZhi(month == 1 ? year - 1 : year);
        int monthZhiValue = month + 1;
        int monthGanValue = (ganZhiYear.getGan().getValue() * 2 + (monthZhiValue - 3 + 12) % 12) % 10 + 1;
        return GanZhi.of(monthGanValue, monthZhiValue);
    }

    public static GanZhi toGanZhi(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        int julianDate = new Double(SolarDate.of(date).toJulianDate()).intValue() + 1;
        return GanZhi.of((julianDate - 1) % 10 + 1, (julianDate + 1) % 12 + 1);
    }

    public static GanZhi toGanZhi(int year, int month, int day, int time) {
        return toGanZhi(toGanZhi(year, month, day), time);
    }

    @Override
    public int getYear() {
        return year.getValue();
    }

    @Override
    public int getMonth() {
        return month.getValue();
    }

    @Override
    public int getDay() {
        return day.getValue();
    }

    @Override
    public int getHour() {
        return time.getValue();
    }

    @Override
    public int getMinute() {
        return 0;
    }

    @Override
    public boolean isLeap() {
        return false;
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
        return LunarDateTime.of(dateTime);
    }

    @Override
    public GanZhiDateTime toGanZhiDateTime() {
        return this;
    }

    @Override
    public String toString() {
        return "" + year + month + day + time;
    }
}
