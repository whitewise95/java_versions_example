package java8_기능.completionFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTest {

    public static CompletableFuture<Integer> waitAndReturn(int time, int value) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return value;
        });
    }


    public static void main(String[] args) throws InterruptedException, ExecutionException {

        /**
         * supplyAsync
         * */
        System.out.println("==================================supplyAsync=====================================");
        CompletableFuture<Integer> supplyAsync = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 1;
        });

        System.out.println(!supplyAsync.isDone());  //true
        Thread.sleep(1000);

        System.out.println(supplyAsync.isDone());   //true
        System.out.println(supplyAsync.get() == 1); //true

        /**
         * runAsync
         * */
        System.out.println("==================================runAsync=====================================");
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println(!runAsync.isDone()); //true
        Thread.sleep(1000);
        System.out.println(runAsync.isDone()); //true
        System.out.println(runAsync.get() == null); //true

        /**
         * complete
         * CompletableFuture가 완료되지 않았다면 주어진 값으로 채운다. 반환값으로 complete에 의해서 상태값이 변경되었다면 ? true : false
         * */
        System.out.println("==================================complete=====================================");
        CompletableFuture<Integer> future = new CompletableFuture<>();
        System.out.println(!future.isDone());

        boolean triggered = future.complete(1);
        System.out.println(future.isDone());
        System.out.println(triggered);
        System.out.println(future.get() == 1);

        triggered = future.complete(2);
        System.out.println(future.isDone());
        System.out.println(!triggered);
        System.out.println(future.get() == 1);


        /**
         * completedExceptionally
         * Exception에 의해서 complete 되었는지 확인 할 수 있다.
         * */
        System.out.println("==================================completedExceptionally=====================================");
        CompletableFuture<Integer> completedExceptionally = CompletableFuture.supplyAsync(() -> {
            return 1 / 0;
        });
        Thread.sleep(100);
        System.out.println(completedExceptionally.isDone());
        System.out.println(completedExceptionally.isCompletedExceptionally());

        /**
         * allOf
         * 여러 CompletableFuture를 모아서 하나의 CompletableFuture로 변경할 수 있다.
         * 모든 CompletableFuture가 완료되면 상태가 done으로 변경
         * Void를 반환하므로 각각의 값에 get으로 접근해야 한다.
         * */
        System.out.println("==================================allOf=====================================");
        long start = System.currentTimeMillis();
        CompletableFuture<Integer> first = CompletableFutureTest.waitAndReturn(100, 1);
        CompletableFuture<Integer> second = CompletableFutureTest.waitAndReturn(500, 2);
        CompletableFuture<Integer> third = CompletableFutureTest.waitAndReturn(1000, 3);

        CompletableFuture.allOf(first, second, third)
                .thenAcceptAsync(v -> {
                    try {
                        System.out.println(first.get());
                        System.out.println(second.get());
                        System.out.println(third.get());
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }).join();

        long end = System.currentTimeMillis();
        System.out.println(end - start + "ms");


        /**
         * anyOf
         * 여러 CompletableFuture를 모아서 하나의 CompletableFuture로 변경할 수 있다.
         * 주어진 CompletableFuture가 하나라도 완료되면 상태가 done으로 변경
         * 제일 먼저 done상태가 되는  future을 반환한다.
         * */
        System.out.println("==================================anyOf=====================================");
        long start1 = System.currentTimeMillis();
        CompletableFuture<Integer> first2 = CompletableFutureTest.waitAndReturn(100, 1);
        CompletableFuture<Integer> second2 = CompletableFutureTest.waitAndReturn(500, 2);
        CompletableFuture<Integer> third2 = CompletableFutureTest.waitAndReturn(1000, 3);
        CompletableFuture.anyOf(first2, second2, third2).thenAcceptAsync(System.out::println).join();

        long end1 = System.currentTimeMillis();
        System.out.println(end1 - start1 + "ms");
    }
}
