package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("M") // 이 클래스가 상속받고 있는 부모 클래스(Item)의 @DiscrimitatorColumn에 지정된 필드(DTYPE)의 값으로 저장될 이름을 지정하는 애노테이션이다.
@Getter @Setter
public class Movie extends Item {
    private String dirctor;
    private String actor;
}
