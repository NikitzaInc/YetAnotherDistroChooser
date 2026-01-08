package com.nikitzainc.distrochooser;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class DistroLoadTest extends Simulation {

    String distroWatchUrl = "https://distrowatch.com/search.php?ostype=All&category=Server&origin=All&basedon=Debian&notbasedon=None&desktop=All&architecture=All&package=All&rolling=All&isosize=All&netinstall=All&language=All&defaultinit=All&status=Active#simpleresults";

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8081")
            .acceptHeader("application/json");

    ScenarioBuilder scn = scenario("Distro Search Load Test")
            .repeat(10).on(
                    exec(http("Get Distros Request")
                            .get("/api/link")
                            .queryParam("distroWatchLink", distroWatchUrl)
                            .check(status().is(200))
                    )
            );

    {
        setUp(
                scn.injectOpen(atOnceUsers(1000))
        ).protocols(httpProtocol);
    }
}