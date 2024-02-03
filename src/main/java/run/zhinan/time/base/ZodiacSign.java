package run.zhinan.time.base;

import run.zhinan.time.ganzhi.GanZhiDate;
import run.zhinan.time.ganzhi.Zhi;

import java.time.LocalDate;
import java.time.LocalDateTime;

public enum ZodiacSign {
    Rat    (Zhi.ZI  , "鼠", "🐭"),
    Ox     (Zhi.CHOU, "牛", "🐂"),
    Tiger  (Zhi.YIN , "虎", "🐯"),
    Rabbit (Zhi.MAO , "兔", "🐰"),
    Dragon (Zhi.CHEN, "龙", "🐲"),
    Snake  (Zhi.SI  , "蛇", "🐍"),
    Horse  (Zhi.WU  , "马", "🐎"),
    Sheep  (Zhi.WEI , "羊", "🐑"),
    Monkey (Zhi.SHEN, "猴", "🐒"),
    Rooster(Zhi.YOU , "鸡", "🐔"),
    Dog    (Zhi.XU  , "狗", "🐶"),
    Pig    (Zhi.HAI , "猪", "🐷");

    final Zhi    zhi;
    final String name;
    final String symbol;

    ZodiacSign(Zhi zhi, String name, String symbol) {
        this.zhi    = zhi;
        this.name   = name;
        this.symbol = symbol;
    }

    public int    getValue() {
        return ordinal();
    }

    public String getName() {
        return name;
    }

    public Zhi    getZhi() {
        return zhi;
    }

    public String getSymbol() {
        return symbol;
    }

    public static ZodiacSign getByValue(int value) {
        return values()[value];
    }

    public static ZodiacSign getByZhi(Zhi zhi) {
        return getByValue(zhi.getValue() - 1);
    }

    public static ZodiacSign getByDate(LocalDate date) {
        return getByZhi(GanZhiDate.of(date).getGanZhiYear().getZhi());
    }

    public static ZodiacSign getByDateTime(LocalDateTime dateTime) {
        return getByDate(dateTime.toLocalDate());
    }
}
