package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository // MemberController에 정리한 내용과 동일하다.
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    /** Java 문법
     * Map은 자료형이며 Java에서는 이 자료형의 형태를 java.util 패키지에 Map이라는 인터페이스로 정의해놓았다.
     * 그리고 이 Map을 구현하는 구현체들이 존재하는데, HashMap, TreeMap, LinkedHashMap, HashTable 등이 있고 이들은 Map 인터페이스를 구현한 구현체 클래스들이면서 각각이 Map의 한 종류이다.
     * 그렇기 Map 타입의 참조 변수 store에는 Map을 구현하는 구현체 중 하나이고 Map의 한 종류인 HashMap 인스턴스를 참조할 수 있는 것이다.
     * 물론 Map 타입의 변수에는 Map을 구현하는 구현체 클래스의 인스턴스는 모두 참조 가능하다.
     * Map<Long, Member>에서 제네릭 Long 부분의 타입은 Map의 Key에 지정되고 Memeber는 Map의 Value에 지정된다.
     */
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);

        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // store.get(id)가 null인 경우를 대비해서 Opational로 감싸서 처리
        // 메서드의 반환 값도 Optional<Member>
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values() // store의 모든 속성(값)을 Collection framework 형태로 반환
                .stream() // 위에서 반환한 Collection framework에 대한 Stream 생성
                .filter(member -> member.getName().equals(name)) // filter로 member.getName()이 name인 것만 찾아서 배열로 반환
                .findAny(); // 찾은 값을 Opational<>로 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // 객체를 요소로 가진 배열(ArrayList) 반환
    }

    public void clearStore() {
        store.clear();
    }
}
