package org.jophiel.http;

import org.jophiel.api.*;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

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

        server.createContext("/api", router::handle);

server.createContext("/resources", exchange -> {
    String path = exchange.getRequestURI().getPath().replaceFirst("/resources/", "");
    if (path.startsWith("/")) path = path.substring(1);
    File file = new File("/home/jophi/NeoNeighborhood/frontend/resources", path);
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
    try (OutputStream os = exchange.getResponseBody()) { os.write(bytes); }
});

server.createContext("/", exchange -> {
    File indexFile = new File("/home/jophi/NeoNeighborhood/frontend/index.html");
    byte[] bytes = Files.readAllBytes(indexFile.toPath());
    exchange.getResponseHeaders().add("Content-Type", "text/html");
    exchange.sendResponseHeaders(200, bytes.length);
    try (OutputStream os = exchange.getResponseBody()) { os.write(bytes); }
});
    }
}