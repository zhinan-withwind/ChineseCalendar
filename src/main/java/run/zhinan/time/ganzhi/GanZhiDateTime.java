package run.zhinan.time.ganzhi;

import java.time.LocalDateTime;

public class GanZhiDateTime {
    GanZhi year;
    GanZhi month;
    GanZhi day;
    GanZhi time;

    public GanZhiDateTime(GanZhi year, GanZhi month, GanZhi day, GanZhi time) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.time = time;
    }

    public static GanZhiDateTime of(GanZhi year, GanZhi month, GanZhi day, GanZhi time) {
        return new GanZhiDateTime(year, month, day, time);
    }

    public static GanZhiDateTime of(LocalDateTime dateTime) {
        return null;
    }
}
