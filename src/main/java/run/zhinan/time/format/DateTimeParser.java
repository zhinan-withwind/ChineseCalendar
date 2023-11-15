package run.zhinan.time.format;

public interface DateTimeParser<T> {
    T parse(String dateString);
    String format(T t);

    static DateTimeParser<?> getDateTimeParser(DATE_TYPE dateType) {
        switch (dateType) {
            case SOLAR:
                return new SolarDateTimeParser();
            case LUNAR:
                return new LunarDateTimeParser();
            case GANZHI:
            default:
                throw new RuntimeException("不支持的日期类型");
        }
    }

    static DateTimeParser<?> getDateParser(DATE_TYPE dateType) {
        switch (dateType) {
            case SOLAR:
                return new SolarDateParser();
            case LUNAR:
                return new LunarDateParser();
            case GANZHI:
            default:
                throw new RuntimeException("不支持的日期类型");
        }
    }
}
