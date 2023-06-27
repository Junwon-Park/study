package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService = new MemberService();
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }


    @Test
    void 회원가입() { // 테스트 코드는 실제 빌드 코드에 포함되지 않기 때문에 테스트 코드의 메서드 이름은 한글해서 직관적으로 지어도 상관 없다.
        // Given(무언가가 주어졌다.)
        Member member = new Member();
        member.setName("hello");

        // When(이렇게 실행했을 때.)
        Long saveId = memberService.join(member);

        // Then(결과가 이렇게 나와야 한다. -> Test)
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() { // MemberService.join에서 이름이 중복되는 회원에 대해서 예외처리를 했었다.
        // 테스트에서는 이 예외 처리 부분도 잘 동작하는 지 테스트를 만들어 테스트를 해주는 것이 좋다.
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // 위 member1과 member2에 지정한 이름이 동일하다. 중복

        memberService.join(member1); // member1은 일단 가입이 된다. 동일한 이름의 가입자가 현재 없기 때문이다.
        // 하지만 이후 member2가 가입할 때 이미 동일한 이름을 가진 member1이 가입되었기 때문에 이름 이 중복되어 가입할 수 없다.
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // assertThrows()는 두 번째 인자인 람다식 부분을 실행했을 때, 첫 번째 인자인 예외가 터지는 지 체크하는 테스트 메서드이다.
        // 람다식 부분 실행 결과 발생한 예외와 첫 번째 인자에 지정한 예외가 일치하지 않는 경우 에러가 발생한다.
        // 예외 클래스가 일치하는 경우 람다식에 실행한 로직의 발생한 예외의 메세지가 반환된다. 그 것을 해당 예외 클래스 타입으로 받을 수 있다.
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다."); // 여기에서는 위에서 터진 예외 처리 메세지를 검증한다.
        // 메세지 내용도 일치한지 체크해주는 것이 좋다.

        // 위 내용을 아래와 같이 작성하던 것을 위 처럼 더 간결한 코드로 리팩토링 했다.
        //        try {
        //            memberService.join(member2);
        //            fail();
        //        } catch (IllegalStateException e) {
        //            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        //        }

    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}