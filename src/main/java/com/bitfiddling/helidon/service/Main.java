package com.bitfiddling.helidon.service;

import static io.avaje.http.api.MediaType.APPLICATION_JSON;
import static org.apache.logging.log4j.LogManager.getLogger;

import io.avaje.json.stream.JsonOutput;
import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Jsonb;
import io.helidon.config.Config;
import io.helidon.http.HeaderNames;
import io.helidon.logging.common.LogConfig;
import io.helidon.service.registry.Services;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;
import org.apache.logging.log4j.Logger;

/**
 * The application main class.
 */
public class Main {

    public static final Logger LOGGER = getLogger(Main.class);

    /**
     * Cannot be instantiated.
     */
    private Main() {}

    /**
     * Application main entry point.
     * @param args command line arguments.
     */
    public static void main(String[] args) {

        // load logging configuration
        LogConfig.configureRuntime();

        // initialize global config from default configuration
        Config config = Config.create();
        Services.set(io.helidon.common.config.Config.class, config);

        var routeBuilder = HttpRouting.builder();
        routing(routeBuilder);

        WebServer server = WebServer.builder()
                .config(config.get("server"))
                .routing(routeBuilder)
                .build()
                .start();

        LOGGER.info("WEB server is up! http://localhost:{}", server.port());
    }

    static void routing(HttpRouting.Builder routeBuilder) {
        Jsonb jsonb = Jsonb.instance();

        WidgetController widgetController = new WidgetController();
        WidgetController$Route widgetControllerRoute = new WidgetController$Route(widgetController, jsonb);

        GreetController greetController = new GreetController("Hello");
        GreetController$Route greetControllerRoute = new GreetController$Route(greetController, jsonb);

        SimpleGreetController simpleGreetController = new SimpleGreetController();
        // Since this doesn't produce Json, on jsonb parameter needs to be generated.
        SimpleGreetController$Route simpleGreetControllerRoute =
                new SimpleGreetController$Route(simpleGreetController);

        routeBuilder.addFeature(widgetControllerRoute);
        routeBuilder.addFeature(greetControllerRoute);
        routeBuilder.addFeature(simpleGreetControllerRoute);
        routeBuilder.error(JsonHttpException.class, ((req, res, ex) -> {
            JsonType<Object> type = jsonb.type(Object.class);
            res.status(ex.status());
            res.header(HeaderNames.CONTENT_TYPE, APPLICATION_JSON);
            type.toJson(ex.body(), JsonOutput.of(res));
        }));
    }
}
