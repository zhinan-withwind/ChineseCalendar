package run.zhinan.time.format;

public enum DATE_TYPE {
    LUNAR, SOLAR, GANZHI;

    public static DATE_TYPE getByValue(int value) {
        return values()[value];
    }
}
