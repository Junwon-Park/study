package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    // === OrderItem 생성 메서드 === //
    // 엔티티 생성 메서드는 static으로 선언
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); // 주문하는 것이기 때문에 주문한 Item의 stockQuantity(재고 수량)를 주문한 수량(count) 만큼 빼준 것이다.
        return orderItem;
    }

    // === 비즈니스 로직 === //
    // Order가 취소됐을 때, 해당 주문의 OrderItem도 함께 취소되어야 하고 주문한 Item은 OrderItem에 존재하므로 주문이 취소된 해당 Item의 재고 수량을 다시 더해주는 로직은 여기에 존재하는 것이 옳다.
    public void cancel() { // OrderItem은 Order에 주문한 아이템이고 주문(Order)을 취소했으므로 해당 OrderItem의 Item의 수량을 주문 수량인 this.count를 해당 아이템의 stockQuantity(재고 수량)에 더해주면 된다.
        // 그래서 getItem()으로 Item을 불러와 해당 아이템의 재고 수량을 더해주는 addStock()에 현재 주문 아이템의 주문 수량을 다시 더해주는 것이다.(주문 시 주문 수량을 뺐을 것이기 때문에 주문 취소 시 원상복귀 해주는 것이다.
        // OrderItem을 취소하는 로직이기 때문에 이 로직이 여기에 있는 것이며 해당 Item의 StockQuantity를 더하고 빼주는 메서드는 당연히 Item의 멤버 변수를 다루는 메서드이므로 Item에 존재한다.
        this.getItem().addStock(count);
    }

    // === 조회 로직 === //
    public int getTotalPrice() {
        return this.getOrderPrice() * this.getCount();
    }
}
