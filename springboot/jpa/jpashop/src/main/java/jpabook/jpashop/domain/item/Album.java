package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("A")
// Single table 전략이기 때문에 해당 아이템이 Album인지 Book인지 Movie인지 구분할 수 있어야 하기 때문에 부모 클래스에서 @DiscriminatorColumn에 지정한 컬럼에 들어갈 구분 값을 지정한 것이다.
// 지정하지 않으면 해당 클래스의 이름이 들어간다 만약 Album이라면 Album이라고 값이 들어간다.
@Getter @Setter
public class Album extends Item {

    private String artist;
    private String etc;
}
