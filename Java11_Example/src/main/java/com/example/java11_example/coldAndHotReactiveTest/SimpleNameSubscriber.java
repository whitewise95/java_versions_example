package com.example.java11_example.coldAndHotReactiveTest;

import java.util.concurrent.Flow;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleNameSubscriber<T> implements Flow.Subscriber<T> {

	private Flow.Subscription subscription;
	private final String name;

	public SimpleNameSubscriber(String name) {
		this.name = name;
	}

	@Override
	public void onSubscribe(Flow.Subscription subscription) {
		this.subscription = subscription;
		this.subscription.request(1);
		log.info("onSubscribe");
	}

	@Override
	public void onNext(T item) {
		log.info("name: {}, onNext: {}", name, item);
		this.subscription.request(1);
	}

	@Override
	public void onError(Throwable throwable) {
		log.error("onError: {}", throwable.getMessage());
	}

	@Override
	public void onComplete() {
		log.info("onComplete");
	}

	public void cancel(){
		log.info("cancel");
		this.subscription.cancel();
	}
}
