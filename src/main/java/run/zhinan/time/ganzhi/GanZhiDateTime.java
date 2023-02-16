package run.zhinan.time.ganzhi;

import run.zhinan.time.base.BaseDateTime;
import run.zhinan.time.base.DateTimeHolder;
import run.zhinan.time.lunar.LunarDateTime;
import run.zhinan.time.solar.SolarDateTime;
import run.zhinan.time.solar.SolarTerm;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;

public class GanZhiDateTime extends BaseDateTime implements DateTimeHolder, TemporalAccessor {
    GanZhi year;
    GanZhi month;
    GanZhi day;
    GanZhi time;

    private GanZhiDateTime(LocalDateTime dateTime) {
        super(dateTime);
        // 第一步：计算年份干支
        // 如果在立春前，则为上一年
        LocalDateTime springDay = SolarTerm.J01_LICHUN.of(dateTime.getYear()).getDateTime();
        int currentYear = dateTime.getYear() - (dateTime.isBefore(springDay) ? 1 : 0);
        this.year  = GanZhi.toGanZhi(currentYear);

        // 第二步：计算月份干支
        this.month = GanZhi.toGanZhi(currentYear, SolarTerm.getLastMajorSolarTerm(dateTime).getDateTime().getMonthValue());

        // 计算日期干支
        this.day   = GanZhi.toGanZhi(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth());

        // 计算时间干支
        this.time  = GanZhi.toGanZhi(day, dateTime.getHour());
    }

    private GanZhiDateTime(GanZhi year, GanZhi month, GanZhi day, GanZhi time) {
        super(toDateTime(year, month, day, time));
        this.year  = year;
        this.month = month;
        this.day   = day;
        this.time  = time;
    }

    public static GanZhiDateTime of(GanZhi year, GanZhi month, GanZhi day, GanZhi time) {
        return new GanZhiDateTime(year, month, day, time);
    }

    public static GanZhiDateTime of(int year, int month, int day, int time) {
        return of(GanZhi.getByValue(year), GanZhi.getByValue(month), GanZhi.getByValue(day), GanZhi.getByValue(time));
    }

    // 要区分早晚子时，当天的23点-24点，算当天的日子，第二天的子时
    public static GanZhiDateTime of(LocalDateTime dateTime) {
        return new GanZhiDateTime(dateTime);
    }

    // 不区分早晚子时，当天的23点-24点，算转天的日子，第二天的子时
    public static GanZhiDateTime ofNoMidnight(LocalDateTime dateTime) {
        if (dateTime.getHour() == 23) {
            dateTime = dateTime.toLocalDate().plusDays(1L).atTime(0, 0);
        }
        return of(dateTime);
    }


    public static List<LocalDateTime> findByGanZhi(int start, int end, GanZhi year, GanZhi month, GanZhi day, GanZhi time) {
        List<LocalDateTime> result = new ArrayList<>();
        int startYear = start + (year.getValue() - GanZhi.toGanZhi(start).getValue() + 60) % 60;
        for (int y = startYear; y < end; y+=60) {
            int m = (month.getValue() - GanZhi.toGanZhi(y, 1).getValue() + 60) % 60 + 1;
            if (m <= 12) {
                LocalDateTime solarTerm = SolarTerm.ofMajor((m - 2 + 12) % 12).of(y).getDateTime();
                int d = (day.getValue() - GanZhi.toGanZhi(y, m, solarTerm.getDayOfMonth()).getValue() + 60) % 60;
                int duration = (int) Duration.between(solarTerm, SolarTerm.ofMajor(m - 1).of(y).getDateTime()).getSeconds() / 3600 / 24;
                if (d < duration) {
                    LocalDate date = solarTerm.toLocalDate().plusDays(d);
                    int h = (time.getValue() - GanZhi.toGanZhi(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 0).getValue() + 60) % 60;
                    if (h < 12) {
                        result.add(date.atTime(h * 2, 0));
                    }
                }
            }
        }
        return result;
    }

    public static LocalDateTime toDateTime(GanZhi year, GanZhi month, GanZhi day, GanZhi time) {
        LocalDateTime result = null;
        LocalDateTime now = LocalDateTime.now();
        List<LocalDateTime> dateTimeList = findByGanZhi(now.getYear() - 100, now.getYear() + 100, year, month, day, time);
        long duration = 101 * 365 * 24 * 3600L;
        for (LocalDateTime dateTime : dateTimeList) {
            if (dateTime.isAfter(now) && result != null) {
                break;
            } else {
                long d = Math.abs(Duration.between(dateTime, now).getSeconds());
                if (d < duration) {
                    result = dateTime;
                    duration = d;
                }
            }
        }
        return result;
    }

    public GanZhi getGanZhiYear() {
        return year;
    }

    public GanZhi getGanZhiMonth() {
        return month;
    }

    public GanZhi getGanZhiDay() {
        return day;
    }

    public GanZhi getGanZhiTime() {
        return time;
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

    public int getTime() { return time.getValue(); }

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
        return year.toString() + month + day + time;
    }

    @Override
    public boolean isSupported(TemporalField field) {
        return true;
    }

    @Override
    public long getLong(TemporalField field) {
        return toLocalDateTime().get(field);
    }
}
