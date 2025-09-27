package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int quantity;

    //=== 생성 메서드 ===//
    public static OrderItem createOrderItem(Item item, int orderPrice, int quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setQuantity(quantity);

        item.removeStock(quantity);

        return orderItem;
    }

    //=== 비즈니스 로직 ===//
    /**
     * 현재 주문 아이템(OrderItem)의 취소란 기존 아이템(Item)의 수량을 주문 전 상태로 돌려놓는 것이다.
     */
    public void cancel() {
        getItem().addStock(quantity);
    }
}
