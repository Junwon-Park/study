package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller // 스프링 부트가 실행될 때 스프링 컨테이너에 Controller로 MemberController가 스프링 빈으로 등록된다.
// 등록되어 있지 않으면 의존성 주입 등을 할 때, 스프링 부트가 해당 클래스들을 각각 Controller, Service, Repository 등으로 인식할 수 없다.
// Controller, Service, Repository 각각 @Controller, @Service, @Repository 어노테이션이 모두 존재하며 모두 달아줘야 스프링 부트가 스프링 컨테이너에 등록해서 인식할 수 있게 된다.
public class MemberController {
    private final MemberService memberService; // 아래 생성자에서 의존성 주입된 MemberService가 할당된다.

    @Autowired // 스프링 부트 실행 시, 스프링 컨테이너에 등록된 MemberService라는 스프링 빈과 연결해주는 어노테이션이다.
    // 즉, 스프링 부트는 컨테이너에 등록된 스프링 빈 중 @Autowired 어노테이션이 달려 있는 생성자의 파라미터에 대입된 빈을 연결해준다.
    // 이제 MemberController에서 MemberService를 사용할 수 있다. 이 것을 의존성 주입이라고 한다.
    // MemberService가 다른 것으로 교체되거나 내용이 변경되면 MemberController의 내용도 영향을 받기 때문에 MemberController는 MemberService를 의존하게 된다.
    // 의존성을 주입하는 MemberController와 여기에 의존성 주입되는 MemberService 모두 각각의 어노테이션을 달아 스프링 빈에 등록되어 있어야 @Autowired를 통해 의존성 주입이 가능하다.
    public MemberController(MemberService memberService) { // MemberController의 생성자를 통해 MemberService를 의존성 주입한 것이다.
        // 마찬가지로 MemberController와 MemberService가 모두 해당 어노테이션을 달아 스프링 컨테이너에 등록되어 있어야 @Autowired를 달아 의존성 주입할 떄 스프링 부트가 인식하도록 할 수 있다.
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) { // 만들어둔 Member라는 타입으로 데이터를 받는다.
        Member member = new Member();
        member.setName(form.getName());

        System.out.println("member = " + member.getName());

        memberService.join(member); // 위에서 생성한 member 인스턴스 가입 처리

        // 모든 처리 이후 홈화면으로 리디렉션
        return "redirect:/"; // 콜론(:) 뒤에 지정된 경로로 리디렉션된다.
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members); // View에 던져줄 데이터를 Model에 members라는 이름(키)으로 담는다.

        return "members/memberList"; // Model을 members/memberList 템플릿 파일로 전달하면서 해당 화면으로 전환한다.
    }

    /**
     * 스프링 컨테이너에 빈을 등록하는 방법에는 두 가지가 있는데,
     * 하나, 컴포넌트 스캔 방식을 통한 자동 등록과 의존 관계 설정
     * 둘, 자바 코드로 직접 스프링 컨테이너에 빈 등록
     * 어노테이션을 달아 자동으로 등록하는 방식을 컴포넌트 스캔 방식이라고 한다.
     * @Controller, @Service, @Repository 어노테이션은 내부에 @Component라는 어노테이션을 포함하고 있기 때문에 컴포넌트 스캔 방식으로 스프링 부트가 자동 인식 및 등록할 수 있다.
     * 위 세 어노테이션 내부에 들어가보면 어노테이션 클래스에 다른 어노테이션들이 달려 있는데 그 중 하나가 @Component 어노테이션이기 때문에 컴포넌트 스캔 방식으로 인식할 수 있다.
     * 단, 스프링 부트가 컴포넌트 스캔 방식으로 스캔하는 범위는 스프링 부트 실행 클래스(@SpringBootApplication 어노테이션이 달려 있는 클래스)를 포함한 그 하위 클래스들에 대해서만 스캔한다.
     * 즉, 스프링 부트 실행 클래스(@SpringBootApplication 어노테이션이 달려 있는 클래스)가 있는 패키지(hello.hellospring 패키지) 내부에 있는 모든 패키지와 클래스들이 대상이다.
     * @SpringBootApplication 어노테이션을 까보면 해당 어노테이션 클래스에 @ComponentScan이라는 어노테이션이 달려있다.
     *
     * 참고로 스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 싱글톤 패턴으로 등록한다.
     * 싱글톤 패턴은 프로젝트 내에서 유일하게 하나만 생성해서 공유하는 방식을 의미한다.
     * 따라서 스프링에서 하나의 빈은 어디에서 사용하더라도 모두 동일한 인스턴스이다.(그렇기 때문에 단 하나만 생성된 빈을 다른 빈에서 의존성 주입하여 사용한다.)
     * 설정을 통해 싱글톤 패턴이 아니게 사용할 수는 있지만 아주 특별한 경우를 제외하면 모두 싱글톤 패턴을 그대로 사용한다.
     *
     * 스프링의 의존성 주입(Dependency Injection, DI)에는 세 가지 방법이 존재한다.
     * 하나, 필드 의존성 주입
     * 둘, Setter 의존성 주입
     * 셋, 생성자 의존성 주입
     * "필드 의존성 주입"은 @Autowired를 주입하려는 필드에 직접 다는 방법이다. 이 경우 한 번 주입된 의존성을 필요 시에도 절대 변경할 수 없게 된다.
     * "Setter 의존성 주입"은 생성자가 아닌 Setter를 생성해서 @Autowired를 달아 인자로 의존성을 주입받는 방식이다. 이 경우 해당 Setter가 public으로 열려있기 때문에 어디에서든 접근 가능해서 위험하다.
     * "생성자 의존성 주입"은 현재 권장사항이며 그냥 생성자 의존성 주입을 사용하면 된다. 생성자의 경우 생성 시 한 번만 실행되고 이후 다시는 실행되지 않기 때문에 다른 Setter에 비해 안전하다.
     */
}
