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

    @ManyToOne // 연관 관계 애너테이션에서 앞(Many)이 이 엔티티를 가리키고 뒤(One)가 상대 엔티티를 가리킨다.
    // 양방향 연관 관계를 설정할 때, 1 : N 인 경우 N(FK 컬럼을 가지고 있는 엔티티)이 연관 관계 주인이 된다.
    @JoinColumn(name = "member_id") // 연관 관계 주인에 붙이는 애너테이션으로 name 옵션에 상대 엔티티의 PK 컬럼을 넣어서 알려준다.
    // @JoinColumn 애너테이션은 연관 관계 주인에게 붙이며 FK 필드라는 것을 지정하는 것이다.
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;


    private LocalDateTime orderTime; // 날짜 타입을 사용할 때, 자바 8버전 미만은 Date 클래스를 사용하고 자바 8 이상 버전은 LocalDateTime 클래스를 날짜 필드의 타입으로 사용한다.
    // LocalDateTime 타입으로 지정해놓으면 JPA의 하이버네이트가 자동으로 인식하고 테이블 생성 시, 해당 필드를 날짜 타입으로 생성한다.

    private OrderStatus status; // 주문 상태 [ORDER, CANCEL[
}
