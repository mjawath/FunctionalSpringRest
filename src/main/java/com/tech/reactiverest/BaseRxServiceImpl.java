package com.tech.reactiverest;

import org.reactivestreams.Publisher;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class BaseRxServiceImpl implements BaseRxService{

    private ReactiveCrudRepository repository;

    public BaseRxServiceImpl(ReactiveCrudRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono save(BaseEntity var1) {
        return null;
    }

    @Override
    public Mono save(Mono var1) {
        return null;
    }


    @Override
    public Flux saveAll(Iterable var1) {
        return null;
    }

    @Override
    public Flux saveAll(Publisher var1) {
        return null;
    }

    @Override
    public Mono findById(Object var1) {
        return null;
    }

    @Override
    public Mono findById(Publisher var1) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Object var1) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Publisher var1) {
        return null;
    }

    @Override
    public Flux findAll() {
        return null;
    }

    @Override
    public Flux findAllById(Iterable var1) {
        return null;
    }

    @Override
    public Flux findAllById(Publisher var1) {
        return null;
    }

    @Override
    public Mono<Long> count() {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Object var1) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Publisher var1) {
        return null;
    }

    @Override
    public Mono<Void> delete(BaseEntity var1) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Iterable var1) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Publisher var1) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll() {
        return null;
    }

    @Override
    public ReactiveCrudRepository getReactiveCrudRepository() {
        return repository;
    }


}
