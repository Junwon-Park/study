package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // 이 테스트가 스프링 테스트임(테스트 실행 시 스프링을 함께 실행할 것)을 JUnit에 알려주는 애노테이션이다.
@SpringBootTest // 이 애노테이션이 없으면 아래 @Autowired 애노테이션에 컴파일 에러 발생, 스프링 부트에 테스트 클래스라는 것을 알려주는 애노테이션이다.
@Transactional // 테스트 중에 데이터를 변경해야 하는 경우가 있기 때문에 @Transactional 애노테이션을 사용한다.
// 테스트에 붙은 @Transactional 애노테이션은 각 유닛 테스트가 종료되면 해당 트랜잭션을 완전 롤백 시킨다.(당연히 Service 등에 사용된 @Transactional은 롤백되지 않는다.)
// 그래야 해당 테스트 유닛이 끝나고 난 뒤, 데이터가 롤백 된다. -> 롤백을 하지 않도록 하려면 해당 유닛 테스트 메서드에 @Rollback(false)를 달아주면 해당 유닛 테스트가 끝나도 롤백이 일어나지 않는다.
public class MemberServiceTest {

    // 테스트 케이스에서는 다른 곳에서 참조할 필요가 없기 때문에 필드 주입으로 간단하게 주입해도 된다.
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
//    @Rollback(value = false)
    public void 회원가입() throws Exception {
        // given (데이터가 주어진다.)
        Member member = new Member();
        member.setName("kim");

        // when (given에 주어진 데이터를 가지고 이렇게 할 때)
        Long savedId = memberService.join(member);

        // then (when에서 실행한 로직의 결과가 이렇게 나와야 한다.)
        assertEquals(member, memberRepository.findOne(savedId));

        // given -> when -> then은 아래와 같은 의미이다.
        // given에 주어진 데이터를 when에서의 로직을 실행했을 때, then과 같은 결과가 나와야 한다.
    }

    @Test(expected = IllegalStateException.class) // IllegalStateException가 발생해야 통과하는 테스트라는 의미이다.
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim2");

        // when
        memberService.join(member1);

//        try { // try catch를 사용한 이유: 테스트는 Exception이 발생하면 Exception을 던지면서 테스트가 실패한다.
//            // 하지만 이 케이스는 member2에 IllegalStateException이 발생해야 통과하는 테스트이다.
//            // 그렇기 때문에 IllegalStateException이 발생해도 바로 던지지 않고 IllegalStateException이 터지면 아래 catch가 실행되어 터지지 않고 그냥 유닛 테스트 밖으로 나가도록 한 것이다.
//            // 그래야 Exception이 발생해도 터져서 테스트가 실패하지 않고, 아래 then에서 fail()에 걸려 실패하는 상황을 피해서 Excepion이 발생하는 테스트를 통과하도록 할 수 있다.
//            memberService.join(member2); // 중복되므로 예외 발생해야 한다!
//        } catch (IllegalStateException e) {
//            return;
//        }
        // 원래 Exception이 발생하는 테스트가 통과하게 하기 위해서는 위 처럼 try catch를 사용해야 하지만 해당 유닛 테스트의 @Test() 애노테이션에 excepted 옵션에 값으로 발생할 Exception 클래스 타입을 주면
        // 해당 Exception이 발생하면 아래 fail까지 가지 않고 Exception이 발생한 지점에서 유닛 테스트를 종료하고 테스트를 통과시킨다.
        // try catch 보다 간결하게 Exception 테스트 코드를 작성할 수 있다.
        memberService.join(member2); // 중복되므로 예외 발생해야 한다!

        // then
        fail("예외가 발생해야 한다. 근데 발생하지 않았다."); // fail은 여기까지 내려오면 안되는 상황일 때 사용한다.
        // 여기까지 내려오지 않고 테스트가 끝나는 상황일 때를 의미하기 때문에 이 fail()에 걸리면 이 테스트가 제대로 실행되지 않은 것이다.(위 테스트 로직이 잘못된 것이다.)
        // 보통 Exception이 발생해야 할 때, fail()을 사용해서 위에서 Exception을 잡아서 테스트하고 여기까지 내려오지 않도록 작성한다.
    }
}