package com.group.libraryapp.controller;

import com.group.libraryapp.dto.calculator.request.CalculatorAddRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

    @GetMapping("/add")
    public int addTwoNumbers(CalculatorAddRequest request) {
        // 인자가 객체이고 애노테이션을 지정하지 않은 경우 메세지 컨버터가 @ModelAttribute 자동 적용(@ModelAttribute CalculatorAddRequest request과 동일하게 동작)
        return request.getNumber1() + request.getNumber2();
    }
}
