package com.bitfiddling.helidon.service;

import io.avaje.http.api.*;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
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
