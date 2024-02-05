package hello.board.repository;

import hello.board.entity.BoardEntity;
import hello.board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> { // JpaRepository<엔티티, 해당 엔티티의 PK 타입>
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}
