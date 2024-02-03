package run.zhinan.time.base;

import run.zhinan.time.ganzhi.GanZhiDate;
import run.zhinan.time.lunar.LunarDate;
import run.zhinan.time.solar.SolarDate;

import java.time.LocalDate;

public interface DateHolder {
    int getYear();
    int getMonth();
    int getDay();
    boolean isLeap();
    LocalDate  toLocalDate();
    SolarDate  toSolarDate();
    LunarDate  toLunarDate();
    GanZhiDate toGanZhiDate();
}
