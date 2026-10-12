import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JUnitQuiz {

    @Test
    public void junitQuiz1(){
        String name1 = "홍길동";
        String name2 = "홍길동";
        String name3 = "홍길은";

        // null이 아닌지 확인
        assertThat(name1).isNotNull();

        // name1과 name2가 같은지 확인
        assertThat(name1).isEqualTo(name2);

        // name1과 name2가 다른지 확인
        assertThat(name1).isNotEqualTo(name3);
    }

    @Test
    public void junitQuiz2(){
        int n1 = 15;
        int n2 = 0;
        int n3 = -5;

        // n1이 양수인지 확인
        assertThat(n1).isPositive();

        // n3가 음수인지 확인
        assertThat(n3).isNegative();

        // n1이 n2보다 큰지 확인
        assertThat(n1).isGreaterThan(n2);

        // n3가 n2보다 작은지 확인
        assertThat(n3).isLessThan(n2);
    }
}
