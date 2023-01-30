package run.zhinan.time;

import run.zhinan.time.lunar.LunarTerm;
import run.zhinan.time.solar.SolarTerm;

import java.util.Arrays;

public class ChineseCalendar {
    public static void main(String[] args) {
//        System.out.println("今天是: " + LocalDateTime.now());
        Arrays.asList(SolarTerm.values).forEach(solarTerm -> System.out.println(solarTerm.getName() + ": " + solarTerm.of(2023).getDateTime()));
        System.out.println("------------------------------ ");
//        for (int m = 1; m <= 12; m++) {
//            for (LunarTerm lunarTerm : LunarTerm.values()) {
//                System.out.println(lunarTerm.getName() + ": " + lunarTerm.of(2024, m).getDateTime());
//            }
//        }
        LunarTerm.of(2023).forEach(lunarTerm -> System.out.println(lunarTerm.getName() + ": " + lunarTerm.getDateTime()));
    }
}
