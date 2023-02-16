package run.zhinan.time.format;

public enum MONTH_NAME {
    January     ( 1, "正"),
    February    ( 2, "二"),
    March       ( 3, "三"),
    April       ( 4, "四"),
    May         ( 5, "五"),
    June        ( 6, "六"),
    July        ( 7, "七"),
    August      ( 8, "八"),
    September   ( 9, "九"),
    October     (10, "十"),
    November    (11, "冬"),
    December    (12, "腊");

    int    value;
    String name;

    MONTH_NAME(int value, String name) {
        this.value = value;
        this.name  = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static MONTH_NAME getByName(String name) {
        MONTH_NAME result = null;
        for (MONTH_NAME m : values()) {
            if (m.name.equals(name)) {
                result = m;
                break;
            }
        }
        return result;
    }

    public static String getNameByValue(int value) {
        return values()[value - 1].name;
    }
}
