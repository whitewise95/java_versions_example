package java8_기능.메소드_참조;

import java.util.*;
import java.util.stream.Collectors;

public class Ramda {

    public static void main(String[] args) {
        List<Fruit> fruitList = new ArrayList<>();
        fruitList.add(new Fruit(1,"수박"));

        /**
         * 정적 메서드 참조
         * 정적 메서드에 대한 참조는 클래스::메소드명으로 사용된다.
         * */
        boolean isRamda = fruitList.stream().anyMatch(fruit -> Ramda.isEquals(fruit)); // 기존 람다식
        boolean isReference = fruitList.stream().anyMatch(Ramda::isEquals);  // 참조방식
        System.out.println(isReference); // true

        /**
         *  인스턴스 메서드 참조
         *  인스턴스 메서드에 대한 참조는 인스턴스::메소드명으로 사용된다.
         * */
        Fruit fruit = new Fruit(1, "과자");
        Boolean isFruitSaneName = fruitList.stream().anyMatch(fruit::isEquals);
        System.out.println(isFruitSaneName);  // false

        /**
         * 특정 유형의 객체의 인스턴스 메서드 참조
         * 특정 유형(String, BigDecimal 등)의 인스턴스 메소드에 대해 메소드 참조를 사용할 수 있다.
         * */
        List<String> strList = new ArrayList<>();
        strList.add("test");
        strList.add("");
        strList.add("");

        long count = strList.stream().filter(String::isEmpty).count();
        System.out.println(count);  // 2


        /**
         * 생성자 참조
         * 생성자 참조는 클래스명::new로 사용한다. 생성자는 특수한 메소드이기 대문에, 메소드 이름으로 new를 사용한다.
         * */
        List<Fruit> integerList = fruitList.stream().map(Fruit::new).collect(Collectors.toList());
    }

    static boolean isEquals(Fruit fruit){
        return fruit.getName().equals("수박");
    }
}


class Fruit {
    private int id;
    private String name;

    public Fruit(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Fruit(Fruit fruit) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEquals(Fruit fruit) {
        return this.getName().equals(fruit.getName());
    }
}