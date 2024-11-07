package com.example.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!");
        // model 객체를 받아 addAtrribute() 메서드를 사용해서 키:값 형태로 데이터를 받아 { "data": "hello" } 속성 저장

        return "hello";
        // 스프링 내부적으로 컨트롤러의 핸들러 메서드의 문자열 반환 값은 찾는 템플릿 파일의 이름을 가리킨다.
        // return "hello" -> 스프링의 viewResolver가 resources/templates 경로의 hello라는 이름을 가진 파일을 찾아 해당 파일로 전달한다. 키:값 형태로 ({ data: "hello!!" }) 전달
        // 전달 받은 데이터를 Thymeleaf(사용하는 템플릿 엔진이 처리)가 처리한다.
        // model에 키:값 형태로 저장해놓은 model을 viewResolver가 resources/templates 경로의 hello.html로 전달한다.
    }
}

