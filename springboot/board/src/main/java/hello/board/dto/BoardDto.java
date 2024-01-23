package hello.board.dto;

import lombok.*;

import java.time.LocalDateTime;

// DTO: Data Transfer Object

// Lombok
@Getter @Setter
@ToString
// @NoArgsConstructor // 기본 생성자(파라미터가 없는 생성자) 자동 생성
// @AllArgsConstructor // 클래스의 모든 필드 파라미터를 가진 생성자 자동 생성
// @RequiredArgsConstructor // final 상수에 대한 것 만을 파라미터로 받는 생성자 자동 생성
public class BoardDto {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;
}
