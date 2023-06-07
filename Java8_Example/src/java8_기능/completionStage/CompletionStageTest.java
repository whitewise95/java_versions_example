package java8_기능.completionStage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CompletionStageTest {

    static Logger logger = Logger.getLogger("Logger");

    public static class Helper {

        /**
         * 1를 반환하는 완료된 CompletableFuture 반환
         */
        public static CompletionStage<Integer> finishedStage() {
            return CompletableFuture.supplyAsync(() -> {
                System.out.println("supplyAsync");
                return 1;
            });
        }

        /**
         * 1초를 sleep 한 후 1을 반환하는 메소드
         */
        public static CompletionStage<Integer> runningStage() {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000);

                    System.out.println("I' m running");
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                return 1;
            });
        }

        /**
         * int를 받아 +1 하는 메소드
         */
        public static CompletionStage<Integer> addOne(int value) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                return value + 1;
            });
        }

        /**
         * int를 받아 +1 하는 메소드
         */
        public static CompletionStage<String> addResultPrefix(int value) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                return "Result : " + value;
            });
        }
    }


    public static void main(String[] args) throws InterruptedException {

        /**
         * finishedStage
         * */
        CompletionStage<Integer> stage = Helper.finishedStage();
        logger.log(Level.INFO, "startMain");

        stage.thenAccept(i -> logger.log(Level.INFO, "thenAccept1 : " + i))
                .thenAccept(i -> logger.log(Level.INFO, "thenAccept2 : " + i));

        logger.log(Level.INFO, "after thenAccept");

        System.out.println("=====================================================");


        logger.log(Level.INFO, "startMain");
        CompletionStage<Integer> stage2 = Helper.finishedStage();
        stage2.thenAcceptAsync(i -> logger.log(Level.INFO, "thenAcceptAsync1 : " + i))
                .thenAcceptAsync(i -> logger.log(Level.INFO, "thenAcceptAsync2 : " + i));
        logger.log(Level.INFO, "after thenAcceptAsync");


        /**
         *  runningStage
         * */
//        CompletionStage<Integer> stage = Helper.runningStage();
//        logger.log(Level.INFO, "startMain");
//        stage.thenAccept(i -> logger.log(Level.INFO, "thenAccept1 : " + i))
//                .thenAccept(i -> logger.log(Level.INFO, "thenAccept2 : " + i));
//        Thread.sleep(2000);
//
//
//        System.out.println("=====================================================");
//
//        logger.log(Level.INFO, "startMain");
//        CompletionStage<Integer> stage2 = Helper.runningStage();
//        stage2.thenAcceptAsync(i -> logger.log(Level.INFO, "thenAcceptAsync : " + i))
//                .thenAcceptAsync(i -> logger.log(Level.INFO, "thenAcceptAsync : " + i));
//
//        Thread.sleep(2000);


        /**
         * then*Async의 쓰레드풀 변경
         * */
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(10);
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();

        logger.log(Level.INFO, "start");
        CompletionStage<Integer> stage3 = Helper.finishedStage();
        stage3.thenAcceptAsync(i -> {
                    logger.log(Level.INFO, "thenAcceptAsync1 : " + i);
                }, newFixedThreadPool)
                .thenAcceptAsync(i -> {
                    logger.log(Level.INFO, "thenAcceptAsync : " + i);
                }, newSingleThreadExecutor);

        Thread.sleep(2000);


        /**
         * thenApplyAsync
         * */
        CompletionStage<Integer> stage4 = Helper.finishedStage();

        stage4.thenApplyAsync(i -> {
                    int value = i + 1;
                    System.out.println(value);
                    return value;
                }).thenApplyAsync(i -> {
                    String value = "result" + i;
                    return value;
                })
                .thenAcceptAsync(i -> System.out.println(i));
        Thread.sleep(2000);


        /**
         * ThenComposeAsync
         * */
        CompletionStage<Integer> stage5 = Helper.finishedStage();
        stage5.thenComposeAsync(i -> {
                    CompletionStage<Integer> integerCompletionStage = Helper.addOne(i);
                    return integerCompletionStage;
                })
                .thenComposeAsync(i -> Helper.addResultPrefix(i))
                .thenAcceptAsync(i -> System.out.println(i));
        Thread.sleep(2000);


        /**
         * ThenRunAsync
         * */
        CompletionStage<Integer> stage6 = Helper.finishedStage();
        stage6.thenRunAsync(() -> System.out.println("thenRunAsync"))
                .thenRunAsync(() -> System.out.println("thenRunAsync2"))
                .thenAcceptAsync(i -> System.out.println(i));
        Thread.sleep(2000);


//        /**
//         * 50개에 가까운 연산자들을 활용하여 비동기 task들을 실행하고 값을 변형하는 등 chaining을 이용한 조합이 가능
//         * */
        CompletableFuture.completedFuture("Hello")
                .thenApplyAsync(value -> value + " word")
                .thenAccept(value -> System.out.println(value))
                .thenRunAsync(() -> System.out.println("thenRun"))
                .exceptionally(e -> {
                    System.out.println(e.getMessage());
                    return null;
                });
    }
}
