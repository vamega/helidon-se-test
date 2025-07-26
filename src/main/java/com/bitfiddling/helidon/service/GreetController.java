package com.bitfiddling.helidon.service;

import io.avaje.http.api.Controller;
import io.avaje.http.api.Get;
import io.avaje.http.api.Path;
import io.avaje.http.api.Put;
import io.avaje.inject.External;
import io.helidon.http.Status;
import java.util.concurrent.atomic.AtomicReference;

@Path("/greet")
@Controller
public class GreetController {
    private final AtomicReference<String> greeting = new AtomicReference<>();

    public GreetController(@External String greeting) {
        this.greeting.set(greeting);
    }

    /**
     * Return a worldly greeting message.
     */
    @Get("/")
    Message getDefaultMessageHandler() {
        Message message = new Message();
        message.setMessage(String.format("%s %s!", greeting.get(), "World"));
        return message;
    }

    @Get("/{name}")
    Message getMessageHandler(String name) {
        Message message = new Message();
        message.setMessage(String.format("%s %s", greeting.get(), name));
        return message;
    }

    @Put("/greeting")
    Message updateGreetingFromJson(Message message) {
        if (message.getGreeting() == null) {
            Message errorMessage = new Message();
            errorMessage.setMessage("No greeting provided");
            throw new JsonHttpException(errorMessage);
        }
        greeting.set(message.getGreeting());
        throw new JsonHttpException("", Status.NO_CONTENT_204);
    }
}
