package org.jophiel.api;


import org.jophiel.http.Router;
import org.jophiel.utils.QueryParameters;
import org.jophiel.utils.UserServices;

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

            QueryParameters qp = QueryParameters.parse(exchange.getRequestURI().getQuery());

            final String username = qp.getString("username", "");
            final String password = qp.getString("password", "");

            if (!UserServices.validateUsername(username)) { sendStatus(exchange, 401); return; }
            if (!UserServices.validatePassword(password)) { sendStatus(exchange, 401); return; }

            if (UserServices.getUser(username) == null) { sendStatus(exchange, 402); return; }


        });
    }

}
