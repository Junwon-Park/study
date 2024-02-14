package com.studyolle.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("회원 가입 화면 보이는 지 테스트")
    @Test
    void signUpForm() throws Exception {
        mockMvc.perform(get("/sign-up")) // "/sign-up"으로 GET 요청
                .andDo(print()) // 위 perform(get())의 결과 콘솔에 출력
                .andExpect(status().isOk()) // 위 perform(get())의 결과의 status()가 isOk() -> 200, OK면 통과
                .andExpect(view().name("account/sign-up")) // 위 perform(get())의 결과 호출된 view()의 name()이 "account/sign-up"이면 통과
                .andExpect(model().attributeExists("signUpForm")); // 위 perform(get())의 결과 호출 된 View에 넘어온 Attribute 중 이름이 "signUpForm"인 것이 있으면 통과
    }
}