package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // Enum 컬럼에는 JPA jakarta의 @Enumerated() 애너테이션을 붙이고 애너테이션의 인자로 열거형 타입을 넣어준다.
    // EnumType은 반드시 STRING으로 넣어준다.
    // STRING 타입은 열거형을 문자열 그 대로 사용하는 방식이다.
    // ORDINAL 타입은 열거형을 배열 처럼 순서대로(0 ~ *) 사용하는 방식으로 비즈니스 로직에 사용 중이던 Enum 중간에 값이 생기면 순서가 바뀌어 비즈니스 로직에서 사용되던 값이 바뀔 수 있는 위험이 있다.
    // 반면 STRING 타입은 문자열 그 대로 사용하기 때문에 중간에 하나가 추가되어도 동일한 문자열을 지정하기 때문에 비즈니스 로직에서 값이 바뀔 위험이 없어 안전하다.
    private DeliveryStatus status; // READY, COMP
}
