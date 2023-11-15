import org.junit.Assert;
import org.junit.Test;
import run.zhinan.time.format.DATE_TYPE;
import run.zhinan.time.format.DateTimeParser;
import run.zhinan.time.solar.SolarDate;
import run.zhinan.time.solar.SolarDateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FormatTest {
    @Test
    public void testDateTimeParser() {
        LocalDate     date = LocalDate.of(2023, 11, 15);
        LocalDateTime time = date.atTime(11,36);
        Assert.assertEquals(SolarDate    .of(date), DateTimeParser.getDateParser    (DATE_TYPE.SOLAR).parse("2023-11-15"));
        Assert.assertEquals(SolarDateTime.of(time), DateTimeParser.getDateTimeParser(DATE_TYPE.SOLAR).parse("2023-11-15 11:36"));
    }
}
