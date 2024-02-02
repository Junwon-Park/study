package hello.board.dto;

import hello.board.entity.BoardEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

// DTO: Data Transfer Object

// Lombok
@Getter @Setter
@ToString
@NoArgsConstructor // 기본 생성자(파라미터가 없는 생성자) 자동 생성
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
    private MultipartFile boardFile; // 파일 데이터 담을 용도
    private String originalFileName; // 원본 파일 이름
    private String storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)

    public BoardDto(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    public static BoardDto toBoardDto(BoardEntity boardEntity) {
        BoardDto boardDto = new BoardDto();

        boardDto.setId(boardEntity.getId());
        boardDto.setBoardWriter(boardEntity.getBoardWriter());
        boardDto.setBoardPass(boardEntity.getBoardPass());
        boardDto.setBoardTitle(boardEntity.getBoardTitle());
        boardDto.setBoardContents(boardEntity.getBoardContents());
        boardDto.setBoardHits(boardEntity.getBoardHits());
        boardDto.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDto.setBoardUpdatedTime(boardEntity.getUpdatedTime());

        if (boardEntity.getFileAttached() == 0) {
            boardDto.setFileAttached(0);
        } else {
            boardDto.setFileAttached(boardEntity.getFileAttached());

            // 파일 이름을 가져가야 한다.
            // originalFileName, storedFileName -> board_file_table(BoardFileEntity)
            boardDto.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
            boardDto.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
        }

        return boardDto;
    }
}
