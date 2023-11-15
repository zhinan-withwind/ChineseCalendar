package run.zhinan.time.lunar;

import run.zhinan.time.base.BaseYear;
import run.zhinan.time.format.CHINESE_NUMBER;
import run.zhinan.time.ganzhi.GanZhi;
import run.zhinan.time.ganzhi.GanZhiYear;
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
    GanZhi    ganZhiYear;
    LunarTerm springTerm;

    int     monthNum   = 0;
    int     dayNum     = 0;
    int     leapMonth  = 0;

    List<LunarMonth> months;
    Integer   data;

    private LunarYear(int value) {
        super(value);
    }

    public static LunarYear of(int year) {
        LunarYear lunarYear = new LunarYear(year);

        LocalDateTime lastWinterDay = SolarTerm.Z11_DONGZHI.of(year - 1).getDateTime();
        LocalDateTime thisWinterDay = SolarTerm.Z11_DONGZHI.of(year).getDateTime();

        LunarTerm lastNovember = LunarTerm.lastNewMoon(lastWinterDay);
        LunarTerm thisNovember = LunarTerm.lastNewMoon(thisWinterDay);

        LunarMonth lastDecember = LunarMonth.of(year - 1, 12, 12,lastNovember.roll(1));
        LunarMonth thisDecember = LunarMonth.of(year, 12, 12, thisNovember.roll(1));

        boolean isLastDecemberLeap = lastDecember.isLeap();
        boolean isThisDecemberLeap = thisDecember.isLeap();

        lunarYear.springTerm = lastNovember.roll(isLastDecemberLeap ? 3 : 2);
        LunarTerm nextSpring = thisNovember.roll(isThisDecemberLeap ? 3 : 2);

        lunarYear.dayNum = calculateDayNum(lunarYear.springTerm.getDate(), nextSpring.getDate());
        lunarYear.monthNum = lunarYear.dayNum > 360 ? 13 : 12;

        boolean isOriginalLeap = calculateDayNum(lastNovember.getDate(), thisNovember.getDate()) > 360;

        lunarYear.months = new ArrayList<>();
        for (int i = 0; i < lunarYear.monthNum; i++) {
            LunarTerm lunarTerm = lunarYear.springTerm.roll(i);
            LocalDateTime startTime = lunarTerm.getDate().atTime(0, 0);
            LocalDateTime endTime   = lunarTerm.roll(1).getDate().atTime(0, 0).minusSeconds(1);
            boolean isLeap;
            if (i == 11) {
                isLeap = isThisDecemberLeap;
            } else {
                isLeap = isOriginalLeap && LunarMonth.isLeap(year, startTime, endTime);
            }
            int value = i + 1;
            if (lunarYear.leapMonth == 0 && isLeap) lunarYear.leapMonth = value--;
            if (lunarYear.leapMonth >  0 && value > lunarYear.leapMonth)  value--;
            LunarMonth month = new LunarMonth(year, value, i + 1, startTime, endTime ,isLeap);
            lunarYear.months.add(month);
        }

        return lunarYear;
    }

    private static int calculateDayNum(LocalDate startDate, LocalDate endDate) {
        double days = SolarDate.of(endDate).toJulianDate() - SolarDate.of(startDate).toJulianDate();
        return (int) days;
    }

    public int getValue() {
        return value;
    }

    public GanZhi getGanZhiYear() {
        if (ganZhiYear == null) this.ganZhiYear = GanZhiYear.of(value);
        return ganZhiYear;
    }

    public LunarTerm getSpringTerm() {
        return springTerm;
    }

    public LocalDate getSpringDay() {
        return getSpringTerm().getDate();
    }

    public int getMonthNum() {
        return monthNum;
    }

    public static boolean isLeap(int year) {
        return LunarYear.of(year).isLeap();
    }

    public int getLeapMonth() {
        return leapMonth;
    }

    public boolean isLeap() {
        return getMonthNum() > 12;
    }

    public List<LunarMonth> getMonths() {
        return months;
    }

    public int getData() {
        if (data == null) {
            this.data = ((getLeapMonth() == 0 ? 0 : getLeapMonth() - 1) & 0xf) << 20;
            List<LunarMonth> months = this.getMonths();
            for (int i = 0; i < months.size(); i++) {
                this.data += (months.get(i).getDayNum() == 30 ? 1 : 0) << (19 - i);
            }
            this.data += (getSpringDay().getMonthValue() << 5) + getSpringDay().getDayOfMonth();
        }
        return data;
    }

    public String getDataString() {
        String dataString = Integer.toHexString(getData()).toUpperCase(Locale.ROOT);
        String prefixZero = "000000";
        return "0x" + (dataString.length() < 6 ? prefixZero.substring(0, 6 - dataString.length()) : "") + dataString;
    }

    @Override
    public String toString() {
        return CHINESE_NUMBER.toNumberString(value) + "年";
    }
}
