package run.zhinan.time.lunar;

import run.zhinan.time.base.SolarLunarData;
import run.zhinan.time.solar.SolarYear;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LunarTerm implements Cloneable {
    public final static LunarTerm NEW_MOON      = new LunarTerm(0, "月朔");
    public final static LunarTerm FIRST_QUARTER = new LunarTerm(1, "上弦");
    public final static LunarTerm FULL_MOON     = new LunarTerm(2, "月望");
    public final static LunarTerm LAST_QUARTER  = new LunarTerm(3, "下弦");

    public final static LunarTerm[] values = {NEW_MOON, FIRST_QUARTER, FULL_MOON, LAST_QUARTER};

    LunarTerm(int value, String name) {
        this.value = value;
        this.name = name;
    }

    int    value;
    String name;

    int year;
    int index;
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
            lunarTerm.dateTime = SolarYear.of(year).atMinute(minuteOfYear);
            result.add(lunarTerm);
        }
        return result;
    }

    public LunarTerm of(int year, int i) {
        LunarTerm lunarTerm = this.clone();
        lunarTerm.year  = year;
        lunarTerm.index = i;

        List<Integer> data = SolarLunarData.getLunarData(year);
        int offset = getValue() - data.get(0) < 0 ? getValue() - data.get(0) + 4 : getValue() - data.get(0);
        int index  =  i * 4 + offset;
        while (index < 0) {
            data = SolarLunarData.getLunarData(--year);
            index = index + data.size() - 1;
            lunarTerm.year  = year;
            lunarTerm.index = index / 4;
        }
        while (index >= data.size() - 1) {
            index = index - (data.size() - 1);
            data = SolarLunarData.getLunarData(++year);
            lunarTerm.year  = year;
            lunarTerm.index = index / 4;
        }
        int minuteOfYear = data.get(index + 1);

        lunarTerm.dateTime = SolarYear.of(year).atMinute(minuteOfYear);

        return lunarTerm;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public LocalDate getDate() {
        return getDateTime().toLocalDate();
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

    public LunarTerm roll(int i) {
        return of(year, index + i);
    }

    public static LunarTerm lastNewMoon(LocalDateTime dateTime) {
        LunarTerm newMoon = LunarTerm.NEW_MOON.of(dateTime.getYear(), dateTime.getMonthValue() - 1);
        while (dateTime.toLocalDate().isBefore(newMoon.getDateTime().toLocalDate())) {
            newMoon = newMoon.roll(-1);
        }
        while (!dateTime.toLocalDate().isBefore(newMoon.roll(1).getDate())) {
            newMoon = newMoon.roll(1);
        }
        return newMoon;
    }


}
