package run.zhinan.time.format;

public enum DATE_NAME {
    D01( 1, "初一"),
    D02( 2, "初二"),
    D03( 3, "初三"),
    D04( 4, "初四"),
    D05( 5, "初五"),
    D06( 6, "初六"),
    D07( 7, "初七"),
    D08( 8, "初八"),
    D09( 9, "初九"),
    D10(10, "初十"),
    D11(11, "十一"),
    D12(12, "十二"),
    D13(13, "十三"),
    D14(14, "十四"),
    D15(15, "十五"),
    D16(16, "十六"),
    D17(17, "十七"),
    D18(18, "十八"),
    D19(19, "十九"),
    D20(20, "二十"),
    D21(21, "廿一"),
    D22(22, "廿二"),
    D23(23, "廿三"),
    D24(24, "廿四"),
    D25(25, "廿五"),
    D26(26, "廿六"),
    D27(27, "廿七"),
    D28(28, "廿八"),
    D29(29, "廿九"),
    D30(30, "三十");

    int    value;
    String name;

    DATE_NAME(int value, String name) {
        this.value = value;
        this.name  = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static DATE_NAME getByName(String name) {
        DATE_NAME result = null;
        for (DATE_NAME d : values()) {
            if (d.name.equals(name)) {
                result = d;
                break;
            }
        }
        return result;
    }

    public static String getNameByValue(int value) {
        return values()[value - 1].name;
    }
}
