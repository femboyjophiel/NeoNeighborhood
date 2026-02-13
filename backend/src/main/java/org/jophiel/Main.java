package org.jophiel;

import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

import org.jophiel.http.*;


public class Main {
    public static void main(String[] args) throws IOException {

        // Creates  the server then sends the handling to /http/ServerConfig.java
        final int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        
        ServerConfig.configure(server);
        server.start();
        System.out.println("Server started on port: " + port);
        //

    }
}
