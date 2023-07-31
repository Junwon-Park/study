package hello.core.member;

// MemberServiceImpl 클래스는 클라이언트가 실제 바라보고 있고 Repository를 의존하는 서비스 객체이고 MemberService 인터페이스를 구현하고 있다.
public class MemberServiceImpl implements MemberService {
    // MemberServiceImpl에서 MemberRepository 인터페이스와 MemoryMemberRepository 구현체를 모두 의존하고 있다.
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
