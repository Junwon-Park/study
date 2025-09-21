package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// 상속관계 엔티티 전략은 부모 클래스에서 명시해야 한다.
// 이번 예제에서는 한 테이블에 모든 자식 테이블의 모든 컬럼 까지 가지도록 구현하는 Single table 전략을 설정했다.
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {
    // Item은 다른 클래스에서 공통 속성만 상속받아 사용될 목적이므로 Item 클래스를 생성할 수 없도록 abstract class로 구현
    // abstract class 엔티티도 다른 엔티티에서 상속 받아 사용될 것이므로 JPA가 이 것을 유추할 수 있도록 @Entity 어노테이션을 붙여줘야 한다.

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
