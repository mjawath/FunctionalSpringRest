package com.tech.reactiverest;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ReactiverestApplicationTests {

    @Autowired
    private PersonHandler controller;

    @Autowired
    private Router router;

    @Test
    public void hc() {
        Object getxx = router.getForClass();
        assertThat(getxx).isNotNull();
        assertThat(getxx.getClass()).isEqualTo(PersonHandler.class);

    }

    @Test
    public void testWhenRouterInitThenNotnull() {
        Object getxx = router.route();
        assertThat(getxx).isNotNull();
//        assertThat(getxx instanceof RouterFunction<?>).isTrue();

    }

}
