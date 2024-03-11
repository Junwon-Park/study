package sample.cafekiosk.unit.beverage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//import static org.junit.jupiter.api.Assertions.*; // JUnit
import static org.assertj.core.api.Assertions.assertThat; // AssertJ 라이브러리의 assertThat (static import)

class AmericanoTest {

    @Test
    void getName() {
        Americano americano = new Americano();

//        assertEquals(americano.getName(), "아메리카노"); // JUnit의 assertEquals
        assertThat(americano.getName()).isEqualTo("아메리카노"); // AssertJ의 assertThat() -> 메서드 체이닝 형식으로 작성 가능
    }

    @Test
    void getPrice() {
        Americano americano = new Americano();

        assertThat(americano.getPrice()).isEqualTo(4000);
    }
}