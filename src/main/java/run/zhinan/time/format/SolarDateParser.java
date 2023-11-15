package run.zhinan.time.format;

import run.zhinan.time.solar.SolarDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class SolarDateParser extends BaseDateTimeParser implements DateTimeParser<SolarDate> {
    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public SolarDateParser() {
        super(FormatStyle.FULL, NumberStyle.ARABIC);
    }

    public SolarDateParser(FormatStyle formatStyle, NumberStyle numberStyle) {
        super(formatStyle, numberStyle);
    }

    @Override
    public SolarDate parse(String dateString) {
        return SolarDate.of(LocalDate.parse(dateString, dateTimeFormatter));
    }

    @Override
    public String format(SolarDate solarDate) {
        return dateTimeFormatter.format(solarDate);
    }
}
