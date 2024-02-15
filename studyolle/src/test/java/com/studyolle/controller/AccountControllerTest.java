package com.studyolle.controller;

import com.studyolle.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepository;

    @MockBean
    JavaMailSender javaMailSender;

    @DisplayName("회원 가입 화면 보이는 지 테스트")
    @Test
    void signUpForm() throws Exception {
        mockMvc.perform(get("/sign-up")) // "/sign-up"으로 GET 요청
                .andDo(print()) // 위 perform(get())의 결과 콘솔에 출력
                .andExpect(status().isOk()) // 위 perform(get())의 결과의 status()가 isOk() -> 200, OK면 통과
                .andExpect(view().name("account/sign-up")) // 위 perform(get())의 결과 호출된 view()의 name()이 "account/sign-up"이면 통과
                .andExpect(model().attributeExists("signUpForm")); // 위 perform(get())의 결과 호출 된 View에 넘어온 Attribute 중 이름이 "signUpForm"인 것이 있으면 통과
    }

    @DisplayName("회원 가입 처리 - 입력 값 오류")
    @Test
    void signUpSubmit_with_wrong_input() throws Exception {
        mockMvc.perform(post("/sign-up")
                        .param("nickname", "Jackson")
                        .param("email", "your@email.com")
                        .param("password", "12345")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("account/sign-up"));
    }

    @DisplayName("회원 가입 처리 - 입력 값 정상")
    @Test
    void signUpSubmit_with_correct_input() throws Exception {
        mockMvc.perform(post("/sign-up")
                        .param("nickname", "jackson")
                        .param("email", "your@email.com")
                        .param("password", "12345678")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        assertTrue(accountRepository.existsByEmail("your@email.com"));
        then(javaMailSender).should().send(any(SimpleMailMessage.class));
    }
}