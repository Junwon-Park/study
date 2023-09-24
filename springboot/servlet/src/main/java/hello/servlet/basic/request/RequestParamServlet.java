package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * 1. 파라마터 전송 기능
 * http://localhost:8080/request-param?username=hello&age=20
 * http://localhost:8080/request-param?username=hello&age=20&username=hello2(중복 파라미터)
 */
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("[전체 파라미터 조회] - start");

        req.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName + "= " + req.getParameter(paramName)));

        System.out.println("[전체 파라마터 조회] - end");

        System.out.println("[단일 파라미터 조회]");
        String username = req.getParameter("username"); // req.getParameter는 파라미터의 중복되는 키가 없는 경우에만 사용할 수 있다.
        // 하지만 파라미터의 키가 중복되는 경우는 없도록 하는 것이 좋다.
        String age = req.getParameter("age");
        System.out.println("username = " + username);
        System.out.println("age = " + age);
        System.out.println();

        System.out.println("[이름이 같은 복수 파라미터 조회]");
        String[] usernames = req.getParameterValues("username"); // req.getParameterValues로 쿼리 파라미터의 키 값이 중복되는 경우 값을 배여롤 가져올 수 있다.
        for (String name : usernames) { // iter를 입력하고 나오는 추천 키워드를 선택하면 인텔리제이가 자동으로 forEach문 생성해준다.
            System.out.println("username = " + name);
        }

        resp.getWriter().write("OK");
    }
}
