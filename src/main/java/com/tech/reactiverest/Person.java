package com.tech.reactiverest;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;;

@Document
public class Person {

    @Id
    private String id;


    public Person(){

    }
    public Person(Person p, String id) {
        this.id = id;

    }
}
