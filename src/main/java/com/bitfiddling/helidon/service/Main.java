package com.bitfiddling.helidon.service;

import static org.apache.logging.log4j.LogManager.getLogger;

import io.helidon.config.Config;
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
        // initialize global config from default configuration
        Config config = Config.create();
        Config.global(config);

        WidgetController widgetController = new WidgetController();
        WidgetController$Route widgetControllerRoute = new WidgetController$Route(widgetController);

        var routeBuilder = HttpRouting.builder();
        routeBuilder.addFeature(widgetControllerRoute);

        WebServer server = WebServer.builder()
                .config(config.get("server"))
                .routing(Main::routing)
                .addRouting(routeBuilder)
                .build()
                .start();

        LOGGER.info("WEB server is up! http://localhost:{}", server.port());
    }

    /**
     * Updates HTTP Routing.
     */
    static void routing(HttpRouting.Builder routing) {
        routing.register("/greet", new GreetService()).get("/simple-greet", (req, res) -> res.send("Hello World!"));
    }
}
