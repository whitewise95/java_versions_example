package java8_기능.StreamAPI;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.*;

public class Stream_활용 {

    public void stream() {
        /**
         * 목록에 쉼표로 구분된 문자열
         */
        String numbers = "1,2,3,4,5";

        List<Integer> numberList = Stream.of(numbers.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        // 1,2,3,4,5

        /**
         * 목록에서 찾기
         */
        List<String> books = Arrays.asList("백설공주", "인어공주", "트라이미벌");

        books.stream().filter(book -> book.equals("백설공주"))
                .findFirst()
                //.findAny()   // 또는 findAny
                .orElse(null);

        /**
         * 객체 목록의 합계
         * */
        List<Long> integers = Arrays.asList(1L,2L,3L,4L,5L,6L);
        BigDecimal sum = integers.stream()
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        /**
         * 중복 제거 또는 가져오기
         * */
        List<Integer> numberGroup = Arrays.asList(1,1,2,2,3,4,5);
        numberGroup = numberGroup.stream().distinct().collect(Collectors.toList());
    }
}
