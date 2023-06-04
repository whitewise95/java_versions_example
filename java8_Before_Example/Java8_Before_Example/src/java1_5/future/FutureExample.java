package java1_5.future;

import java.util.concurrent.*;

public class FutureExample {

    /**
     * 새로운 쓰레드를 생성하여 1을 반환
     */
    public static Future<Integer> getFuture() {
        var executor = Executors.newSingleThreadExecutor();

        try {
            return executor.submit(() -> 1);
        } finally {
            executor.shutdown();
        }
    }

    /**
     * 새로운 쓰레드를 생성하고 1초 대기 후 1을 반환
     */
    public static Future<Integer> getFutureCompleteAfter1s() {
        var executor = Executors.newSingleThreadExecutor();

        try {
            return executor.submit(() -> {
                Thread.sleep(1000);
                return 1;
            });
        } finally {
            executor.shutdown();
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        Future future = FutureExample.getFuture();
        System.out.println(future.isDone()); // false
        System.out.println(future.isCancelled()); // false

        var result = future.get();
        System.out.println(result.equals(1));  //true
        System.out.println(future.isDone());   //true
        System.out.println(future.isCancelled());  //false

        System.out.println("============================================================================");
        /**
         * 무한 루프의 위험을 위한 time out
         * */
        Future futureCompleteAfter1s = FutureExample.getFutureCompleteAfter1s();
        var result2 = futureCompleteAfter1s.get(1500, TimeUnit.MILLISECONDS);
        System.out.println(result2.equals(1));  //true

        Future futureCompleteAfter1s2 = FutureExample.getFutureCompleteAfter1s();
    
        Exception exception = null;
        try {

            /**
             * 여기서 타임아웃은 500 milliseconds를 기다리는 것이 아니라 500 milliseconds를 기다리고 결과를 보겠다는 의미
             * */
            Object o = futureCompleteAfter1s2.get(500, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            exception = e;
        }

        System.out.println(exception != null);
        System.out.println("============================================================================");



        Future future3 = FutureExample.getFuture();
        boolean cancel = future3.cancel(true);
        System.out.println(future3.isCancelled());
        System.out.println(future3.isDone());
        System.out.println(cancel);


        boolean cancel2 = future3.cancel(true);
        System.out.println(future3.isCancelled());
        System.out.println(future3.isDone());

        /**
         *  boolean cancel = future3.cancel(true); 이 라인에서 취소 되었기에 false
         * */
        System.out.println(cancel2);
    }


}

