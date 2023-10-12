package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable // 내장 타입 클래스라는 것을 JPA에 알려주는 애노테이션이다.
// 내장 타입을 사용하는 곳의 필드에도 @Embedded 애노테이션을 붙여줘야 하는데, 사실 내장 타입 클래스에 @Embeddable 애노테이션을 붙이거나 사용하는 곳의 필드에 @Embedded 애노테이션을 붙이거나 둘 중 하나만 붙여도 동작은 한다.
// 하지만 운영상 둘 다 붙여주는 것이 관례이다.
@Getter // 내장 타입은 처음 생성할 때 데이터를 넣어 생성하면 그 이후 값을 절대 변경할 수 없도록 해야 한다.(Immutable)
// 그래서 Getter만 열어 두고 Setter는 닫아 둔 것이다.
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() { // JPA 스펙상 엔티티와 임베디드 타입은 자바 기본 생성자를 public 또는 protected로 설정해서 생성해야 한다.
        // JPA가 자바 기본 생성자를 생성하도록 하는 이유는 JPA 구현 라이브러리가 객체를 생성할 때 리플랙션 같은 기술을 사용할 수 있도록 할 때 필요하기 때문이다.
        // 하지만 public으로 열어두는 것 보다는 protected 정도로 좁혀두는 것이 더 안전하다. 즉, protected를 사용하는 것이 권장 사항이다.
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
