package com.example.java11_example.coldAndHotReactiveTest;

import lombok.SneakyThrows;

public class ColdMain {

	@SneakyThrows
	public static void main(String[] args) {
		SimpleColdPublisher publisher = new SimpleColdPublisher();
		SimpleNameSubscriber<Object> subscriber = new SimpleNameSubscriber<>("subscriber1");
		publisher.subscribe(subscriber);

		Thread.sleep(5000);

		SimpleNameSubscriber<Object> subscriber2 = new SimpleNameSubscriber<>("subscriber2");
		publisher.subscribe(subscriber2);
	}
}
