package hello.springmvc.basic;

// Lombok 사용으로 더 이상 사용하지 않는다.
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// SLF4J는 로거 라이브러리이며 Spring boot가 공식 지원하는 로거 라이브러리이다.
// Spring boot로 프로젝트를 생성하면 의존성에 자동으로 설치되어 있어 바로 사용할 수 있다.(Gradle에 보면 spring boot starter 안에 SLF4J가 포함되어 있다.
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j // Lombok에서 제공하는 애노테이션이며 아래 LoggerFactory로 로거 객체를 생성하는 부분을 Lombok이 대신 해줘서 해당 코드를 생략할 수 있다.
// @Controller 애노테이션을 사용하면 스프링의 컴포넌트 스캔에 의해서 빈에 등록은 되지만 핸들러의 반환 값을 View의 이름으로 인식하고 View resolver가 지정한 이름의 View로 반환 값을 전달한다.
@RestController // REST API Controller -> 이 애노테이션을 사용해야 핸들러의 반환 값을 그 대로 클라이언트에 반환(응답)한다.
public class LogTestController {

//    private final Logger log = LoggerFactory.getLogger(getClass()); // SLF4J의 LoggerFactory를 사용해서 Logger 객체 생성 -> Lombok의 @Slf4j 애노테이션 사용으로 코드 생략 가능

    @RequestMapping // DispatcherServlet이 핸들러를 찾을 때 @RequestMapping 애노테이션이 달린 핸들러를 0순위로 조회한다.
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);

        // 아래는 로거에서 제공하는 로그의 레벨이며 error로 갈 수록 심각한 로그이다.
        // 기본적으로는 trace와 debug는 출력되지 않으며 info ~ error만 출력된다.
        // trace 레벨 부터 모두 출력하고 싶다면 "src/resources/application.properties 파일에 설정을 추가할 수 있다.
        // 보통 개발, 디버깅 환경에서는 trace와 debug를 사용하고, 실제 서비스에서는 info ~ error를 용도에 맞게 사용한다. (사실 개발 서버에는 어떤 레벨의 로그를 출력해도 상관은 없다. 하지만 운영 서버에 trace, debug 로그가 출력되는 것은 옳지 않다.)
        // {}을 사용해서 변수의 값으로 치환하도록 사용하는 것이 옳은 사용법(연산하지 않기 때문이다.)이며 만약 "trace log ="+name 과 같이 사용하면 문자열 더하기 연산을 수행해야하기 때문에 의미 없는 리소스가 사용되어 낭비된다.
        log.trace("trace log = {}, {}, {}", name, "두 번째 값", "세 번째 값"); // {}에는 , 다음 오는 변수로 치환되며 여러 개를 사용할 수도 있다.
        log.debug("debug log = {}", name);
        log.info("info log = {}", name);
        log.warn("warn log = {}", name);
        log.error("error log = {}", name);
        // System.out.println()가 아닌 SLF4J(로거 라이브러리)를 사용하는 이유는 콘솔에 보기 좋게 출력되는 것도 있지만 application.properties 파일에 설정을 통해 로그를 파일로 출력하거나 네트워크를 통해 외부에 전송할 수도 있다.
        // 성능도 System.out 보다 더 좋다.(내부 버퍼링, 멀티 쓰레드 등), 그렇기 때문에 실무에서는 반드시 로거를 사용해야 한다.(운영 서버 뿐만 아니라 개발, 테스트 시에도 마찬가지로 반드시 로거를 사용해야 한다.)

        return "OK";
    }
}
