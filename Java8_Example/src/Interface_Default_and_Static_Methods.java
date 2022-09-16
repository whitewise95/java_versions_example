import static java.versions.java_8.Interface_Default_and_Static_Methods.findByAll;

public interface Interface_Default_and_Static_Methods {
    /*
     * 인터페이스에 디폴트 메소드와 정적 메소드를 포함할 수 있게 되었습니다.
     *
     * 디폴트나 정적을 인터페이스에 사용하는 이유?
     *
     * 기존에 존재하던 인터페이스를 이용하여서 구현된 클래스를 만들고 사용하고 있는데 인터페이스를 보완하는 과정에서 추가적
     * 으로 구현해야 할 혹은 필수적으로 존재해야 할 메소드가 있다면, 이미 이 인터페이스를 구현한 클래스와의 호환성이 떨어 지게
     * 됩니다. 이러한 경우 default 메소드를 추가하게 된다면 하위 호환성은 유지되고 인터페이스의 보완을 진행 할 수 있습니다.
     * 실제로 스프링 프레임 워크 버전 4에서 WebMvcConfigure라는 인터페이스를 구현한 클래스 WebMvcConfigurerAdapter
     * 를 사용하였지만 자바8에서 default 메소드가 등장하고 WebMvcConfigurerAdapter클래스는 더 이상 사용되지 않습니다.
     * */
    default void save() {
        System.out.println("save");
    }

    static void findByAll() {
        System.out.println("userInfo");
    }
}

class TestClass implements Interface_Default_and_Static_Methods {
}

class DefaultMethodTest {

    public static void main(String[] args) {
        TestClass testClass = new TestClass();
        testClass.save();  // save;
        findByAll();  // userInfo;
    }

}

