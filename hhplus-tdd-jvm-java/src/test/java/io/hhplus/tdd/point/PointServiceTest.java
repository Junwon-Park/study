package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class PointServiceTest {
    @Autowired
    private PointService pointService;
    @Autowired
    private UserPointTable userPointTable;
    @Autowired
    private PointHistoryTable pointHistoryTable;

    @AfterEach
    public void afterEach() {

    }

    @DisplayName("id로 조회한 유저가 존재하지 않는 경우 NoSuchElementException 예외가 발생한다.")
    @Test
    public void userDoesNotExist() {
        // Given
        userPointTable.insertOrUpdate(1L, 100L);

        // Then
        assertThatThrownBy(() -> {
            pointService.getUserPointById(2L);
        })
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("해당 유저의 포인트가 존재하지 않습니다.");
    }

    @DisplayName("id로 조회한 유저가 존재하는 경우 UserPoint 객체를 반환한다.")
    @Test
    void userExist() {
        // Given
        userPointTable.insertOrUpdate(1L, 100L);

        // When
        UserPoint userPoint =  pointService.getUserPointById(1L);

        // Then
        assertThat(userPoint).isInstanceOf(UserPoint.class);
        assertThat(userPoint.id()).isEqualTo(1L);
        assertThat(userPoint.point()).isEqualTo(100L);
    }

    @DisplayName("해당 id의 유저에 대한 내역이 없는 경우 빈 배열을 반환한다.")
    @Test
    void noUserHistoryReturnsEmptyList() {
        // Given
        pointHistoryTable.insert(1L, 100L, TransactionType.CHARGE, System.currentTimeMillis());
        pointHistoryTable.insert(2L, 200L, TransactionType.USE, System.currentTimeMillis());

        // When
        List<PointHistory> pointHistories = pointService.getHistoriesById(3L);

        // Then
        assertThat(pointHistories).hasSize(0);
    }

    @DisplayName("해당 id의 유저에 대한 내역이 존재하는 경우 해당 유저의 내역 리스트가 반환된다.")
    @Test
    void userHistoryReturnsUserHistoryList() {
        // Given
        pointHistoryTable.insert(1L, 100L, TransactionType.CHARGE, System.currentTimeMillis());
        pointHistoryTable.insert(2L, 200L, TransactionType.CHARGE, System.currentTimeMillis());
        pointHistoryTable.insert(2L, 100L, TransactionType.USE, System.currentTimeMillis());
        pointHistoryTable.insert(2L, 600L, TransactionType.CHARGE, System.currentTimeMillis());
        pointHistoryTable.insert(2L, 500L, TransactionType.USE, System.currentTimeMillis());
        pointHistoryTable.insert(3L, 300L, TransactionType.CHARGE, System.currentTimeMillis());
        pointHistoryTable.insert(3L, 100L, TransactionType.USE, System.currentTimeMillis());

        // When
        List<PointHistory> pointHistories_1L = pointService.getHistoriesById(1L);
        List<PointHistory> pointHistories_2L = pointService.getHistoriesById(2L);
        List<PointHistory> pointHistories_3L = pointService.getHistoriesById(3L);

        System.out.println(pointHistories_3L);

        // Then
        // 1L
        assertThat(pointHistories_1L).hasSize(1);
        assertThat(pointHistories_1L.get(0)).isInstanceOf(PointHistory.class);
        assertThat(pointHistories_1L.get(0).userId()).isEqualTo(1L);
        assertThat(pointHistories_1L.get(0).amount()).isEqualTo(100L);
        assertThat(pointHistories_1L.get(0).type()).isEqualTo(TransactionType.CHARGE);

        // 2L
        assertThat(pointHistories_2L).hasSize(4);
        assertThat(pointHistories_2L.get(0)).isInstanceOf(PointHistory.class);
        assertThat(pointHistories_2L.get(0).userId()).isEqualTo(2L);
        assertThat(pointHistories_2L.get(0).amount()).isEqualTo(200L);
        assertThat(pointHistories_2L.get(0).type()).isEqualTo(TransactionType.CHARGE);

        // 3L
        assertThat(pointHistories_3L).hasSize(2);
        assertThat(pointHistories_3L.get(0)).isInstanceOf(PointHistory.class);
        assertThat(pointHistories_3L.get(0).userId()).isEqualTo(3L);
        assertThat(pointHistories_3L.get(0).amount()).isEqualTo(300L);
        assertThat(pointHistories_3L.get(0).type()).isEqualTo(TransactionType.CHARGE);
    }

    @DisplayName("")
    @Test
    void test() {
        // Given

        // When

        // Then

    }
}