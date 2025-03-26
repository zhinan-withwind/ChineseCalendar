package run.zhinan.time.festival;

import run.zhinan.time.solar.SolarTerm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public enum SolarTermFestival {
    J01_LICHUN      ("立春日", SolarTerm.J01_LICHUN,       0, 0),
    Z01_YUSHUI      ("雨水日", SolarTerm.Z01_YUSHUI,       0, 0),
    J02_JINGZHE     ("惊蛰日", SolarTerm.J02_JINGZHE,      0, 0),
    Z02_CHUNFEN     ("春分日", SolarTerm.Z02_CHUNFEN,      0, 0),
    J03_QINGMING    ("清明节", SolarTerm.J03_QINGMING,     0, 0),
    Z03_GUYU        ("谷雨日", SolarTerm.Z03_GUYU,         0, 0),
    J04_LIXIA       ("立夏日", SolarTerm.J04_LIXIA,        0, 0),
    Z04_XIAOMAN     ("小满日", SolarTerm.Z04_XIAOMAN,      0, 0),
    J05_MANGZHONG   ("芒种日", SolarTerm.J05_MANGZHONG,    0, 0),
    Z05_XIAZHI      ("夏至日", SolarTerm.Z05_XIAZHI,       0, 0),
    J06_XIAOSHU     ("小暑日", SolarTerm.J06_XIAOSHU,      0, 0),
    Z06_DASHU       ("大暑日", SolarTerm.Z06_DASHU,        0, 0),
    J07_LIQIU       ("立秋日", SolarTerm.J07_LIQIU,        0, 0),
    Z07_CHUSHU      ("处暑日", SolarTerm.Z07_CHUSHU,       0, 0),
    J08_BAILU       ("白露日", SolarTerm.J08_BAILU,        0, 0),
    Z08_QIUFEN      ("秋分日", SolarTerm.Z08_QIUFEN,       0, 0),
    J09_HANLU       ("寒露日", SolarTerm.J09_HANLU,        0, 0),
    Z09_SHUANGJIANG ("霜降日", SolarTerm.Z09_SHUANGJIANG,  0, 0),
    J10_LIDONG      ("立冬日", SolarTerm.J10_LIDONG,       0, 0),
    Z10_XIAOXUE     ("小雪日", SolarTerm.Z10_XIAOXUE,      0, 0),
    J11_DAXUE       ("大雪日", SolarTerm.J11_DAXUE,        0, 0),
    Z11_DONGZHI     ("冬至日", SolarTerm.Z11_DONGZHI,      0, 0),
    J12_XIAOHAN     ("小寒日", SolarTerm.J12_XIAOHAN,      0, 0),
    Z12_DAHAN       ("大寒日", SolarTerm.Z12_DAHAN,        0, 0),

    E01_COLD_FOOD   ("寒食节", SolarTerm.J03_QINGMING,     0, -1);

    final String name;
    final SolarTerm solarTerm;
    final int yearOffset;
    final int dayOffset;

    SolarTermFestival(String name, SolarTerm solarTerm, int yearOffset, int offset) {
        this.name = name;
        this.solarTerm  = solarTerm;
        this.yearOffset = yearOffset;
        this.dayOffset  = offset;
    }

    public static List<String> of(LocalDate date) {
        List<String> festivalNameList = new ArrayList<>();
        for (SolarTermFestival festival : values()) {
            if (festival.solarTerm.of(date.getYear() + festival.yearOffset).getDateTime().toLocalDate().plusDays(festival.dayOffset).equals(date)) {
                festivalNameList.add(festival.name);
                break;
            }
        }
        return festivalNameList;
    }

    public LocalDateTime of(int year) {
        return solarTerm.of(year).getDateTime();
    }
}
