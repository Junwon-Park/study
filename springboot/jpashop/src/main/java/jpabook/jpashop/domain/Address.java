package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable // 내장 타입 클래스라는 것을 JPA에 알려주는 애노테이션이다.
// 내장 타입을 사용하는 곳의 필드에도 @Embedded 애노테이션을 붙여줘야 하는데, 사실 내장 타입 클래스에 @Embeddable 애노테이션을 붙이거나 사용하는 곳의 필드에 @Embedded 애노테이션을 붙이거나 둘 중 하나만 붙여도 동작은 한다.
// 하지만 운영상 둘 다 붙여주는 것이 관례이다.
@Getter // 내장 타입은 처음 생성할 때 데이터를 넣어 생성하면 그 이후 값을 절대 변경할 수 없도록 해야 한다.(Immutable)
// 그래서 Getter만 열어 두고 Setter는 닫아 둔 것이다.
public class Address {

}
