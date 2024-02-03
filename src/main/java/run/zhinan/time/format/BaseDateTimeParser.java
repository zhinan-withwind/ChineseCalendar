package run.zhinan.time.format;

import java.time.format.FormatStyle;

public class BaseDateTimeParser {
    final String DATE_TIME_SEPARATOR = " ";
    final String DATE_FIELD_SEPARATOR = "-";
    final String PLACE_HOLDER = "0";

    FormatStyle formatStyle;
    NumberStyle numberStyle;

    public BaseDateTimeParser(FormatStyle formatStyle, NumberStyle numberStyle) {
        this.formatStyle = formatStyle;
        this.numberStyle = numberStyle;
    }

    String formatNumber(int n, int width) {
        return formatNumber(n, width, PLACE_HOLDER);
    }

    String formatNumber(int n, int width, String placeHolder) {
        String result;
        for (result = String.valueOf(n); result.length() < width; ) {
            result = placeHolder + result;
        }
        return result;
    }

    public String[] divideDateAndTime(String dateTimeString) {
        return divideDateAndTime(dateTimeString, DATE_TIME_SEPARATOR);
    }

    public String[] divideDateAndTime(String dateTimeString, String separator) {
        if (dateTimeString.indexOf(separator) > 0) {
            String dateString = dateTimeString.substring(0, dateTimeString.indexOf(separator));
            String timeString = dateTimeString.substring(dateTimeString.indexOf(separator) + 1);
            return new String[] {dateString, timeString};
        } else {
            return new String[] {dateTimeString};
        }
    }
}
