package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter @Setter
// 아래 Lombok의 애노테이션을 달아주면 스프링이 실행될 때 해당 클래스의 기본 생성자의 접근제어자를 Protected로 자동 생성해준다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    /**
     * JPA에서는 기본 생성자(No args constructor)가 필요한데
     * 해당 기본 생성자의 접근 제어자는 public과 protected만 가능하다.
     * 자바는 클래스에 생성자를 직접 명시하지 않으면 기본적으로 파라미터가 없는 기본 생성자를 내부적으로 생성해주지만
     * 자바가 자동으로 생성해주는 생성자의 접근 제어자는 public이기 때문에 접근 제어자를 protected로 설정하라면 아래 처럼 직접 명시해줘야 한다.
     * 생성자를 직접 명시하면서까지 protected로 설정한 이유는 아래 생성 메서드 createOrderItem() 메서드를 사용해서 인스턴스를 생성하는 방식을 강제하여
     * 코드의 일관성을 지키기 위함이다.(new OrderItem()으로 기본 생성자 인스턴스 생성 방지)
     * 하지만 이 부분도 Class 레벨에 Lombok의 @NoArgsConstructor를 적용하면 생성자를 생략해도 스프링 내부에서 적용해준다.
     * AccessLevel을 PROTECTED로 설정(@NoArgsConstructor(access = AccessLevel.PROTECTED))해주면 해당 기본 생정자의 접근 레벨을 protected로 생성해준다.
     * 항상 코드를 작성할 때, 필요한 범위 까지만 제약을 주는 구조로 개발해야 실수를 줄이면서 유지보수성이 좋은 애플리케이션을 개발할 수 있다.
     */
//    protected OrderItem() {}

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
