package run.zhinan.time.solar;

public class SolarMonth {
    private final static int[] dayNums = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private final int year;
    private final int value;
    private final int dayNum;

    public SolarMonth(int year, int value) {
        this.year   = year;
        this.value  = value;
        this.dayNum = dayNums[value - 1] + (value == 2 && getSolarYear().isLeap() ? 1 : 0 );
    }

    public static SolarMonth of(int year, int month) {
        return new SolarMonth(year, month);
    }

    public SolarMonth at(int year) {
        return new SolarMonth(year, getValue());
    }

    public int getValue() {
        return value;
    }

    public SolarYear getSolarYear() {
        return SolarYear.of(this.year);
    }

    public int getDayNum() {
        return dayNum;
    }
}
