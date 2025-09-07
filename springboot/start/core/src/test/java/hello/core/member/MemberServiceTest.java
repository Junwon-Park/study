package hello.core.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    MemberService memberService = new MemberServiceImpl();

    @Test
    void join(){
        // Given(테스트를 위해 주어진 데이터)
        Member member = new Member(1L, "memberA", Grade.VIP);

        // When(데이터를 가지고 실행되어야 하는 부분
        memberService.join(member);
        Member findMember = memberService.findMember(member.getId());

        // Then(위 Given에서 생성된 데이터와 When에서 실행된 부분이 모두 잘 동작 하였는지 검증하는 부분)
        Assertions.assertThat(member).isEqualTo(findMember);
    }
}
