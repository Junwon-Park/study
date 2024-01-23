package hello.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity // 이 애노테이션을 달면 스프링이 이 클래스를 Entity class로 인식하게 되기 때문에 해당 클래스에 @Id 애노테이션을 달고 있는 id(PK) 필드가 없다면 컴파일 에러가 발생한다.
@Table(name = "board_table")
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false) // length와 nullable 옵션은 지정하지 않은 경우 각각 255, null 허용 필드가 된다.
    private String boardWriter;

    @Column()
    private String boardTitle;

    @Column(length = 500) // length는 500, nullable = true 필드가 된다.
    private String boardContents;

    @Column
    private int boardHits;
}
