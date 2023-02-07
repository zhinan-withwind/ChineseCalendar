package run.zhinan.time.ganzhi;

public enum Gan {
    JIA ( 1, "甲"),
    YI  ( 2, "乙"),
    BING( 3, "丙"),
    DING( 4, "丁"),
    WU  ( 5, "戊"),
    JI  ( 6, "己"),
    GENG( 7, "庚"),
    XIN ( 8, "辛"),
    REN ( 9, "壬"),
    GUI (10, "癸");

    int value;
    String name;

    Gan(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static Gan getByValue(int value) {
        return values()[value - 1];
    }
}
