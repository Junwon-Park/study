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

    public List<Order> findAll(OrderSearch orderSearch) {
        return em.createQuery("select o from Order o join o.member m" +
                " where o.status = :status" + // :status는 변수임을 의미한다.(아래에 setParameter() 함수에 해당 변수에 값을 할당해준다.
                " and m.name like :name", Order.class) // 여기에서 = 이 아닌 like문을 사용한 것은 SQL의 like문으로 부분적으로 일치하는 것을 의미한다.
                .setParameter("status", orderSearch.getOrderStatus()) // 위 where문에 정의한 :status 변수에 값을 할당(세팅)하는 부분이다.
                .setParameter("name", orderSearch.getMemberName()) // 위 where 문에 정의한 :name 변수에 값을 할당(세팅)하는 부분이다.
//                .setFirstResult(10) // 조회하는 데이터의 시작을 지정할 수 있다.
                .setMaxResults(1000) // 조회하는 데이터의 최대 개수를 지정할 수 있다. 위 setFirstResutl()와 함께 페이징에 사용할 수 있다.
                .getResultList(); // 결과를 List 형으로 반환한다.

        // 하지만 위 처럼 작성하면 orderSearch.getOrderStatus()와 orderSearch.getMemberName()가 각각 null인 경우를 체크해서 그에 맞게 쿼리가 동적으로 바뀌도록,
        // 즉, 동적 쿼리로 작성해야하는데 위 처럼 작성하면 그렇게 할 수 없다.
        // 이 때, 동적 쿼리를 작성하는 방법은 단순히 각각을 조건문으로 null 체크하여 변수에 문자열로 이어 붙이는 방법과
        // JPA Criteria라는 JPA 문법이 있는데 이 것은 동적 쿼리 자체에는 강력하지만 유지보수 시, 완성된 쿼리가 어떻게 나올 지 예측이 어렵다는 치명적인 단점이 있다.
        // 그래서 이 부분은 QueryDSL로 작성하면 위 모든 문제점들을 해결할 수 있다.
    }
}
