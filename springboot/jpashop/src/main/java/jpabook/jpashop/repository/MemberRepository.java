package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor // 엔티티 매니저도 Lombok의 해당 애노테이션을 사용해서 생성자 주입과 동일하게 동작하도록 해서 엔티티 매니저를 주입받도록 할 수 있다.
// 그러면 @PersistenceContext 애노테이션을 생략할 수 있고 생성자도 생략할 수 있다.
// 엔티티 매니저 타입의 final 상수만 있으면 된다.
// 원래 생성자 주입은 변수에 @AutoWired 애노테이션을 붙여야 하지만 엔티티 매니저에는 주입을 위해 @PersistenceContext 애노테이션만 붙일 수 있다.
// 하지만 JPA에서 @PersistenceContext가 아닌 @AutoWired 애노테이션을 붙여 생성자 주입이 가능하도록 지원해주고 있기 때문에 그 것을 Lombok의 @RequiredArgsConstructor을 사용해 생성자 주입 코드를 작성하지 않고 생성자 주입이 되도록 할 수 있는 것이다.
// 그래서 최종적으로 아래 코드만으로 동작하도록 할 수 있게 된 것이다.
public class MemberRepository {
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); // 엔티티 매니저의 단건 조회 메서드로 첫 번째 인자로 조회할 엔티티의 타입, 두 번째 인자로 식발자를 넣어주면 된다.
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
