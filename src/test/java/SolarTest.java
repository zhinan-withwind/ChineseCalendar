import org.junit.Test;
import run.zhinan.time.solar.SolarDateTime;

import java.time.LocalDateTime;

public class SolarTest {
    @Test
    public void testApparentSolarTime() {
        System.out.println(SolarDateTime.of(LocalDateTime.now()).toApparentSolarTime("320506"));
    }
}
