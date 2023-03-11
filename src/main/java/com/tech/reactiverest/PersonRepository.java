package com.tech.reactiverest;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface PersonRepository extends ReactiveCrudRepository<Person, String> {


}
