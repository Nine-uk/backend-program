import org.junit.jupiter.api.*;

public class JUnitTest {

    @DisplayName("1+2는 3이다")
    @Test
    public void junitTest1(){
        int a = 1;
        int b = 2;
        int sum = 3;

        int result = a + b;

        System.out.println("1+2는 3이다");

        Assertions.assertEquals(sum,result);
    }

    @DisplayName("1+3은 3이다")
    @Test
    public void junitTest2() {
        int a = 1;
        int b = 3;

        int result = a+b;

        System.out.println("1+3는 3이다");

        // Assertions.assertEquals(비교값,기대값);
        Assertions.assertEquals(3,result);

        // 같은 기능 assertThat(기대값).isEqualTo(기대값); << 요걸 더 많이 쓴데
    }

    @BeforeEach
    public void prepare(){
        System.out.println("준비");
    }

    @AfterEach
    public void cleanup(){
        System.out.println("설겆이");
    }

    @BeforeAll
    public static void prepareAll(){
        System.out.println("최초 준비");
    }

    @AfterAll
    public static void cleanupAll(){
        System.out.println("최종 마무리");
    }
}
