package org.jophiel.api;


import org.jophiel.http.Router;

public class UserApi extends AbstractApi {
    
    public UserApi(Router router) {
        super(router);
    }

    @Override
    public void register() {
        // Handles user login and registration
        post("/api/user/login", exchange -> {
            System.out.println("Login request received");
        });

        post("/api/user/register", exchange -> {
            System.out.println("Registration request received");
        });
    }

}
