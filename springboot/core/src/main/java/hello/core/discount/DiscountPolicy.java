package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {
    /**
     * @param member 할인 대상 멤버 객체
     * @param price 가격
     * @return 할인 금액 반환
     */
    int discount(Member member, int price);
}
