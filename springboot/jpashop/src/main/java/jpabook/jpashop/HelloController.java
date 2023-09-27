package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("hello")
    public String hello(Model model) {
        // 여기서의 Model 인터페이스는 MVC의 Model이며, 데이터를 View로 넘겨주는 기능(메서드)을 가지고 있다.
        model.addAttribute("data", "hello!!!");
        return "hello"; // templates 디렉토리 내에서 여기에서 반환하는 문자열과 동일한 이름을 가진 html(hello.html) 파일에 위에서 생성한 model을 전달한다.
        // 스프링 부트에 Thymeleaf를 설치하면 자동으로 스프링 부트의 설정에서 resources:templetes/ +{viewName}+ .html을 자동으로 세팅하고 이대로 실행한다.
        // 기본 설정은 templetes 디렉토리가 Root 경로로 되어 있지만 이 경로를 변경해서 다른 경로를 지정할 수 있다.(스프링 부트 메뉴얼 참조, 하지만 기본 설정 권장)
    }
}
