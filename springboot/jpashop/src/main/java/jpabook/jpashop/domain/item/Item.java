package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속관계 매핑 시, 부모 클래스에 상속 전략을 지정해줘야 하는데, 이 떄, @Inheritance() 애노테이션의 strategy 옵션에 사용할 옵션 값을 설정해주면 된다.
// Item의 경우 멤버의 타입이 단순하고 많지 않아서 한 필드에 이 상속관계에 연결된 모든 필드를 다 매핑하는 SINGLE_TABLE 전략을 사용할 것이기 떄문에 SINGLE_TABLE 옵션을 지정했다.
// 하지만 꼭 필요한 경우가 아니라면 기본으로 JOINED 전략을 사용하는 것이 좋다.
// 또 하나의 전략인 TABLE_PER_CLASS도 있는데 이 것은 사용하지 않는 것이 좋다.
// JOINED - 기본으로 채택, SINGLE_TABLE - 멤버가 많지 않고 단순하며 정규화 보다 성능만이 중요하다면 SINGLE_TABLE 전략을 사용할 수 있다.
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item { // 상속관계 매핑에서 부모 클래스는 구현 클래스인 자식 클래스로서 사용되기 때문에 추상 클래스(Abstract class)로 선언한다.
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
}
