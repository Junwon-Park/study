package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

@Repository // Repository 컴포넌트 스캔(스프링 빈 자동 등록)
public class MemberRepository {

    // JPA를 사용할 때는 EntityManager가 있어야 한다.
    // 스프링 부트는 @PersistenceContext 애노테이션이 붙어 있는 변수에 EntityManager를 자동으로 주입해준다.
    // EntityManager를 주입받아야 하기 때문에 타입을 EntityManager로 한다.
    // gradle에 jpa를 설치했고 등록되어 있기 때문에 스프링 부트가 이 모든 것을 알아서 한다. 즉, 위 과정은 JPA가 설치되어 있어야 스프링 부트가 수행한다.
    // 심지어 application.yml에 설정도 모두 적용해서 수행한다.
    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member); // 여기에서는 flush(실제 DB에 쿼리)를 하지 않고 영속성 컨텍스트에 저장만 한다.
        return member.getId(); // 여기에서 저장한 Member 객체를 반환하지 않고 ID 정도만 반환하는 이유는 "커멘드와 쿼리를 분리하자"는 이유이다.
        // save는 객체를 저장하라는 명령형 메서드이고 명령형(커멘드)에서는 되도록 값을 반환하지 않고 값을 반환(쿼리)하는 목적이 있다면 해당 메서드를 따로 분리해서 만들기 위함이다.
        // 하지만 id 정도는 나중에 비즈니스 로직에서 멤버를 조회하는 정도로 사용할 수도 있기 때문에 반환한다.(반환하지 않아도 된다.)
        // 만약 Member를 조회하려고 한다면 find() 또는 findMember() 메서드(쿼리형)를 따로 만들어 명령(커멘드)과 쿼리를 분리하는 방식이 권장된다.
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
