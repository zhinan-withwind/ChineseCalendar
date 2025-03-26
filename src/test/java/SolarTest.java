import org.junit.Test;
import run.zhinan.time.base.DateTimeHolder;
import run.zhinan.time.base.Region;
import run.zhinan.time.solar.SolarDateTime;

import java.time.LocalDateTime;

public class SolarTest {
    @Test
    public void testApparentSolarTime() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 24, 15, 32, 0);
        System.out.println(SolarDateTime.of(dateTime).toApparentSolarTime("150105"));
        Region region = Region.getByCode("150105");
        // "code": "150105", "name": "赛罕区", "latitude": 40.791950, "longitude": 111.701710
        region.setLongitude(111.911710);
        region.setLatitude ( 40.791950);
        System.out.println(DateTimeHolder.toMeanSolarTime(dateTime, region));
        System.out.println(DateTimeHolder.toApparentSolarTime(dateTime, region));
    }
}
