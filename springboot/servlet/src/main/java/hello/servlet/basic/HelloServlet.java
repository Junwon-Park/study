package hello.servlet.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
// @WebServlet 애노테이션을 붙여 놓으면 서블릿 컨테이너가 서블릿 컴포넌트 스캔할 때, 애노테이션에 설정된 옵션을 적용해서 해당 클래스를 서블릿으로 등록한다.
// name 옵션은 이 서블릿의 이름을(아무 이름 지정 가능), urlPatterns 옵션은 이 서블릿의 Path를 설정하는 옵션(/hello 경로로 요청하면 이 서블릿 실행)이다.
// name 옵션과 urlPatterns 옵션의 값은 각각 고유해야 한다.
public class HelloServlet extends HttpServlet { // 서블릿은 HttpServlet 클래스를 상속받아야 한다.

    // @WebServlet의 urlPatterns에 매핑된 Path로(여기에서는 /hello) Http 요청이 들어오면 서블릿 컨테이너는 해당 서블릿의 service 메서드를 자동으로 호출한다.
    // 인텔리제이에서 service 메서드를 간편하게 정의하는 방법은 ctrl + o에서 자물쇠 모양(protected 접근제어자)의 service를 불러오면 된다.
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        System.out.println("req = " + req);
        System.out.println("resp = " + resp);

        String username = req.getParameter("username");
        System.out.println("username = " + username);

        resp.setContentType("text/plain");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().write("hello " + username);
    }
}
