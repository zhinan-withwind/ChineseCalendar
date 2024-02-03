package run.zhinan.time.festival;

import java.time.LocalDate;
import java.util.ArrayList;
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

}
