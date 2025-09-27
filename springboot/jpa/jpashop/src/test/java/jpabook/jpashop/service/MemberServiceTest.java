package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
// JUnit5에서는 RunWith(SpringRunner.class)를 사용할 수 없고 더 많은 기능을 제공하는 @ExtendWith(SpringExtension.class)를 사용해야 한다.
// 스프링과 함께 통합해서 실행하기 위한 애노테이션이다.
@SpringBootTest
// @ExtendWith(SpringExtension.class)와 함께 @SpringBootTest 애노테이션이 있어야 스프링과 함께 통합 테스트를 돌릴 수 있게 된다.
// 스프링 부트를 띄운 상태로 테스트하기 위한 애노테이션으로 테스트 시 스프링 부트가 제공하는 기능(스프링 컨테이너 사용 -> Spring bean 등.. @Autowired, @Repository, @Service 등)
@Transactional
// 테스트 케이스에 이 애노테이션이 사용되면 테스트를 실행 후 데이터를 모두 Rollback 해버린다.(테스트에 사용된 데이터는 실제 사용할 데이터가 아니기 때문에)
// 테스트에도 마찬가지로 public 메서드에 트랜잭션을 적용해줘야 하고 회원 서비스에는 CRUD 중 CUD 비중이 많기 때문에 readOnly 옵션은 false(기본값)로 설정한다.
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    // 테스트는 다른 곳에서 참조할 일이 없기 때문에 그냥 @Autowired로 필드 주입 해준다.

    @Test
//    @Rollback(false)
    public void 회원가입() throws Exception {
        // Given
        Member member = new Member();
        member.setName("Park");

        // When
        Long savedId = memberService.join(member);

        //Then
        assertThat(member).isEqualTo(memberRepository.findOne(savedId));

     }

    /**
     * 회원가입 예외 처리 테스트
     */
     @Test
     public void 중복_회원_예외() throws Exception {
         // Given
         Member member1 = new Member();
         member1.setName("Park");
         Member member2 = new Member();
         member2.setName("Park");

         // When
         memberService.join(member1);
         assertThrows(IllegalStateException.class, () -> memberService.join(member2)); // 예외가 발생해야 하는 부분!!
         // JUnit5에서는 예외 처리 테스트를 @test(excepted = class)를 사용하지 않고 assertThrows를 사용한다.
         // 여기에서 첫 번째 인자로 대입한 IllegalStateException.class가 발생하면 정상적으로 테스트가 성공하고 발생하지 않으면 실패한다.
         // 그렇기 때문에 아래 fail()로 테스트 실패하는 코드를 작성할 필요가 없다.(어차피 여기에서 지정한 예외가 발생하지 않으면 여기에서 실패함)

         //Then
         //         fail("예외가 발생해야 한다"); // 이 부분은 JUnit4에서 예외가 터지지 않고 내려왔을 때 테스트가 실패하도록 하는 부분이었다.
         // 하지만 JUnit5에서는 assertThrows를 사용해서 첫 번째 인자로 대입한 예외가 터지면 테스트 성공, 터지지 않거나 다른 예외가 발생하면 내부적으로 테스트를 실패하도록 해준다.
         // 코드가 훨씬 간결해졌다.
      }

}