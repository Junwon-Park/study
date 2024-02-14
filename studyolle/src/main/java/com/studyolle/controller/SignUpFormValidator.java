package com.studyolle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

// 커스텀 Validator 생성
@Component // 해당 Validator를 AccountController에서 주입 받아 사용할 것이기 때문에 컴포넌트 스캔에 잡혀서 빈으로 등록되도록 애노테이션 추가
@RequiredArgsConstructor // AccountRepository 생성자 주입
public class SignUpFormValidator implements Validator {

    private final AccountRepository accountRepository; // final로 선언해야 @RequiredArgsConstructor가 생성자 자동 주입 해준다.

    @Override
    public boolean supports(Class<?> clazz) { // 인자로 들어온 클래스 타입이 이 Validator에서 지원하는 타입인지 검사하는 메서드
        return clazz.isAssignableFrom(SignUpForm.class); // 인자로 받은 clazz를 isAssignableFrom() 메서드를 사용해서 SignUpForm.class 타입이 맞는 지 확인
    }

    @Override
    public void validate(Object target, Errors errors) { // support에서 지원하는 타입인지 확인 됐으면 실제로 이 Validator에서 검증하려는 것을 검증하는 메서드
        // TODO email, nickname
        SignUpForm signUpForm = (SignUpForm) target;

        if (accountRepository.existsByEmail(signUpForm.getEmail())) {
            errors.rejectValue("email", "invalid.email", new Object[]{signUpForm.getEmail()}, "이미 사용 중인 이메일입니다.");
        }

        if (accountRepository.existsByNickname(signUpForm.getNickname())) {
            errors.rejectValue("nickname", "invalid.nickname", new Object[]{signUpForm.getNickname()}, "이미 사용 중인 닉네임입니다.");
        }
    }
}
