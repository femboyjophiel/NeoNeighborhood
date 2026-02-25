package org.jophiel.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.jophiel.utils.MapServices;
import org.jophiel.utils.QueryParameters;
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


    
    }


}
