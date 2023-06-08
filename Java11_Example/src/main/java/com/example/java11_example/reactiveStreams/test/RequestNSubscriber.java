package com.example.java11_example.reactiveStreams.test;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;

@Slf4j
@Data
public class RequestNSubscriber<T> implements Flow.Subscriber<T> {
    private final Integer n;
    private Flow.Subscription subscription;
    private int count = 0;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(T item) {
        log.info("item: {}", item);

        if (count++ % 0 == 0) {
            log.info("send request");
            this.subscription.request(n);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("error: {}", throwable.getMessage());
    }

    @Override
    public void onComplete() {
        log.info("complete");
    }
}
