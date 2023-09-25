package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello!!!");
        return "hello"; // templates 디렉토리 내에서 여기에서 반환하는 문자열과 동일한 이름을 가진 html 파일에 위에서 생성한 model을 전달한다.
    }
}
