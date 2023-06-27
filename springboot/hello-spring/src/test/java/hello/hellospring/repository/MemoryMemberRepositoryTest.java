package hello.hellospring.repository;

import hello.hellospring.domain.Member;

// Test JUnit
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.assertj.core.api.Assertions.*; // import static 불러오면 내부 메서드를 Assertions 클래스를 쓰지 않고 바로 Assertions의 메서드를 사용할 수 있다.

import java.util.List;


class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // org.junit.jupiter.api의 AfterEach 어노테이션은 각 테스트(@Test 어노테이션이 붙은 메서드) 케이스가 하나 끝날 때마다 한 번 씩 실행할 메서드임을 의미한다.
    public void afterEach() {
        repository.clearStore(); // DB에 아래 각 동작에 대한 테스트 수행 후 다음 테스트 수행에 영향을 주지 않도록 DB를 비워주는 동작을 하도록 했다.
    }

    @Test // org.junit.jupiter.api의 Test 어노테이션은 이 메서드가 테스트 케이스임을 의미한다.(이 클래스 파일을 실행하면 이 어노테이션이 붙은 메서드들이 실행된다.)
    public void save() {
        Member member = new Member();
        member.setName("spring");
        repository.save(member);

        Member result = repository.findById(member.getId()).get();

        Assertions.assertEquals(member, result); // org.junit.jupiter.api의 Assertions 클래스는 JUnit 테스트 메서드들을 가지고 있다.
        // assertEquals를 사용하면 첫 번째 인자가 두 번째 인자와 동일한 지를 체크한다.

    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring2").get();

        assertThat(member2).isEqualTo(result); // org.assertj.core.api.Assertions의 테스트 메서드이다.
        // org.assertj.core.api.Assertions를 import static으로 불러와 클래스 없이 메서드만 불러와 사용할 수 있게 됐다.
        // assertThat()의 인자가 isEqualTo()의 인자와 같은 지를 체크한다.
        // 위에서 org.junit.jupiter.api의 Assertions와 동일하게 동작한다.
    }
    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
