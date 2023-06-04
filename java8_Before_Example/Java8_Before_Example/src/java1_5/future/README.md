# 자바 1.5

## 1. Future란?

> Future 객체는 작업이 완료될 때까지 기다렸다가 최종 결과를 얻는 데 사용하며, 때문에 지연 완료(pending completion) 객체라고도 합니다.

### 1-1.특징

- 자바 1.5에서 나온 인터페이스로 비동기적 연산의 처리 `결과를 표현하기 위해 사용`
- 비동기 처리가 완료되었는지 확인하고, 처리 완료를 기다리고, 처리 결과를 반환하는 메서드를 제공
- 멀티 스레드 환경에서 처리된 어떤 데이터를 다른 스레드에 전달가능
- 내부적으로 Thread-Safe 하게 구현

### 1-2. Future Interface의 메서드

```java
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public interface Future<V> {

    /**
     * 작업된 결과를 가지고 오는 메서드 
     * 만약 결과가 아직 리턴되지 않았다면 결과가 나올 때까지 기다립니다. 이때 중요한 점은 결과가 반환되기 전까지 애플리케이션의 진행을 'block' 
     * */
    V get() throws InterruptedException, ExecutionException;

    /**
     * Timeout을 설정하는 해당 메서드를 통해 일정 시간 내에 응답이 없으면 'TimeoutException' 예외를 발생할 수 있다.
     * */
    V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;

    /**
     * Task가 정상적으로 완료되기 전에 삭제된 경우 true를 반환
     * */
    boolean isCancelled();

    /**
     * ask가 완료되었을 경우에 true를 반환
     * 여기서 말하는 완료에는 정상적인 동작 완료, 예외, 취소 등이 있고, 해당되는 모든 경우 true를 반환
     * */
    boolean isDone();

    /**
     * 현재 작업(Task)의 중단을 시도하는 메서드
     * */
    boolean cancel(boolean mayInterruptIfRunning);
}
```

---

<br>
<br>

## 2 ExecutorService

### 2-1. 특징

- 쓰레드 풀을 이용하여 비동기적으로 작업을 실행하고 관리
- 별도의 쓰레드를 생성하고 관리하지 않아도 되므로 코드를 간결하게 유지가능
- 쓰레드 풀을 이용하여 자원을 효율적으로 관리

### 2-2. ExecutorService 메소드

```java
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public interface ExecutorService extends Executor {

    /**
     * ExecutorService 를 종료 더 이상 task 를 받지 않는다.
     * */
    void shutdown();

    /**
     * Callable 인터페이스를 구현한 작업을 쓰레드 풀에서 비동기적으로 실행하고, 
     * 해당 작업의 결과를 Future<T> 객체로 반환
     * */
    <T> Future<T> submit(Callable<T> task);

    /**
     * Runnable 인터페이스를 구현한 작업을 쓰레드 풀에서 비동기적으로 실행
     * */
    void execute(Runnable command);
}
```

### 2-3. Executors - ExecutorService 생성

```text
- newSingleThreadExecutor : 단일 쓰레드로 구성된 스레드 풀을 생성. 한 번에 하나의 작업만 실행
- newFixedThreadPool : 고정된 크기의 쓰레드 풀을 생성. 크기는 인자로 주어진 n 과 동일
- newCachedThreadPool : 사용한 쓰레드가 없다면 새로 생성해서 작업을 처리하고, 있다면 재사용. 쓰레드가 일정 시간 사용되지 않으면 회수
- newScheduledThreadPool : 스케줄링 기능을 갖춘 고정 크기의 쓰레드 풀을 생성. 주기적이거나 지연이 발생하는 작업을 실행
- newWorkStealingPool : work steal 알고리즘을 사용하는 ForkJoinPool 생성
```

---

<br>
<br>

## 3. TEST

### 3-1. 메소드 생성

> 두개의 메소드를 만든다. `getFuture` 는 그냥 1의 값을 반환하고 `getFutureCompleteAfter1s`는 1초를 기다리고 1을 반환하는 메소드이다.

```java
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
}
```

<br> 

### isDone(), isCancelled()

> `FutureExample.getFuture()` 호출해 Future 를 반환 받는다. get() 전에는 실행되지 않기에 false가 get 이후에는 실행되었기에 값과 true를 확인할 수 있다.

```java
class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        Future future = FutureExample.getFuture();
        System.out.println(future.isDone()); // false
        System.out.println(future.isCancelled()); // false

        var result = future.get();
        System.out.println(result.equals(1));  //true
        System.out.println(future.isDone());   //true
        System.out.println(future.isCancelled());  //false
    }
}
```

### TimeOut

> get에 Timeout을 설정하면 해당 시간에 메소드의 결과를 볼 수 있다.
- 1.5초를 준 future는 `getFutureCompleteAfter1s` 는 1초를 대기후 값을 반환하기에 정상 작동을 했지만
- 0.5초 를 준 future2는 TimeoutException가 터진걸 확인 할 수 있다.

```java
class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        Future future = FutureExample.getFutureCompleteAfter1s();
        var result = future.get(1500, TimeUnit.MILLISECONDS);
        System.out.println(result.equals(1));  //true


        Future future2 = FutureExample.getFutureCompleteAfter1s();
        Exception exception = null;
        try {
            Object o = future2.get(500, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            exception = e;
        }

        System.out.println(exception != null);

    }
}
```

## Future 인터페이스의 한계
- cancel을 제외하고 외부에서 future를 컨트롤할 수 없다.
- 반환된 결과를 get() 해서 접근하기 때문에 비동기 처리가 어렵다.
- 완료되었거나 에러가 발생했는지 구분하기 어렵다.









