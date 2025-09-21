package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    //    @PersistenceContext
    // EntityManager 인터페이스에 @PersistenceContext 애노테이션을 붙이면 스프링이 처음 실행될 때 엔티티 매니저를 해당 필드에 주입해준다.
    // 클래스 레벨에 @RequiredArgsConstructor 적용으로 스프링이 자동으로 EntityManager를 생성자 주입 해주게 된다.
    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findById(long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
        // createQuery()는 TypedQuery라는 인터페이스를 반환하는데 이 인터페이스에는 getResultList() 메서드가 있다.
        // getResultList()는 DB에서 조회한 목록을 Java의 List 타입으로 변환하여 반환해준다.
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                // setParameter는 위 createQuery의 쿼리 스트링의 :name에 해당 파라미터에 대입된 값을 바인딩 해준다.
                .getResultList();
    }

}
