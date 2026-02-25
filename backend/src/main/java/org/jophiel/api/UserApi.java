package org.jophiel.api;


import org.jophiel.http.Router;
import org.jophiel.utils.MapServices;
import org.jophiel.utils.QueryParameters;
import org.jophiel.utils.UserServices;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;

public class UserApi extends AbstractApi {
    
    public UserApi(Router router) {
        super(router);
    }

    @Override
    public void register() {
        // Handles user login and registration
        post("/api/user/login", new HttpHandler() {
         public void handle(HttpExchange exchange) throws IOException {   
        
            System.out.println("Login request received");

            
        }});

        post("/api/user/register", new HttpHandler() {
         public void handle(HttpExchange exchange) throws IOException {   
            System.out.println("Registration request received");

            QueryParameters qp = QueryParameters.parse(exchange.getRequestURI().getQuery());

            final String username = qp.getString("username", "");
            final String password = qp.getString("password", "");

            if (!UserServices.validateUsername(username)) { sendStatus(exchange, 401); return; }
            if (!UserServices.validatePassword(password)) { sendStatus(exchange, 401); return; }

            if (UserServices.getUser(username) == null) { sendStatus(exchange, 402); return; }


        }});
    }

}
