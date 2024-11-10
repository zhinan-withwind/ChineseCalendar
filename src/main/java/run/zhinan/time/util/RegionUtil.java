package run.zhinan.time.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import run.zhinan.time.base.Region;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class RegionUtil {

    private static void generateRegionData() {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get("/Users/withwind/Downloads/regionMap.json")));
            JSONObject data = JSON.parseObject(jsonContent);
            Map<String, Map<String, Object>> result = new TreeMap<>();
            for (String key : data.keySet()) {
                JSONObject regionData = data.getJSONObject(key);
                for (String code : regionData.keySet()) {
                    Region region = Region.getByCode(code);
                    if (region != null) {
//                        if (!region.getCode().equals(code))
//                            System.out.println("没找到：" + regionData.get(code) + "(" + code + ")，用" + region.getName() + "(" + region.getCode() + ") 替代！！！");
                        result.put(code, new JSONObject()
//                                .fluentPut("name", regionData.getString(code))
                                .fluentPut("latitude" , region.getLatitude ())
                                .fluentPut("longitude", region.getLongitude())
                        );
                    } else {
                        System.out.println("无法找到：" + regionData.get(code) + "(" + code + ")！！！！");
                    }
                }
            }

            try (FileWriter writer = new FileWriter("/Users/withwind/Downloads/regionMapWithData.json")) {
                writer.write(JSON.toJSONString(result,
                        SerializerFeature.PrettyFormat,
                        SerializerFeature.DisableCircularReferenceDetect,
                        SerializerFeature.SortField
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportRegionDataFromJson() throws IOException {
        String jsonFilePath = "src/main/resources/data/region.json";
        String jsonContent = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        JSONArray data = JSON.parseArray(jsonContent);
        Map<String, String> idMap = new HashMap<>();
        idMap.put("0", "000086");
        for (Object o : data) {
            JSONObject region = (JSONObject) o;
            String id = region.getString("id");
            idMap.put(id, region.getString("code"));
        }
        Map<String, Map<String, String>> regionMap = new TreeMap<>();
        for (Object o : data) {
            JSONObject region = (JSONObject) o;
            String parentId = region.getString("parentId");
            String code = region.getString("code");
            String name = region.getString("name");
            regionMap.computeIfAbsent(idMap.get(parentId), k -> new TreeMap<>()).put(code, name);
        }
        writeJsonToFile(JSON.toJSONString(regionMap, true), "/Users/withwind/Downloads/regionMap.json");
    }

    public static void writeJsonToFile(String json, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        }
    }

    public static void main(String[] args) throws IOException {
//        generateRegionData();
        exportRegionDataFromJson();
    }
}
