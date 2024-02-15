package com.studyolle.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
@EqualsAndHashCode(of = "id") // equals와 hashCode 메서드를 자동으로 오버라이딩 해주는 Lombok 애노테이션
// of 속성으로 특정 필드를 지정해서 해당 필드에 대해서만 equals와 hashCode를 생성하도록 할 수 있다.(현재는 id 필드만 equals와 hashCode 적용)
@Builder @AllArgsConstructor @NoArgsConstructor
public class Account {
    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String password;

    private boolean emailVerified;

    private String emailCheckToken;

    private LocalDateTime joinedAt; // 가입일자

    private String bio; // 자기소개

    private String url;

    private String occupation; // 직업

    private String location; // 사는 곳 주소

    @Lob @Basic(fetch = FetchType.EAGER) // 이미지의 경우 계정을 조회할 때, 함께 가져오는 경우가 많아 FetchType을 EAGER로 설정
    // 확인 해보니 @Basic의 fetch 속성은 기본 값이 FetchType.EAGER이기 때문에 FetchType.EAGER로 명시할 필요는 없다.(여기에선 강의와 동일하게 명시)
    private String profileImage; // String 타입은 기본적으로 varchar(255)로 설정됨
    // 이미지의 경우 varchar(255) 이상이 될 수 있기 때문에 @Lob으로 지정

    private boolean studyCreatedByEmail; // 스터디 생성 여부 이메일 수신 여부

    private boolean studyCreatedByWeb; // 스터디 생성 여부 웹 수신 여부

    private boolean studyEnrollmentResultByEmail; // 스터디 가입 신청 결과 이메일 수신 여부

    private boolean studyEnrollmentResultByWeb; // 스터디 가입 신청 결과 웹 수신 여부

    private boolean studyUpdatedByEmail; // 스터디 변경 정보 이메일 수신 여부

    private boolean studyUpdatedByWeb; // 스터디 변경 정보 웹 수신 여부

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
    }
}
