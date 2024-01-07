package hello.springmvc.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException { // response.getWriter()을 사용하면 예외 처리를 해줘야 한다.
        ServletInputStream inputStream = request.getInputStream(); // 네트워크 통신은 바이트 코드 상태로 통신하기 때문에 요청이 들어오면 바이트 코드 형태이다.
        // 해당 바이트 코드를 스트림을 사용해 받아야 하며, HttpServletRequest의 getInputStream() 메서드를 사용해서 바이트 코드를 스트림으로 받을 수 있다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // Stream 관련 유틸들을 제공하는 StreamUtils의 copyToString() 메서드를 사용해서 Stream을 String 형태로 변환한다.
        // copyToString() 메서드는 어떤 문자 형식으로 받을 지 두 번째 인자에 넣어줘야 한다.(StandardCharsets.UTF_8로 UTF-8 형식으로 변환

        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok"); // ok 라는 String 값을 응답의 Body에 담아 응답
    }

    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        // HttpServletRequest과 HttpServletResponse을 사용하지 않고 InputStream과 Writer를 직접 파라미터로 받을 수 있다.
        // 이러면 InputStream에 바로 스트림 형태로 받을 수 있고, Writer 객체를 바로 받을 수 있다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);

        responseWriter.write("ok");
    }

    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) { // 이 방식은 HttpEntity<>를 반환하기 떄문에 IOException 처리를 하지 않아도 된다.
        // httpEntity 형태로 RequestBody와 RequestHeader를 받아 사용할 수 있다.
        String messageBody = httpEntity.getBody();
        HttpHeaders headers = httpEntity.getHeaders();

        log.info("messageBody={}", messageBody);
        log.info("headers={}", headers);

        return new HttpEntity<>("ok");
    }

    @ResponseBody // 반환하는 String 값을 응답 객체의 Body에 담아 응답해준다.
    // 그렇기 때문에 이 애노테이션을 달면 ViewResolver에 반환하는 String을 전달하지 않는다.
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) { // @RequestBody 애노테이션은 Request 객체의 Body를 파라미터에 지정한 타입으로 변환해서 바인딩 해준다.
        log.info("messageBody={}", messageBody);

        return "ok";
    }
}
