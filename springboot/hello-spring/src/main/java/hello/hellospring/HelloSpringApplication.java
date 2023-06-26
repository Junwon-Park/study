package hello.hellospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 메인 클래스에 이 어노테이션을 달아야 Spring boot가 이 클래스를 Spring boot 메인 클래스로 인식할 수 있다.
public class HelloSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloSpringApplication.class, args);
	}
	// 그리고 메인 메서드에서 SpringApplication의 run() 메서드를 호출해서 Spring boot 애플리케이션을 실행한다.
	// run() 메서드의 인자로는 메인 클래스와 메인 메서드의 매개변수 agrs를 대입해서 호출한다.

}

/**
 * Spring boot는 Gradle, Tomcat 서버 등을 내장하고 실행 시 자동으로 이들을 함께 실행한다.
 * Gradle은 Spring boot 사용자가 설치한 애플리케이션에 필요한 라이브러리들을 찾아서 설치(Spring initializr에서 추가한 라이브러리도 여기에서 설치한다.)하고 그 라이브러리를 사용하기 위해 필요한 의존성 라이브러리들을 자동으로 찾아 설치한다.(라이브러리, 의존성 관리 툴이다.)
 * 그래서 Spring initializr에서 추가한 라이브러리 외에 해당 라이브러리들의 의존성 라이브러리들도 함께 설치되어 더 많은 라이브러리들이 자동으로 설치된 것이다.
 * Tomcat은 웹서버(WAS: Web Application Server)이며 Spring boot는 Tomcat 서버를 내장하고 있으며 실행 시 이를 함께 실행시킨다.
 * 그렇기 떄문에 Spring boot를 실행하면 Tomcat이 함께 실행되어 바로 기본 Port 혹은 지정한 Port로 서버가 열리며 URL을 브라우저에 입력해서 접근할 수 있게 된다.
 * Tomcat 서버는 요청이 오면 해당 요청을 Spring boot 애플리케이션에 전달한다.
 * -------------------------------------------------------------
 * *** IDE를 사용하지 않고 Spring boot 서버를 실행시키는 방법 ***
 * 먼저 애플리케이션을 빌드해야 하는데, 터미널의 프로젝트 디렉토리 위치에서 ./gradlew build 명령어를 입력하면 이 프로젝트가 빌드 되고 빌드가 되면 프로젝트 디렉토리에 build 디렉토리가 생성(이미 있다면 내용이 갱신된다.)된다.
 * 프로젝트에 변경된 내용이 있다면 새로 빌드를 해야 build 디렉토리에 내용이 반영되며 실행 파일에 적용된다.
 * 그리고 빌드된 애플리케이션을 실행하는 방법은 터미널에서 /build/libs경로로 가서 해당 애플리케이션.jar(여기에서는 해당 경로에 가보면 hello-spring-0.0.1-SNAPSHOT.jar) 파일을 실행시키면 된다.
 * .jar 파일을 실행 시키는 명령어는 java -jar 실행 파일.jar(여기에서는 hello-spring-0.0.1-SNAPSHOT.jar) 이다.
 * 만약 서버를 배포할 때, EC2에서 위와 같은 순서로 .yaml 파일을 작성하거나 명령을 입력해서 애플리케이션을 빌드하고 실행시킬 수 있다.
 */