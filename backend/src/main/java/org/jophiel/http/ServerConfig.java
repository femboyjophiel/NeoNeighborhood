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


        
                // Static website route (index.html)
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                // Hardcoded path to your index.html
                File file = new File("/home/jophi/NeoNeighborhood/frontend/index.html");

                if (!file.exists()) {
                    String response = "404 Not Found";
                    exchange.sendResponseHeaders(404, response.length());
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                    return;
                }

                byte[] bytes = Files.readAllBytes(file.toPath());
                exchange.getResponseHeaders().add("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, bytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            }
        });

    }
}
