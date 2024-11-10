package run.zhinan.time.ganzhi;

import com.alibaba.fastjson.JSONObject;
import run.zhinan.time.solar.SolarDate;

import java.time.LocalDate;

public class GanZhi {
    Gan gan;
    Zhi zhi;

    public GanZhi(Gan gan, Zhi zhi) {
        this.gan = gan;
        this.zhi = zhi;
    }

    public static GanZhi of(int ganValue, int zhiValue) {
        return new GanZhi(Gan.getByValue(ganValue), Zhi.getByValue(zhiValue));
    }

    public static GanZhi getByName(String name) {
        return new GanZhi(Gan.getByName(name.substring(0, 1)), Zhi.getByName(name.substring(1)));
    }

    public static GanZhi getByValue(int value) {
        return of(value % 10 + 1, value % 12 + 1);
    }

    public static GanZhi toGanZhi(GanZhi day, int hour) {
        int dayGanValue = (((day.getGan().getValue()  - 1) % 5) * 2 + (hour + 1) / 2) % 10 + 1;
        int dayZhiValue = (hour + 1) / 2 % 12 + 1;
        return GanZhi.of(dayGanValue, dayZhiValue);
    }

    public static GanZhi toGanZhi(int year) {
        int yearGanValue = (year - 3 - 1) % 10 + 1;
        int yearZhiValue = (year - 3 - 1) % 12 + 1;
        return GanZhi.of(yearGanValue, yearZhiValue);
    }

    public static GanZhi toGanZhi(int year, int month) {
        GanZhi ganZhiYear = toGanZhi(year);
        int monthZhiValue = month % 12 + 1;
        int monthGanValue = (ganZhiYear.getGan().getValue() * 2 + ((monthZhiValue - 3 + 12) % 12)) % 10 + 1;
        return GanZhi.of(monthGanValue, monthZhiValue);
    }

    public static GanZhi toGanZhi(int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        int julianDate = new Double(SolarDate.of(date).toJulianDate()).intValue() + 1;
        return GanZhi.of((julianDate - 1) % 10 + 1, (julianDate + 1) % 12 + 1);
    }

    public static GanZhi toGanZhi(int year, int month, int day, int time) {
        return toGanZhi(toGanZhi(year, month, day), time);
    }

    public Gan getGan() {
        return gan;
    }

    public Zhi getZhi() {
        return zhi;
    }

    public Xun getXun() {
        return Xun.getByValue((6 - ((zhi.getValue() - gan.getValue() + 12) % 12) / 2) % 6);
    }

    public int getValue() {
        return getXun().getValue() * 10 + gan.getValue();
    }

    public String getName() {
        return getGan().getName() + getZhi().getName();
    }

    public GanZhi roll(int i) {
        return getByValue((getValue() + i + 60) % 60);
    }

    @Override
    public String toString() {
        return getName();
    }

    public JSONObject toJSON() {
        return new JSONObject()
                .fluentPut("value", getValue())
                .fluentPut("name", getName())
                .fluentPut("gan", gan.toJSON())
                .fluentPut("zhi", zhi.toJSON());
    }
}
