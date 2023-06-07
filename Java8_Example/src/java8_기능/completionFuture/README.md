# CompletableFuture 

## 1. CompletableFuture 클래스  
```java
public class CompletableFuture<T> implements Future<T>, CompletionStage<T> {
    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {return asyncSupplyStage(asyncPool, supplier);}
    public static CompletableFuture<Void> runAsync(Runnable runnable) {return asyncRunStage(asyncPool, runnable);}
    public boolean complete(T value) {...}
    public boolean completeExceptionally(Throwable ex) {...}
    public static CompletableFuture<Void> allOf(CompletableFuture<?>... cfs) {return andTree(cfs, 0, cfs.length - 1);}
    public static CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs) {return orTree(cfs, 0, cfs.length - 1);}
}
```  
<br>
<br>  

---

## 2. supplyAsync
- Supplier를 제공하여 CompletableFuture를 생성 가능
- Supplier의 반환값이 CompletableFuture의 결과로 내려받기 가능
```java
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
```  
<br>
<br>  

---



## 3. runAsync 
- Runnable를 제공하여 CompletableFuture를 생성할 수 있다.
- 값을 반환하지 않습는다.
- 다음 task에 null이 전달된다.
```java
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
```  
<br>
<br>  

---

## 4. completedExceptionally
- Exception에 의해서 complete 되었는지 확인 할 수 있다.
```java
System.out.println("==================================completedExceptionally=====================================");
CompletableFuture<Integer> completedExceptionally = CompletableFuture.supplyAsync(() -> {
    return 1 / 0;
});
Thread.sleep(100);
System.out.println(completedExceptionally.isDone());
System.out.println(completedExceptionally.isCompletedExceptionally());
```  
<br>
<br>  

---


## 5. allOf And anyOf

### 테스트를 위한 waitAndReturn 메소드 생성 
```java
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
```

### 5-1. allOf
- 여러 CompletableFuture를 모아서 하나의 CompletableFuture로 변경할 수 있다.
- 모든 CompletableFuture가 완료되면 상태가 done으로 변경
- Void를 반환하므로 각각의 값에 get으로 접근해야 한다.
```java
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
    System.out.println(end - start + "ms");  // 1012ms
```

### 5-2. anyOf
- 여러 CompletableFuture를 모아서 하나의 CompletableFuture로 변경할 수 있다.
- 주어진 CompletableFuture가 하나라도 완료되면 상태가 done으로 변경
- 제일 먼저 done상태가 되는  future을 반환한다.
```java
long start1 = System.currentTimeMillis();
CompletableFuture<Integer> first2 = CompletableFutureTest.waitAndReturn(100, 1);
CompletableFuture<Integer> second2 = CompletableFutureTest.waitAndReturn(500, 2);
CompletableFuture<Integer> third2 = CompletableFutureTest.waitAndReturn(1000, 3);
CompletableFuture.anyOf(first2, second2, third2).thenAcceptAsync(System.out::println).join();

long end1 = System.currentTimeMillis();
System.out.println(end1 - start1 + "ms"); //105ms
```




