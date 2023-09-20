package hello.core;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// 이 테스트는 Spring에서 자동으로 생성해주는 테스트 클래스이다.
// 하지만 이 테스트는 실행하려면 스프링을 우선 실행해야 하므로 시간이 오래 걸리고 테스트 자체가 무거워진다.
// 그래서 MemberServiceTest와 OrderServiceTest와 같이 순수 자바만을 사용해서 단위 테스트를 작성해서 테스트하는 것이 더 성능이 좋다.
@SpringBootTest
class CoreApplicationTests {

	@Test
	void contextLoads() {
	}

}
