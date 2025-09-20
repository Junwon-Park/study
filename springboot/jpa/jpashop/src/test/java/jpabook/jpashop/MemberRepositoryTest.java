package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    // 테스트 메서드에 사용되면 모든 동작이 완료된 후 이 메서드에서 실행한 트랜잭션을 모두 롤백시킨다.(멤버를 생성했으나 테스트 수행 후 DB에 생성된 멤버 데이터 삭제됨)
    // 반복적인 테스트가 가능하기 위해서 이다.
    // 만약 테스트 코드에서도 롤백되지 않도록 설정하고 싶다면 @Rollback(false)로 롤백 기능을 비활성화 해주면 된다.
    public void testMember() throws Exception {
        // Given
        Member member = new Member();
        member.setUsername("memberA");

        // When
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        //Then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}