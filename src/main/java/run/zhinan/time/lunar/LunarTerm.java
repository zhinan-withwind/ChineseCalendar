package run.zhinan.time.lunar;

import run.zhinan.time.base.SolarLunarData;
import run.zhinan.time.solar.Year;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LunarTerm implements Cloneable {
    public final static LunarTerm NEW_MOON = new LunarTerm(0, "月朔");
    public final static LunarTerm FIRST_QUARTER = new LunarTerm(1, "上弦");
    public final static LunarTerm FULL_MOON = new LunarTerm(2, "月望");
    public final static LunarTerm LAST_QUARTER = new LunarTerm(3, "下弦");

    public final static LunarTerm[] values = {NEW_MOON, FIRST_QUARTER, FULL_MOON, LAST_QUARTER};

    LunarTerm(int value, String name) {
        this.value = value;
        this.name = name;
    }

    int value;
    String name;

    int year;
    int month;
    LocalDateTime dateTime;

    public static LunarTerm getByValue(int value) {
        return values[value];
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static List<LunarTerm> of(int year) {
        List<LunarTerm> result = new ArrayList<>();
        List<Integer> data = SolarLunarData.getLunarData(year);
        int offset = data.get(0);
        for (int i = 1; i < data.size(); i++) {
            int value = (i + offset - 1) % 4;
            LunarTerm lunarTerm = LunarTerm.getByValue(value).clone();
            int minuteOfYear = data.get(i);
            lunarTerm.dateTime = Year.of(year).atMinute(minuteOfYear);
            result.add(lunarTerm);
        }
        return result;
    }

    public LunarTerm of(int year, int month) {
        LunarTerm lunarTerm = this.clone();
        lunarTerm.year = year;
        lunarTerm.month = month;

        List<Integer> data = SolarLunarData.getLunarData(year);
        int offset = 4 - data.get(0);
        int minuteOfYear = data.get(getValue() + (month - 1) * 4 + 1 + offset);

        lunarTerm.dateTime = Year.of(year).atMinute(minuteOfYear);

        return lunarTerm;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public LunarTerm clone() {
        try {
            LunarTerm clone = (LunarTerm) super.clone();
            clone.value = getValue();
            clone.name  = getName ();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
