package com.example.java11_example.coldAndHotReactiveTest;

import lombok.SneakyThrows;

public class HotMain {

	@SneakyThrows
	public static void main(String[] args) {
		SimpleHotPublisher simpleHotPublisher = new SimpleHotPublisher();
		Thread.sleep(5000);
		simpleHotPublisher.shutdown();

		// SimpleNameSubscriber<Object> subscriber = new SimpleNameSubscriber<>("subscriber1");
		// simpleHotPublisher.subscribe(subscriber);
		//
		// Thread.sleep(5000);
		// subscriber.cancel();
		//
		// SimpleNameSubscriber<Object> subscriber2 = new SimpleNameSubscriber<>("subscriber2");
		// SimpleNameSubscriber<Object> subscriber3 = new SimpleNameSubscriber<>("subscriber3");
		// simpleHotPublisher.subscribe(subscriber2);
		// simpleHotPublisher.subscribe(subscriber3);
		//
		// Thread.sleep(5000);
		// subscriber2.cancel();
		// subscriber3.cancel();
		//
		// Thread.sleep(1000);
		// SimpleNameSubscriber<Object> subscriber4 = new SimpleNameSubscriber<>("subscriber4");
		// simpleHotPublisher.subscribe(subscriber4);
		//
		// Thread.sleep(5000);
		// subscriber4.cancel();
		//
		// simpleHotPublisher.shutdown();
	}
}
