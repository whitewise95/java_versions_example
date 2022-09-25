package java8_기능.함수형_인터페이스사용_가능;

import java.util.function.*;

// @FunctionalInterface
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
        FunctionalInterface functionalInterface = FunctionalInterface.addText(fi);
        functionalInterface.setText("0");
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
        Function<Integer, String> function = value -> Integer.toString(value);
        System.out.println(function.apply(10).getClass().getSimpleName()); //⚙️실행결과 : String

        // 다음과 같이 compose를 사용하여 새로운 Function을 만들 수 있습니다. apply를 호출하면 add 먼저 수행되고 그 이후에 multiply가 수행됩니다.
        Function<Integer, Integer> multiply = (value) -> value * 2;
        Function<Integer, Integer> add = (value) -> value + 3;
        Function<Integer, Integer> addThenMultiply = multiply.compose(add);
        System.out.println(addThenMultiply.apply(3));  //⚙️실행결과 : 12



        /* ⭐️다섯번째 : Predicate - Predicate<T>는 T타입 인자를 받고 결과로 boolean을 리턴합니다. */
        Predicate<Integer> isEven = value -> value % 2 == 0;
        System.out.println(isEven.test(10));  //⚙️실행결과 : true

        // and()는 두개의 Predicate가 true일 때 true
        Predicate<Integer> isBiggerThanFive = value -> value % 2 == 0;
        Predicate<Integer> isLowerThanSix = value -> value < 6;
        System.out.println(isBiggerThanFive.and(isLowerThanSix).test(4)); //⚙️실행결과 : true 4는 6보다 작으며, 짝수이기 때문에

        // or()는 두개 중에 하나만 true이면 true
        System.out.println(isBiggerThanFive.or(isLowerThanSix).test(10));   //⚙️실행결과 : true 10은 4보다 크지만, 짝수이기때문에
        System.out.println(isBiggerThanFive.or(isLowerThanSix).test(11));   //⚙️번외 실행결과 : false 11은 4보다 크며, 홀수 이기때문에

    }
}

interface FunctionalInterface {
    void setText(String value);

    static FunctionalInterface addText(FunctionalInterface functionalInterface) {
        return value -> functionalInterface.setText(value);
    }
}