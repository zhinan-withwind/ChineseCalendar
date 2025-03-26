package run.zhinan.time.base;

import run.zhinan.time.ganzhi.GanZhiDateTime;
import run.zhinan.time.lunar.LunarDateTime;
import run.zhinan.time.solar.SolarDateTime;

import java.time.LocalDateTime;

public interface DateTimeHolder extends DateHolder {
    int getHour();
    int getMinute();
    LocalDateTime  toLocalDateTime();
    SolarDateTime  toSolarDateTime();
    LunarDateTime  toLunarDateTime();
    GanZhiDateTime toGanZhiDateTime();

    static LocalDateTime toMeanSolarTime(LocalDateTime dateTime, Region region) {
        return region != null ? dateTime.plusSeconds((long)((region.getLongitude() - 120.0D) * 240.0D)) : dateTime;
    }

    static LocalDateTime toMeanSolarTime(LocalDateTime dateTime, String regionCode) {
        return toMeanSolarTime(dateTime, Region.getByCode(regionCode));
    }

    static LocalDateTime toApparentSolarTime(LocalDateTime dateTime, Region region) {
        if (region == null) {
            return dateTime;
        } else {
            double N0 = 79.6764D + 0.2422D * (double)(dateTime.getYear() - 1985) - Math.floor(0.25D * (double)(dateTime.getYear() - 1985));
            double sita = 6.283185307179586D * ((double)dateTime.getDayOfYear() - N0) / 365.2422D;
            double t = 0.0028D - 1.9857D * Math.sin(sita) + 9.9059D * Math.sin(2.0D * sita) - 7.0924D * Math.cos(sita) - 0.6882D * Math.cos(2.0D * sita);
            return toMeanSolarTime(dateTime, region).plusMinutes((new Double(Math.floor(t))).longValue()).plusSeconds((new Double((double)Math.floorMod((new Double(t * 100.0D)).intValue(), 100) * 0.6D)).longValue());
        }
    }

    static LocalDateTime toApparentSolarTime(LocalDateTime dateTime, String region) {
        return region == null ? dateTime : toApparentSolarTime(dateTime, Region.getByCode(region));
    }
}
