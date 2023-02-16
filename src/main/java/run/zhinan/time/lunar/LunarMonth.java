package run.zhinan.time.lunar;

import com.alibaba.fastjson.annotation.JSONField;
import run.zhinan.time.format.MONTH_NAME;
import run.zhinan.time.solar.SolarTerm;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LunarMonth {
    LocalDate startDate;
    LocalDate endDate;

    int year;
    int value;
    int dayNum;
    boolean leap;

    public LunarMonth(int year, int month, LocalDate startDate, LocalDate endDate) {
        this.year       = year;
        this.startDate  = startDate;
        this.endDate    = endDate;
        this.value      = month;
        this.dayNum     = new Long(Duration.between(
                startDate.atTime(0, 0), endDate.atTime(23, 59)).toDays()).intValue() + 1;

        if (LunarYear.isLeap(year)) {
            // 计算是否为闰月，如果一个月不包含中气，则为闰月
            SolarTerm minorTerm = SolarTerm.ofMinor(1).of(year);
            boolean contained = false;
            LocalDateTime startTime = getStartTime();
            LocalDateTime endTime = getEndTime();
            while (!minorTerm.getDateTime().isAfter(endTime)) {
                if (!minorTerm.getDateTime().isBefore(startTime)) {
                    contained = true;
                    break;
                }
                minorTerm = minorTerm.roll(2);
            }
            leap = !contained;
        } else {
            leap = false;
        }
    }

    public static LunarMonth of(int year, int month) {
        LocalDateTime lastWinterDay = SolarTerm.Z11_DONGZHI.of(year - 1).getDateTime();
        LunarTerm     lastNovember  = LunarTerm.lastNewMoon(lastWinterDay);
        LocalDate     startDate = lastNovember.roll(month).getDate();
        LocalDate     endDate   = lastNovember.roll(month + 1).getDate().minusDays(-1);
        return new LunarMonth(year, month, startDate, endDate);
    }

    public boolean contains(LocalDateTime dateTime) {
        return !dateTime.isBefore(getStartTime()) && !dateTime.isAfter(getEndTime());
    }

    public int getYear() {
        return year;
    }

    @JSONField(serialize = false)
    public LunarYear getLunarYear() {
        return LunarYear.of(year);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDateTime getStartTime() {
        return startDate.atTime(0, 0);
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDateTime getEndTime() {
        return endDate.atTime(23, 59, 59, 999999999);
    }

    public int getValue() {
        return value;
    }

    public int getDayNum() {
        return dayNum;
    }

    public boolean isLeap() {
        return leap;
    }

    public String getName() {
        String name = "";
        int m = value;
        LunarYear lunarYear = getLunarYear();
        if (lunarYear.isLeap()) {
            if (m == lunarYear.getLeapMonth()) {
                name += "闰";
            }
            m = m < lunarYear.getLeapMonth() ? m : m - 1;
        }
        name += MONTH_NAME.getNameByValue(m) + "月";
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
