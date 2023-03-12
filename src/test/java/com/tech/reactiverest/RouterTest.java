package com.tech.reactiverest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@SpringBootTest
public class RouterTest {


    @Autowired
    private Router router;

    @Autowired
    private WebTestClient  client;

    @Test
    public void testRoute() {


        client.get().uri("/")
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Hello, world!");
    }
}