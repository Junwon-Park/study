package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속관계 매핑 시, 부모 클래스에 상속 전략을 지정해줘야 하는데, 이 떄, @Inheritance() 애노테이션의 strategy 옵션에 사용할 옵션 값을 설정해주면 된다.
// Item의 경우 멤버의 타입이 단순하고 많지 않아서 한 필드에 이 상속관계에 연결된 모든 필드를 다 매핑하는 SINGLE_TABLE 전략을 사용할 것이기 떄문에 SINGLE_TABLE 옵션을 지정했다.
// 하지만 꼭 필요한 경우가 아니라면 기본으로 JOINED 전략을 사용하는 것이 좋다.
// 또 하나의 전략인 TABLE_PER_CLASS도 있는데 이 것은 사용하지 않는 것이 좋다.
// JOINED - 기본으로 채택, SINGLE_TABLE - 멤버가 많지 않고 단순하며 정규화 보다 성능만이 중요하다면 SINGLE_TABLE 전략을 사용할 수 있다.
@DiscriminatorColumn(name = "dtype") // 상속관계 매핑에서 부모 클래스에 해당 엔티티의 구현체(자식 클래스)가 어떤 것인지 저장할 필드를 생성하도록 알려주는 애노테이션이다.
// 위에서는 name 옵션을 정해줬는데, 해당 필드의 필드명을 name 옵션에 지정한 이름으로 생성하는 옵션이다.
// name 옵션을 지정하지 않으면 기본 값은 DTYPE이라고 생성된다.
// 그리고 위 애노테이션이 달린 부모 클래스를 상속받는 자식 클래스들은 데이터를 저장 할 때, DTYPE에 저장할 값을 지정해줘야 하는데, 자식 클래스에 @DiscriminatorValue 애노테이션을 붙이면 자식 클래스의 이름이 기본 값으로 부모 클래스의 DTYPE에 저장되도록 기본 값이 설정되어 있다.
// 만약 DTYPE에 저장될 자식 클래스의 이름을 지정하려면 애노테이션의 파라미터에 문자열로 넣어서 지정할 수 있다.
@Getter @Setter
public abstract class Item { // 상속관계 매핑에서 부모 클래스는 구현 클래스인 자식 클래스로서 사용되기 때문에 추상 클래스(Abstract class)로 선언한다.
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
