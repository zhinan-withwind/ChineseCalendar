package run.zhinan.time.format;

public enum CHINESE_NUMBER {
    ZERO    ( 0, "零"),
    ONE     ( 1, "一"),
    TWO     ( 2, "二"),
    THREE   ( 3, "三"),
    FOUR    ( 4, "四"),
    FIVE    ( 5, "五"),
    SIX     ( 6, "六"),
    SEVEN   ( 7, "七"),
    EIGHT   ( 8, "八"),
    NINE    ( 9, "九"),
    TEN     (10, "十");

    int    value;
    String name;

    CHINESE_NUMBER(int value, String name) {
        this.value = value;
        this.name  = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static CHINESE_NUMBER getByName(String name) {
        CHINESE_NUMBER result = null;
        for (CHINESE_NUMBER n : values()) {
            if (n.name.equals(name)) {
                result = n;
                break;
            }
        }
        return result;
    }

    public static String toNumberString(int num) {
        return num == 0 ? "" : toNumberString(num / 10) + values()[num % 10].name;
    }

    public static int fromNumberString(String text) {
        int result = 0;
        for (int i = 0; i < text.length(); i++) {
            result = result * 10 + getByName(text.substring(i, i+1)).value;
        }
        return result;
    }
}
