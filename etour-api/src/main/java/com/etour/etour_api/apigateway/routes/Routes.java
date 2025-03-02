package com.etour.etour_api.apigateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * @version 1.0
 * @project etour-api
 * @since 29-01-2025
 */

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> tourServiceRoute() {
        return GatewayRouterFunctions.route("tour_service")
                .route(RequestPredicates.path("/tour-service/**"), HandlerFunctions.http("http://localhost:8081"))
                .build();
    }

}
