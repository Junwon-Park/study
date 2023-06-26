package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);

        return member;
    }

    @Override
    public Optional<Member> findbyId(Long id) {
        return Optional.ofNullable(store.get(id)); // store.get(id)가 null인 경우를 대비해서 Opational로 감싸서 처리
        // 메서드의 반환 값도 Optional<Member>
    }

    @Override
    public Optional<Member> findbyName(String name) {
        return store.values() // store의 모든 속성(값)을 Collection framework 형태로 반환
                .stream() // 위에서 반환한 Collection framework에 대한 Stream 생성
                .filter(member -> member.getName().equals(name)) // filter로 member.getName()이 name인 것만 찾아서 배열로 반환
                .findAny(); // 찾은 값을 Opational<>로 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // 객체를 요소로 가진 배열(ArrayList) 반환
    }
}
