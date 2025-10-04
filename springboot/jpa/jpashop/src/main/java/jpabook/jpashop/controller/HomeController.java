package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j // Lombok에서 제공하는 slf4j 로거를 내부적으로 사용할 수 있도록 해주는 애노테이션
// 원래는 클래스 내부에 LoggerFactory의 getLogger() 메서드를 호출해서 사용하는 클래스를 매개변수로 줘서 사용해야 하지만 해당 코드 없이 스프링 실행 시 내부적으로 해주는 기능
public class HomeController {

//    Logger logger = LoggerFactory.getLogger(HomeController.class);
    // 위에서 @Slf4j 애노테이션 사용으로 해당 코드 없이 Slf4j 로거를 사용할 수 있다.

    @RequestMapping("/")
    public String home() {
        log.info("Home controller");

        return "home";
    }
}
