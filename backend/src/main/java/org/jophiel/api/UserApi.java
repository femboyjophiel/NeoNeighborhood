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


    post("/api/user/register", exchange -> {
        BodyServices bs;
        try {
            bs = BodyServices.parse(exchange);
        } catch (Exception e) {
            sendJson(exchange, 400, "{\"error\":\"Bad JSON\"}");
            return;
        }
        String username = bs.getFallback("username", "");
        String password = bs.getFallback("password", "");

        if (!UserServices.validateUsername(username)) {
            sendJson(exchange, 401, "{\"error\":\"Invalid Username\"}");
            return;
        }

        if (!UserServices.validatePassword(password)) {
            sendJson(exchange, 401, "{\"error\":\"Invalid Password\"}");
            return;
        }

        if (!UserServices.getUser(username).equals("nope")) {
            sendJson(exchange, 402, "{\"error\":\"User already exists\"}");
            return;
        }

        try {
            UserServices.createUser(username, password);
        } catch (Exception e) {
            sendJson(exchange, 400, "{\"error\":\"Error creating user\"}");
            return;
        }
        sendJson(exchange, 201, "{\"success\":true}");
    });

    options("/api/user/login", new HttpHandler() {
        public void handle(HttpExchange exchange) throws IOException {
            preflight(exchange);
        }
    });

    post("/api/user/login", exchange -> {
        BodyServices bs;
        try {
            bs = BodyServices.parse(exchange);
        } catch (Exception e) {
            sendJson(exchange, 400, "{\"error\":\"Bad JSON\"}");
            return;
        }
        String username = bs.getFallback("username", "");
        String password = bs.getFallback("password", "");

        if (!UserServices.validateLogin(username, password)) {
            sendJson(exchange, 401, "{\"error\":\"Invalid credentials\"}");
            return;
        }

        sendJson(exchange, 200, "{\"success\":true}");
    });
        

}

}
