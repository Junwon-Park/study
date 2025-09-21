package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") // mappedBy에는 필드 이름을 지정한다.
    // mappedBy는 연관관계의 주인의 어떤 필드에 맵핑되어 있는 것인지 명시하는 것이다.
    // 연관관계의 주인아 아니기 때문에 이 orders 필드의 값은 변경 불가하며 조회만 가능하다.(연관관계의 주인인 order에서는 member 필드를 변경할 수 있다.)
     private List<Order> orders = new ArrayList<>();
}
