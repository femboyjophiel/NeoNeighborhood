package org.jophiel.api;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import org.jophiel.http.Router;
import org.jophiel.utils.MapServices;

public class MapApi extends AbstractApi {
    
    public MapApi(Router router) {
        super(router);
    }

    @Override
    public void register() {

        // Returns the map data to be rendered client-side
        get("/api/map/tiles", new HttpHandler() {
            
            public void handle(HttpExchange exchange) throws IOException {


                System.out.println("Map tiles request received");
                org.json.JSONObject mapJson = MapServices.getMapJson();
                sendJson(exchange, mapJson != null ? mapJson.toString() : "{}");
            }
        });

        // A popup that will introduce a user to the website
        get("/api/map/info", new HttpHandler() {
            
            public void handle(HttpExchange exchange) throws IOException {
            
            }
        });
    }



}
