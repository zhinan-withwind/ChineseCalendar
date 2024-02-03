package run.zhinan.time.format;

import run.zhinan.time.ganzhi.Zhi;
import run.zhinan.time.lunar.LunarDate;
import run.zhinan.time.lunar.LunarDateTime;

import java.time.format.FormatStyle;

public class LunarDateTimeParser extends BaseDateTimeParser implements DateTimeParser<LunarDateTime> {
    LunarDateParser lunarDateParser;
    DiZhiTimeParser diZhiTimeParser;

    public final static LunarDateTimeParser DEFAULT = new LunarDateTimeParser();

    public LunarDateTimeParser() {
        this(FormatStyle.FULL, NumberStyle.CHINESE);
    }

    public LunarDateTimeParser(FormatStyle formatStyle, NumberStyle numberStyle) {
        super(formatStyle, numberStyle);
        lunarDateParser = new LunarDateParser(this.formatStyle, this.numberStyle);
        diZhiTimeParser = new DiZhiTimeParser(this.formatStyle, this.numberStyle);
    }

    @Override
    public LunarDateTime parse(String dateTimeString) {
        String[] dateTimeStrings  = divideDateAndTime(dateTimeString);
        String dateString = dateTimeStrings[0];
        String timeString = dateTimeStrings.length > 1 ? dateTimeStrings[1] : null;
        LunarDate date = lunarDateParser.parse(dateString);
        Zhi       time = timeString != null ? diZhiTimeParser.parse(timeString) : Zhi.ZI;
        return date.atTime(time.getValue() - 1);
    }

    @Override
    public String format(LunarDateTime lunarDateTime) {
        return lunarDateParser.format(lunarDateTime.toLunarDate()) + DATE_TIME_SEPARATOR
             + diZhiTimeParser.format(Zhi.getByValue(lunarDateTime.getTime() + 1));
    }
}
