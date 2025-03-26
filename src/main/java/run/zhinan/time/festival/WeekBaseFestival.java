package run.zhinan.time.festival;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public enum WeekBaseFestival {
    MOTHER_DAY        ("母亲节",  5, 2, 7),
    FATHER_DAY        ("父亲节",  6, 3, 7),
    THANKS_GIVING_DAY ("感恩节", 11, 4, 4);

    final String name;
    final int month;
    final int week;
    final int offset;

    WeekBaseFestival(String name, int month, int week, int offset) {
        this.name = name;
        this.month = month;
        this.week = week;
        this.offset = offset;
    }

    public static List<String> of(LocalDate date) {
        List<String> festivalNameList = new ArrayList<>();
        for (WeekBaseFestival festival : values()) {
            if (date.getMonthValue() == festival.month && date.getDayOfWeek().getValue() == festival.offset
                    && date.getDayOfMonth() > (festival.week - 1) * 7 && date.getDayOfMonth() <= festival.week * 7) {
                festivalNameList.add(festival.name);
                break;
            }
        }
        return festivalNameList;
    }

    public LocalDateTime of(int year) {
        return LocalDate.of(year, month, (week - 1) * 7 + offset).atTime(0, 0);
    }
}
