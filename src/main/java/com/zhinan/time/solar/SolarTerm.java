package com.zhinan.time.solar;

import com.zhinan.time.base.SolarLunarData;

import java.time.LocalDateTime;
import java.util.List;

public final class SolarTerm {
    public final static SolarTerm J01 = new SolarTerm(1, "立春");
    public final static SolarTerm Z01 = new SolarTerm(2, "雨水");
    public final static SolarTerm J02 = new SolarTerm(3, "惊蛰");
    public final static SolarTerm Z02 = new SolarTerm(4, "春分");
    public final static SolarTerm J03 = new SolarTerm(5, "清明");
    public final static SolarTerm Z03 = new SolarTerm(6, "谷雨");
    public final static SolarTerm J04 = new SolarTerm(7, "立夏");
    public final static SolarTerm Z04 = new SolarTerm(8, "小满");
    public final static SolarTerm J05 = new SolarTerm(9, "芒种");
    public final static SolarTerm Z05 = new SolarTerm(10,"夏至");
    public final static SolarTerm J06 = new SolarTerm(11,"小暑");
    public final static SolarTerm Z06 = new SolarTerm(12,"大暑");
    public final static SolarTerm J07 = new SolarTerm(13,"立秋");
    public final static SolarTerm Z07 = new SolarTerm(14,"处暑");
    public final static SolarTerm J08 = new SolarTerm(15,"白露");
    public final static SolarTerm Z08 = new SolarTerm(16,"秋分");
    public final static SolarTerm J09 = new SolarTerm(17,"寒露");
    public final static SolarTerm Z09 = new SolarTerm(18,"霜降");
    public final static SolarTerm J10 = new SolarTerm(19,"立冬");
    public final static SolarTerm Z10 = new SolarTerm(20,"小雪");
    public final static SolarTerm J11 = new SolarTerm(21,"大雪");
    public final static SolarTerm Z11 = new SolarTerm(22,"冬至");
    public final static SolarTerm J12 = new SolarTerm(23,"小寒");
    public final static SolarTerm Z12 = new SolarTerm(24,"大寒");

    public final static SolarTerm[] values = {
            J01, Z01, J02, Z02, J03, Z03, J04, Z04, J05, Z05, J06, Z06,
            J07, Z07, J08, Z08, J09, Z09, J10, Z10, J11, Z11, J12, Z12,
    };

    int    value;
    String name;

    int    year;
    LocalDateTime dateTime;

    SolarTerm(int value, String name) {
        this.value = value;
        this.name  = name;
    }

    public SolarTerm of(int year) {
        SolarTerm solarTerm = new SolarTerm(this.getValue(), this.getName());
        solarTerm.year = year;
        List<Integer> data = SolarLunarData.getSolarData(year);
        int index = getValue() - 1 + 3;
        if (index < 24) {
            solarTerm.dateTime = Year.of(year).atMinute(data.get(index));
        } else {
            List<Integer> dataOfNextYear = SolarLunarData.getSolarData(year + 1);
            solarTerm.dateTime = Year.of(year + 1).atMinute(dataOfNextYear.get(index - 24));
        }
        return solarTerm;
    }



    public int getValue() { return value; }

    public String getName() { return name; }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}