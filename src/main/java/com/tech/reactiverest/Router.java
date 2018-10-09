package com.tech.reactiverest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;


import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;


@Configuration
public class Router {

    @Bean
    public RouterFunction<ServerResponse> route(PersonHandler handler) {

        return nest(path("testpath"),
                nest(accept(MediaType.APPLICATION_JSON),
                        route(handler)
                                .andRoute(GET("/{id}"), handler::get)
                                .andRoute(POST("/"), handler::post)
                                .andRoute(PUT("/{id}"), handler::put)
                                .andRoute(DELETE("/{id}"), handler::delete)
                                .andRoute(GET("/"), handler::all)
                ));
//        return RouterFunctions.route(GET("/people/{id}").and(accept(APPLICATION_JSON)), personHandler::get)
//                .andRoute(GET("/people").and(accept(APPLICATION_JSON)), personHandler::all)
//                .andRoute(POST("/people").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), personHandler::post)
//                .andRoute(PUT("/people/{id}").and(accept(APPLICATION_JSON)).and(contentType(APPLICATION_JSON)), personHandler::put)
//                .andRoute(DELETE("/people/{id}"), personHandler::delete)
//                .andRoute(GET("/people/country/{country}").and(accept(APPLICATION_JSON)), personHandler::getByCountry);
    }
}
