package jpabook.jpashop;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j // Lombok에서 제공하는 Logger 애노테이션이다.
// 이 애노테이션을 붙이면 아래 Logger를 정의한 부분을 작성하지 않아도 작성한 것과 동일하게 동작한다.
public class HomeController {

//    Logger log = LoggerFactory.getLogger(getClass()); // Lombok의 @Slf4j 애노테이션으로 이 줄을 생략할 수 있다.

    @RequestMapping("/")
    public String home() {
        log.info("home controller");

        return "home";
    }
}
