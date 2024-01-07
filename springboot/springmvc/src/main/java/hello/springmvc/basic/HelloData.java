package hello.springmvc.basic;

import lombok.Data;

@Data // Lombok의 @Data 애노테이션은 @Getter, @Setter, @ToString, @EqualAndHashCode, @RequiredArgsConstructor 애노테이션을 모두 적용한 것과 같다.
// @Getter, @Setter, @ToString, @EqualAndHashCode, @RequiredArgsConstructor 애노테이션 모두 Lombok에서 제공하는 애노테이션이다. -> 애노테이션 각각 Getter()와 Setter(), toString(), equals(), hashCode(), 생성자를 자동으로 적용해주는 애노테이션이다.
// 실제로 위 메서드들이 클래스 내부에 코드로 존재하지는 않지만 내부적으로 존재하는 것 처럼 동작하도록 적용해준다. 사용하면 실제로 동작한다.
public class HelloData {
    private String username;
    private int age;
}
