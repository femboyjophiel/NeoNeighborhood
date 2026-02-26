package org.jophiel.utils;

import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.io.IOException;

public class MapServices {
    private static final String MAP_FILE = "backend/src/main/java/org/jophiel/storage/world/map.json";

    public static String getMapData() {
        try {
            return new String(Files.readAllBytes(
                Paths.get(MAP_FILE)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject getMapJson() {
        String data = getMapData();
        return data != null ? new org.json.JSONObject(data) : null;
    }
    // This will get extra inforation about a plot
    public static JSONObject getPlot(int x, int y) {
        final JSONObject map = getMapJson();

        Iterator<String> keys = map.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            Object value = map.get(key);
            System.out.println(key + " : " + value);
        }

        return map;
        
    }
    // This will edit a tile, ie. letting a user claim it as a plot or edit their pre-existing plot. 
    public static boolean editPlot(int x, int y, String newType, JSONObject properties) {
         return true;
    }


   
}