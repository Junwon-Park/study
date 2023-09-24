package hello.servlet.basic.request;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("--- Request line start ---");

        printStartLine(req);
        printHeaders(req);
        printHeaderUtils(req);
        printEtc(req);
    }

    private static void printEtc(HttpServletRequest req) {
        System.out.println("--- 기타 조회 start ---");

        System.out.println("[Remote 정보]");
        System.out.println("req.getRemoteHost = " + req.getRemoteHost());
        System.out.println("req.getRemoteAddr = " + req.getRemoteAddr());
        System.out.println("req.getRemotePort = " + req.getRemotePort());
        System.out.println();

        System.out.println("[Local 정보]");
        System.out.println("req.getLocalName = " + req.getLocalName());
        System.out.println("req.getLocalAddr = " + req.getLocalAddr());
        System.out.println("req.getLocalPort = " + req.getLocalPort());
        System.out.println();

        System.out.println("--- 기타 조회 end ---");
        System.out.println();
    }

    private static void printHeaderUtils(HttpServletRequest req) {
        System.out.println("--- Header 편의 조회 start ---");
        System.out.println("[Host 편의 조회]");
        System.out.println("req.getServerName = " + req.getServerName());
        System.out.println("req.getServerPort = " + req.getServerPort());
        System.out.println();

        System.out.println("[Accept-Language 편의 조회]");
        req.getLocales().asIterator()
                .forEachRemaining(locale -> System.out.println("locale = " + locale));
        System.out.println("req.getLocale = " + req.getLocale());
        System.out.println();

        System.out.println("[cookie 편의 조회]");
        if (req.getCookies() != null) {
            for (Cookie cookie : req.getCookies()) {
                System.out.println(cookie.getName() + ": " + cookie.getValue());
            }
        }
        System.out.println();

        System.out.println("[Content 편의 조회]");
        System.out.println("req.getContentType = " + req.getContentType());
        System.out.println("req.getContentLength = " + req.getContentLength());
        System.out.println("req.getCharacterEncoding = " + req.getCharacterEncoding());

        System.out.println("--- Header 편의 조회 end ---");
        System.out.println();
    }

    private static void printHeaders(HttpServletRequest req) {
        System.out.println("--- Headers start ---");

        req.getHeaderNames().asIterator()
                // asIterator()를 사용하면 req.getHeaderNames들을 Iterator로써 사용한다는 것이고 그 뒤에 forEachRemaining()을 사용하여 순회할 수 있다.
                .forEachRemaining(headerName -> System.out.println(headerName + ": " + headerName));

        System.out.println("--- Headers end ---");
        System.out.println();
    }

    private static void printStartLine(HttpServletRequest req) {
        String method = req.getMethod();
        System.out.println("req.getMethod = " + method);
        String protocol = req.getProtocol();
        System.out.println("req.getProtocol = " + protocol);
        String scheme = req.getScheme();
        System.out.println("req.getScheme = " + scheme);
        StringBuffer requestURL = req.getRequestURL();
        System.out.println("req.getRequestURL = " + requestURL);
        String requestURI = req.getRequestURI();
        System.out.println("req.getRequestURI = " + requestURI);
        String queryString = req.getQueryString();
        System.out.println("req.getQueryString = " + queryString);
        boolean secure = req.isSecure();
        System.out.println("req.isSecure = " + secure);

        System.out.println("--- Request line end ---");
        System.out.println();
    }
}
