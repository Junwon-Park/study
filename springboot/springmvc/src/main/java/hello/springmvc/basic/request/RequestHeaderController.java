package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@RestController
public class RequestHeaderController {

    @RequestMapping("/headers")
    public String headers(
            // Spring은 기본적으로 DispatcherServlet을 Front controller로 사용하기 때문에 Servlet의 HttpServletRequest, HttpServletResponse를 인자로 받아 직접 다룰 수 있다. -> 스프링의 요청, 응답 객체는 모두 각각 이 둘을 사용한다.
            HttpServletRequest request,
            HttpServletResponse response,
            HttpMethod httpMethod, // 요청의 메서드 정보
            Locale locale, // 가장 우선 순위가 높은 Locale 정보
            @RequestHeader MultiValueMap<String, String> headerMap, // Request 객체의 모든 혜더를 Map 형태로 받는 것이다.
            // 그리고 MultiValueMap은 키의 값이 중복될 수 있는 Map이다.(원래의 Map은 키 중복 불가)
            // 그래서 MultiValueMap의 반환 값의 타입은 배열이며, 중복되는 키의 이름이 있을 때, 해당 값을 배열로 모두 반환한다.
            // MultiValueMap<String, String> map = new LinkedMultiValueMap();
            // map.add("keyA", "value1");
            // map.add("keyA", "value2");
            // List<String> values = map.get("keyA"); -> ["value1", "value2"]
            // 헤더에 중복된 키 이름이 올 수 있기 때문에 이를 처리하기 위해 MultiValueMap을 사용한다.
            @RequestHeader("host") String host, // Request 객체의 host라는 헤더만 가져온 것이다.
            @CookieValue(value = "myCookie", required = false) String cookie // 쿠키의 정보를 받을 수 있는 애노테이션이고 required 옵션은 필수로 받을 지 말지 설정하는 것이다. -> true로 설정하면 필수로 받겠다고 설정하는 것이다.
    ) {

        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("host={}", host);
        log.info("cookie={}", cookie);

        return "ok";
    }
}
