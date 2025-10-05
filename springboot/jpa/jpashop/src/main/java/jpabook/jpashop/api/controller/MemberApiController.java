package jpabook.jpashop.api.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();

        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {

        Long memberId = memberService.update(id, request.getName());
        Member member = memberService.findOne(memberId);
        // update()에서 변경한 Member를 반환할 수도 있지만 유지보수 측면에서 update()는 Update 기능에만 충실하도록 하고(변경한 엔티티의 id 정도는 반환해도 괜찮다.)
        // 그리고 update() 메서드가 종료되면 트랜잭션도 함께 종료되어 해당 메서드에서 반환된 엔티티는 준영속성 엔티티이기 때문에 해당 엔티티는 사용에 주의해야 한다.(되도록 준영속 엔티티는 사용을 지양하는 것이 좋다.)
        // 그렇기 때문에 이후 Member 엔티티가 필요하다면 해당 엔티티의 식별자(id)를 사용해 다시 find()로 조회 후 영속성 엔티티를 반환한다.

        return new UpdateMemberResponse(member.getId(), member.getName());
    }

    @Data
    public static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor // Response DTO는 모든 필드에 대한 생성자 필드를 열어두었다.(Response DTO에서는 항상 API 스펙과 같은 값을 반환하는 용도로만 사용되기 때문에 이 방법도 괜찮다.)
    public static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    public static class CreateMemberRequest {
        @NotEmpty
        private String name;
    }

    @Data
    public static class CreateMemberResponse {
        private Long id;

        public CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
