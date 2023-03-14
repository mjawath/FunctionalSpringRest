package com.tech.reactiverest;

import com.tech.reactiverest.Product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.support.SimpleReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@SpringBootTest
public class RouterTest {


    @Autowired
    private ApplicationContext ac;
    @Autowired
    private Router router;

//    @Autowired
//    private WebTestClient  client;

    @Test
    public void testRoute() {


//        client.get().uri("/")
//                .accept(MediaType.TEXT_PLAIN)
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody(String.class).isEqualTo("Hello, world!");
    }

    @Test
    public void testPost() {
        ReactiveCrudRepository repo = ac.getBean(ReactiveCrudRepository.class, Product.class);

//        SimpleReactiveMongoRepository bean = ac.getBean(SimpleReactiveMongoRepository.class, Product.class, String.class);
        Product p= new Product();
        p.setDescription("mydd");
        Mono save = repo.save(p);

    }
}