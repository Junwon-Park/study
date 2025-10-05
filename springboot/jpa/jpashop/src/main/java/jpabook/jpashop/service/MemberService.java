package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
// public 메서드에는 모두 트랜잭션이 걸린다.
// 클래스 레벨에 @Transactional을 사용하면 해당 클래스의 모든 메서드에 트랜잭션이 걸리고 여기에 옵션을 추가하면 해당 옵션이 추가된 트랜잭션이 기본적으로 적용된다.
// 여기에서는 @Transactional(readOnly = true) 읽기(조회) 전용 트랜잭션이 클래스 기본 트랜잭션으로 되어 있지만 아래에는 회원가입(생성) 메서드도 존재한다.
// 이런 경우 해당 메서드에는 따로 readOnly 옵션이 적용되지 않은 트랜잭션 애노테이션을 달아서 해당 메서드는 읽기 전용이 아닌 트랜잭션으로 적용되도록 할 수 있다.
// @Transactional의 readOnly 옵션의 기본 설정 값은 false 이다.
// 만약 해당 서비스 대부분의 메서드가 생성, 수정, 삭제라면 클래스 레벨에 @Transactional 애노테이션만 적용하고 조회 메서드에만 따로 @Transactional(readOnly = true)을 달아주면 된다.
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    // 처음 객체 생성시 주입된 레포지토리를 변경할 일이 없기 때문에 final로 선언한다.

    // @Autowired
    // @Autowired private MemberRepository memberRepository 이렇게 한 줄로 필드 주입을 할 수 있지만
    // 아래 처럼 생성자 주입(Setter 주입)을 하는 경우 코드는 조금 길어질 수 있지만 테스트 할 때 Mock을 주입할 수 있는 등의 장점이 존재한다.
    // 필드 주입을 사용하면 테스트 시 Mock을 주입할 방법이 없다.
    //    public void setMemberRepository(MemberRepository memberRepository) {
    //        this.memberRepository = memberRepository;
    //    }
    // 하지만 Setter 주입은 런타임 중에 메서드를 호출할 수 있기 때문에 문제가 될 수 있어 위험하다.
    // 그래서 처음 객체가 생성된 이후 변경할 수 없는 생성자 주입을 사용할 것을 권장한다.
    // 이렇게 생성자로 주입을 하면 객체가 처음 생성된 이후 추가로 생성할 수 없다.
    //    public MemberService(MemberRepository memberRepository) {
    //        this.memberRepository = memberRepository;
    //    }
    // 하지만 여기에 Lombok의 기능으로 클래스 레벨에 @RequiredArgsConstructor 애노테이션을 적용하면 스프링이 알아서 final이 붙은 멤버에 생성자 주입을 해준다.
    // 그러면 클래스 내부에 @Autowired와 생성자를 직접 작성하지 않아도 스프링이 알아서 동일하게 처리해준다.
    // 이 방법은 Repository에서 EntityManager를 주입받는 부분에 동일하게 적용할 수 있다.


    /**
     * == 회원 기능 ==
     * 회원 가입
     * 회원 전체 조회
     */

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateByMemberName(member);

        return memberRepository.save(member);
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회
     */
    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    private void validateDuplicateByMemberName(Member member) {
        // EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());

        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public Long update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);

        return member.getId();
    }
}
