package org.jophiel.utils;

import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

public class MapServices {

    private static final String MAP_FILE = "src/main/java/org/jophiel/storage/world/map.json";

    public static String getMapData() {
        try {
            return new String(Files.readAllBytes(Paths.get(MAP_FILE)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject getMapJson() {
        String data = getMapData();
        return data != null ? new JSONObject(data) : null;
    }

    public static JSONObject getPlotById(int id) {
        JSONObject map = getMapJson();
        if (map == null) return null;

        String idStr = String.valueOf(id);
        if (!map.has(idStr)) return null;

        JSONObject plot = map.getJSONObject(idStr);

        // Add full user info if owner exists
        if (plot.has("owner") && plot.get("owner") != null) {
            String username = plot.getString("owner");
            String userJson = org.jophiel.utils.UserServices.getUser(username);
            if (userJson != null) {
                JSONObject userInfo = new JSONObject(userJson);
                plot.put("userInfo", userInfo);
            }
        }

        return plot;
    }

    // Edit plot by ID
    public static boolean editPlotById(int id, String newType, JSONObject properties) {
        // implement editing logic here
        return true;
    }
}