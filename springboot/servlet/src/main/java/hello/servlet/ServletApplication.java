package hello.servlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan // 서블릿 자동 등록
// 이 애노테이션을 붙여놓으면 스프링 부트가 실행되면서 @WebServlet 애노테이션이 붙은 클래스를 컴포넌트 스캔 방식으로 조회해서 톰캣의 서블릿 컨테이너에 자동으로 등록한다.
// 스프링 부트가 실행되면 내부에 내장된 톰캣의 서블릿 컨테이너에 자동으로 동록된다.
@SpringBootApplication
public class ServletApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServletApplication.class, args);
	}

}
