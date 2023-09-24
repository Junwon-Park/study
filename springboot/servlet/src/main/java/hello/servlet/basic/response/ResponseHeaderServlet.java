package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        content(resp); // response 객체 헤더 Content 부분 세팅 메서드
        cookie(resp); // response 객체 헤더 Cookie 세팅 메서드
        redirect(resp); // Redirection

        PrintWriter writer = resp.getWriter(); // 브라우저에 텍스트를 출력해주기 위해 response 객체의 getWriter로 PrintWriter 객체를 가져온다.
        writer.println("OK"); // 출력할 데이터 입력
    }

    private static void redirect(HttpServletResponse resp) throws IOException {
        // resp.setStatus(HttpServletResponse.SC_FOUND); // 302 Redirect
        // resp.setHeader("Location", "basic/hello-form.html");
        resp.sendRedirect("basic/hello-form.html"); // 위 두줄 이 한줄로 대체 가능
        // 상태 코드는 자동으로 302 Redirct로 세팅해주고 지정한 경로로 새로고침 된다.
    }

    private static void cookie(HttpServletResponse resp) {
        // resp.setHeader("Set-Cookie", "myCookie=good;Max-Age=600"); // 이렇게 헤더에 직접 생성하거나 아래 처럼 쿠키 객체 생성해서 response 객체에 추가하는 방법도 있다.
        Cookie cookie = new Cookie("myCookie", "good"); // cookie 인스턴스 생성
        cookie.setMaxAge(600); // 생성한 쿠키의 유효 시간 (600 초)
        resp.addCookie(cookie); // response 객체의 Cookie에 추가
    }

    private static void content(HttpServletResponse resp) {
        // [status-line]
        resp.setStatus(HttpServletResponse.SC_OK); // HttpServletResponse 인터페이스에 상태 코드가 상수로 정의되어 있다. SC_OK는 200 OK를 의미한다.

        // [response-headers]
        // resp.setHeader("Content-Type", "text/plain;charset=utf-8"); // 아래 처럼 각각의 헤더 메서드를 사용할 수도 있다.
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("my-header", "hello"); // 임의의 헤더(http 기본 헤더 아님)
        resp.setContentLength(2); // 지정하면 지정한 값으로 세팅되고 생략하면 WAS(Tomcat)가 자동으로 생성해서 내려준다.
    }
}
