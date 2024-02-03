package run.zhinan.time.format;

import run.zhinan.time.solar.SolarDateTime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class SolarDateTimeParser extends BaseDateTimeParser implements DateTimeParser<SolarDateTime> {
    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public  final static SolarDateTimeParser DEFAULT = new SolarDateTimeParser();

    public SolarDateTimeParser() {
        this(FormatStyle.FULL, NumberStyle.ARABIC);
    }

    public SolarDateTimeParser(FormatStyle formatStyle, NumberStyle numberStyle) {
        super(formatStyle, numberStyle);
    }

    @Override
    public SolarDateTime parse(String dateTimeString) {
        String[] strings    = divideDateAndTime(dateTimeString);
        String dateString   = strings[0];
        String timeString   = strings.length > 1 ? strings[1] : null;
        LocalDate localDate = LocalDate.parse(dateString);
        LocalTime localTime = timeString != null ? LocalTime.parse(timeString) : LocalTime.of(0, 0);
        return SolarDateTime.of(localDate.atTime(localTime));
    }

    @Override
    public String format(SolarDateTime solarDateTime) {
        return dateTimeFormatter.format(solarDateTime);
    }
}
