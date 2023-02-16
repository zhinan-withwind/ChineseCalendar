package run.zhinan.time.format;

import java.time.format.FormatStyle;

public class BaseDateTimeParser {
    final String SEPARATOR = " ";

    FormatStyle formatStyle = FormatStyle.FULL;
    NumberStyle numberStyle = NumberStyle.ARABIC;

    public BaseDateTimeParser() {
    }

    public BaseDateTimeParser(FormatStyle formatStyle, NumberStyle numberStyle) {
        this.formatStyle = formatStyle;
        this.numberStyle = numberStyle;
    }

    public FormatStyle getFormatStyle() {
        return formatStyle;
    }

    public NumberStyle getNumberStyle() {
        return numberStyle;
    }

    String formatNumber(int n, int width) {
        String result;
        for (result = String.valueOf(n); result.length() < width; result = "0" + result);
        return result;
    }
}
