package org.jophiel.utils;

import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import org.json.JSONObject;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class BodyServices {

    private final Map<String, String> parameters;

    private BodyServices(Map<String, String> parameters) { 
        this.parameters = Collections.unmodifiableMap(parameters);
    }

    // When using this class, you must call this function to create a QP object.
    // QP Objects can only handle a single query at a time, each query should create its own QP Object.
    // A query is the requestURI of an HttpExchange object
    public static BodyServices parse(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        
        Map<String, String> map = new HashMap<>();

        if (!body.isEmpty()) {
            JSONObject json = new JSONObject(body);
            for (String key : json.keySet()) {
                map.put(key, json.get(key).toString());
            }
            
        }

        return new BodyServices(map);
    
    }


    public String getFallback(String key, String stringDefault) {
        try {
            if (has(key) == false) return stringDefault;
            return get(key);
        } catch (Exception e) {
            return stringDefault;
        }
    }

    public String get(String key) {
        return parameters.get(key);
    }

    public boolean has(String key) {
        return parameters.containsKey(key);
    }

    public Map<String, String> asMap() {
        return parameters;
    }


}

