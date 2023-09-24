package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletInputStream inputStream = req.getInputStream(); // req의 getInputStream()으로 Body의 데이터를 가져올 수 있다.
        // 타입은 Servlet의 ServletInputStream 타입으로 받아야 한다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // 스프링에 StreamUtils라는 유틸을 제공한다.
        // StreamUtils의 copyToString() 메서드를 사용해서 첫 번째 인자로 위에서 가져온 값을 넣고, 두 번째 인자로 인코딩 정보를 넣어주면 된다.

        System.out.println("messageBody = " + messageBody);

        resp.getWriter().write("OK");
    }
}

