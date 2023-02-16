package run.zhinan.time;

import com.alibaba.fastjson.JSON;
import run.zhinan.time.lunar.LunarTerm;
import run.zhinan.time.lunar.LunarYear;
import run.zhinan.time.solar.SolarTerm;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ChineseCalendar {
    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(LunarYear.of(2023)));

    }

    private static void printSolarTermData(int startYear, int endYear) {
        // 打印表头
        System.out.print("年份,");
        SolarTerm.values.forEach(solarTerm -> System.out.print(solarTerm.getName() + ","));
        System.out.println();

        for (int y = startYear; y < endYear; y++) {
            List<SolarTerm> solarTerms = SolarTerm.getSolarTerms(y);
            StringBuilder sb = new StringBuilder().append(y).append(",");
            solarTerms.forEach(solarTerm -> sb.append(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(solarTerm.getDateTime())).append(","));
            System.out.println(sb);
        }
    }
}
