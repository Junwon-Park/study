package jpabook.jpashop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity // JPA Entity 지정
@Getter @Setter // Lombok 사용
public class Member {
    @Id @GeneratedValue // @Id 애노테이션은 이 필드를 Id 값으로 매핑하도록 지정한 것이고, @GeneratedValue 애노테이션은 데이터 생성 시 이 값을 자동으로 생성하도록 지정한 것이다.
    private Long id;

    private String username;
}
