package org.jophiel.utils;

import org.json.JSONObject;
import org.json.JSONArray;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MapServices {
    private static final String MAP_FILE = "src/java/storage/world/map.json";

    public static String getMapData() {
        System.out.println("Looking for map file at: " + java.nio.file.Paths.get(MAP_FILE).toAbsolutePath());
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

    public static org.json.JSONArray getTile(int x, int y) {
        org.json.JSONObject mapJson = getMapJson();
        if (mapJson == null) return null;

        JSONArray tiles = mapJson.optJSONArray("tiles");
        if (tiles == null) return null;

        for (int i = 0; i < tiles.length(); i++) {
            JSONObject tile = tiles.getJSONObject(i);
            JSONArray xCoords = tile.optJSONArray("x");
            JSONArray yCoords = tile.optJSONArray("y");

            if (xCoords != null && yCoords != null) {
                for (int j = 0; j < xCoords.length(); j++) {
                    if (xCoords.getInt(j) == x && yCoords.getInt(j) == y) {
                        return new org.json.JSONArray().put(tile);
                    }
                }
            }
        }
        return null;
    }

    public static boolean editTile(int x, int y, String newType, JSONObject properties) {
        try {
            JSONObject mapJson = getMapJson();
            if (mapJson == null) return false;

            JSONArray tiles = mapJson.optJSONArray("tiles");
            if (tiles == null) return false;

            for (int i = 0; i < tiles.length(); i++) {
                JSONObject tile = tiles.getJSONObject(i);
                JSONArray xCoords = tile.optJSONArray("x");
                JSONArray yCoords = tile.optJSONArray("y");

                if (xCoords != null && yCoords != null) {
                    for (int j = 0; j < xCoords.length(); j++) {
                        if (xCoords.getInt(j) == x && yCoords.getInt(j) == y) {
                            // Found the tile, update it
                            tile.put("type", newType);
                            if (properties != null) {
                                properties.keys().forEachRemaining(key -> 
                                    tile.put(key, properties.get(key))
                                );
                            }
                            saveMap(mapJson);
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean editTile(int x, int y, String newType) {
        return editTile(x, y, newType, null);
    }

    private static void saveMap(JSONObject mapJson) throws java.io.IOException {
        Files.write(
            Paths.get(MAP_FILE),
            mapJson.toString(2).getBytes(),
            StandardOpenOption.WRITE,
            StandardOpenOption.TRUNCATE_EXISTING
        );
    }
}