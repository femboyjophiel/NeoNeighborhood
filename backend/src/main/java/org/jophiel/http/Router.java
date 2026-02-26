package org.jophiel.http;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

public class Router {
    
    // routes defines all the different API modules that can be accessed by users
    // A route would look like: "/api/map" or "/api/map/tiles"
    // The routes a module will have is defined in their class
    private final Map<String, Map<String, HttpHandler>> routes = new HashMap<>(); 

    // An http GET request is used when a user requests something
    // It usually includes parameters beyond the router they are using
    // Parameters are added after the route using "&"
    // This is determined on client side, whether a specific request is a get or a post
    public void get(String path, HttpHandler handler) {
        register("GET", path, handler);
    }

    // An http POST request is used when the user is sending something to the server, like login credentials
    public void post(String path, HttpHandler handler) {
        register("POST", path, handler);
    }

    public void options(String path, HttpHandler handler) {
        register("OPTIONS", path, handler);
    }

    // Api modules use this to register their routes,
    public void register(String method, String path, HttpHandler handler) {
        routes.computeIfAbsent(method, k -> new HashMap<>()).put(path, handler);
    }

    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        
        Map<String, HttpHandler> methodRoutes = routes.get(method);
        
        if (methodRoutes != null && methodRoutes.containsKey(path)) {
            methodRoutes.get(path).handle(exchange);
        } else {
            exchange.sendResponseHeaders(404, -1);
            exchange.getResponseBody().close();
        }
    }

}
