package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    // Enum 카입 컬럼에는 반드시 @Enumerated 애노테이션을 붙여줘야 하고 EnumType은 반드시 STRING으로 설정해야 한다.
    // ORDINARY로 설정하면 해당 컬럼의 값이 숫자로 저장되는데(READY: 1, COMP: 2) 이 때 만약 중간에 다른 ENUM이 추가되면 기존에 저장된 것들을 모두 수정해줘야 하는 참사가 발생한다. 그 전에 시스템 장애가 먼저 발생할 것이다.
    // STRING으로 설정하면 해당 ENUM 값 그대로 저장된다. (READY: READY, COMP: COMP)
    private DeliveryStatus status;
}
