package run.zhinan.time.format;

import run.zhinan.time.solar.SolarDateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class SolarDateTimeParser extends BaseDateTimeParser implements DateTimeParser<SolarDateTime> {
    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SolarDateTimeParser() {
        super(FormatStyle.FULL, NumberStyle.ARABIC);
    }

    public SolarDateTimeParser(FormatStyle formatStyle, NumberStyle numberStyle) {
        super(formatStyle, numberStyle);
    }

    @Override
    public SolarDateTime parse(String dateString) {
        return SolarDateTime.of(
                LocalDateTime.parse(dateString.length() == 16 ? dateString + ":00" : dateString, dateTimeFormatter)
        );
    }

    @Override
    public String format(SolarDateTime solarDateTime) {
        return dateTimeFormatter.format(solarDateTime);
    }
}
