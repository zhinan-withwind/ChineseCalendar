package run.zhinan.time.format;

import run.zhinan.time.lunar.LunarDate;
import run.zhinan.time.lunar.LunarYear;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;

public class LunarDateParser extends BaseDateTimeParser implements DateTimeParser<LunarDate> {
    public final static LunarDateParser DEFAULT = new LunarDateParser();

    public LunarDateParser() {
        super(FormatStyle.FULL, NumberStyle.CHINESE);
    }

    public LunarDateParser(FormatStyle formatStyle, NumberStyle numberStyle) {
        super(formatStyle, numberStyle);
    }

    private String[] getFieldStrings(String dateString) {
        String[] result;
        if (dateString.indexOf("-") > 0) {
            result = dateString.split("-");
            if (result.length != 3) {
                throw new DateTimeParseException("日期格式不正确", dateString, 0);
            }
        } else if (dateString.indexOf("年") > 0 && dateString.indexOf("月") > 0) {
            String yearString  = dateString.substring(0, dateString.indexOf("年"));
            String monthString = dateString.substring(dateString.indexOf("年") + 1, dateString.indexOf("月"));
            String dayString   = dateString.substring(dateString.indexOf("月") + 1);
            result = new String[] {yearString, monthString, dayString};
        } else {
            throw new DateTimeParseException("日期格式不正确", dateString, 0);
        }
        return result;
    }

    @Override
    public LunarDate parse(String dateString) {
        String[] fieldStrings = getFieldStrings(dateString);
        int year, month, day;
        switch (numberStyle) {
            case ARABIC:
                year  = Integer.parseInt(fieldStrings[0]);
                month = Integer.parseInt(fieldStrings[1]);
                day   = Integer.parseInt(fieldStrings[2]);
                break;
            case CHINESE:
                year = CHINESE_NUMBER.fromNumberString(fieldStrings[0]);
                String monthString = fieldStrings[1];
                if (monthString.length() == 1) {
                    LunarYear lunarYear = LunarYear.of(year);
                    month = MONTH_NAME.getByName(monthString).getValue();
                    month += (lunarYear.isLeap() && lunarYear.getLeapMonth() < month ? 1 : 0);
                } else {
                    month = MONTH_NAME.getByName(monthString.substring(1, 2)).getValue() + 1;
                }
                day = DATE_NAME.getByName(dateString.substring(7, 9)).getValue();
                break;
            case GANZHI:
            default:
                return null;
        }
        return LunarDate.of(year, month, day);
    }

    @Override
    public String format(LunarDate lunarDate) {
        String result;
        switch (numberStyle) {
            case ARABIC:
                result = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(lunarDate);
                break;
            case GANZHI:
                result = lunarDate.getLunarYear().getGanZhiYear().toString()
                        + lunarDate.getLunarMonth() + DATE_NAME.getNameByValue(lunarDate.getDay());
                break;
            case CHINESE:
            default:
                result = lunarDate.getLunarYear().toString()
                        + lunarDate.getLunarMonth() + DATE_NAME.getNameByValue(lunarDate.getDay());
        }
        return result;
    }
}
