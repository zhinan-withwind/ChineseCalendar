package run.zhinan.time.solar;

import run.zhinan.time.base.SolarLunarData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SolarTerm {
    public final static SolarTerm J01_LICHUN      = new SolarTerm(1, "立春");
    public final static SolarTerm Z01_YUSHUI      = new SolarTerm(2, "雨水");
    public final static SolarTerm J02_JINGZHE     = new SolarTerm(3, "惊蛰");
    public final static SolarTerm Z02_CHUNFEN     = new SolarTerm(4, "春分");
    public final static SolarTerm J03_QINGMING    = new SolarTerm(5, "清明");
    public final static SolarTerm Z03_GUYU        = new SolarTerm(6, "谷雨");
    public final static SolarTerm J04_LIXIA       = new SolarTerm(7, "立夏");
    public final static SolarTerm Z04_XIAOMAN     = new SolarTerm(8, "小满");
    public final static SolarTerm J05_MANGZHONG   = new SolarTerm(9, "芒种");
    public final static SolarTerm Z05_XIAZHI      = new SolarTerm(10,"夏至");
    public final static SolarTerm J06_XIAOSHU     = new SolarTerm(11,"小暑");
    public final static SolarTerm Z06_DASHU       = new SolarTerm(12,"大暑");
    public final static SolarTerm J07_LIQIU       = new SolarTerm(13,"立秋");
    public final static SolarTerm Z07_CHUSHU      = new SolarTerm(14,"处暑");
    public final static SolarTerm J08_BAILU       = new SolarTerm(15,"白露");
    public final static SolarTerm Z08_QIUFEN      = new SolarTerm(16,"秋分");
    public final static SolarTerm J09_HANLU       = new SolarTerm(17,"寒露");
    public final static SolarTerm Z09_SHUANGJIANG = new SolarTerm(18,"霜降");
    public final static SolarTerm J10_LIDONG      = new SolarTerm(19,"立冬");
    public final static SolarTerm Z10_XIAOXUE     = new SolarTerm(20,"小雪");
    public final static SolarTerm J11_DAXUE       = new SolarTerm(21,"大雪");
    public final static SolarTerm Z11_DONGZHI     = new SolarTerm(22,"冬至");
    public final static SolarTerm J12_XIAOHAN     = new SolarTerm(23,"小寒");
    public final static SolarTerm Z12_DAHAN       = new SolarTerm(24,"大寒");

    public final static List<SolarTerm> values = Arrays.asList(
            J01_LICHUN,     Z01_YUSHUI,     J02_JINGZHE,    Z02_CHUNFEN,    J03_QINGMING,   Z03_GUYU,
            J04_LIXIA,      Z04_XIAOMAN,    J05_MANGZHONG,  Z05_XIAZHI,     J06_XIAOSHU,    Z06_DASHU,
            J07_LIQIU,      Z07_CHUSHU,     J08_BAILU,      Z08_QIUFEN,     J09_HANLU,      Z09_SHUANGJIANG,
            J10_LIDONG,     Z10_XIAOXUE,    J11_DAXUE,      Z11_DONGZHI,    J12_XIAOHAN,    Z12_DAHAN);

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

    public static List<SolarTerm> getSolarTerms(int year) {
        List<SolarTerm> result = new ArrayList<>();
        SolarTerm.values.forEach(solarTerm -> result.add(solarTerm.of(year)));
        return result;
    }

    public int getValue() { return value; }

    public String getName() { return name; }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public SolarTerm roll(int i) {
        return values.get((getValue() - 1 + i) % 24);
    }

    private static int getSolarTermYear(LocalDateTime dateTime) {
        int year = dateTime.getYear();
        // 在立春之前，则需要算是上一年
        if (dateTime.isBefore(SolarTerm.J01_LICHUN.of(year).getDateTime())) {
            year -= 1;
        }
        return year;
    }

    public static SolarTerm getLastMajorSolarTerm(LocalDateTime dateTime) {
        int year = getSolarTermYear(dateTime);
        SolarTerm solarTerm = SolarTerm.J01_LICHUN;
        for (int i = 0; i < 12; i++) {
            LocalDateTime currentSolarTermDateTime = solarTerm.of(year).getDateTime();
            LocalDateTime nextSolarTermDateTime    = solarTerm.roll(2).of(i < 11 ? year : year + 1).getDateTime();
            if (!currentSolarTermDateTime.isAfter(dateTime) && nextSolarTermDateTime.isAfter(dateTime)) {
                break;
            }
            solarTerm = solarTerm.roll(2);
            if (i == 11) year++;
        }
        return solarTerm.of(year);
    }

    public static SolarTerm getNextMajorSolarTerm(LocalDateTime dateTime) {
        return getLastMajorSolarTerm(dateTime).roll(2);
    }
}