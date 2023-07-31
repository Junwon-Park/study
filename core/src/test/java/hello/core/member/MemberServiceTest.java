package hello.core.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService = new MemberServiceImpl();

    // 테스트 메서드는 @Test 어노테이션을 달아줘야 테스트 실행 시 테스트 메서드로 인식한다.
    @Test
    void join() {
        // given: join(가입)을 위해 주어진 것(member)
        Member member = new Member(1L, "memberA", Grade.VIP);

        // when: 아래 로직을 수행했을 때
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        // then: 테스트 코드
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
