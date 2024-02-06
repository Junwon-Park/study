package hello.board.service;

import hello.board.dto.BoardDto;
import hello.board.entity.BoardEntity;
import hello.board.entity.BoardFileEntity;
import hello.board.repository.BoardFileRepository;
import hello.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    public void save(BoardDto boardDto) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리 필요
        if (boardDto.getBoardFile().isEmpty()) {
            // 첨부 파일 없음.
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDto);

            boardRepository.save(boardEntity);
        } else {
            // 첨부 파일 있음.
            /*
            1. DTO에 담긴 파일을 꺼낸다.
            2. 파일의 이름을 가져온다.
            3. 서버 저장용 이름을 만든다.
            4. 저장 경로를 설정한다.
            5. 해당 경로에 파일을 저장한다.
            6. board_table에 해당 데이터 save 처리한다.
            7. board_file_table에 해당 데이터 save 처리한다.
             */

            BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDto);
            Long savedId = boardRepository.save(boardEntity).getId();
            BoardEntity board = boardRepository.findById(savedId).get();
            for (MultipartFile boardFile : boardDto.getBoardFile()) {
//            List<MultipartFile> boardFile = boardDto.getBoardFile(); // 1.
                String originalFilename = boardFile.getOriginalFilename(); // 2.
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3.
                String savePath = "/Users/jackson/springboot_img/" + storedFileName; // 4. 저장할 Local 경로
                boardFile.transferTo(new File(savePath)); // 5. 경로에 파일 저장
                // transferTo() 메서드는 MultipartFile 인터페이스에서 제공하는 것으로 지정한 경로에 파일을 저장하는 메서드이다.

                BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFilename, storedFileName);
                boardFileRepository.save(boardFileEntity);
            }
        }
    }

    @Transactional
    public List<BoardDto> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (BoardEntity boardEntity: boardEntityList) {
            boardDtoList.add(BoardDto.toBoardDto(boardEntity));
        }

        return boardDtoList;
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDto findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();

            BoardDto boardDto = BoardDto.toBoardDto(boardEntity);
            log.info("BoardDTO={}", boardDto);

            return boardDto;
        } else {
            return null;
        }
    }

    public BoardDto update(BoardDto boardDto) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDto);
        boardRepository.save(boardEntity);

        return findById(boardDto.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public Page<BoardDto> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        log.info("page={}", page);
        log.info("Pageable={}", pageable);
        
        int pageLimit = 3;

        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        // DTO 필드: id, boardWriter, title hits, createdTime
        return boardEntities.map(board ->
                new BoardDto(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime())
        );
    }
}