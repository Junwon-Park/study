package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);
        // 생성 메서드를 사용하는 방식으로 개발했기 때문에 new Order();로 생성하면 안된다.
        // 이 것을 막는 방법은 Order 엔티티 클래스에 생성자를 만들어서 생성자의 접근 제어자를 protected로 변경해서 생성자로는 아에 접근하지 못하도록 막아준다.
        // 그러면 new Order() 생성자를 사용하면 당연히 접근 제어자에 의해 막혀서 컴파일 에러가 발생한다.
        // 이 것도 Order 엔티티 클래스에 Lombok의 @NoArgsConstructor(access = Accesslevle.PROTECTED)을 달아주면 생성자를 생성하지 않아도 생성자를 생성해서 접근 제어자를 Protected로 작성한 것 처럼 동작하게 해준다.

        // 주문 저장
        orderRepository.save(order); // Order를 생성하려면 Order의 멤버인 Delivery와 OrderItem 모두 영속성 컨텍스트에 올라와야하는데,
        // 그렇게 하려면 원래 Delivery도 DeliveryRepository를 생성해서 거기에 save() 메서드로 em.persist(delivery)를 해주고
        // 마찬가지로 OrderItem도 OrderItemRepository를 생성해서 거기에 save() 메서드로 em.persist(OrderItem)를 해서 모두 영속성 컨텍스트의 1차 캐시에 올라와있어야 DB에 Order의 멤버로 Delivery와 OrderItem 가지고 저장할 수 있지만
        // Order에 Delivery와 OrderItem 멤버 변수의 연관관계 매핑 애노테이션에 cascade 옵션을 CascadeType.All로 줘서 해당 엔티티인 Order를 em.Persist(order)해주면 Order의 멤버인 Delivery와 OrderItem 모두 자동으로 Persist 된다.
        // 그래서 여기에 order만 save()해서 persist 해주면 의도한 대로 동작한다.
        // 단!!!, Cascade는 아무 때나 사용하면 안되고 Order와 멤버인 Delivery와 OrderItem 처럼 해당 멤버 변수의 엔티티들을 다른 엔티티에서 참조하지 않는 경우, 즉 이 경우 처럼 Delivery와 OrderItem를 오로지 Order에서만 사용하는 경우!
        // 이런 경우에 Order를 Persist 하면 Delivery와 OrderItem도 Persist 되도록 Casecade를 걸어주어야 한다.
        // Order 외에 단 한 곳이라도 Delivery와 OrderItem 둘 중 하나라도 참조하는 경우 Cascade를 걸면 안된다.

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        // 주문 취소
        order.cancel();
    }

    // 검색
}
