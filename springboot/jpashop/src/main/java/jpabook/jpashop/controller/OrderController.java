package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {
        // Client에서 Params에 넘겨주는 데이터를 @RequestParam() 애노테이션으로 받아 API 함수의 파라미터의 인자로 바인딩 할 수 있다.

        orderService.order(memberId, itemId, count);
        // 여기에서 주문(order)을 할 때, 주문자(member)와 주문할 상품(item)을 여기(Controller)에서 조회해서 orderService의 order() 메서드에 넘겨서
        // 주문을 생성할 수도 있지만 여기에서 Member와 Item 엔티티를 조회해서 넘기게 되면, 영속 상태가 아닌 단순 객체를 넘기게 되는 것이므로 영속 상태일 때의 이점을 전혀 얻지 못하게 된다.
        // 정확히는 여기에서 조회해서 넘기게 되면 이미 해당 엔티티에 대한 Transaction이 끝났기 때문에 해당 엔티티에 대한 영속성이 종료된 상태로 JPA의 영속성 컨텍스트의 관리를 전혀 받지 못하는 상태로 넘어가게 되기 떄문에
        // 되도록이면 DB와 관련된 핵심 비즈니스 로직은 Controller에서 하지 않고 Service 또는 Repository 또는 Entity의 메서드에 필요한 값만 넘겨서 진행하는 것이 좋다.
        // 위 처럼 값만 넘겨서 order() 메서드 내에서 DB 관련 작업 및 핵심 비즈니스 로직을 진행하게 되면 일단, 해당 Transaction 내에서 진행되기 때문에 JPA의 영속성 컨텍스트의 관리를 받을 수 있고
        // 한 Transaction 내에서 이루어져야 유지 보수 및 테스트에도 이점을 얻을 수 있다.

        return "redirect:/orders";
    }
}
