package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    /** MVC + 템플릿 엔진 방식 */

    @GetMapping("hello") // Get 메서드로 /hello라는 경로로 요청하면 Tomcat 서버는 해당 요청을 이 Controller로 전달한다.
    public String hello(Model model) { // 여기에서 Model은 MVC 패턴의 Model이다.
        // MVC 패턴에서 Controller는 요청을 받으면 해당 요청을 Model에 전달한다. 바로 Model에 전달하기 위해 Controller에서는 Model을 불러와야 한다.
        model.addAttribute("data1", "Spring!!"); // model의 addAttribute() 메서드로 Model에 데이터를 전달하는데,
        // 여기에서는 data1이라는 이름으로 Spring!!이라는 값을 전달했다.
        return "hello"; // 여기에서 반환한 값으로 Spring boot는 resource/templates 하위에 있는 반환 값과 동일한 이름을 가진 .html 파일을 찾는다.
        // 해당 파일을 찾으면 위에서 Model에 전달한 data1: "Spring!!"이라는 데이터를 해당 정적 파일(템플릿 엔진)에 전달한다.
        // 이 때, resource/template에서 리턴 값인 "hello"라는 이름을 가진 html 파일을 찾는 것이 viewResovler라는 것이다. Controller는 Model에 데이터를 담아 텍스트와 함께 반환하고 viewResolver는 Controller에서 반환한 텍스트와 동일한 이름을 가진 html 파일을 찾아
        // Controller에서 Model에 넣은 데이터와 함께 템플릿 엔진 변환 처리를 해서 Tomcat 서버에 전달하면 Tomcat 서버는 브라우저에 해당 화면을 응답한다.
    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);

        return "hello-template";
    }


    /** API 방식 */

    @GetMapping("hello-string")
    @ResponseBody // @ResponseBody 핸들러(컨트롤러의 API 메서드) 어노테이션을 달면 이 메서드의 반환 값을 viewResovler에 전달하는 것이 아니라
    // 해당 값을 바로 http 응답 값으로 브라우저에 내려준다.
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; // url의 param name에 spring 전달 시, 브라우저에 hello spring 출력
        // 이 문자열 반환 값을 그 대로 브라우저에 내려준다.
    }

    @GetMapping("hello-api")
    @ResponseBody // @ResponseBody 핸들러(컨트롤러의 API 메서드) 어노테이션을 달면 이 메서드의 반환 값을 viewResovler에 전달하는 것이 아니라
    // 해당 값을 바로 http 응답 값으로 브라우저에 내려준다.
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name); // ?name=spring

        return hello; // 브라우저에는 {"name":"spring"} JSON 형식으로 전달.(?name=spring)
        // 이렇게 핸들러에 @ResponseBody 어노테이션을 달고 객체(클래스 인스턴스)를 만들어서 객체를 반환하면 기본적으로 JSON 형식으로 클라이언트에 내려준다.
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 클라이언트로 부터 요청을 받으면 스프링 부트는 요청의 paht에 해당하는 컨트롤러가 있는지 먼저 확인한다.
     * 해당하는 컨트롤러가 있다면 해당 요청을 해당 컨트롤러에 요청을 전달한다.
     * 처리할 로직이 있다면 처리하고 반환할 때, 만약 해당 컨트롤러에 @ResponseBody 어노테이션의 달려 있다면 이 반환 값을 viewResolver에 전달하는 것이 아니라
     * HttpMessageConverter에 전달한다. 그 종류는 JsonConverter와 StringConverter 등이 있다.
     * 그래서 반환 값이 String이면 StringConverter에 반환 값을 전달해서 변환 후 변환된 값을 http body에 담아 클라이언트에 응답하고
     * 반환 값이 객체라면 JsonConverter에 전달해서 JSON 형식으로 변환 후 변환된 값을 http body에 담아 클라이언트에 응답한다.
     *
     * 컨트롤러에 @ResponseBody 어노테이션이 달려 있으면 viewResolver 대신 HttpMessageConverter가 동작한다.
     * 기본 문자 처리: StringHttpMessageConverter
     * 기본 객체 처리: MappingJackson2HtpMessageConverter -> JSON 변환에는 여러 라이브러리가 있지만 Spring boot에서는 기본으로 Jackson이라는 JSON 변환 라이브러리가 등록되어 있다.(Jackson2의 2는 버전이다.)
     * 변환 라이브러리는 변경 가능하다. 하지만 실무에서는 이대로 쓰기 때문에 굳이 바꿀 필요 없이 그냥 사용하면 된다.
     *
     * 만약 응답 헤더의 Accept 헤더에 반환 타입을 명시하면 그 반환 타입에 따라 HttpMessageConverter가 선택된다.
     */
}
