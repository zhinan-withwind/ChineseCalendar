package run.zhinan.time.lunar;

import run.zhinan.time.base.BaseYear;
import run.zhinan.time.format.CHINESE_NUMBER;
import run.zhinan.time.ganzhi.GanZhi;
import run.zhinan.time.solar.SolarDate;
import run.zhinan.time.solar.SolarTerm;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 阴历年
 * 从正月初一开始，到下一年的腊月三十为止
 * 对应的阳历年为该阴历年的正月初一所在的阳历年
 */
public class LunarYear extends BaseYear {
    GanZhi  ganZhiYear;

    int     dayNum;
    int     monthNum;
    int     leapMonth  = 0;
    boolean leap;

    List<LunarMonth> months = new ArrayList<>();

    LocalDate springDay;
    Integer   data;

    public LunarYear(int value) {
        super(value);
        this.ganZhiYear = GanZhi.toGanZhi(value);

        LocalDateTime lastWinterDay = SolarTerm.Z11_DONGZHI.of(value - 1).getDateTime();
        LocalDateTime thisWinterDay = SolarTerm.Z11_DONGZHI.of(value).getDateTime();

        LunarTerm lastNovember  = LunarTerm.lastNewMoon(lastWinterDay);
        LunarTerm thisNovember  = LunarTerm.lastNewMoon(thisWinterDay);

        double days = SolarDate.of(thisNovember.getDate()).toJulianDate() -
                      SolarDate.of(lastNovember.getDate()).toJulianDate() ;

        this.dayNum    = new Double(days).intValue();
        this.monthNum  = days > 360 ? 13 : 12;
        this.leap      = this.monthNum > 12;
        this.springDay = lastNovember.roll(2).getDate();

        for (int i = 0; i < this.monthNum; i++) {
            LunarMonth month = new LunarMonth(value, i + 1,
                    lastNovember.roll(i + 2).getDate(), lastNovember.roll(i + 3).getDate().minusDays(1));
            if (month.isLeap()) leapMonth = i + 1;
            this.months.add(month);
        }
    }

    public static LunarYear of(int year) {
        return new LunarYear(year);
    }

    public int getValue() {
        return value;
    }

    public GanZhi getGanZhiYear() {
        return ganZhiYear;
    }

    public int getMonthNum() {
        return monthNum;
    }

    public int getDayNum() {
        return dayNum;
    }

    public static boolean isLeap(int year) {
        LocalDateTime lastWinterDay = SolarTerm.Z11_DONGZHI.of(year - 1).getDateTime();
        LocalDateTime thisWinterDay = SolarTerm.Z11_DONGZHI.of(year    ).getDateTime();

        LunarTerm lastNovember  = LunarTerm.lastNewMoon(lastWinterDay);
        LunarTerm thisNovember  = LunarTerm.lastNewMoon(thisWinterDay);

        double days = SolarDate.of(thisNovember.getDate()).toJulianDate() -
                SolarDate.of(lastNovember.getDate()).toJulianDate() ;
        return days > 360;
    }

    public int getLeapMonth() {
        return leapMonth;
    }

    public boolean isLeap() {
        return leap;
    }

    public List<LunarMonth> getMonths() {
        return months;
    }

    public LocalDate getSpringDay() {
        return springDay;
    }

    public int getData() {
        if (data == null) {
            this.data = ((leapMonth == 0 ? 0 : leapMonth - 1) & 0xf) << 20;
            for (int i = 0; i < months.size(); i++) {
                this.data += (months.get(i).dayNum == 30 ? 1 : 0) << (19 - i);
            }
            this.data += (springDay.getMonthValue() << 5) + springDay.getDayOfMonth();
        }
        return data;
    }

    public String getDataString() {
        return "0x" + Integer.toHexString(data).toUpperCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        return CHINESE_NUMBER.toNumberString(value) + "年";
    }
}
