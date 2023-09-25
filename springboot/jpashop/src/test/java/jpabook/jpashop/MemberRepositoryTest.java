package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class) // junit에게 스프링과 관련된 테스트라는 것을 알려주는 애노테이션이다.
@SpringBootTest // 스프링 부트에서 테스트를 하기 위해서는 이 애노테이션을 달아줘야 스프링 부트가 테스트임을 인식한다.
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository; // 테스트를 위한 필드 주입

    @Test
    @Transactional // EntityManager를 사용하는 모든 동작은 트랜잭션 안에서 이루어져야 하며, 아래 테스트 메서드에서는 EntityManager를 사용하는 MemeberRepository를 사용하고 있기 때문에 이 테스트에는 이 애노테이션이 붙어야 에러가 발생하지 않는다.
    // jakarta 것과 spring 것이 있는데, spring 것을 사용하는 것을 권장
    // @Transactional 애노테이션이 테스트 코드에 있으면 테스트를 완료한 후에 테스트 중에 했던 DB 작업에 대해서 모두 Rollback 해버린다.(테스트 코드가 아닌 곳에 있으면 Rollback 하지 않는다.)
    // 테스트에 사용된 데이터가 DB에 영구 저장되는 것도 말이 안되고 반복적인 테스트를 위해 중복 데이터가 존재하는 것도 방지하기 위함이다.
    // Rollback을 원치 않는다면 @Rollback(value = false)를 붙여서 Rollback을 수행하지 않도록 할 수 있다.
     @Rollback(false)
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("memberA");

        // when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        // then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member); // 결과는 True이고 한 트랜잭션 안에서 영속성 컨텍스트에서 식별자(아이디)의 값이 같으면 같은 엔티티로 인식한다.
    }
}