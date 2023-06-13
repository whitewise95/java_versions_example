package com.example.java11_example.coldAndHotReactiveTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.Future;

public class SimpleHotPublisher implements Flow.Publisher<Integer> {

	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
	private final Future<Void> task;
	private List<Integer> numbers = new ArrayList<>();


	public SimpleHotPublisher() {
		numbers.add(1);
		task = executorService.submit(() -> {
			for (int i = 0; Thread.interrupted(); i++){
				numbers.add(i);
				Thread.sleep(100);
			}
			return null;
		});
	}

	@Override
	public void subscribe(Flow.Subscriber<? super Integer> subscriber) {

	}

	public void shutdown(){
		this.task.cancel(true);
	}
}
