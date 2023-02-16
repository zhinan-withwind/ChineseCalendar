package run.zhinan.time.format;

public interface DateTimeParser<T> {
    T parse(String dateString);
    String format(T t);
}
