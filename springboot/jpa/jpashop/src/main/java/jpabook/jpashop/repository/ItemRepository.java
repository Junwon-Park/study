package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            // 인자로 들어온 Item의 id가 없으면 영속성 컨텍스트의 관리를 한 번도 받지 않은 아에 새로 생성된 객체
            // 완전히 새로운 객체를 저장할 때에는 persist() 메서드로 영속성 컨텍스트(1차 캐시)에 저장
            em.persist(item);
        }
        else {
            // id가 이미 존재한다는 것은 기존에 영속성 컨텍스트의 관리를 받았던(DB에 저장되어 있던 데이터) 객체이므로 Update에 해당.
            // 다시 영속성 컨텍스트에 해당 객체 머지
            // ** 하지만 아주 간단한 기능 외에 일반적으로 실무에서 merge를 사용하는 것은 지양하는 것이 좋다.
            // 왜냐하면 merge는 변경된 엔티티를 통채로 1차 캐시에 저장하여 update 쿼리 시 기존 DB의 데이터를 덮어 써버리기 때문에
            // 예상치 못한 값이 덮어써지기 될 위험이 존재한다.
            // 그래서 merge 보다는 클라이언트에서 보낸 엔티티의 key로 엔티티를 조회하여 merge 하지 말고 dirty check 만을 통해 update 될 수 있도록 하는 것이 안전한 방법이다.
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() { // 다 건 조회는 JPQL로 해야 한다.(JPA에서 findAll()을 제공하지 않음, 단 건 조회만 메서드 제공 -> find()는 단 건 조회 메서드)
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
