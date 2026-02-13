package org.jophiel.api;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
                if (mapJson != null) { 
                    sendJson(exchange, 200, mapJson.toString());
                    return;
                } 
                sendStatus(exchange, 404);
            }
        });

        // A popup that will introduce a user to the website
        get("/api/map/info", new HttpHandler() {
            
            public void handle(HttpExchange exchange) throws IOException {
            
            }
        });

        // Im sure theres a way to have this only in 'test' builds without releaing this to the public, but thats a lot of work so lets just make sure to remove it before the final build is released
        post("/api/temp/build", new HttpHandler() {
            public void handle(HttpExchange exchange) throws IOException {
                String json = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8)
                System.out.println("Received json map: " + json);

                if (MapServices.buildMap(json)) {
                    sendStatus(exchange, 200);
                    return;
                }
                sendStatus(exchange, 400);
            }
        });
    }



}
