package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		Hello hello = new Hello();
		hello.setData("Hello");
		String data = hello.getData();
		System.out.println("data = " + data); // 스프링 실행 시, 이 부분이 먼저 실행
		// 아래 스프링 실행되는 코드보다 위에 작성되었기 때문에 이 부분이 먼저 실행

		SpringApplication.run(JpashopApplication.class, args); // 이 부분이 스프링 서버 실행되는 부분
	}
}
