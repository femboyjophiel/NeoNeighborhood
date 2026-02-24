package org.jophiel.api;
import org.jophiel.http.Router;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

public abstract class AbstractApi {
    
    protected final Router router;

<<<<<<< Updated upstream
    private final String SITE = "http://127.0.0.1:3000";
=======
    private final String SITE = "null"; // This needs to be the url of whatever website will be communicating with the server
>>>>>>> Stashed changes

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


    // Send a simple status with NO body
    // * 200 : Action successful
    // * 201 : User created something successfully
    // * 404 : Something could not be found
    // * 403 : Logged in but not allowed
    // * 402 : Not Authorized (Not logged in)
    // * 401 : API Error (Bad Request)
    // * 400 : Internal Server Error
    // NOTE:  Statuses with bodies should not use this
    protected void sendStatus(HttpExchange exchange, int status) throws IOException {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", SITE);
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Authorization");
        exchange.sendResponseHeaders(status, -1);
        exchange.getResponseBody().close();
    }
    
    // A status with a body (usually to explain errors) 
    protected void sendJson(HttpExchange exchange, int status, String json) throws IOException {
        byte[] data = json.getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", SITE);
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Authorization");
        exchange.sendResponseHeaders(status, data.length);
        exchange.getResponseBody().write(data);
        exchange.close();
    }

}
