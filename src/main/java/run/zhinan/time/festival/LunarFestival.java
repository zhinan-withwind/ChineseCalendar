package run.zhinan.time.festival;

import run.zhinan.time.lunar.LunarDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public enum LunarFestival {
    SPRING_DAY       ("春节"  , 1, 1 ),
    LANTERN_DAY      ("元宵节", 1 , 15),
    DRAGON_HEAD_DAY  ("龙抬头", 2 , 2 ),
    DRAGON_BOAT_DAY  ("端午节", 5 , 5 ),
    DOUBLE_SEVEN_DAY ("七夕节", 7 , 7 ),
    JULY_MIDDLE_DAY  ("中元节", 7 , 15),
    MOON_DAY         ("中秋节", 8 , 15),
    DOUBLE_NINE_DAY  ("重阳节", 9 , 9 ),
    WINTER_CLOTH_DAY ("寒衣节", 10, 1 ),
    LAST_EIGHT_DAY   ("腊八节", 12, 8 );

    final String name;
    final int month;
    final int day;

    LunarFestival(String name, int month, int day) {
        this.name = name;
        this.month = month;
        this.day = day;
    }

    public static List<String> of(LocalDate date) {
        LunarDate lunarDate = LunarDate.of(date);

        List<String> festivalNameList = new ArrayList<>();
        for (LunarFestival festival : values()) {
            if (lunarDate.getMonth() == festival.month && lunarDate.getDay() == festival.day && !lunarDate.isLeap()) {
                festivalNameList.add(festival.name);
                break;
            }
        }
        return festivalNameList;
    }

    public LocalDateTime of(int year) {
        return LunarDate.of(year, month, day, false).toLocalDate().atTime(0, 0);
    }
}
