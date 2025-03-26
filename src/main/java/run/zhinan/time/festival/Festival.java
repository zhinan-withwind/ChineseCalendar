package run.zhinan.time.festival;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Festival {

    static List<String> of(LocalDate date) {
        List<String> festivals = new ArrayList<>();
        festivals.addAll(LunarFestival.of(date));
        festivals.addAll(SolarFestival.of(date));
        festivals.addAll(WeekBaseFestival.of(date));
        festivals.addAll(SolarTermFestival.of(date));
        return festivals;
    }

    static void main(String[] args) {
        int year = 2025;
        System.out.println("阳历节日");
        System.out.println("=========================");
        Arrays.stream(SolarFestival.values()).forEach(festival -> System.out.println(festival.name + " - " + festival.of(year)));
        System.out.println("=========================");
        System.out.println("农历节日");
        System.out.println("=========================");
        Arrays.stream(LunarFestival.values()).forEach(festival -> System.out.println(festival.name + " - " + festival.of(year)));
        System.out.println("=========================");
        System.out.println("节气节日");
        System.out.println("=========================");
        Arrays.stream(SolarTermFestival.values()).forEach(festival -> System.out.println(festival.name + " - " + festival.of(year)));
        System.out.println("=========================");
        System.out.println("星期节日");
        System.out.println("=========================");
        Arrays.stream(WeekBaseFestival.values()).forEach(festival -> System.out.println(festival.name + " - " + festival.of(year)));
    }
}
