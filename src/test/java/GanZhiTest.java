import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import run.zhinan.time.ganzhi.GanZhiDateTime;
import run.zhinan.time.solar.SolarTerm;
import run.zhinan.time.util.FileUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TreeMap;

public class GanZhiTest {
    static JSONObject loadTestData(int year) {
        return JSON.parseObject(FileUtil.loadResource("data/wenzhen/" + year + ".json"));
    }

    @Test
    public void testGanZhiDateTime() {
        System.out.println("开始测试：");
        int i = 0;
        for (int y = 1900; y < 2100; y++) {
            TreeMap<LocalDateTime, String> result = new TreeMap<>();
            JSONObject data = loadTestData(y);
            for (String key : data.keySet()) {
                LocalDateTime dateTime = LocalDateTime.parse(key);
                GanZhiDateTime ganZhiDateTime = GanZhiDateTime.ofNoMidnight(dateTime);
                if (!data.getString(key).equals(ganZhiDateTime.ganzhiString())) {
                    SolarTerm springDay = SolarTerm.J01_LICHUN.of(dateTime.getYear());
                    if (Math.abs(Duration.between(dateTime, springDay.getDateTime()).toDays()) > 1) {
                        result.put(dateTime, data.getString(key) + " : " + ganZhiDateTime.ganzhiString());
                        i++;
                    }
                }
            }
            result.forEach((key, value) -> System.out.println(key.toString() + " - " + value));
        }
        System.out.println(i);
    }

    @Test
    public void testSingleGanZhiDateTime() {
        LocalDateTime dateTime = LocalDateTime.of(2025, 2, 3, 22, 30);
        JSONObject data = loadTestData(dateTime.getYear());
        GanZhiDateTime ganZhiDateTime = GanZhiDateTime.ofNoMidnight(dateTime);
        System.out.println(ganZhiDateTime.ganzhiString());
        Assert.assertEquals(data.getString(dateTime.toString()), ganZhiDateTime.ganzhiString());
    }

    @Test
    public void exportData() throws Exception {
        LocalDateTime startTime = LocalDateTime.of(1950, 1, 1, 0, 0, 0);
        LocalDateTime endTime   = LocalDateTime.of(2050, 1, 1, 0, 0, 0);
        TreeMap<String, String> result = new TreeMap<>();
        while (startTime.isBefore(endTime)) {
            GanZhiDateTime ganZhiDateTime = GanZhiDateTime.ofNoMidnight(startTime);
            result.put(startTime.toString().replace("T", " "), ganZhiDateTime.toString());
            startTime = startTime.plusHours(1);
        }
        FileUtil.saveToFile("/Users/withwind/Downloads/ganZhiData.json", JSON.toJSONString(result, true));
    }

    @Test
    public void testSpecialGanZhiDateTime() {
        System.out.println(GanZhiDateTime.ofNoMidnight(LocalDateTime.of(2025, 2, 3, 22,  1)));
        System.out.println(GanZhiDateTime.ofNoMidnight(LocalDateTime.of(2025, 2, 3, 22, 30)));
    }
}
