package java8_기능.StreamAPI;

import java.util.*;
import java.util.stream.Collectors;

public class Stream_병렬처리 {

    public static void main(String[] args) {
        System.out.println("==============10000건===================");
        List<Integer> integers2 = new ArrayList<>();
        for (int i=0; i < 1000000; i++){
            integers2.add(i);
        }
        System.out.println(" useFor : " + useFor(integers2));
        System.out.println(" useStream1Filter  : " + useStream1Filter(integers2));
        System.out.println(" useParallelStreamFilter :  " + useParallelStreamFilter(integers2));
        System.out.println(" useStream2Filters  : " + useStream2Filters(integers2));

        List<Integer> integers = new ArrayList<>();
        for (int i=0; i < 70000000; i++){
            integers.add(i);
        }
        System.out.println("==============70000000건===================");
        System.out.println(" useFor : " + useFor(integers));
        System.out.println(" useStream1Filter  : " + useStream1Filter(integers));
        System.out.println(" useParallelStreamFilter :  " + useParallelStreamFilter(integers));
        System.out.println(" useStream2Filters  : " + useStream2Filters(integers));


        System.out.println("==============10000건===================");
        System.out.println(" useFor : " + useForCount(integers2));
        System.out.println(" useStream1Filter  : " + useStream1FilterCount(integers2));
        System.out.println(" useParallelStreamFilter :  " + useParallelStreamFilterCount(integers2));

        System.out.println("==============70000000건===================");
        System.out.println(" useFor : " + useForCount(integers));
        System.out.println(" useStream1Filter  : " + useStream1FilterCount(integers));
        System.out.println(" useParallelStreamFilter :  " + useParallelStreamFilterCount(integers));
    }

    public static void test1() {
        Long start = System.currentTimeMillis();


    }

    public static Long useFor(List<Integer> integerList) {
        Long start = System.currentTimeMillis();
        List<Integer> res = new ArrayList<>();
        for(int i : integerList) {
            if(i % 2 == 0 && i % 3 == 0) {
                res.add(i);
            }
        }
        Long end = System.currentTimeMillis();
        return end - start;
    }

    public static Long useStream1Filter(List<Integer> integerList) {
        Long start = System.currentTimeMillis();
        List<Integer> res = integerList
                .stream()
                .filter(i-> i % 2 == 0 && i % 3 == 0)
                .collect(Collectors.toList());
        Long end = System.currentTimeMillis();
        return end - start;
    }

    public static Long useParallelStreamFilter(List<Integer> integerList) {
        Long start = System.currentTimeMillis();
        List<Integer> res = integerList
                .parallelStream()
                .filter(i-> i % 2 == 0 && i % 3 == 0)
                .collect(Collectors.toList());
        Long end = System.currentTimeMillis();
        return end - start;
    }

    public static Long useStream2Filters(List<Integer> integerList) {
        Long start = System.currentTimeMillis();
        List<Integer> res = integerList
                .stream()
                .filter(i-> i % 2 == 0)
                .filter(i -> i % 3 == 0)
                .collect(Collectors.toList());
        Long end = System.currentTimeMillis();
        return end - start;
    }


    public static long useForCount(List<Integer> integerList) {
        Long start = System.currentTimeMillis();
        long count = 0;
        for(int i : integerList) {
            if(i % 2 == 0 && i % 3 == 0) {
                count++;
            }
        }
        Long end = System.currentTimeMillis();
        return  end - start;
    }

    public static long useStream1FilterCount(List<Integer> integerList) {
        Long start = System.currentTimeMillis();
         integerList
                .stream()
                .filter(i-> i % 2 == 0 && i % 3 == 0)
                .count();
        Long end = System.currentTimeMillis();
        return end - start;
    }

    public static long useParallelStreamFilterCount(List<Integer> integerList) {
        Long start = System.currentTimeMillis();
         integerList
                .parallelStream()
                .filter(i-> i % 2 == 0 && i % 3 == 0)
                .count();
        Long end = System.currentTimeMillis();
        return end - start;
    }
}

