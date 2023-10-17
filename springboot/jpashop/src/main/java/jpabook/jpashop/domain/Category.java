package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    // 아래 JoinTable() 애노테이션과 내부 옵션은 N : N을 구현하는 방법이다. 하지만 N : N 방식은 시용하지 않는 것이 좋다.
    @ManyToMany
    @JoinTable(name = "category_item", // @JoinTable() 애노테이션은 ManyToMany 관계에서 조인 테이블을 생성하는 애노테이션이다.
            // ManyToMany 관계로 설정한 두 테이블을 매핑해주는 중간 조인 테이블이 생성된다.(DB에 실제 지정한 이름으로 아래 지정한 컬럼을 가지고 있는 테이블이 생성된다.)
            // name 옵션에 조인 테이블의 이름을 지정할 수 있다.
            joinColumns = @JoinColumn(name = "category_id"), // 현재 엔티티의 ID가 들어가는 컬럼과 그 이름을 지정한 것이다.
            inverseJoinColumns = @JoinColumn(name = "item_id")) // 상대편 앤티티의 ID가 들어가는 컬럼과 그 이름을 지정한 것이다.
    private List<Item> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();
}
