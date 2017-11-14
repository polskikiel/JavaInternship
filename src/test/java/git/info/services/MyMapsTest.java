package git.info.services;

import git.info.util.MyMaps;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MyMapsTest {

    @Test
    public void test() {
        Map<String, Integer> map = new HashMap<>();
        map.put("Java", 1239);
        map.put("C", 1339);
        map.put("HTML", 1439);
        map.put("CSS", 2359);
        map.put("KKM", 1250);
        map.put("PLA", 1000);

        System.out.println(MyMaps.sortMapByValue(map));

    }
}
