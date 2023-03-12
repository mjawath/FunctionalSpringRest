package com.tech.reactiverest;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

public class GenericReactiveRestHandler2<T extends BaseEntity>  {


    private BaseRxService<T,String> service;
    private Class<T> classEntity;

    public GenericReactiveRestHandler2() {
        this.classEntity = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        final String id = request.pathVariable("id");
        final Mono retVal = service.findById(id);
        return retVal
                .flatMap(p -> ok().contentType(APPLICATION_JSON).body(fromPublisher(retVal, classEntity)))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> all(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(service.findAll(), classEntity));
    }

    public Mono<ServerResponse> put(ServerRequest request) {
        final String id = request.pathVariable("id");
        final Mono<T> bodyToMono = request.bodyToMono(classEntity);
        return service
                .findById(id)
                .flatMap(
                        old ->
                                ok().contentType(APPLICATION_JSON)
                                        .body(
                                                fromPublisher(
                                                        bodyToMono.flatMap(p -> service.save(old)),
                                                        classEntity)))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> post(ServerRequest request) {
        final Mono<T> person = request.bodyToMono(classEntity);
        Mono<T> savedM = service.save(person);
        return savedM
                .flatMap(saved->
                created((UriComponentsBuilder.fromPath("??? where to get the url for this domain/" + saved.getId()).build().toUri()))
                        .body(fromObject(saved)));
//         created(UriComponentsBuilder.fromPath("Todo").build().toUri())
//                .contentType(APPLICATION_JSON)
//                .body(
//                        fromPublisher(
//                                person.flatMap(service::save), classEntity));
    }


    public Mono<ServerResponse> delete(ServerRequest request) {
        final String id = request.pathVariable("id");
        return service
                .findById(id)
                .flatMap(p -> noContent().build(service.delete(p)))
                .switchIfEmpty(notFound().build());
    }

//    public Mono<ServerResponse> getByCountry(ServerRequest serverRequest) {
//        final String country = serverRequest.pathVariable("country");
//        return ok().contentType(APPLICATION_JSON)
//                .body(fromPublisher(service.findById(country), Person.class));
//    }



}
