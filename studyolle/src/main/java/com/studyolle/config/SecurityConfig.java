package com.studyolle.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 스프링 빈 컨테이너로 등록
@EnableWebSecurity // 스프링 시큐리티 설정으로 지정
public class SecurityConfig {

    @Bean // 빈으로 등록
    // 스프링 시큐리티 5.7 이후 부터 SecurityFilterChain을 사용해서 하는 방식만 사용 가능 -> WebSecurityConfigurerAdapter 사용 중단(Deprecated)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http // 스프링 시큐리티 6.1 이후 부터 아래 람다 방식으로 사용하도록 변경됨
                .authorizeHttpRequests((authorizeRequests) ->
                    authorizeRequests
                            .requestMatchers("/", "/login", "/sign-up", "/check-email", "/check-email-token",
                            "/email-login", "/check-email-login", "/login-link").permitAll()
                            // 기존 mvcMatchers()가 requestMatchers()로 변경됨
                            // 특정 메서드에 대한 내용이 없기 때문에 지정된 경로로 들어오는 모든 요청 메서드에 대해서 허용
                            .requestMatchers(HttpMethod.GET, "/profile/*").permitAll() // /profile/* 로 접근한 모든 GET 요청에 대해 허용
                            .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean // 해당 빈과 아래 설정을 해줘야 Spring security의 static 리소스 사용을 허용할 수 있다.
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // Spring security 5.7 이후 WebSecurityConfigurerAdapter 사용 중단(Deprecated)으로 변경된 사항 아래 문서 링크에서 확인 및 적용
    // https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
}
