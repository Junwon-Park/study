package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello") // Get 메서드로 /hello라는 경로로 요청하면 Tomcat 서버는 해당 요청을 이 Controller로 전달한다.
    public String hello(Model model) { // 여기에서 Model은 MVC 패턴의 Model이다.
        // MVC 패턴에서 Controller는 요청을 받으면 해당 요청을 Model에 전달한다. 바로 Model에 전달하기 위해 Controller에서는 Model을 불러와야 한다.
        model.addAttribute("data1", "Spring!!"); // model의 addAttribute() 메서드로 Model에 데이터를 전달하는데,
        // 여기에서는 data1이라는 이름으로 Spring!!이라는 값을 전달했다.
        return "hello"; // 여기에서 반환한 값으로 Spring boot는 resource/templates 하위에 있는 반환 값과 동일한 이름을 가진 .html 파일을 찾는다.
        // 해당 파일을 찾으면 위에서 Model에 전달한 data1: "Spring!!"이라는 데이터를 해당 정적 파일(템플릿 엔진)에 전달한다.
    }
}
