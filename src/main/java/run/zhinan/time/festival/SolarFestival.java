package run.zhinan.time.festival;

import run.zhinan.time.solar.SolarDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public enum SolarFestival {
    NEW_YEAR_DAY    ("元旦节",  1,  1),
    VALENTINE_DAY   ("情人节",  2, 14),
    GIRL_DAY        ("女生节",  3,  7),
    WOMAN_DAY       ("女神节",  3,  8),
    ARBOR_DAY       ("植树节",  3, 12),
    FOOLS_DAY       ("愚人节",  4,  1),
//    COLD_FOOD_DAY   ("寒食节",  4,  4),
//    TOMB_SWEEP_DAY  ("清明节",  4,  5),
    LABOUR_DAY      ("劳动节",  5,  1),
    YOUTH_DAY       ("青年节",  5,  4),
    CHILDREN_DAY    ("儿童节",  6,  1),
    PARTY_DAY       ("建党节",  7,  1),
    ARMY_DAY        ("建军节",  8,  1),
    TEACHER_DAY     ("教师节",  9, 10),
    NATIONAL_DAY    ("国庆节", 10,  1),
    PROGRAMMER_DAY  ("程序员节",10, 24),
    HALLOWEEN       ("万圣节", 11,  1),
    CHRISTMAS_EVE   ("圣诞夜", 12, 24),
    CHRISTMAS       ("圣诞节", 12, 25);

    final String name;
    final int month;
    final int day;

    SolarFestival(String name, int month, int day) {
        this.name = name;
        this.month = month;
        this.day = day;
    }

    public static List<String> of(LocalDate date) {
        SolarDate solarDate = SolarDate.of(date);

        List<String> festivalNameList = new ArrayList<>();
        for (SolarFestival festival : values()) {
            if (solarDate.getMonth() == festival.month && solarDate.getDay() == festival.day && !solarDate.isLeap()) {
                festivalNameList.add(festival.name);
                break;
            }
        }
        return festivalNameList;
    }

    public LocalDateTime of(int year) {
        return SolarDate.of(year, month, day).toLocalDate().atTime(0, 0);
    }
}
