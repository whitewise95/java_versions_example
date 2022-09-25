package java8_기능.인터페이스에_디폴트_메소드와_정적_메소드_포함가능;

//@FunctionalInterface 어노테이션을 사용하는데, 이 어노테이션은 해당 인터페이스가 함수형 인터페이스 조건에 맞는지 검사해줍니다.
//인터페이스 검증과 유지보수를 위해 붙여주는 게 좋습니다.
interface InterfaceClass {

    default void save() {
        System.out.println("save");
    }

    static void findByAll() {
        System.out.println("userInfo");
    }
}

class TestClass implements InterfaceClass {
}

public class Interface_Default_and_Static_Methods {
    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        testClass.save();  // save
        InterfaceClass.findByAll(); // userInfo
    }
}
