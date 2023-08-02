package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {
    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Override
    public Order createOrder(long memberId, String itemName, int itemPrice) {
        // 아래 discount() 함수의 인자로 member를 넘기기 위해서 조회한다.
        Member member = memberRepository.findById(memberId);
        // discount()의 인자로 member를 받는 이유는 member의 등급에 따라서 할인 금액이 다르기 때문인데,
        // 이 때, member를 넘기느냐 member.getGrade()로 등급을 넘기느냐는 선택하면 된다.
        int discountPrice = discountPolicy.discount(member, itemPrice); // 최종 할인된 금액 반환

        // 위에서 만든 데이터를 가지고 주문 객체를 반환한다.
        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
