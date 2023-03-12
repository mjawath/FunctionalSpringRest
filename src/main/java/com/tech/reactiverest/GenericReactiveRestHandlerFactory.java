package com.tech.reactiverest;

import org.springframework.data.mongodb.repository.support.SimpleReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

public class GenericReactiveRestHandlerFactory{


    public <T extends BaseEntity> GenericReactiveRestHandler<T> create(Class<T> pojoClass,ReactiveCrudRepository repo) {

        BaseRxServiceImpl<T> tBaseRxService = new BaseRxServiceImpl<>(pojoClass,repo);
        GenericReactiveRestHandler<T> handler = new GenericReactiveRestHandler<>(pojoClass,tBaseRxService);
        return handler;
    }


}
