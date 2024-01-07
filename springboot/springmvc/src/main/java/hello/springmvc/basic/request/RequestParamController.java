package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException; // HttpServletResponse의 .getWriter()을 사용하려면 IOException이 필요하다.
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
        // @ResponseBody을 달지 않고 응답 객체의 Body에 값을 담아 클라이언트에 응답하려면 HttpServletResponse의 .getWriter()로 Writer 객체를 가져와 write() 메서드에 보낼 값을 담아줘야 한다.
    }

    @ResponseBody // 컨트롤러 객체에 @Controller 애노테이션을 달면 기본적으로 API에서 반환하는 텍스트를 ViewResolver에 전달해서 해당 논리 이름을 가진 뷰 파일(정적 파일)을 찾아 렌더링하지만
    // @ResponseBody 애노테이션을 달면 해당 API는 반환하는 텍스트를 응답 객체의 Body에 담아 클라이언트에 응답한다.
    // 컨트롤러에 @RestController 애노테이션을 달면 이 애노테이션을 달지 않아도 API가 반환하는 텍스트를 응답 객체의 Body에 담아 클라이언트에 응답하는 방식으로 동작한다.
    // @RestController 애노테이션 내부에 @RequestBody 애노테이션을 포함하고 있다.
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName, // 요청 파라미터를 매핑한다. -> 요청 파라미터의 이름과 함수의 매개 변수 이름이 다르기 떄문에 애노테이션에 요청 파라미터 이름을 명시해줘야 한다.
            // String username = request.getParameter("username");와 동일하다. -> 애노테이션 방식이 더 편리하고 더 깔끔한 코드 작성이 가능해진다.
            @RequestParam int age // 요청 파라미터의 이름과 함수의 매개 변수의 이름이 동일하면 애노테이션의 요청 파라미터 이름을 생략할 수 있다.
            // 함수의 매개 변수의 타입을 int로 정의해 놓으면 @RequestParam 애노테이션의 해당 값을 자동으로 int로 파싱한다.
            ) {

        log.info("username={}, age={}", memberName, age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            // 요청 파라미터의 이름과 함수의 매개 변수 이름이 동일하면 애노테이션 자체를 생략할 수 있다.
            // 그래도 @RequestParam을 붙이는 것이 더 명시적일 수 있다. (선택)
            String username, // 요청 파라미터의 username의 값 바인딩
            int age // 요청 파라미터의 age의 값 바인딩
    ) {

        log.info("username={}, age={}", username, age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(
            // 요청 파라미터 필수 여부를 설정 가능하다.
            @RequestParam(required = true) String username, // 필수 파라미터인 경우 기본 값이 true이기 때문에 굳이 설정해줄 필요는 없다.
            // 필수 파라미터가 들어오지 않은 경우(null인 경우) 서버가 400(Bad Request) 응답을 내린다.
            @RequestParam(required = false) Integer age // 필수 파라미터가 아닌 경우 required 옵션을 false라고 설정해줘야 한다.
            // false로 설정한다는 것은 age의 값이 null로 들어올 수 있다는 것인데 int 타입으로 null을 받으면 파싱이 불가능하기 때문에 500 에러가 발생한다.
            // 그래서 Integer를 사용한 것이고 Integer는 객체이기 때문에 null을 받아도 에러가 발생하지 않는다. 그래서 요청 파라미터가 필수 파라미터가 아니고 숫자로 받는 경우 Integer 객체 타입으로 받아야 한다.
    ) {

        log.info("username={}, age={}", username, age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v5")
    public String requestParamV5(
            @RequestParam(required = true, defaultValue = "guest") String username,
            // defaultValue 옵션을 통해 값이 들어오지 않았을 때, 대체 값을 기본 값으로 사용하도록 할 수 있다.
            @RequestParam(required = false, defaultValue = "-1") int age
            // 요청 파라미터가 필수 값이 아닌 경우 defaultValue 설정을 통해 null을 다른 값으로 받을 수 있고
            // 이 경우 숫자로 받아 int를 사용해도 에러가 발생하지 않도록 할 수 있다.
    ) {

        log.info("username={}, age={}", username, age);

        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v6")
    public String requestParamV6(
            @RequestParam Map<String, Object> paramMap // 요청 파라미터를 Map 형태로 받으면 Key : Value 형태로 요청에 들어온 모든 파라미터를 받을 수 있다.
            // Map의 값은 Object 타입으로 받아야 모든 타입의 값을 다 받을 수 있다.
    ) {

        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));

        return "ok";
    }

    // @RequestParam은 단순 원시 타입 하나하나를 받을 때 사용하고 그 외에는 @ModelAttribute를 사용한다.
    // @ModelAttribute는 요청 파라미터를 받아서 매개 변수의 타입에 지정한 클래스 객체를 생성해서 객체의 멤버 변수에 바인딩해서 API 매개 변수에 바인딩 해준다. -> @ModelAttribute를 사용하면 여러 요청 파라미터를 하나의 객체 형태로 받을 수 있다.
    // @ModelAttribute는 생성한 객체의 멤버 변수에 요청 파라미터의 값을 해당 객체의 Setter를 호출해서 바인딩 해준다. -> 이 때, 요청 파라미터의 이름과 객체의 멤버 변수의 이름이 일치해야 한다.
    // 그래서 바인딩 받을 객체에 Setter가 반드시 존재해야 한다.(Lombok의 @Data 사용)
    // @RequestParam와 @ModelAttribute 모두 애노테이션 생략이 가능하며, 바인딩 매개 변수의 타입이 원시타입인 경우 @RequestParam을, 그 외(객체 등)라면 @ModelAttribute이 적용된다.

    // @RequestParam 사용
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String requestParamV1(
            @RequestParam String username,
            @RequestParam int age
    ) {
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData);

        return "ok";
    }

    // @ModelAttribute 사용
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String requestParamV2(
            @ModelAttribute HelloData helloData
    ) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData);

        return "ok";
    }

    // @ModelAttribute 생략
    @ResponseBody
    @RequestMapping("/model-attribute-v3")
    public String requestParamV3(HelloData helloData) { // @ModelAttribute도 생략 가능하다.
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        log.info("helloData={}", helloData);

        return "ok";
    }
}
