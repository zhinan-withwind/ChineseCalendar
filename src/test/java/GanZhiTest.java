import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
                if (!data.getString(key).equals(ganZhiDateTime.toString())) {
                    SolarTerm springDay = SolarTerm.J01_LICHUN.of(dateTime.getYear());
                    if (Math.abs(Duration.between(dateTime, springDay.getDateTime()).toDays()) > 1) {
                        result.put(dateTime, data.getString(key) + " : " + ganZhiDateTime);
                        i++;
                    }
                }
            }
            result.forEach((key, value) -> System.out.println(key.toString() + " - " + value));
        }
        System.out.println(i);
    }
}
