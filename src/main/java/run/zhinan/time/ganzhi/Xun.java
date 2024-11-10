package run.zhinan.time.ganzhi;

import com.alibaba.fastjson.JSONObject;

public enum Xun {
    JIA_ZI(0, "甲子"), JIA_XU  (1, "甲戌"), JIA_SHEN(2, "甲申"),
    JIA_WU(3, "甲午"), JIA_CHEN(4, "甲辰"), JIA_YIN (5, "甲寅");

    final int value;
    final String name;

    Xun(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static Xun getByName(String name) {
        Xun result = null;
        for (Xun xun : values()) {
            if (xun.getName().equals(name)) {
                result = xun;
                break;
            }
        }
        return result;
    }

    public static Xun getByValue(int value) {
        return values()[value];
    }

    public JSONObject toJSON() {
        return new JSONObject()
                .fluentPut("value", getValue())
                .fluentPut("name" , getName() );
    }
}
