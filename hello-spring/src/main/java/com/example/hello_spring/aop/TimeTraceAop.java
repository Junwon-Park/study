package com.example.hello_spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
//@Component // 컴포넌트 스캔 방식으로 해도 문제 없고 Config에 @Bean으로 등록해서 사용해도 되지만
// @Service나 @Repository 처럼 정형화 되어 있는 것들은 컴포넌트 스캔 방식으로 많이 쓰지만 AOP 처럼 정형화 되지 않은 것들은 개발자들이 명시적으로 알 수 있도록 Config에 @Bean으로 등록해서 사용하는 경우가 많다(컴포넌트 스캔으로 사용해도 무방)
public class TimeTraceAop {

    @Around("execution(* com.example.hello_spring..*(..))") // com 패키지 하위의 example 패키지 하위의 hello_spring 패키지의 하위에 있는 모든 클래스에 해당 AOP 적용
//    @Around("execution(* com.example.hello_spring.service..*(..))") // com 패키지 하위의 example 패키지 하위의 hello_spring 패키지의 service 패키지의 하위에 있는 모든 클래스에 해당 AOP 적용
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } finally {
            long end = System.currentTimeMillis();
            long timeMs = end - start;

            System.out.println("join = " + timeMs + " ms");
        }
    }
}
