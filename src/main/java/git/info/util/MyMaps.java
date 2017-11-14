package git.info.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MyMaps {
    public static Map<String, Integer> sortMapByValue(Map<String, Integer> map) {

        return map.entrySet().stream().    //  iterate through map entries
                sorted(Map.Entry.comparingByValue(Collections.reverseOrder())). //  sorting it by value descending
                collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,   //  then collect it to LinkedHashMap with same key and value
                (integer, integer2) -> integer,
                LinkedHashMap::new      // have to be Linked to sort ( HashMap didn't )
        ));
    }
}
