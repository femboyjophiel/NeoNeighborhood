package org.jophiel.utils;

import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MapServices {
    private static final String MAP_FILE = "backend/src/main/java/org/jophiel/storage/world/map.json";

    public static String getMapData() {
        try {
            return new String(java.nio.file.Files.readAllBytes(
                java.nio.file.Paths.get(MAP_FILE)));
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static org.json.JSONObject getMapJson() {
        String data = getMapData();
        return data != null ? new org.json.JSONObject(data) : null;
    }
    /* This will get extra inforation about a plot
    public static org.json.JSONArray getPlot(int x, int y) {

    } */
    /* This will edit a tile, ie. letting a user claim it as a plot or edit their pre-existing plot. 
    public static boolean editPlot(int x, int y, String newType, JSONObject properties) {
         
    } */


    private static void saveMap(JSONObject mapJson) throws java.io.IOException {
        Files.write(
            Paths.get(MAP_FILE),
            mapJson.toString(2).getBytes(),
            StandardOpenOption.WRITE,
            StandardOpenOption.TRUNCATE_EXISTING
        );
    }

    public static boolean buildMap(String map) {
        try {

            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}