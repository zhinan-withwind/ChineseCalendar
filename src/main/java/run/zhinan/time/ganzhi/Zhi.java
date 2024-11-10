package run.zhinan.time.ganzhi;

import com.alibaba.fastjson.JSONObject;

public enum Zhi {
    ZI  ( 1, "子"),
    CHOU( 2, "丑"),
    YIN ( 3, "寅"),
    MAO ( 4, "卯"),
    CHEN( 5, "辰"),
    SI  ( 6, "巳"),
    WU  ( 7, "午"),
    WEI ( 8, "未"),
    SHEN( 9, "申"),
    YOU (10, "酉"),
    XU  (11, "戌"),
    HAI (12, "亥");

    final int value;
    final String name;

    Zhi(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Zhi getByValue(int value) {
        return values()[value - 1];
    }

    public static Zhi getByName(String name) {
        Zhi result = null;
        for (Zhi z : values()) {
            if (z.name.equals(name)) {
                result = z;
                break;
            }
        }
        return result;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public JSONObject toJSON() {
        return new JSONObject()
                .fluentPut("value", getValue())
                .fluentPut("name" , getName() );
    }
}
