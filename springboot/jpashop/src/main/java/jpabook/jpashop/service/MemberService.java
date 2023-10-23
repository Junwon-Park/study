package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // DB의 데이터를 변경하는 로직이 있는 경우 반드시 @Transactional 애노테이션을 달아주어야 한다.
// @Transactional 애노테이션은 spring.transaction.annotation과 jakarta.transaction 두 가지가 있는데, spring.transaction.annotation이 기능이 더 많기 때문에 이 것을 사용하는 것이 좋다.
// readOnly를 true로 설정하면 이 엔티티의 모든 메서드에 readOnly 옵션이 적용되어 읽기만 가능한 기능이 된다.
// readOnly 옵션을 true로 하면 내부적으로 조회만 가능하다는 것을 지정한 것이기 때문에 데이터를 변경할 때 영속성 컨텍스트에서 더티 체크를 수행하지 않고 DB에 따라 추가적인 조회에 한해 성능에 이점이 있게 된다.
// @Transactional의 readOnly 기본 값은 false이다. 그래서 회원 조회와 같은 조회 메서드에는 이 옵션을 true로 하고 데이터를 변경하는 메서드는 false로 해주면 된다.
@RequiredArgsConstructor // Lombok 라이브러리에서 제공하는 애노테이션이다.
// final로 선언된 DI 변수에 자동으로 생성자 주입을 해주는 애노테이션이다.
// !!! 반드시 final로 선언된 DI 상수에만 주입해준다.
// 그러면 해당 DI 상수에 값을 넣어줄 @AutoWired 생성자가 필요 없어지게 된다.
public class MemberService {
    private final MemberRepository memberRepository; // @RequiredArgsConstructor를 사용하려면 final 상수여야 하기도 하지만 이걸 사용하지 않고 생성자 주입 시
    // final을 안쓰면 컴파일 단계에서 에러를 띄우기 때문에 강제로 사용해야 하기 때문에 안전하다.
    // DI 같은 경우 최초 생성 당시에만 할당(주입)이 일어나고 이후에는 변하면 안되기 때문에 final을 사용하는 것이 맞다.
    // 결론은 DI 멤버는 모두 final을 사용해서 상수로 선언하는 것이 좋다.

    // ! Lombok의 @RequiredArgsConstructor를 사용했기 때문에 아래 코드를 작성하지 않아도 자동으로 위 DI 변수에 해당 빈을 주입해준다.
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 회원가입
    @Transactional // 클래스 레벨에서 @Transactional의 readOnly를 true로 주었기 때문에 모든 메서드에 readOnly = true로 동작하게 되는데,
    // 회원가입 메서드는 데이터를 추가하는 메서드이기 때문에 readOnly를 true로 하면 조회만 가능하게 되기 때문에 데이터 저장이 수행되지 않는다.
    // 그래서 이 경우 readOnly 옵션을 false로 줘야 하는데, 클래스 레벨에서 준 옵션과 다른 옵션을 주고싶을 때는 해당 메서드에만 추가로 @Transactional 애노테이션을 달아주면 되고,
    // 기본 값이 readOnly = false이기 때문에 여기에서는 기본 값으로 설정되도록 하면 된다.
    // 당연히 반대로도 가능하기 때문에 클래스 레벨에서 @Transactional(readOnly 기본 값 false)로 했다면 모든 메서드에 readOnly 옵션 값이 false로 지정된 것이기 때문에 추가로 애노테이션을 붙이지 않아도 되고,
    // 이 때 단순 조회 메서드에서는 @Transactional(readOnly = true)를 각각 달아줘야 한다.
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원이 있는지 검증

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findAll();
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
