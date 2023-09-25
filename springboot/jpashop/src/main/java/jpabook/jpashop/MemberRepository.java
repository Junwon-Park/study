package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository // Repository 컴포넌트 스캔(스프링 빈 자동 등록)
public class MemberRepository {

    // JPA를 사용할 때는 EntityManager가 있어야 한다.
    // 스프링 부트는 @PersistenceContext 애노테이션이 붙어 있는 변수에 EntityManager를 자동으로 주입해준다.
    // EntityManager를 주입받아야 하기 때문에 타입을 EntityManager로 한다.
    // gradle에 jpa를 설치했고 등록되어 있기 때문에 스프링 부트가 이 모든 것을 알아서 한다.
    // 심지어 application.yml에 설정도 모두 적용해서 수행한다.
    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
