package run.zhinan.time.base;

import java.time.LocalDateTime;

public class BaseDateTime extends BaseDate {
    protected LocalDateTime dateTime;

    public BaseDateTime() {}

    public BaseDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }

    public LocalDateTime toApparentSolarTime(String region) {
        return DateTimeHolder.toApparentSolarTime(dateTime, region);
    }
}
