package Controller;

import Model.Account;
import Service.AccountService;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerAccountHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
   

    private void registerAccountHandler(Context context) {
        ObjectMapper mapper = new ObjectMapper();

    try {
        Account account = mapper.readValue(context.body(), Account.class);
        Optional <Account> registeredAccount  = accountService.registerAccount(account);
         if(registeredAccount.isPresent()) {
            context.json(mapper.writeValueAsString(registeredAccount.get()));
         } else {
            context.status(400);
             context.json("Account registration faild");
         }

    } catch (JsonProcessingException  e) {
        e.printStackTrace();
        context.status(400);
    }
     
    }


}