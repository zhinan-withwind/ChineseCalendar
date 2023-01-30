package run.zhinan.time.solar;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Year {
    private final int value;

    public Year(int value) {
        this.value = value;
    }

    public static Year of(int year) {
        return new Year(year);
    }

    public int getValue() {
        return value;
    }

    public int getTotalDays() {
        int days = (value == 1582 ? 355 : 365) + (Math.abs(value) % 4 == 0 ? 1 : 0);
        if (value > 1582) {
            days += (value % 100 == 0 ? -1 : 0) + (value % 400 == 0 ? 1 : 0);
        }
        return days;
    }

    public boolean isLeap() {
        return getTotalDays() == 366;
    }

    public int[] getMonthDays() {
        int leap = isLeap() ? 1 : 0;
        return new int[] {0, 31, 59 + leap, 90 + leap, 120 + leap, 151 + leap, 181 + leap,
                212 + leap, 243 + leap, 273 + leap, 304 + leap, 334 + leap, 365 + leap};
    }

    public int getMonthDays(int month) {
        return getMonthDays()[month];
    }

    public LocalDate atDay(int dayOfYear) {
        int dayNumOfYear = Year.of(getValue()).getTotalDays();
        if (dayOfYear < 1) {
            return Year.of(getValue() - 1).atDay(dayOfYear +  Year.of(getValue() - 1).getTotalDays());
        } else if (dayOfYear > dayNumOfYear) {
            return Year.of(getValue() + 1).atDay(dayOfYear - dayNumOfYear);
        }

        int month = 0;
        for (int num : getMonthDays()) {
            if (dayOfYear - num < 1) {
                break;
            }
            month++;
        }
        int day = dayOfYear - getMonthDays(month - 1);

        return LocalDate.of(getValue(), month, day);
    }

    public LocalDateTime atMinute(int minuteOfYear) {
        int year = getValue();
        int dayOfYear = minuteOfYear < 0 ? minuteOfYear / 1441 - 1 : minuteOfYear / 1441;
        int minuteOfDay = minuteOfYear - 1441 * dayOfYear;
        int hour   = minuteOfDay / 60;
        int minute = minuteOfDay - 60 * hour;
        return Year.of(year).atDay(dayOfYear).atTime(hour, minute);
    }
}
