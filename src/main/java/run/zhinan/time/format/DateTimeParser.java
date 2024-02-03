package run.zhinan.time.format;

public interface DateTimeParser<T> {
    T parse(String dateString);
    String format(T t);

    static DateTimeParser<?> getDateTimeParser(DATE_TYPE dateType) {
        switch (dateType) {
            case SOLAR:
                return SolarDateTimeParser.DEFAULT;
            case LUNAR:
                return LunarDateTimeParser.DEFAULT;
            case GANZHI:
            default:
                throw new RuntimeException("不支持的日期类型");
        }
    }

    static DateTimeParser<?> getDateParser    (DATE_TYPE dateType) {
        switch (dateType) {
            case SOLAR:
                return SolarDateParser.DEFAULT;
            case LUNAR:
                return LunarDateParser.DEFAULT;
            case GANZHI:
            default:
                throw new RuntimeException("不支持的日期类型");
        }
    }
}
