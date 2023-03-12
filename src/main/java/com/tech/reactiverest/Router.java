package com.tech.reactiverest;

import com.tech.reactiverest.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Configuration
public class Router {

    @Autowired
    ApplicationContext context;


    @Bean
    public RouterFunction<ServerResponse> dynamicRoute() {
        ReactiveCrudRepository repo = context.getBean(ReactiveCrudRepository.class, Product.class);
        GenericReactiveRestHandlerFactory genericReactiveRestHandlerFactory = new GenericReactiveRestHandlerFactory();
        GenericReactiveRestHandler<Product> req = genericReactiveRestHandlerFactory.create(Product.class,repo);
//        GenericReactiveRestHandler<Product> req = productGenericReactiveRestHandler;

        RequestPredicate acceptJson = accept(APPLICATION_JSON);
        RequestPredicate contentType = contentType(APPLICATION_JSON);
        RouterFunction<ServerResponse> route = RouterFunctions.route(GET("/")
                .and(acceptJson), req::all);
        return RouterFunctions.nest(RequestPredicates.path("/one"), route);
    }
        @Bean
    public RouterFunction<ServerResponse> route() {
        PersonHandler handler = (PersonHandler) getForClass();/*load dynamically*/
        String context = "/people";
        PersonHandler handler2 = (PersonHandler) getForClass();/*load dynamically*/

        String context2 = "/people2";
        String booking = "/booking";
        RequestPredicate acceptJson = accept(APPLICATION_JSON);
        RequestPredicate contentType = contentType(APPLICATION_JSON);
        RouterFunction<ServerResponse> route = RouterFunctions.route(GET("/{id}").and(acceptJson), handler::get)
                .andRoute(GET("").and(acceptJson), handler::all)
                .andRoute(POST("/").and(acceptJson).and(contentType), handler::post)
                .andRoute(PUT("/{id}").and(acceptJson).and(contentType), handler::put)
                .andRoute(DELETE("/{id}"), handler::delete)
                .andRoute(GET("/country/{country}").and(acceptJson), handler::getByCountry);
//        route.andRoute(GET(context2).and(acceptJson), handler2::all);
        return RouterFunctions.nest(RequestPredicates.path(context), route)
                .andNest(RequestPredicates.path(context2), RouterFunctions.route(GET("").and(acceptJson), handler2::all));
//                .andNest(RequestPredicates.path(booking), RouterFunctions.route(GET("").and(acceptJson), getHandler(BookingHandler.class)::all));
    }


    public Object getForClass() {
        String packageName = Product.class.getPackageName();
        Object bean = context.getBean(PersonHandler.class);
        return bean;
    }

    public <T> T getHandler(Class<T> cls) {
        return context.getBean(cls);
    }



}
