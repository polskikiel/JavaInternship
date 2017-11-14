package git.info.util;

import java.util.Map;
import java.util.stream.Collectors;

public class MyMaps {
    public static Map<String, Integer> sortMapByValue(Map<String, Integer> map) {

        Map<String, Integer> map1 = map.entrySet().stream().    //  iterate through map entries
                sorted(Map.Entry.comparingByValue()). //  sorting it by value descending
                collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue   //  then collect it to HashMap with same key and value
        ));

        return map1;
    }
}
