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
