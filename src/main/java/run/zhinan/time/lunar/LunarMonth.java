package run.zhinan.time.lunar;

import com.alibaba.fastjson.annotation.JSONField;
import run.zhinan.time.format.MONTH_NAME;
import run.zhinan.time.solar.SolarTerm;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LunarMonth {
    LocalDateTime startTime;
    LocalDateTime endTime;

    Integer year;
    Integer value;
    Integer index;
    Integer dayNum;
    Boolean leap;

    LunarMonth(int year, int value, int index, LocalDateTime startTime, LocalDateTime endTime, boolean isLeap) {
        this.year      = year;
        this.value     = value;
        this.index     = index;

        this.startTime = startTime;
        this.endTime   = endTime;
        this.dayNum    = new Long(Duration.between(startTime, endTime).toDays()).intValue() + 1;

        this.leap      = isLeap;
    }

    public static LunarMonth of(int year, int value, int index, LunarTerm lunarTerm) {
        LocalDateTime startTime = lunarTerm.getDate().atTime(0, 0);
        LocalDateTime endTime   = lunarTerm.roll(1).getDate().atTime(0, 0).minusSeconds(1);
        return new LunarMonth(year, value, index, startTime, endTime, isLeap(year, startTime, endTime));
    }

    public static LunarMonth of(int year, int index) {
        return LunarYear.of(year).getMonths().get(index - 1);
    }

    public static LunarMonth of(int year, int month, boolean leap) {
        LunarYear lunarYear = LunarYear.of(year);
        int index = month + (leap ? 1 : 0) + (lunarYear.getLeapMonth() > 0 && month >= lunarYear.getLeapMonth() ? 1 : 0);
        return lunarYear.getMonths().get(index - 1);
    }

    public static LunarTerm getLastNovember(int year) {
        LocalDateTime lastWinterDay = SolarTerm.Z11_DONGZHI.of(year - 1).getDateTime();
        return LunarTerm.lastNewMoon(lastWinterDay);
    }

    public static LocalDateTime getStartTime(int year, int index) {
        return getLastNovember(year).roll(index + 1).getDate().atTime(0, 0);
    }

    public static LocalDateTime getEndTime(int year, int index) {
        return getStartTime(year, index + 1).minusSeconds(1);
    }

    public static boolean isLeap(int year, LocalDateTime startTime, LocalDateTime endTime) {
        // 计算是否为闰月，如果一个月不包含中气，则为闰月
        SolarTerm minorTerm = SolarTerm.ofMinor(0).of(year);
        boolean contained = false;
        while (!minorTerm.getDateTime().isAfter(endTime)) {
            if (!minorTerm.getDateTime().isBefore(startTime)) {
                contained = true;
                break;
            }
            minorTerm = minorTerm.roll(2);
        }
        return !contained;
    }

    public boolean isLeap() {
        return leap;
    }

    public boolean contains(LocalDateTime dateTime) {
        return !dateTime.isBefore(getStartTime()) && !dateTime.isAfter(getEndTime());
    }

    public int getYear() {
        return year;
    }

    public int getIndex() {
        return index;
    }

    @JSONField(serialize = false)
    public LunarYear getLunarYear() {
        return LunarYear.of(this.year);
    }

    public LocalDate getStartDate() {
        return startTime.toLocalDate();
    }

    public LocalDateTime getStartTime() {
        return getStartDate().atTime(0, 0);
    }

    public LocalDate getEndDate() {
        return endTime.toLocalDate();
    }

    public LocalDateTime getEndTime() {
        return getEndDate().atTime(23, 59, 59, 999999999);
    }

    public int getValue() {
        return value;
    }

    public int getDayNum() {
        return dayNum;
    }

    public String getName() {
        LunarYear lunarYear = getLunarYear();
        return  (isLeap() ? "闰" : "") + MONTH_NAME.getNameByValue(value) + "月";
    }

    @Override
    public String toString() {
        return getName();
    }
}
