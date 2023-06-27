package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    public long join(Member member) {
        // 이름이 중복되는 회원이 있으면 가입되면 안된다.
        validateDuplicateMember(member); // 중복 회원이 회원이 존재하는 지 체크해서 존재한다면 Exception을 뱉는 기능 로직 한 덩이를 따로 메서드로 빼서 모듈화했다.
        // 기능 모듈화 관련 리펙터 단축 키는 해당 기능 로직 하이라이트 상태에서 ctrl + t -> Extract Method 선택 또는 Option + command + m(Extract Method 숏컷)
        memberRepository.save(member);

        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m -> { // ifPresent() 메서드는 Optional<>로 감싸면 사용할 수 있는 Optional 메서드이다.
            // findByName 메서드의 반환 값이 Optional이기 때문에 ifPresent를 사용할 수 있다.
            // ifPresent는 결과 반환 값이 null이면 실행된다.
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });

        // 아래 코드를 위 처럼 간결하게 작성한 것이다.
        //        Optional<Member> result = memberRepository.findByName(member.getName());
        //        result.ifPresent(m -> {
        //            throw new IllegalStateException("이미 존재하는 회원입니다");
        //        });
    }

    /**
     * 전체 회원 조회
     */

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
