[https://codechacha.com/ko/java8-functional-interface/](https://codechacha.com/ko/java8-functional-interface/) 를 참고함

# 함수형 인터페이스

> java8 에서는 함수형 인터페이스를 사용할 수 있다.
> 그렇다면 이 함수형 인터페이스를 사용하는 이유는?

Javascript같은 언어는 함수만 따로 존재할 수 있고, 함수도 객체 취급해서 함수 자체를 인자로 넘길 수 있습니다.이러한 부분은 프로그래밍 할때 굉장히 장점이 될 수 있고, 함수형 프로그래밍을 할 때 반드시
필요한 부분입니다.

자바는 함수만 따로 존재할 수 없기 때문에 1개의 메소드만 가진 인터페이스를 두고, 그 인터페이스를 구현하고 있는 이름없는 객체를 람다식으로 표현함으로써 인자로 전달할 수 있게 한 것입니다.

## 기본 사용법

<br>

##### 1. `FunctionalInterface` 인터페이스 생성

- `setText` - 추상메소드
- `addText` - `FunctionalInterface`을 매개변수로 받고 매개변수의 `setText`를 실행하고 자신의 `setText`를 실행하는 메소드

```java
interface FunctionalInterface {
    void setText(String value);

    static FunctionalInterface addText(FunctionalInterface functionalInterface) {
        return value -> functionalInterface.setText(value);
    }
}
```

<br>

##### 2. Test

- `setText()`에 인자로 문자열을 전달하면 람다식에 정의된 것처럼 로그로 출력을 합니다.

```java
        FunctionalInterface fi=value->System.out.println(value);
        fi.setText("hello word");
//⚙️실행결과 : "hello word"
```

<br>

##### 3. 번외

- `#1` : `fi`에 setText를 정의해준다.
- `#2` : 새로운 `FunctionalInterface` 인스턴스를 만들어 `addText()` 메소드에 `fi`를 인자로 준다.
- `#3` : 2번째로 만들어준 `functionalInterface`의 `setText`로 `#4`에 출력할 데이터를 인자로 준다.

```java
        FunctionalInterface fi=value->System.out.println(value);

        // #1
        fi=value->{
        for(int i=3;i>Integer.parseInt(value);i--){
        System.out.println(i);
        }
        System.out.println(value);  // #4
        };

        // #2
        FunctionalInterface functionalInterface=FunctionalInterface.addText(fi);

        // #3
        functionalInterface.setText("0");
```

<br>
<br>
<br>

# 기본함수형 인터페이스

> 위의 예제처럼 람다식을 사용할 때마다 함수형 인터페이스를 매번 정의하기에는 불편하기 때문에 자바에서 라이브러리로 제공하는 것들이 있습니다.

## Runnable

- 인자를 받지 않고 리턴값도 없는 인터페이스입니다.

```java
@FunctionalInterface
public interface Runnable {
    public abstract void run();
}
```

##### Test

```java
        Runnable runnable=()->System.out.println("Run");
        runnable.run();  //⚙️실행결과 : "Run"
```

<hr>

## Supplier<T>

- 인자를 받지 않고 T 타입의 객체를 리턴합니다.

```java
@FunctionalInterface
public interface Supplier<T> {
    T get();
}
```

##### Test

```java
        Supplier<Integer> supplierI = () -> 1;
        System.out.println(supplierI.get());  //⚙️실행결과 : 1

        Supplier<String> supplierS=()->"text";
        System.out.println(supplierS.get()); //⚙️실행결과 : "text"
```

<hr>

## Consumer<T>

- T 타입의 객체를 인자로 받고 리턴 값은 없습니다.

```java
@FunctionalInterface
public interface Consumer<T> {

    void accept(T t);

    default Consumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
```

##### Test

```java
        Consumer<String> printString = text -> System.out.println("Miss "+text+"?");
        printString.accept("me");  //⚙️실행결과 : "Miss me?"

        // 또한, andThen()을 사용하면 두개 이상의 Consumer를 연속적으로 실행할 수 있습니다.
        Consumer<String> printString2 = text -> System.out.println("--> Yes");
        printString.andThen(printString2)
        .accept("me"); //⚙️실행결과 : "Miss me?", "--> Yes"
```

<hr>

## Function<T, R>

- T : 인자를 받고
- R : 객체를 리턴합니다.

```java
@FunctionalInterface
public interface Function<T, R> {

    R apply(T t);

    default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    static <T> Function<T, T> identity() {
        return t -> t;
    }
}
```

##### Test

```java
        Function<Integer, String> function = value -> Integer.toString(value);
        System.out.println(function.apply(10).getClass().getSimpleName()); //⚙️실행결과 : String

        // 다음과 같이 compose를 사용하여 새로운 Function을 만들 수 있습니다. apply를 호출하면 add 먼저 수행되고 그 이후에 multiply가 수행됩니다.
        Function<Integer, Integer> multiply = (value) -> value * 2;
        Function<Integer, Integer> add = (value) -> value + 3;
        Function<Integer, Integer> addThenMultiply = multiply.compose(add);
        System.out.println(addThenMultiply.apply(3));  //⚙️실행결과 : 12
```


## Predicate<T>
- T타입 인자를 받고 결과로 boolean을 리턴합니다.

```java
@FunctionalInterface
public interface Predicate<T> {
    
    boolean test(T t);
    
    default Predicate<T> and(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) && other.test(t);
    }

    default Predicate<T> negate() {
        return (t) -> !test(t);
    }

    default Predicate<T> or(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) || other.test(t);
    }

    static <T> Predicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? Objects::isNull
                : object -> targetRef.equals(object);
    }
}
```

##### Test

```java
        Predicate<Integer> isEven = value -> value % 2 == 0;
        System.out.println(isEven.test(10));  //⚙️실행결과 : true

        // and()는 두개의 Predicate가 true일 때 true
        Predicate<Integer> isBiggerThanFive = value -> value % 2 == 0;
        Predicate<Integer> isLowerThanSix = value -> value < 6;
        System.out.println(isBiggerThanFive.and(isLowerThanSix).test(4)); //⚙️실행결과 : true 4는 6보다 작으며, 짝수이기 때문에

        // or()는 두개 중에 하나만 true이면 true
        System.out.println(isBiggerThanFive.or(isLowerThanSix).test(10));   //⚙️실행결과 : true 10은 4보다 크지만, 짝수이기때문에
        System.out.println(isBiggerThanFive.or(isLowerThanSix).test(11));   //⚙️번외 실행결과 : false 11은 4보다 크며, 홀수 이기때문에

```






