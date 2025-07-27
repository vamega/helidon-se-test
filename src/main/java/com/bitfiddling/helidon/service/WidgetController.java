package com.bitfiddling.helidon.service;

import io.avaje.http.api.Controller;
import io.avaje.http.api.Get;
import io.avaje.http.api.Path;
import io.avaje.jsonb.Json;
import java.util.List;

@Path("/widgets")
@Controller
public class WidgetController {
    public WidgetController() {}

    @Get("/{id}")
    Widget getById(int id) {
        return new Widget(id, "you got it Varun");
    }

    @Get()
    List<Widget> getAll() {
        return List.of(new Widget(1, "Rob"), new Widget(2, "Fi"));
    }

    @Json
    record Widget(int id, String name) {}
}
