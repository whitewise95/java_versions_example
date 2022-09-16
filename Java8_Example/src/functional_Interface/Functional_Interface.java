package functional_Interface;

import java.util.Objects;

/*
 *  ⭐️자바8 에서는 함수형 인터페이스를 사용할 수 있다. 그렇다면 이 함수형 인터페이스를 사용하는 이유는?
 *
 * 1.자바의 람다식은 함수형 인터페이스로만 접근이 가능
 * 2.람다식으로 만든 객체에 접근하기 위해
 * */
public class Functional_Interface {
    public static void main(String[] args) {

        /* ⭐️1. setText()에 인자로 문자열을 전달하면 람다식에 정의된 것처럼 로그로 출력을 합니다.  */
        FunctionalInterface fi = value -> System.out.println(value);
        fi.setText("hello word");
        //⚙️실행결과 : "hello word"

        fi = value -> {
            for (int i = 3; i > Integer.parseInt(value); i--) {
                System.out.println(i);
            }
            System.out.println(value);
        };
        fi.setText("0");
        //⚙️실행결과 : "3","2","1","0"


        /* ⭐️2. 위의 예제처럼 람다식을 사용할 때마다 함수형 인터페이스를 매번 정의하기에는 불편하기 때문에 자바에서 라이브러리로 제공하는 것들이 있습니다.
         *       첫번째 : Runnable - 인자를 받지 않고 리턴값도 없는 인터페이스입니다.
         * */
        Runnable runnable = () -> System.out.println("Run");
        runnable.run();  //⚙️실행결과 : "Run"



        /* ⭐️두번째 : Supplier - 인자를 받지 않고 T 타입의 객체를 리턴합니다. */
        Supplier<Integer> supplierI = () -> 1;
        System.out.println(supplierI.get());  //⚙️실행결과 : 1

        Supplier<String> supplierS = () -> "text";
        System.out.println(supplierS.get()); //⚙️실행결과 : "text"




        /* ⭐️세번째 : Consmer - Consumer<T>는 T 타입의 객체를 인자로 받고 리턴 값은 없습니다. */
        Consumer<String> printString = text -> System.out.println("Miss " + text + "?");
        printString.accept("me");  //⚙️실행결과 : "Miss me?"

        // 또한, andThen()을 사용하면 두개 이상의 Consumer를 연속적으로 실행할 수 있습니다.
        Consumer<String> printString2 = text -> System.out.println("--> Yes");
        printString.andThen(printString2)
                .accept("me"); //⚙️실행결과 : "Miss me?", "--> Yes"



        /* ⭐️네번째 : Function - Function<T, R>는 T타입의 인자를 받고, R타입의 객체를 리턴합니다. */


    }
}

interface FunctionalInterface {
    void setText(String value);
}

interface Runnable {
    void run();
}

interface Supplier<T> {
    T get();
}

interface Consumer<T> {
    void accept(T t);

    default Consumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> {
            accept(t);
            after.accept(t);
        };
    }
}


interface Function<T, R> {
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