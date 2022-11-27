# 스트림(stream) 사용방법


## 스트림 생성

##### 1. 스트림 컬렉션

- 자바의 스트림을 사용하려면 우선 스트림 객체를 생성해야한다.

```java
List<String> list = Arrays.asList("A", "B", "C");
Stream<String> stream = list.stream();
```

<br>
<br>


##### 2. 스트림 배열

- 배열의 경우 정적 메소드를 이용하면 된다.

```java
String[] arr = { "a", "b", "c" };

Stream<String> stream1 = Arrays.stream(arr);
Stream<String> stream2 = Arrays.stream(arr, 1, 3); 
//인덱스 1포함, 3제외 substring(0,10) 과 비슷한 개념
```

<br>
<br>


##### 3. 스트림 빌더
- 배열이나 컬렉션을 통해서 생성하는게 아닌 직접 값을 입력해서 스트림 객체를 생성하는 방법도 있다.

```java
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
```

<br>
<br>



##### 4. 스트림 Generator

- 데이터를 생성하는 람다식을 이용해서 스트림을 생성할 수도 있습니다.
- generate() 메소드의 인자로 "Hello"를 찍어주는 람다식을 주었습니다. 이렇게 되면 "Hello"를 무한으로 찍어내고 .limit(5) 메소드로 5개만 찍어내라는 제한을 걸어 무한생성을 막았습니다.

```java
Stream<String> stream4 = Stream.generate(() -> "Hello").limit(5);
```

<br>
<br>



##### 4. 스트림 iterate
- iterate() 메소드를 이용해서 수열 형태의 데이터를 생성할 수도 있습니다. 첫번째 매개변수 '100' 은 100을 초기 데이터로 잡고 n은 100 이며 n을 +10 을 .limit(5) 5번하겠다는 메소드입니다.

```java
Stream<Integer> stream5 = Stream.iterate(100, n -> n + 10).limit(5);
```

<br>
<br>


##### 6.  Empty 스트림
- 빈 스트림을 만들고 싶다면 .empty(); 를 사용하면됩니다.

```java
Stream<String> stream6 = Stream.empty();
```

<br>
<br>


### 7. 스트림 - 기본타입

-자바에서 기본타입에 대해서 오토박싱과 언박싱이 발생한다. int 변수를 다룰 때,  
Integer 클래스로 오토박싱해서 처리하는 경우가 있는데, 이경우 오버헤드가 발생해서 성능  
저하가 있을 수 있다고 한다. 스트림 객체의 생성에서도 마찬가지인데 오토박싱을 하지 않으려면 다음과 같이 스트림을 사용하면된다.  

```java
        IntStream intStream = IntStream.range(1, 10); // 1~ 9
        LongStream longStream = LongStream.range(1, 10000000); // 1~ 99999999
```

- 제네릭을 이용한 클래스로 사용하려면 박싱을 해서 사용해야한다.

```java
Stream<Integer> stream7 = IntStream.range(1, 10).boxed();
```

<br>
<br>



### 8. 스트림 - 문자열 스트림
- 특정 구분자를 이용해서 문자열을 스플릿 한 다음 각각을 스트림으로 뽑아낼 수도 있다.
```java
Stream<String> stream8 = Pattern.compile(",").splitAsStream("Apple,Banana,Melon");
```

<br>
<br>


### 9. 스트림 - 스트림연결
- 두 개의 스트림을 연겨래서 하나의 새로운 스트림으로 만들어 낼 수도 있습니다. Stream.concat(); 메소드를 이용합니다.

```java
        Stream<String> stream9 = Stream.of("1", "2", "3");
        Stream<String> stream10 = Stream.of("4", "5", "6");

        Stream<String> stream11 = Stream.concat(stream1, stream2);
```

<hr>

<br>
<br>


## 스트림 데이터 가공

##### 1. filter
- filter() 메소드에는 boolean값을 리턴하는 람다식을 넘겨주게 됩니다. 그러면 뽑아져 나오는 데이터에 대해 람다식을 적용해서 true가 리턴되는 데이터만 선별합니다.

```java
        Stream<Integer> stream1 = IntStream.range(1, 10).boxed();

        stream1.filter(num -> num == 1)
                .forEach(System.out::println);
```

<br>
<br>


##### 2. Map
- Map()은 스트림에서 뽑아져 나오는 데이터에 변경을 가해준다. 0 ~ 9를 생성하여 filter에서 0을 걸러내고 map에서 * 10 해주는 로직입니다.

```java
        Stream<Integer> stream2 = IntStream.range(0, 10).boxed();

        stream2.filter(num -> num != 0)
                .map(filteringNum -> filteringNum * 10)
                .forEach(System.out::println); //10 , 20 , 30 , 40 , 50 , 60 , 70 , 80 , 90

```

<br>
<br>


##### 3. flatMap
- flatMap() 메소드는 중첩된 스트림 구조를 단일 컬렉션에 대한 스트림으로 만들어주는 역활을 한다. 프로그래밍에서는 이런 작업을 플랫트닝(Flattening)이라고 한다.

```java
        Stream<List<String>> stream3 = Stream.of(
                Arrays.asList("A", "B", "C"),
                Arrays.asList("D", "E", "F")
        );   // [{"A", "B", "C"}, {"D", "E", "F"}]


        List<String> flatMap = stream3
        .flatMap(Collection::stream)
        .collect(Collectors.toList());    // {"A", "B", "C", "D", "E", "F"}
```

<br>
<br>



##### 4. sorted
- 메소드명 그대로 오름차순으로 순서를 정렬합니다.

```java
        Stream<Integer> stream4_1 = Arrays.asList(10, 1, 5, 4, 20).stream();
        Stream<Integer> stream4_2 = stream4_1.sorted().peek(System.out::println);
```

<br>
<br>



##### 5. Peek 
- peek() 메소드는 스트림 내 엘리먼트들을 대상으로 Map()매소드 처럼 연산을 수행한다.\
- 새로운 스트림으 생성하지 않고 그냥 인자로 받은 람다를 적용하기만 한다.
- peek()메소드는 그냥 한번 해본다는 말로 생성되는 데이터들에 변형을 가하지 않고 그냥 인자로 받은 람다식만 수행해준다.
- peek() 메소드는 리턴값이 있지만 forEach()는 void 메소드이다.

```java
        Stream<Integer> stream5_1 = Arrays.asList(10, 1, 5, 4, 20).stream();
        Stream<Integer> stream5_2 = stream5_1.sorted().peek(System.out::println);
```

<hr>
<br>
<br>


## 스트림 결과 생성

##### 1. 통계 값
- 정수 값을 받는 스트림의 마무리는 총합을 구하거나 최대값, 최소값, 숫자의 개수, 평균값을의 계산이다.

```java
  int sum = IntStream.range(0, 10).sum();
  Long count = IntStream.range(0, 10).count();
  OptionalInt max = IntStream.range(0, 10).max();
  OptionalInt min = IntStream.range(0, 10).min();
  OptionalDouble average = IntStream.range(0, 10).average();
```

<br>
<br>


##### 2. Reduce
- Stream의 데이터를 변환하지 않고, 더하거나 빼는 등의 연산을 수행하여 하나의 값으로 만든다면 reduce를 사용할 수 있습니다. 예를 들면, 아래 코드는 1 + 2 + ... + 10의 합을 출력해줍니다.

```java
Stream<Integer> numbers = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
Optional<Integer> sum = numbers.reduce((x, y) -> x + y);
sum.ifPresent(s -> System.out.println("sum: " + s)); // sum: 55
```

- 초기 값이 있는 reduce

```java
Stream<Integer> numbers3 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
Integer sum3 = numbers3.reduce(10, (total, n) -> total + n);
System.out.println("sum3: " + sum3);

// sum3: 65
```


<br>
<br>


##### 3. Collect
- 자바 스트림을 이용하는 가장 많은 패턴 중 하나는 컬렉션 엘리먼트 중 일부를 필터링하고, 값을 변형해서 또 다른 컬렉션으로 만드는 것이다. collect(Collectors.toList()) 를 이용해 Stream 반환값을 list로 변형한 예제입니다.

```java
        Stream<Integer> stream = Arrays.asList(10,1,5,4,20).stream();
        List<Integer> list = Arrays.asList(10,1,5,4,20).stream().collect(Collectors.toList());
```

<br>
<br>


##### 4. foreach
- 스트림에서 뽑아져 나오는 값에 대해서 어떤 작업을 하고 싶을 때 foreach()메소드를 사용합니다. 리턴값이 존재하지 않으며 중간연산보다는 최종연산이 목적의 취지에 맞습니다.

```java
        Stream<Integer> stream = Arrays.asList(10,1,5,4,20).stream();
        stream.forEach(System.out::println);
```

<hr>
<br>
<br>
<br>
<br>

# Stream 활용

##### 1. 목록에 쉼표로 구분된 문자열
- 쉼표로 구분된 숫자 문자열이 있다고 가정해 보겠습니다. 많은 코드 줄을 작성할 필요 없이 예제에 따라 숫자 목록을 얻을 수 있습니다.

```java
        String numbers = "1,2,3,4,5";

        List<Integer> numberList = Stream.of(numbers.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
```
<br>
<br>



##### 2. 목록에서 찾기
- 이름 목록에서 특정 이름을 찾고 싶다고 가정해 보겠습니다. Java 스트림을 사용하여 값이 "Amin"인 이름을 찾을 수 있습니다. 이것은 "Amin" 값이 있는 문자열을 제공하거나 존재하지 않는 경우 null을 제공합니다.

```java
        List<String> books = Arrays.asList("백설공주", "인어공주", "트라이미벌");

        books.stream().filter(book -> book.equals("백설공주"))
                .findFirst()
                //.findAny()   // 또는 findAny
                .orElse(null
```

<br>
<br>



##### 3. 객체 목록의 합계
- 모든 합계를 계산하려면 map과 reduce를 사용할 수 있습니다.
```java
        List<Long> integers = Arrays.asList(1L,2L,3L,4L,5L,6L);
        BigDecimal sum = integers.stream()
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
```

<br>
<br>


##### 4. 중복 제거 또는 가져오기
- 아래와 같이 숫자 목록이 있고 중복된 값을 제거하려고 한다고 가정해 보겠습니다. 스트림을 사용하여 중복을 가져오거나 제거할 수 있습니다.

```java
    List<Integer> numberGroup = Arrays.asList(1,1,2,2,3,4,5);
    numberGroup = numberGroup.stream().distinct().collect(Collectors.toList());
```

<br>
<br>

