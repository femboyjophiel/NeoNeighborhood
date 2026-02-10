package org.jophiel.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jophiel.utils.QueryParameters;
import java.io.IOException;
import org.jophiel.http.Router;

public class PlotApi extends AbstractApi {
    
    public PlotApi(Router router) {
        super(router);
    }

    @Override
    public void register() {

        // Return associated plot data, called when a user clicks on a plot
        get("/api/plot/data", new HttpHandler() {
            
            public void handle(HttpExchange exchange) throws IOException {

                QueryParameters qp = QueryParameters.parse(exchange.getRequestURI().getQuery());

                int plotId = qp.getInt("plotId", 0);
                
            }
        });

    
    }


}
