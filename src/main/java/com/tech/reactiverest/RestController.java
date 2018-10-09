package com.tech.reactiverest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RestController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("/people")
    public Flux<Person> all() {
        return personRepository.findAll();
    }

    @GetMapping("/people/{id}")
    Mono<Person> findById(@PathVariable String id) {
        return personRepository.findById(id);
    }


}
