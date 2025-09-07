package hello.core;

import hello.core.member.*;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order); // Order 클래스에 toString()이 정의되어 있기 때문에 toString()을 호출하지 않아도 자동 실행된다.(스프링이 내부적으로 실행)
        System.out.println("order.calculateTotalPrice() = " + order.calculateTotalPrice());
    }
}
