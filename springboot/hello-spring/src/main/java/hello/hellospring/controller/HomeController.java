package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // 스프링 부트는 Tomcat에서 전달 받은 요청에 대해서 요청의 메서드와 path에 맵핑되는 메서드가 있는지 스프링 컨테이너에 등록된 Controller를 먼저 찾고 없으면 path와 동일한 이름의 static(정적) 파일을 찾는다.
    // 그렇기 때문에 원래는 index.html이 기본으로 웰컴 페이지로써 실행됐지만 스프링 부트는 요청에 대해서 스프링 컨테이너에 등록된 컨트롤러를 먼저 찾고 이 경우 맵핑되는 컨트롤러가 존재하기 때문에 기존 index.html은 무시되고 여기에 매핑된 화면이 실행된다.
    @GetMapping("/") // localhost:8080으로 들어온 요청은 이 메서드를 찾아 들어온다.
    public String home() {
        return "home"; // @ResponseBody 어노테이션이 없기 때문에 ViewResolver가 home이라는 이름을 가진 템플릿 파일을 찾아 거기로 이동시킨다.(없으면 에러)
    }
}
