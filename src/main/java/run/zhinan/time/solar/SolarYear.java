package run.zhinan.time.solar;

import run.zhinan.time.base.BaseYear;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SolarYear extends BaseYear {
    public List<SolarMonth> months;
    public final static int monthNum = 12;

    public SolarYear(int year) {
        super(year);
    }

    public static SolarYear of(int year) {
        return new SolarYear(year);
    }

    public int getDayNum() {
        int days = (value == 1582 ? 355 : 365) + (Math.abs(value) % 4 == 0 ? 1 : 0);
        if (value > 1582) {
            days += (value % 100 == 0 ? -1 : 0) + (value % 400 == 0 ? 1 : 0);
        }
        return days;
    }

    public boolean isLeap() {
        return getDayNum() == 366;
    }

    public List<SolarMonth> getMonths() {
        if (months == null) {
            months = new ArrayList<>();
            for (int i = 0; i < monthNum; i++) {
                months.add(new SolarMonth(value, i + 1));
            }
        }
        return months;
    }

    public LocalDate atDay(int days) {
        int dayNum = SolarYear.of(getValue()).getDayNum();
        if (days < 1) {
            return SolarYear.of(getValue() - 1).atDay(days +  SolarYear.of(getValue() - 1).getDayNum());
        } else if (days > dayNum) {
            return SolarYear.of(getValue() + 1).atDay(days - dayNum);
        }

        int month = 0;
        for (SolarMonth m : getMonths()) {
            if (days - m.getDayNum() < 1) {
                break;
            }
            days -= m.getDayNum();
            month++;
        }
        int day = days;

        return LocalDate.of(getValue(), month + 1, day);
    }

    public LocalDateTime atMinute(int minuteOfYear) {
        int year = getValue();
        int dayOfYear = minuteOfYear < 0 ? minuteOfYear / 1441 - 1 : minuteOfYear / 1441;
        int minuteOfDay = minuteOfYear - 1441 * dayOfYear;
        int hour   = minuteOfDay / 60;
        if (hour > 23) {
            dayOfYear++;
            minuteOfDay -= 1440;
            hour = 0;
        }
        int minute = minuteOfDay - 60 * hour;
        return SolarYear.of(year).atDay(dayOfYear).atTime(hour, minute);
    }
}
