package com.zhinan.time.base;

import com.zhinan.time.ganzhi.GanZhiDateTime;
import com.zhinan.time.lunar.LunarDateTime;
import com.zhinan.time.solar.SolarDateTime;

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
