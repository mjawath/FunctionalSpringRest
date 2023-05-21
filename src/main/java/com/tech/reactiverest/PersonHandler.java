package com.tech.reactiverest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.BootstrapRegistry;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

@Component
@Scope("prototype")
public class PersonHandler {

    @Autowired
    private final PersonRepository personManager;

    public void setTest(String test) {
        this.test = test;
    }

    private String test=null;

    public PersonHandler(PersonRepository personManager) {
        this.personManager = personManager;
    }

    public  Mono<ServerResponse> getxx(ServerRequest request) {

        return ServerResponse.ok().contentType(TEXT_PLAIN)
                .bodyValue("Hello xxxxxxx!"+test);
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        final String id = request.pathVariable("id");
        final Mono<Person> person = personManager.findById(id);
        System.out.println(test);
        return person
                .flatMap(p -> ok().contentType(APPLICATION_JSON).body(fromPublisher(person, Person.class)))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> all(ServerRequest request) {
        System.out.println(this.hashCode());
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(personManager.findAll(), Person.class));
    }
    public Mono<ServerResponse> all2(ServerRequest request) {
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(personManager.findAll(), Person.class));
    }

    public Mono<ServerResponse> put(ServerRequest request) {
        final String id = request.pathVariable("id");
        final Mono<Person> person = request.bodyToMono(Person.class);
        return personManager
                .findById(id.toString())
                .flatMap(
                        old ->
                                ok().contentType(APPLICATION_JSON)
                                        .body(
                                                fromPublisher(
                                                        person.flatMap(p -> personManager.save(old)),
                                                        Person.class)))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> post(ServerRequest request) {
        final Mono<Person> person = request.bodyToMono(Person.class);
        final UUID id = UUID.randomUUID();
        return created(UriComponentsBuilder.fromPath("people/" + id).build().toUri())
                .contentType(APPLICATION_JSON)
                .body(
                        fromPublisher(
                                person.flatMap(personManager::save), Person.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        final String id = request.pathVariable("id");
        return personManager
                .findById(id.toString())
                .flatMap(p -> noContent().build(personManager.delete(p)))
                .switchIfEmpty(notFound().build());
    }

    public Mono<ServerResponse> getByCountry(ServerRequest serverRequest) {
        final String country = serverRequest.pathVariable("country");
        return ok().contentType(APPLICATION_JSON)
                .body(fromPublisher(personManager.findById(country), Person.class));
    }


}