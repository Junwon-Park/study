package com.studyolle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AccountController {

    @GetMapping("/sign-up")
    public String signUpForm(@ModelAttribute SignUpForm signUpForm) {

        return "account/sign-up";
    }
}
