package git.info.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class MyJson {
    /*public static Map<String, Integer> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Integer> retMap = new HashMap<>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, Integer> toMap(JSONObject object) throws JSONException {
        Map<String, Integer> map = new HashMap<>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Integer value = (Integer) object.get(key);

            map.put(key, value);
        }
        return map;
    }

    public static List<Integer> toList(JSONArray array) throws JSONException {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Integer value = (Integer) array.get(i);
            list.add(value);
        }
        return list;
    }*/
}
