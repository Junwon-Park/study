package hello.hellospring;

import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 이 어노테이션이 달려 있으면 스프링이 실행될 때 이 파일을 읽게 된다.
public class SpringConfig {
    /**
     * 스프링이 이 파일을 읽을 때 @Bean이라는 어노테이션이 달린 메서드를 호출해서 메서드가 반환하는 인스턴스를 컨테이너에 빈으로 등록해준다.
     * 이 방식이 스프링 컨테이너에 자바 코드로 스프링 빈을 등록하는 방법이다.
     * 하지만 자바 코드로 직접 스프링 빈을 등록하는 벙밥과 컴포넌트 스캔 방식으로 자동 등록하는 방법의 장당점이 있다.
     * 일반적인 정황화된 컨트롤러, 서비스, 레포지토리들은 그냥 해당 어노테이션을 달아서 컴포넌트 스캔 방식을 사용한다.
     * 그리고 정형화 되지 않거나 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해(자바 코드로 직접 @Configuration, @Bean을 달아서 등록) 스프링 빈으로 등록한다.
     * 상황에 따라 구현 클래스를 변경하는 경우의 예로는 현재 DB를 어떤 것을 사용할 지 결정되지 않아 메모리를 임시로 저장소로 사용하고 있다.(당연히 프로그램 껐다 켜면 데이터 날아감)
     * 그래서 MemberRepository 인터페이스를 설계하고 이를 구현한 MemoryMemberRepository 구현체를 MemberServie에 주입해서 사용하고 있다.
     * DB가 결정되면 MemoryMemberRepository를 실제 DB 레포지토리로 교체해야 한다. 이 때, 다른 코드를 건드리지 않고 쉽게 교체가 가능하다.
     * 그 방법이 바로 아래 방법과 같이 @Configuration, @Bean을 사용해서 자바 코드로 직접 스프링 빈을 등록하는 방법이다.
     * 그러면 아래 memberRepository() 메서드의 반환 인스턴스만 DB의 레포지토리 인스턴스로 수정해주면 다른 코드는 전혀 수정하지 않고 교체된다.
     * 즉, 애플리케이션에서 교체되지 않는 정형화된 것은 컴포넌트 스캔 방식을 사용해서 자동으로 빈으로 등록되도록 하고 정형화 된 것이 아닌 교체 가능하도록 설계된 것들은 직접 자바 코드로 등록하여 나중에 수정하기 편하도록 하는 것이다.
     */

    @Bean // 스프링 실행 시 이 메서드를 호출해서 반환하는 인스턴흐를 스프링 컨테이너에 빈으로 등록
    public MemberService memberService() {
    return new MemberService(memberRepository()); // MemberService의 생성자에서는 MemoryMemberRepository을 인자로 받기 때문에 넣어줘야 한다.
        // 이렇게 하면 MemberService에 MemoryMemberRepository를 의존성 주입한 것이다.
        // 물론, MemberService와 MemoryMemberRepository 모두 스프링 컨테이너에 스프링 빈으로 등록되어 있어야 한다.
    }

    @Bean // DB가 결정되면 반환하는 인스턴스만 실제 DB 레포지토리로 수정해주면 MemberService에 주입되는 의존성이 변경된 것이다.
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
