import org.junit.Assert;
import org.junit.Test;
import run.zhinan.time.solar.SolarTerm;

public class SolarTermTest {
    @Test
    public void testRoll() {
        int year = 2023;
        SolarTerm solarTerm = SolarTerm.J01_LICHUN.of(year);
        for (int i = 0; i < 500; i++) {
            SolarTerm actual = solarTerm.roll(i);
            SolarTerm expect = SolarTerm.getSolarTerms(year + i / 24).get(i % 24);
//            System.out.println(i + " - expect: " + expect.getDateTime() + ", actual: " + actual.getDateTime());
            Assert.assertEquals(expect.getDateTime(), actual.getDateTime());
        }
        for (int i = 0; i < 500; i++) {
            SolarTerm actual = solarTerm.roll(-i);
            SolarTerm expect = SolarTerm.getSolarTerms(year - (i + 23) / 24).get((24 * (i / 24 + 1) - i) % 24);
//            System.out.println(i + " - expect: " + expect.getDateTime() + ", actual: " + actual.getDateTime());
            Assert.assertEquals(expect.getDateTime(), actual.getDateTime());
        }
    }
}
