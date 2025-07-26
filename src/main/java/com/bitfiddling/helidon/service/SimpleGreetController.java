package com.bitfiddling.helidon.service;

import io.avaje.http.api.Controller;
import io.avaje.http.api.Get;
import io.avaje.http.api.Path;

@Path("/")
@Controller
public class SimpleGreetController {

    public SimpleGreetController() {}

    /**
     * Return a worldly greeting message.
     */
    @Get("/simple-greet")
    String simpleGreet() {
        return "Hello World!";
    }
}
