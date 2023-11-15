package run.zhinan.time.base;

import run.zhinan.time.ganzhi.GanZhiDate;
import run.zhinan.time.lunar.LunarDate;
import run.zhinan.time.solar.SolarDate;

import java.time.LocalDate;

public abstract class BaseDate {
    protected LocalDate date;

    public BaseDate() {}

    public BaseDate(LocalDate date) {
        this.date = date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate  toLocalDate() {
        return date;
    }

    public SolarDate  toSolarDate() {
        return toLocalDate() == null ? null : SolarDate.of(toLocalDate());
    }

    public LunarDate  toLunarDate() {
        return toLocalDate() == null ? null : LunarDate.of(toLocalDate());
    }

    public GanZhiDate toGanZhiDate() {
        return toLocalDate() == null ? null : GanZhiDate.of(toLocalDate());
    }
}
