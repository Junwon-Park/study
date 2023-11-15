package jpabook.jpashop.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController // 이 애노테이션 내부에 @Cotroller + @ResponseBody 애노테이션이 있다.
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        // 파라미터에 @RequestBody 애노테이션을 붙이면 요청 객체의 Body(Payload)가 파라미터의 인자에 바인딩 되어 들어온다.
        // @Valid 애노테이션을 붙이면 해당 객체의 각 필드에 달아놓은 Validator들에 대한 검증을 수행한다.

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        // 파라미터에 @RequestBody 애노테이션을 붙이면 요청 객체의 Body(Payload)가 파라미터의 인자에 바인딩 되어 들어온다.
        // @Valid 애노테이션을 붙이면 해당 객체의 각 필드에 달아놓은 Validator들에 대한 검증을 수행한다.
        // 여기에서 중요한 것은 위 v1에서는 Member 엔티티 자체를 Request 객체를 사용했지만 이럴 경우 엔티티가 변경되었을 때 API 스펙 자체가 바뀌어 버리게 되기 때문에 절대 이렇게 사용하면 안된다.
        // 그리고 엔티티 자체를 노출하는 것도 문제이면서 엔티티 내부에 유효성 검사를 위한 Validator를 다는 것도 사이드 이펙트의 위험이 있기 때문에 더욱 지양해야 한다.
        // API 명세에서 Request 객체는 반드시 Request DTO를 생성해서 그 것을 사용하는 것이 좋다.
        // 이러면 엔티티와 Request 객체가 분리되어 서로 연관성이 없어지기 때문에 엔티티 변경이 있어도 API 스펙에는 영향이 없다.
        // 절대 엔티티 자체를 외부에 스펙으로써 노출해서는 안된다.

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request
    ) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class UpdateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    static class UpdateMemberResponse {
        private Long id;
        private String name;

        public UpdateMemberResponse(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
