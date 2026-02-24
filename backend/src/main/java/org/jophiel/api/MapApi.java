package org.jophiel.api;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import org.jophiel.http.Router;
import org.jophiel.utils.MapServices;
import org.jophiel.utils.QueryParameters;
import org.json.JSONObject;

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
                JSONObject mapJson = MapServices.getMapJson();
                if (mapJson != null) { 
                    sendJson(exchange, 200, mapJson.toString());
                    return;
                } 
                sendStatus(exchange, 400);
            }
        });

        get("/api/map/plotinfo", new HttpHandler() {
            public void handle(HttpExchange exchange) throws IOException {
                System.out.println("Info about a tile request recieved");

                QueryParameters qp = QueryParameters.parse(exchange.getRequestURI().getQuery());

                final int x = qp.getInt("x", -1);
                final int y = qp.getInt("y", -1);

                if (x<0||y<0) {
                    sendStatus(exchange, 401);
                    return;
                }

                JSONObject tileInfo = MapServices.getPlot(x, y);

                if (tileInfo != null) {
                    sendJson(exchange, 200, tileInfo.toString());
                    return;
                }

                sendStatus(exchange, 400); 

            }
        });

        // A popup that will introduce a user to the website
        get("/api/map/info", new HttpHandler() {
            
            public void handle(HttpExchange exchange) throws IOException {
            
            }
        });

    
    }



}
