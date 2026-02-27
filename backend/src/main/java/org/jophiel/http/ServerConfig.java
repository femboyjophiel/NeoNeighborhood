package org.jophiel.http;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jophiel.api.*;

import com.sun.net.httpserver.HttpServer;

public class ServerConfig {
    public static void configure(HttpServer server) {
        Router router = new Router();
        
        AbstractApi[] modules = new AbstractApi[] {
            new MapApi(router),
            new UserApi(router),
            new PlotApi(router)
        };

        for (AbstractApi module : modules) {
            module.register();
        }

server.createContext("/api", router::handle); // API routes

// Serve static files (JS, CSS, images)
server.createContext("/", exchange -> {
    String path = "/home/pi/NeoNeighborhood" + exchange.getRequestURI().getPath();
    File file = new File(path);
    if (!file.exists() || file.isDirectory()) {
        exchange.sendResponseHeaders(404, 0);
        exchange.close();
        return;
    }

    String mime = Files.probeContentType(file.toPath());
    if (mime == null) mime = "application/octet-stream";

    byte[] bytes = Files.readAllBytes(file.toPath());
    exchange.getResponseHeaders().add("Content-Type", mime);
    exchange.sendResponseHeaders(200, bytes.length);
    try (OutputStream os = exchange.getResponseBody()) {
        os.write(bytes);
    }
});

// Fallback for SPA
server.createContext("/", exchange -> {
    File file = new File("/home/pi/NeoNeighborhood/index.html");
    byte[] bytes = Files.readAllBytes(file.toPath());
    exchange.getResponseHeaders().add("Content-Type", "text/html");
    exchange.sendResponseHeaders(200, bytes.length);
    try (OutputStream os = exchange.getResponseBody()) {
        os.write(bytes);
    }
});

    }
}
