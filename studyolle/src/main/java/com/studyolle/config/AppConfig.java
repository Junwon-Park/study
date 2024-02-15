package com.studyolle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    @Bean
    public PasswordEncoder passwordEncoder() { // PasswordEncoder는 Spring security가 제공하는 인코딩 인터페이스이다.
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // PasswordEncoderFactories의 createDelegatingPasswordEncoder()를 호출하면 인코더가 반환되는데, BCryptPasswordEncoder를 받아 사용하게 된다.
    }
}
