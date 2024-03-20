package io.hhplus.tdd.point;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointService {

    public final UserPointTable userPointTable;
    private final PointHistoryTable pointHistoryTable;

    public UserPoint getUserPointById(Long id) {
        UserPoint userPoint = userPointTable.selectById(id);

        if (userPoint.point() == 0) throw new NoSuchElementException("해당 유저의 포인트가 존재하지 않습니다.");

        return userPoint;
    }

    public List<PointHistory> getHistoriesById(Long id) {
        return pointHistoryTable.selectAllByUserId(id);
    }
}
