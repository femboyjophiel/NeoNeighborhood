package org.jophiel.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.jophiel.utils.BodyServices;
import org.jophiel.utils.MapServices;
import org.json.JSONObject;

import java.io.IOException;
import org.jophiel.http.Router;

public class PlotApi extends AbstractApi {
    
    public PlotApi(Router router) {
        super(router);
    }

    @Override
    public void register() {

        // Return associated plot data, called when a user clicks on a plot
        get("/api/plot/plotdata", new HttpHandler() {
            public void handle(HttpExchange exchange) throws IOException {
                System.out.println("Info about a tile request recieved");

                BodyServices bs = BodyServices.parse(exchange);

                final int x = Integer.parseInt(bs.getFallback("x", "-1"));
                final int y = Integer.parseInt(bs.getFallback("y", "-1"));

                if (x<0||y<0) {
                    sendJson(exchange, 401, "Invalid Coordinates");
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


    
    }


}
