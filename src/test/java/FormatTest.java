import org.junit.Assert;
import org.junit.Test;
import run.zhinan.time.format.DATE_TYPE;
import run.zhinan.time.format.DateTimeParser;
import run.zhinan.time.lunar.LunarDate;
import run.zhinan.time.lunar.LunarDateTime;
import run.zhinan.time.solar.SolarDate;
import run.zhinan.time.solar.SolarDateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatTest {
    LocalDate     date = LocalDate.of(2024, 1, 13);
    LocalDateTime time = date.atTime(11,36);
    LunarDate lunarDate = LunarDate.of(date);

    @Test
    public void testDateTimeParser() {

        Assert.assertEquals(SolarDate    .of(date), DateTimeParser.getDateParser    (DATE_TYPE.SOLAR).parse("2024-01-13"));
        Assert.assertEquals(SolarDateTime.of(time), DateTimeParser.getDateTimeParser(DATE_TYPE.SOLAR).parse("2024-01-13 11:36"));
        Assert.assertEquals(LunarDateTime.of(LocalDateTime.of(2011, 1, 6, 11, 40)).toString(),
                DateTimeParser.getDateTimeParser(DATE_TYPE.LUNAR).parse("二零一零-腊月-初三 午时").toString());
    }

    @Test
    public void testJava8DateTimeFormatter() {
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.CHINA).format(lunarDate));
    }
}
