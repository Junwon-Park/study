package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // Given
        Member member = createMember();
        Book book = createBook();

        int orderCount = 2;

        // When
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //Then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류의 수가 정확해야 한다.");
        assertEquals(10000 * orderCount, getOrder.getTotalPrice() , "주문 가격은 가격 * 수량이다.");
        assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
    }

    @Test
    public void 상품주문_재고수량_초과() throws Exception {
        // Given
        Member member = createMember();
        Book book = createBook();

        // createBook() 에서 재고수량을 10개로 세팅
        // 주문 수량을 11개로 하면 재고 수량을 초과하기 때문에 Item의 removeStock() 메서드 실행 시 NotEnoughStockException이 발생해야 한다.
        int orderCount = 11;

        // When & Then
        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), book.getId(), orderCount), "예외가 발생해야 한다.");
    }

    @Test
     public void 주문취소() throws Exception {
         // Given -> When을 실행하기 위해 필요한 데이터 세팅
        Member member = createMember();
        Book book = createBook();

        int orderCount = 2;

        // 주문이 취소되었을 때를 검증하려고 하기 때문에 주문을 생성하는 것 까지가 Given이다.
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // When -> 주문을 취소 했을 때
        // When에서는 검증하려고 하는 주문 취소 로직 실행
        orderService.cancelOrder(orderId);

        //Then -> When의 로직을 실행했을 때, 검증하려고 하는 것(어떤 값이 나와야 하는 지, 어떤 예외가 발생해야 하는 지 등..)
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소 시 상태는 CANCEL이 되어야 한다.");
        assertEquals(10, book.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");
    }

      private Book createBook() {
          Book book = new Book();
          book.setName("시골 JPA");
          book.setPrice(10000);
          book.setStockQuantity(10);
          em.persist(book);
          return book;
      }

      private Member createMember() {
          Member member = new Member();
          member.setName("회원1");
          member.setAddress(new Address("서울", "강가", "123-123"));
          em.persist(member);
          return member;
      }

}