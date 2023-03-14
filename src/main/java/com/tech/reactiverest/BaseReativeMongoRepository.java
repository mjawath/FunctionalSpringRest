package com.tech.reactiverest;

import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

public class BaseReativeMongoRepository extends ReactiveMongoTemplate {

    public BaseReativeMongoRepository(MongoClient mongoClient, String databaseName) {
        super(mongoClient, databaseName);
    }
}
