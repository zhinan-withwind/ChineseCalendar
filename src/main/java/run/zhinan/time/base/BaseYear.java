package run.zhinan.time.base;

import java.time.DateTimeException;
import java.time.temporal.ValueRange;

public class BaseYear {
    private static final ValueRange valueRange = ValueRange.of(-721, 2200);

    protected int value;

    public BaseYear(int value) {
        if (!isValid(value)) {
            throw new DateTimeException("年份超出范围（公元前721年至公园2200年）");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isValid(int year) {
//        return valueRange.isValidValue(year);
        return true;
    }
}
