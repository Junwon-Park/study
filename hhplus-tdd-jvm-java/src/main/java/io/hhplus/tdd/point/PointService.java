package io.hhplus.tdd.point;

import io.hhplus.tdd.database.UserPointTable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointService {

    public final UserPointTable userPointTable;

    public void test(Long id) throws InterruptedException {
        System.out.println("Service id: " + id);
        userPointTable.selectById(id);
    }
}
