import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import run.zhinan.time.solar.SolarTerm;
import run.zhinan.time.util.FileUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

     @Test
    public void exportData() throws Exception {
        Map<String, List<String>> data = new TreeMap<>();
        for (int year = 1900; year <= 2100; year++) {
            data.put(String.valueOf(year), exportData(year));
        }
        FileUtil.saveToFile("/Users/withwind/Downloads/solarTermData.json", JSONObject.toJSONString(data));
    }

    public List<String> exportData(int year) {
        LocalDateTime startTime = LocalDateTime.of(year, 1, 1, 0, 0);
        List<String> solarTerms = new ArrayList<>();
        for (SolarTerm solarTerm : SolarTerm.values) {
            LocalDateTime dateTime = solarTerm.of(year).getDateTime();
            long days = Duration.between(startTime, dateTime).toDays();
            int seconds = dateTime.toLocalTime().toSecondOfDay();
            Assert.assertEquals(dateTime, startTime.plusDays(days).plusSeconds(seconds));
            solarTerms.add(days + ":" + seconds);
        }
        System.out.println("Exported " + year);
        return solarTerms;
    }

    @Test
    public void exportSingleYearData() {
        int year = 2025;
        List<String> data = exportData(year);
        for (int i = 0; i < data.size(); i++) {
            String d = data.get(i);
            String[] parts = d.split(":");
            LocalDateTime dateTime = LocalDateTime.of(year, 1, 1, 0, 0).plusDays(Long.parseLong(parts[0])).plusSeconds(Long.parseLong(parts[1]));
            SolarTerm solarTerm = SolarTerm.getSolarTerms(year).get(i).of(year);
            System.out.println(solarTerm.getName() + " - " + d + " - " + solarTerm.getDateTime() + " - " + dateTime);
            Assert.assertEquals(solarTerm.getDateTime(), dateTime);
        }
    }
}
