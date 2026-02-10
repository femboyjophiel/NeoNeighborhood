package org.jophiel.api;
import org.jophiel.http.Router;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;

public abstract class AbstractApi {
    
    protected final Router router;
    
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

    protected void sendJson(HttpExchange exchange, String json) throws IOException {
        byte[] data = json.getBytes("UTF-8");
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, data.length);
        exchange.getResponseBody().write(data);
        exchange.close();
    }

    // Send a simple status with NO body
    // * 201 : User created something successfully
    // * 204 : Action successful
    // * 404 : Something could not be found
    // * 403 : Logged in but not allowed
    // * 401 : Not Authorized (Not logged in)
    // * 400 : Bad Request
    // NOTE:  Statuses with bodies should not use this (200)
    protected void sendStatus(HttpExchange exchange, int status) throws IOException {
        exchange.sendResponseHeaders(status, -1);
        exchange.getResponseBody().close();
    }
    

}
