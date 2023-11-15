package run.zhinan.time.solar;

public class Day {
    private final SolarYear year;
    private final SolarMonth month;
    private final int value;

    public Day(int year, int month, int value) {
        this.year  = SolarYear.of(year);
        this.month = SolarMonth.of(year, month);
        this.value = value;
    }

    public static Day of(int year, int month, int day) {
        return new Day(year, month, day);
    }
}
