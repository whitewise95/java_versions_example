package com.example.java11_example.reactiveStreams.test;

import java.util.concurrent.Flow;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("===============");
		Flow.Publisher publisher = new FixedIntPublisher();
		Flow.Subscriber subscriber = new RequestNSubscriber(5);
		publisher.subscribe(subscriber);

		Thread.sleep(100);
	}
}
