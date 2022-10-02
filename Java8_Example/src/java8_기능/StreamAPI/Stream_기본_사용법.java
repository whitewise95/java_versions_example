package java8_기능.StreamAPI;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.*;

public class Stream_기본_사용법 {
    /**
     * Stream 기본 생성방법
     */
    public void streamBasic() {

        //  #1 스트림 컬렉션
        List<String> list = Arrays.asList("A", "B", "C");
        Stream<String> stream = list.stream();

        //  #2 스트림 배열
        String[] arr = { "a", "b", "c" };

        Stream<String> stream1 = Arrays.stream(arr);
        Stream<String> stream2 = Arrays.stream(arr, 1, 3); //인덱스 1포함, 3제외 substring(0,10) 과 비슷한 개념

        //  #3  스트림 빌더
        Stream<Object> streamBuilder = Stream.builder()
                .add("Apple1")
                .add("Apple2")
                .add("Apple3")
                .build();

        Stream<String> streamMap = Stream.builder()
                .add("Apple1")
                .add("Apple2")
                .add("Apple3")
                .build()
                .map(String::valueOf);

        // #4 스트림 Generator
        Stream<String> stream4 = Stream.generate(() -> "Hello").limit(5);

        // #5 스트림 iterate
        Stream<Integer> stream5 = Stream.iterate(100, n -> n + 10).limit(5);

        // #6 Empty 스트림
        Stream<String> stream6 = Stream.empty();

        // #7 스트림 - 기본타입
        IntStream intStream = IntStream.range(1, 10); // 1~ 9
        LongStream longStream = LongStream.range(1, 10000000); // 1~ 99999999

        // 제네릭을 이용한 클래스로 사용하려면 박싱을 해서 사용해야한다.
        Stream<Integer> stream7 = IntStream.range(1, 10).boxed();

        // #8 스트림 - 문자열 스트림
        Stream<String> stream8 = Pattern.compile(",").splitAsStream("Apple,Banana,Melon");

        // #9  스트림 - 스트림연결
        Stream<String> stream9 = Stream.of("1", "2", "3");
        Stream<String> stream10 = Stream.of("4", "5", "6");

        Stream<String> stream11 = Stream.concat(stream1, stream2);

    }

    /**
     * 스트림 데이터 가공
     */
    public void streamProcessing() {
        // #1 filter
        Stream<Integer> stream1 = IntStream.range(1, 10).boxed();

        stream1.filter(num -> num == 1)
                .forEach(System.out::println);

        // #2 Map
        Stream<Integer> stream2 = IntStream.range(0, 10).boxed();

        stream2.filter(num -> num != 0)
                .map(filteringNum -> filteringNum * 10)
                .forEach(System.out::println); //10 , 20 , 30 , 40 , 50 , 60 , 70 , 80 , 90

        // #3 flatMap
        Stream<List<String>> stream3 = Stream.of(
                Arrays.asList("A", "B", "C"),
                Arrays.asList("D", "E", "F")
        );   // [{"A", "B", "C"}, {"D", "E", "F"}]

        List<String> flatMap = stream3
                .flatMap(Collection::stream)
                .collect(Collectors.toList());    // {"A", "B", "C", "D", "E", "F"}

        // #4 sorted
        Stream<Integer> stream4_1 = Arrays.asList(10, 1, 5, 4, 20).stream();
        Stream<Integer> stream4_2 = stream4_1.sorted().peek(System.out::println);

        // #5 Peek - peek() 메소드는 리턴값이 있지만 forEach()는 void 메소드이다.
        Stream<Integer> stream5_1 = Arrays.asList(10, 1, 5, 4, 20).stream();
        Stream<Integer> stream5_2 = stream5_1.sorted().peek(System.out::println);
    }

    /**
     * 스트림 결과 생성
     * 지금까지 본 데이터 수정 연산들은 또 데이터에 수정을 가한 결과 데이터들을 만들어내는 또 다른 스트림 객체를 리턴했다.
     * 즉 중간작업들이며 이들만으로는 의미 있는 스트림을 만들 수 없다. 데이터를 가공하고, 필터링한 다음 그 값을 출력하거나
     * 또 다른 컬렉션으로 모아두는 등의 마무리 작업이 필요하다.
     */
    public void streamResult() {

        // #1 통계 값
        int sum = IntStream.range(0, 10).sum();
        Long count = IntStream.range(0, 10).count();
        OptionalInt max = IntStream.range(0, 10).max();
        OptionalInt min = IntStream.range(0, 10).min();
        OptionalDouble average = IntStream.range(0, 10).average();


        // #2 Reduce
        Stream<Integer> numbers2 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum2 = numbers2.reduce(10, (total, n) -> total + n);
        System.out.println("sum3: " + sum2); // sum3: 65


        Stream<Integer> numbers3 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum3 = numbers3.reduce(10, (total, n) -> total + n);
        System.out.println("sum3: " + sum3); // sum3: 65

        /*
        *   #3 Collect
        *   자바 스트림을 이용하는 가장 많은 패턴 중 하나는 컬렉션 엘리먼트 중 일부를 필터링하고, 값을 변형해서 또 다른 컬렉션으로 만드는 것이다.
        *  .collect(Collectors.toList()) 를 이용해 Stream 반환값을 list로 변형한 예제입니다.
        * */
        Stream<Integer> stream3 = Arrays.asList(10,1,5,4,20).stream();
        List<Integer> list3 = Arrays.asList(10,1,5,4,20).stream().collect(Collectors.toList());

        // #4 foreach
        Stream<Integer> stream = Arrays.asList(10,1,5,4,20).stream();
        stream.forEach(System.out::println);
    }
}
