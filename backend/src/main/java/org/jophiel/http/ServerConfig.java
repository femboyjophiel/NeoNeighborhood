package org.jophiel.http;

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


        
        server.createContext("/", router::handle);

    }
}
