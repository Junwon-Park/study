package com.studyolle.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
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
    private String profileImage; // String 타입은 기본적으로 varchar(255)로 설정됨
    // 이미지의 경우 varchar(255) 이상이 될 수 있기 때문에 @Lob으로 지정

    private boolean studyCreatedByEmail; // 스터디 생성 여부 이메일 수신 여부

    private boolean studyCreatedByWeb; // 스터디 생성 여부 웹 수신 여부

    private boolean studyEnrollmentResultByEmail; // 스터디 가입 신청 결과 이메일 수신 여부

    private boolean studyEnrollmentResultByWeb; // 스터디 가입 신청 결과 웹 수신 여부

    private boolean studyUpdatedByEmail; // 스터디 변경 정보 이메일 수신 여부

    private boolean studyUpdatedByWeb; // 스터디 변경 정보 웹 수신 여부
}
