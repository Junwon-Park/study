package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int quantity) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), quantity);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    // 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        // 취소할 주문 조회
        Order order = orderRepository.findOne(orderId);

        // 주문 취소
        /**
         * 주문을 취소하는 로직은 Order 엔티티 내부에 존재하고 OrderService에서는 해당 메서드를 호출하면 주문 취소의 모든 로직이 처리되도록 하는 것이 좋다.
         * 해당 도메인의 비즈니스 로직은 해당 도메인 엔티티에 존재한다.
         * 이런 객체 지향의 특성을 적극 활용하는 것을 **도메인 모델 패턴**이라고 한다.
         * 반대로 대부분의 도메인 비즈니스 로직이 엔티티가 아닌 Service 레이어에 작성하는 것을 **트랜젝션 스크립트 패턴**이라고 한다.
         * JPA와 같은 ORM을 사용할 때에는 **도메인 모델 패턴**이 여러 측면에서 훨씬 실용적이다.
         * 하지만 상황에 따라 어떤 패턴으로 작성하는 것이 유지보수 측면에서 더 좋은지 고민해서 적용해야 한다.(무조건 도메인 모델 패턴이 더 좋다는 것이 아니다.)
         */
        order.cancel();
    }

     // 검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }

}
