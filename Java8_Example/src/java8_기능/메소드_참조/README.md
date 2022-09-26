[https://steady-coding.tistory.com/598](https://steady-coding.tistory.com/598) 참고

# 메소드_참조
- 메소드 참조는 람다식을 좀 더 짧고 읽기 쉽게 사용하는 것이다. 메소드 참조에는 4가지 방법이 있다.

## Test전 Setting
```java
public class Ramda {

    public static void main(String[] args) {
    }

    static boolean isEquals(Fruit fruit){
        return fruit.getName().equals("수박");
    }
}


class Fruit {
    private int id;
    private String name;

    {생성자...}

    {getter, setter...}

    public boolean isEquals(Fruit fruit) {
        return this.getName().equals(fruit.getName());
    }

    
}
```

## 1. 정적 메서드 참조
- 정적 메서드에 대한 참조는 클래스::메소드명으로 사용된다.
- `#1` : 기존 람다시을 사용한 방법
- `#2` : 참조식으로 표현
```java
        List<Fruit> fruitList = new ArrayList<>();
        fruitList.add(new Fruit(1,"수박"));

        boolean isRamda = fruitList.stream().anyMatch(fruit -> Ramda.isEquals(fruit)); // #1
        
        boolean isReference = fruitList.stream().anyMatch(Ramda::isEquals);  // #2
        System.out.println(isReference); // true
```
<hr>

## 2. 인스턴스 메서드 참조
- 인스턴스 메서드에 대한 참조는 인스턴스::메소드명으로 사용된다.

```java
        List<Fruit> fruitList = new ArrayList<>();
        fruitList.add(new Fruit(1,"수박"));

        Fruit fruit = new Fruit(1, "과자");
        Boolean isFruitSaneName = fruitList.stream().anyMatch(fruit::isEquals);
        System.out.println(isFruitSaneName);  // false
```

## 3. 특정 유형의 객체의 인스턴스 메서드 참조
- 특정 유형(String, BigDecimal 등)의 인스턴스 메소드에 대해 메소드 참조를 사용할 수 있다.

```java
        List<Fruit> fruitList = new ArrayList<>();
        fruitList.add(new Fruit(1,"수박"));
        
        List<String> strList = new ArrayList<>();
        strList.add("test");
        strList.add("");
        strList.add("");

        long count = strList.stream().filter(String::isEmpty).count();
        System.out.println(count);  // 2
```
<hr>

## 4. 생성자 참조
- 생성자 참조는 클래스명::new로 사용한다. 생성자는 특수한 메소드이기 대문에, 메소드 이름으로 new를 사용한다.

```java
    List<Fruit> fruitList = new ArrayList<>();
    fruitList.add(new Fruit(1,"수박"));

    List<Fruit> integerList = fruitList.stream().map(Fruit::new)
                                                .collect(Collectors.toList());
    
```
