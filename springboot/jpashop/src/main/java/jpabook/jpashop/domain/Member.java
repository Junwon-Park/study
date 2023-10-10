package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity // JPA Entity 지정
@Getter @Setter // Lombok 사용
public class Member {
    @Id
    @GeneratedValue // @Id 애노테이션은 이 필드를 Id 값으로 매핑하도록 지정한 것이고, @GeneratedValue 애노테이션은 데이터 생성 시 이 값을 자동으로 생성하도록 지정한 것이다.
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded // 기본 값 타입으로 새로운 타입을 정의한 클래스를 것을 내장 타입이라고 한다.
    // 내장 타입을 사용하는 쪽에서 @Embedded 애너테이션을 붙여서 해당 필드의 타입이 내장 타입임을 지정한다.
    // 원래 내장 타입을 정의한 클래스에 @Embeddable 애너테이션을 붙여서 해당 클래스가 내장 타입 클래스임을 알려줘야 하지만 사용하는 쪽에서 @Embedded 애너테이션을 붙이면 해당 타입의 클래스가 내장 타입 클래스라고 인식해서 정상 동작한다.
    // 결론은 사용하는 쪽에서는 해당 필드에 @Embedded 애너테이션을 붙여주고 해당 내장 클래스에 @Embeddable 애너테이션을 붙여도 되고 안붙여도 되지만, 관례상 붙이는 것을 권장한다.
    private Address address;

    @OneToMany(mappedBy = "member") // 연관 관계 애너테이션에서 앞(One)이 이 엔티티를 가리키고 뒤(Many)가 상대 엔티티를 가리킨다.
    // 연관 관계 애노테이션의 인자로 mappedBy 옵션에 연관 관계 주인인 Order 엔티티의 member 필드에 맵핑되어 있다고 알려줘야 한다.
    private List<Order> orders = new ArrayList<>();

}
