package homework.applylecture.domain.lecture;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lectures")
@NoArgsConstructor
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "lectureId")
    private Long lectureId;

    @Column(name = "isCancel", nullable = false)
    private Boolean isCancel = false;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
}