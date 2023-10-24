package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity // Order 클래스는 DB에 사용할 엔티티이기 때문에 붙여준다.
@Table(name = "orders")
// Order 클래스는 DB에 사용할 엔티티이기 때문에 JPA가 엔티티로 인식을 하고 자동으로(application.yml에 설정) 테이블을 생성할 때, 클래스 이름인 order라는 이름으로 생성하게 된다.
// 하지만 order라는 이름은 SQL의 쿼리를 사용할 때, order by를 사용하게 되면 order와 워딩이 겹쳐 인식 오류가 발생할 수 있어 관례상 order는 orders라는 테이블로 생성하도록 한다.
// 이 때, 테이블의 이름을 지정하려면 위 처럼 @Table 애너테이션의 name 속성에 값으로 사용할 이름을 넣어주면 테이블 생성 시 해당 이름으로 생성한다.
@Getter @Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    // 모든 연관 관계 애노테이션의 옵션 중 fetch는 반드시 LAZY(지연로딩)로 설정해야 한다.
    // fetch 방식은 EAGER(즉시로딩)와 LAZY(지연로딩) 두 가지가 있는데, EAGER는 N + 1 문제가 발생할 확률이 매우 높다.
    // N + 1이란, 만약 Order를 조회하기 위해 하나의 쿼리를 날리고 Order에 연관된 모든 Member 함께 찾아오기 위해 추가 쿼리를 날리는 것인데,
    // 만약 연관된 Member가 100명 이라면 Order를 조회하는 쿼리 1 + 연관된 Member 100명을 모두 가져오는 쿼리 각각 1번 씩 100번을 날려, 총 100(N) + 1번의 쿼리를 날리게 되는 상황을 말한다.
    // N + 1 문제를 방지하려면 fetch 방식을 LAZY로 설정해야 한다.
    // 특히 주의할 점은, @ManyToOne, @OneToOne (~ToOne으로 끝나는 앞의 두 애노테이션)의 경우 기본 값이 EAGER로 되어 있어 직접 LAZY로 지정해주어야만 한다. N + 1 문제를 방지하기 위해서는 반드시 해주어야 한다!!!!!!
    // @OneToMany와 @ManyToMany는 기본 값이 LAZY로 되어있기 때문에 따로 LAZY로 지정할 필요는 없다.(어차피 @ManyToMany는 사용할 일이 거의 없다.)
    @ManyToOne(fetch = FetchType.LAZY) // 연관 관계 애너테이션에서 앞(Many)이 이 엔티티를 가리키고 뒤(One)가 상대 엔티티를 가리킨다.
    // 양방향 연관 관계를 설정할 때, 1 : N 인 경우 N(FK 컬럼을 가지고 있는 엔티티)이 연관 관계 주인이 된다.
    @JoinColumn(name = "member_id") // 연관 관계 주인에 붙이는 애너테이션으로 name 옵션에 상대 엔티티의 PK 컬럼을 넣어서 알려준다.
    // @JoinColumn 애너테이션은 연관 관계 주인에게 붙이며 FK 필드라는 것을 지정하는 것이다.
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    // 아래 orderDate라는 필드는 테이블이 생성될 때, SpringPhysicalNamingStrategy에 의해서 order_date라는 이름의 컬럼으로 이름이 자동으로 바뀌어 저장된다.
    // 카멜 케이스 -> 언더스코어로 변경 후 저장
    // .(점) -> 점을 언더스코어로 변경 후 저장
    // 대문자 -> 소문자로 변경 후 저장
    // DBA들은 관례상 위 처럼 컬럼 이름을 사용하는데, 이 것을 지키도록 스프링에서는 이 것을 기본 값으로 하도록 만들어졌다.
    // 설정을 통해 기본 값을 변경할 수 있다.(논리명, 물리명 방식이 있다.)
    private LocalDateTime orderDate; // 날짜 타입을 사용할 때, 자바 8버전 미만은 Date 클래스를 사용하고 자바 8 이상 버전은 LocalDateTime 클래스를 날짜 필드의 타입으로 사용한다.
    // LocalDateTime 타입으로 지정해놓으면 JPA의 하이버네이트가 자동으로 인식하고 테이블 생성 시, 해당 필드를 날짜 타입으로 생성한다.

    @Enumerated(EnumType.STRING) // Enum 타입에는 @Enumerated 애노테이션을 달아주고 옵션에 EnumType으로 STRING을 지정해준다.
    // 만약 ORDINAL로 설정하면 Enum을 Index로 사용한다는 것이기 때문에 [ORDER, CANCEL] 중간에 값이 추가되면 기존에 작성된 코드에 문제가 발생할 수 있다.
    // 반면 STRING은 Enum의 값 그 대로 사용하는 것이기 때문에 값이 추가되어도 문제가 되지 않는다.
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]

    // ==== 연관관계 편의 메서드 ====
    // 양방향 연관관계에서 편의상 사용할 수 있도록 메서드를 정의해놓은 것이다.
    // 연관관계 편의 메서드를 정의하려고 할 때, 위치는 두 연관관계 중 연관관계 주인에 해당하는(주가 되는) 엔티티에 정의하면 된다.

    // 현재 엔티티인 Order와 Member의 관계에서 연관관계 주인은 N쪽인 Order이기 때문에 연관관계 편의 메서드가 여기에 위치한 것이다.
    public void setMember(Member member) {
        this.member = member; // 현제 엔티티의 member 필드에 인자로 받은 member를 할당한 것이다.
        member.getOrders().add(this); // 인자로 받은 멤버 객체의 orders(ArrayList)를 가져와 해당 리스트에 현재 엔티티(this)를 추가해 연관관계를 세팅해준 것이다.
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // === Order 생성 메서드 === //
    // Order를 생성하는 메서드이며 Order의 멤버에 값을 채우는 로직이기 때문에 Order에 위치하는 것이 옳다.
    // 이런 로직은 Setter를 사용하지 않고 명시적으로 만들어주는 것이 좋다.
    // 엔티티 생성 메서드는 static으로 선언
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) { // 파라미터의 ...은 자바 문법이며 List 형태의 값을 넘길 수 있다.
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    // === 비즈니스 로직 === //
    /**
     * 주문 취소
     */
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : this.orderItems) {
            orderItem.cancel();
        }
    }

    // === 조회 로직 === //
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : this.orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;
    }
}
