package org.jophiel.api;
import org.jophiel.http.Router;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class AbstractApi {
    
    protected final Router router;

    private final String SITE = "*"; //"http://127.0.0.1:3000";

    protected AbstractApi(Router router) {
        this.router = router;
    }

    // Subclasses overwrite this method to define their urls/linked functions
    public abstract void register();

    protected void get(String path, HttpHandler handler) {
        router.get(path, handler);
    }

    protected void post(String path, HttpHandler handler) {
        router.post(path, handler);
    }

    protected void options(String path, HttpHandler handler) {
        router.options(path, handler);
    }


    // Send a simple status with NO body
    // * 200 : Action successful
    // * 201 : User created something successfully
    // * 404 : Something could not be found
    // * 403 : Logged in but not allowed
    // * 402 : Not Authorized (Not logged in)
    // * 401 : API Error (Bad Request)
    // * 400 : Internal Server Error
    protected void sendStatus(HttpExchange exchange, int status) throws IOException {
        crossOrigin(exchange);
        exchange.sendResponseHeaders(status, -1);
        exchange.getResponseBody().close();
    }
    
    // A status with a body 
    protected void sendJson(HttpExchange exchange, int status, String json) throws IOException {
        byte[] data = json.getBytes(StandardCharsets.UTF_8);
        crossOrigin(exchange);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(status, data.length);

        try (var os = exchange.getResponseBody()) {
            os.write(data);
            os.flush();
        }
    }

    protected void crossOrigin(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", SITE);
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Authorization, Content-Type");
    }
    

    protected void preflight(HttpExchange exchange) throws IOException {
        crossOrigin(exchange);
        exchange.sendResponseHeaders(200, 0); // zero-length body
        try (var os = exchange.getResponseBody()) {
            os.write(new byte[0]);
            os.flush();
        }
    }

}
