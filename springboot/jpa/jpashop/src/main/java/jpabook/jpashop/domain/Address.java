package jpabook.jpashop.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
// 내장타입 객체는 처음 생성한 뒤에는 값이 수정될 일이 없다(Immutable). 이후엔 조회만 하기 때문에 Getter만 생성한다.
public class Address {

    private String city;
    private String street;
    private String zipcode;

    /**
     * Default Constructor for JPA
     */
    protected Address() {}
    // 기본적으로 JPA의 구현 라이브러리가 객체를 생성할 때 리플렉션이나 프록시 같은 기술을 사용하할 수 있도록 지원하기 위함이다.
    // 자바에서 아무런 생성자를 명시적으로 생성해놓지 않으면 자바 내부적으로 기본 생성자를 생성하지만 Address의 경우 아래 생성자를 만들었기 때문에 기본 생성자를 명시적으로 하나 더 만들어줘야 한다.
    // 하지만 이 것은 개발자가 실제로 사용할 생성자는 아니기 때문에 public으로 열어두기 보다는 안전하게 protected로 막아둔다.(JPA가 protected 까지는 허용해준다. private은 불가, 에러 발생)

    /**
     * Address 객체는 Entity가 아닌 내장타입 객체이기 때문에 이후 데이터의 수정을 막기 위해
     * Setter는 생성하지 않고 대신 최초 생성 시 데이터를 넣을 수 있어야 하기 때문에 생성자만 만들어 둔다.
     */
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
