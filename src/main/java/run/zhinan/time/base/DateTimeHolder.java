package run.zhinan.time.base;

import run.zhinan.time.ganzhi.GanZhiDateTime;
import run.zhinan.time.lunar.LunarDateTime;
import run.zhinan.time.solar.SolarDateTime;

import java.time.LocalDateTime;

public interface DateTimeHolder {
    int getYear();
    int getMonth();
    int getDay();
    int getHour();
    int getMinute();
    boolean isLeap();
    LocalDateTime  toLocalDateTime();
    SolarDateTime  toSolarDateTime();
    LunarDateTime  toLunarDateTime();
    GanZhiDateTime toGanZhiDateTime();
}
