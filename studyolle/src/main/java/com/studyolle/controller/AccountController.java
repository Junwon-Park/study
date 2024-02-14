package com.studyolle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;

    @InitBinder("signUpForm")
    // 이 컨트롤러로 들어오는 요청의 데이터 중 "signUpForm"이라는 이름으로 들어오는 데이터는 먼저
    // webDataBinder의 addValidators()에 등록한 Validator로 검증 후 해당 핸들러의 파라미터로 바인딩 된다.
    // 이렇게 하면 아래 signUpForm을 받는 핸들러에 동일한 유효성 검사 코드가 중복되는 것을 막을 수 있다.
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    @GetMapping("/sign-up")
    public String signUpForm(SignUpForm signUpForm) {
        return "account/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpSubmit(@Validated SignUpForm signUpForm, Errors errors) {
        if (errors.hasErrors()) {
            return "account/sign-up";
        }

        // TODO 회원 가입 처리
        return "redirect:/";
    }
}
