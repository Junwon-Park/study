package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    // XXToOne은 기본 fetch 전략이 EAGER로 되어 있기 때문에 LAZY로 명시하여 지연 로딩 될 수 있도록 해준다.
    @JoinColumn(name = "member_id")
    // @JoinColumn 어노테이션은 연관관계의 주인의 FK 컬럼에 사용하며 FK를 가지는 쪽이 연관관계의 주인이다.
    // name 속성의 값으로는 FK로 저장할 상대 데이터의 실제 DB 테이블의 컬럼 네임을 명시한다.
    // 연관관계 주인의 필드의 값은 조회 및 수정이 가능하다.(다른 Member로 수정 가능)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = ALL)
    // cascade는 물이 흐르는 것을 표현하는 어휘로 cascade type을 ALL로 설정하면 orderItems에 저장된 모든 아이템 리스트를 Order 객체를 영속성에 저장할 때 함께 주르륵 저장한다.
    // 원래는 orderItemA, orderItemB, orderItemC가 있다면 각각 persist(orderItemA), persist(orderItemB), persist(orderItemC) 이후 persist(order) 해줘야 한다.
    // 이 필드의 cascade type을 ALL로 설정하면 order를 영속성 저장할 때 이 컬렉션에 저장된 orderItem들을 모두 함께 영속성 저장한다.
    // 꼭 컬렉션이 아니어도 cascade type을 ALL로 설정하면 order를 영속성 저장할 때 함꼐 저장한다. (아래 delivery 필드가 그러하다.)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch =  LAZY, cascade = ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    /**
     * 연관관계 편의 메서드
     * 원래 당연한 것이지만 양방향 연관관계를 다루는 비즈니스 로직에서 member를 저장할 때 order를 저장하는 로직도 구현해야 하지만 개발자도 사람이기 때문에 까먹고 저장하지 않을 수 있다.
     * 그래서 member를 저장하는 메서드에서 member 객체를 생성할 때 member의 order도 함께 저장하도록 하는 메서드를 생성해둔 것이다.
     */
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
