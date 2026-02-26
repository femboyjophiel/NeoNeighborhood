package org.jophiel.api;


import org.jophiel.http.Router;
import org.jophiel.utils.BodyServices;
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

        options("/api/user/login", new HttpHandler() {
            public void handle(HttpExchange exchange) throws IOException {
                preflight(exchange);
            }
        });
        // Handles user login and registration
        post("/api/user/login", new HttpHandler() {
         public void handle(HttpExchange exchange) throws IOException {   
        
            sendStatus(exchange, 200);

            
        }});

        options("/api/user/register", new HttpHandler() {
            public void handle(HttpExchange exchange) throws IOException {
                preflight(exchange);
            }
        });

        post("/api/user/register", new HttpHandler() {
         public void handle(HttpExchange exchange) throws IOException { 
            System.out.println("Registration request received: " + exchange.getRequestMethod());

            BodyServices bs = BodyServices.parse(exchange);
            final String username = bs.getFallback("username", "null");
            final String password = bs.getFallback("password", "null");
            if (!UserServices.validateUsername(username)) { sendJson(exchange, 401, "Invalid Username"); return; }
            if (!UserServices.validatePassword(password)) { sendJson(exchange, 401, "Invalid Password"); return; }

            if (UserServices.getUser(username).equalsIgnoreCase("nope")) { sendStatus(exchange, 402); return; }

            sendStatus(exchange, 201);
        }});
    }

}
