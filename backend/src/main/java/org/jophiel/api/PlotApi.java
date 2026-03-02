package org.jophiel.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jophiel.utils.BodyServices;
import org.jophiel.utils.MapServices;
import org.json.JSONObject;
import java.io.IOException;
import java.util.Map;

import org.jophiel.http.Router;

public class PlotApi extends AbstractApi {

    public PlotApi(Router router) {
        super(router);
    }

    @Override
    public void register() {

        get("/api/plot/plotdata", exchange -> {
            crossOrigin(exchange);

            // Parse query parameters
            Map<String, String> query = parseQuery(exchange);
            int id;
            try {
                id = Integer.parseInt(query.getOrDefault("id", "-1"));
            } catch (NumberFormatException e) {
                id = -1;
            }

            if (id < 0) {
                sendJson(exchange, 400, "{ \"error\": \"Invalid ID\" }");
                return;
            }

            // Fetch plot info from MapServices
            JSONObject plotInfo = MapServices.getPlotById(id);
            if (plotInfo != null) {
                sendJson(exchange, 200, plotInfo.toString());
            } else {
                sendJson(exchange, 404, "{ \"error\": \"Plot not found\" }");
            }
        });

    }

}