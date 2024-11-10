package run.zhinan.time.ganzhi;

import com.alibaba.fastjson.JSONObject;
import run.zhinan.time.base.BaseDate;
import run.zhinan.time.solar.SolarTerm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;

public class GanZhiDate extends BaseDate implements TemporalAccessor {
    GanZhi year;
    GanZhi month;
    GanZhi day;

    private GanZhiDate(LocalDate date, GanZhi year, GanZhi month, GanZhi day) {
        super(date);
        this.year  = year;
        this.month = month;
        this.day   = day;
    }

    private static int calculateCurrentYear(LocalDate date) {
        // 第一步：计算年份干支
        // 如果在立春前，则为上一年
        LocalDate springDay = SolarTerm.J01_LICHUN.of(date.getYear()).getDate();
        return date.getYear() - (date.isBefore(springDay) ? 1 : 0);

    }

    public static GanZhiDate of(LocalDate date) {
        int currentYear = calculateCurrentYear(date);
        GanZhi year  = GanZhi.toGanZhi(currentYear);

        // 第二步：计算月份干支
        LocalDateTime toNight = date.atTime(23, 59, 59);
        SolarTerm lastMajorSolarTerm = SolarTerm.getLastMajorSolarTerm(toNight);
        GanZhi month = GanZhi.toGanZhi(currentYear, lastMajorSolarTerm.getDateTime().getMonthValue());

        // 计算日期干支
        GanZhi day   = GanZhi.toGanZhi(date.getYear(), date.getMonthValue(), date.getDayOfMonth());

        GanZhiDate ganZhiDate = new GanZhiDate(date, year, month, day);
        ganZhiDate.setDate(date);

        return ganZhiDate;
    }

    public GanZhiDateTime atTime(LocalTime time) {
        GanZhiDateTime ganZhiDateTime = atTime(time.getHour());
        ganZhiDateTime.setDateTime(toLocalDate().atTime(time));
        return ganZhiDateTime;
    }

    public GanZhiDateTime atTime(int hour) {
        return new GanZhiDateTime(this, hour);
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

    public int getYear() {
        return year.getValue();
    }

    public int getMonth() {
        return month.getValue();
    }

    public int getDay() {
        return day.getValue();
    }

    @Override
    public boolean isSupported(TemporalField field) {
        return toLocalDate().isSupported(field);
    }

    @Override
    public long getLong(TemporalField field) {
        switch ((ChronoField) field) {
            case DAY_OF_MONTH: return getDay();
            case DAY_OF_YEAR: return 0;
            case MONTH_OF_YEAR: return getMonth();
            case YEAR: return getYear();
        }
        return toLocalDate().getLong(field);
    }

    public JSONObject toJSON() {
        return new JSONObject()
                .fluentPut("year",  getGanZhiYear() .toJSON())
                .fluentPut("month", getGanZhiMonth().toJSON())
                .fluentPut("day",   getGanZhiDay()  .toJSON())
                .fluentPut("date",  toLocalDate());
    }
}
