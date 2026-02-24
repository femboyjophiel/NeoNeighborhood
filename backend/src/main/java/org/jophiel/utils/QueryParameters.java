package org.jophiel.utils;

import java.util.Map;
import java.util.HashMap;

public class QueryParameters {

    private final Map<String, String> parameters = new HashMap<>();

    // When using this class, you must call this function to create a QP object.
    // QP Objects can only handle a single query at a time, each query should create its own QP Object.
    // A query is the requestURI of an HttpExchange object
    public static QueryParameters parse(String query) {

        QueryParameters qp = new QueryParameters();
        if (query == null) return qp;

        // EXAMPLE: "&minX=4&minY=0"
        String[] pairs = query.split("&");
        // EXAMPLE: ["minX=4", "minY=0"]
        for (int i = 0; i < pairs.length; i++) {
            String[] keyValue = pairs[i].split("=");
            // EXAMPLE (at i = 0): ["minX", "4"]
            if (keyValue.length == 2) qp.parameters.put(keyValue[0], keyValue[1]);
        }
    
    return qp;
    }

    // Gets the value part of a key value pair as an int
    // If it fails to get that value, or the key is malformed, it will return intDefault as a fallback
    public int getInt(String key, int intDefault) {
        try {
            Object value = parameters.get(key);
            if (value == null) return intDefault;
            return Integer.parseInt(value.toString());
        } catch (Exception e) {
            return intDefault;
        }

    }

    public String getString(String key, String stringDefault) {
        try {
            Object value = parameters.get(key);
            if (value == null) return stringDefault;
            return value.toString();
        } catch (Exception e) {
            return stringDefault;
        }
    }

}

