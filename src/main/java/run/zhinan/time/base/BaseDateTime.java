package run.zhinan.time.base;

import java.time.LocalDateTime;

public class BaseDateTime {
    protected LocalDateTime dateTime;

    public BaseDateTime() {}

    public BaseDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
}
