package jpabook.jpashop.controller;

import jakarta.validation.Valid;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        // 현재 MemberForm의 name에 @NotEmpty() Validator가 달려 있다. 이 유효성 검사를 발동시키려면 파라미터 앞에 @Valid 애노테이션을 달아줘야 한다.
        // 그러면 인자로 넘어온 MemberForm의 name의 값이 비어있다면 Error가 발생한다.
        // 그런데 Error가 발생하면 프로그램이 죽기 때문에 죽지 않도록 처리해줘야 한다.
        // 이 때 사용할 수 있는 것이 Java에서 제공하는 BindingResult 인터페이스인데, @valid 애노테이션이 달린 객체에서 유효성 검사에 의한 Error가 발생하면, 프로그램이 죽지 않고
        // 해당 Error가 BindingResult에 담기고 그 다음 코드들이 실행된다.(여기에서는 create() 메서드 계속 실행)
        // 그리고 메서드 내부에서 BindingResult에서 제공하는 메서드로 발생한 에러들을 체크할 수 있다.

        // 만약 MemberForm에서 유효성 검사에 의한 에러가 발생한다면 여기에 걸려서 members/createMemberForm으로 이동하도록 처리했다.
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);

        return "redirect:/"; // "/" 경로로 리디렉션
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        // 여기에서는 Member 엔티티 자체를 화면으로 넘겨줬는데, 원래는 이렇게 하면 안된다.
        // 정확히는 API를 개발할 때는 절대 엔티티 자체를 넘기면 안된다.
        // 여기에서는 일단 Thymeleaf를 사용한 SSR이기 때문에 서버 내부의 View로 넘겨 렌더링 후 웹에 보여주기 때문에 예민한 정보가 유출될 가능성이 적고(서버 내에 View 파일이 존재하기 때문에 브라우저에 렌더링 된 파일을 주기 전, 서버에서 보여줄 것만 보여주도록 다룰 수 있기 때문에),
        // 엔티티에 필드가 추가되어도 비슷한 이유로 서버에서 화면에 보여줄 정보들을 쉽게 변경할 수 있다.
        // 하지만 API를 개발할 경우에는 데이터를 내려 줄 때, 엔티티 자체를 내려주는 경우 민감한 정보가 유출될 수 있다.
        // 그리고 만약 엔티티에 필드가 새로 추가되면 API 스펙 자체가 변하기 때문에 그 것을 사용하는 Client 입장에서는 불편하고 굉장히 불완전한 API가 된다.
        // 이런 이유 때문에 절대 API의 응답 값으로 엔티티 자체를 주면 안되고 DTO 등의 통신 객체를 사용해서 스펙에 맞게 내려줘야 한다.
        // 그리고 SSR이라도 DTO 또는 Form을 만들어 넘겨주는 것이 좋기는 하다.
        model.addAttribute("members", members);

        return "members/memberList";
    }

}
