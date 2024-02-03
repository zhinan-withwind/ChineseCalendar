package run.zhinan.time.format;

import run.zhinan.time.ganzhi.Zhi;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoField;

public class DiZhiTimeParser extends BaseDateTimeParser implements DateTimeParser<Zhi> {
    public DiZhiTimeParser() {
        this(FormatStyle.FULL, NumberStyle.CHINESE);
    }

    public DiZhiTimeParser(FormatStyle formatStyle, NumberStyle numberStyle) {
        super(formatStyle, numberStyle);
    }

    public final static DiZhiTimeParser DEFAULT = new DiZhiTimeParser();

    @Override
    public Zhi parse(String timeString) {
        timeString = timeString.replace(" ", "");
        Zhi zhi;
        switch (numberStyle) {
            case CHINESE:
                if (timeString.length() == 2) {
                    timeString = timeString.substring(0, 1);
                }
                zhi = Zhi.getByName(timeString);
                break;
            case ARABIC:
                long hour = DateTimeFormatter.ofPattern("hh:mm").parse(timeString).get(ChronoField.HOUR_OF_DAY);
                zhi = Zhi.getByValue((int) hour / 2 + 1);
                break;
            default:
                zhi = null;
        }
        return zhi;
    }

    @Override
    public String format(Zhi zhi) {
        String result;
        switch (numberStyle) {
            case ARABIC:
                result = formatNumber((zhi.getValue() - 1) * 2, 2) + ":00";
                break;
            case CHINESE:
            default:
                result = zhi.getName() + "时";
        }
        return result;
    }
}
