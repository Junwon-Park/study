package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAllByString(OrderSearch orderSearch) {
        return em.createQuery("select o from Order o join o.member m" +
                " where o.status = :status" +
                " and m.name like :name", Order.class)
                .setParameter("status", orderSearch.getOrderStatus())
                .setParameter("name", orderSearch.getMemberName())
                .setMaxResults(1000) // 최대 1000건 제한
                .getResultList();
    }

    public List<Order> findAll() {
        return em.createQuery("select m from Order m", Order.class).getResultList();
    }

    public List<Order> findWithMemberDelivery() {
        return em.createQuery("select o from Order o"
                        + " join fetch o.member m" // fetch join 사용
                        + " join fetch o.delivery d", Order.class) // fetch join 사용
                .getResultList();
    }

//    // 이 메서드는 특정 DTO에 특화되어 있기 때문에 다른 API에는 사용할 수 없어 재사용성이 떨어진다.
//    // 그리고 DTO에 특화해서 조회하는 방식은 아래 코드 처럼 new 연산자를 써서 해당 DTO의 생성자에 조회한 데이터를 createQuery()에 직접 작성해야 하기 때문에 코드가 지저분해진다.
//    // 하지만 Repository는 엔티티를 조회하는 용도로 사용하는 것이 옳다. Repository에서는 엔티티만 조회하고 DTO에 대입하는 부분은 Controller에서 하는 것이 더 옳은 방법이다.
//    // API 스펙이 Repository에 들어와있는 것은 구조상 좋은 방법이 아니다.
//    // 하지만 필요하다면 사용하게 될 수도 있는데 그러면 Repository 말고 QueryRepository 클래스를 따로 만들어서 해당 QueryRepository에서는 DTO 특화된 쿼리를 관리하는 용도로 사용할 수 있다.
//    public List<OrderSimpleQueryDto> findOrderDtos() {
//        return em.createQuery("select new jpabook.jpashop.repository.simplequery.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, m.address) from Order o"
//                        + " join o.member m"
//                        + " join o.delivery d", OrderSimpleQueryDto.class)
//                .getResultList();
//    }
}
