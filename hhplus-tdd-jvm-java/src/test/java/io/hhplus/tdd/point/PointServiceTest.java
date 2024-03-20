package io.hhplus.tdd.point;

import io.hhplus.tdd.database.UserPointTable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PointServiceTest {
    @Autowired
    private PointService pointService;
    @Autowired
    private UserPointTable userPointTable;

    @DisplayName("id로 조회한 유저가 존재하지 않는 경우 NoSuchElementException 예외가 발생한다.")
    @Test
    public void userDoesNotExist() {
        // Given
        userPointTable.insertOrUpdate(1L, 100L);

        // When
        // ...

        // Then
        assertThatThrownBy(() -> {
            pointService.getUserPointById(2L);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("해당 유저의 포인트가 존재하지 않습니다.");
    }

    @DisplayName("")
    @Test
    void test() {
        // Given

        // When

        // Then

    }
}