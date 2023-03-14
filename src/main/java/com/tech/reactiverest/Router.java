package com.tech.reactiverest;

import com.tech.reactiverest.Product.Book;
import com.tech.reactiverest.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Configuration
public class Router {

    @Autowired
    ApplicationContext context;

    @Autowired
    ReactiveMongoTemplate template;





    private <T> MappingMongoEntityInformation<T, String> createEntityInformation(Class<T> cls,ReactiveMongoTemplate mongoTemplate) {
        MongoMappingContext mappingContext = new MongoMappingContext();
        MongoPersistentEntity<T> entity = (MongoPersistentEntity<T>) mappingContext.getPersistentEntity(cls);
        return new MappingMongoEntityInformation<>(entity);
    }


//    @Bean
    public RouterFunction<ServerResponse> dynamicRoute(Class cls) {


        ReactiveCrudRepository<?,String> repo = new SimpleReactiveMongoRepository(createEntityInformation(cls,template),template);//context.getBean(ReactiveCrudRepository.class, Product.class);
        GenericReactiveRestHandlerFactory genericReactiveRestHandlerFactory = new GenericReactiveRestHandlerFactory();
        GenericReactiveRestHandler<?> handler = genericReactiveRestHandlerFactory.create(cls,repo);

        RequestPredicate acceptJson = accept(APPLICATION_JSON);
//        RequestPredicate contentType = contentType(APPLICATION_JSON);
        String context = "";
        return RouterFunctions.route(GET("").and(acceptJson), handler::all)
                .andRoute(POST("/").and(acceptJson), handler::post)
                .andRoute(PUT("/{id}").and(acceptJson), handler::put)
                .andRoute(DELETE("/{id}"), handler::delete);
//                .andRoute(GET("/country/{country}").and(acceptJson), handler::getByCountry);

    }
    @Bean
    public RouterFunction<ServerResponse> dynamicRoute() {
        RouterFunction<ServerResponse> route = dynamicRoute(Book.class);
        RouterFunction<ServerResponse> route2 = dynamicRoute(Product.class);
        return RouterFunctions.nest(RequestPredicates.path("/book"), route)
                .andNest(RequestPredicates.path("/product"),route2);
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
        RouterFunction<ServerResponse> route1 = RouterFunctions.route(GET("").and(acceptJson), handler2::all);
        return RouterFunctions.nest(RequestPredicates.path(context), route)
                .andNest(RequestPredicates.path(context2), route1);
//                .andNest(RequestPredicates.path(booking), RouterFunctions.route(GET("").and(acceptJson), getHandler(BookingHandler.class)::all));
    }
    @Bean
    public RouterFunction<ServerResponse> testRoute() {

        RouterFunctions.route();
        return RouterFunctions.route(path("/x"),
                        request ->  ServerResponse.ok().contentType(TEXT_PLAIN)
                                .body(BodyInserters.fromObject("Hello World!")))
                .andRoute(path("/x2"),
                        request ->  ServerResponse.ok().contentType(TEXT_PLAIN)
                                .body(BodyInserters.fromObject("Hello xxxx222!")));
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
