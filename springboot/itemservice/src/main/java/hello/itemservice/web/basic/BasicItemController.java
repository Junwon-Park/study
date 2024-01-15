package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor // (Lombok) final이 붙은 멤버 변수에 빈을 주입해준다.
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

    @PostMapping("/v1/add")
    public String saveV1(@RequestParam("itemName") String itemName,
                         @RequestParam("price") int price,
                         @RequestParam("quantity") Integer quantity,
                         Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    @PostMapping("/v2/add")
    public String saveV2(@ModelAttribute("item") Item item) { // @ModelAttribute()은 Model을 자동으로 생성해주고 이름을 지정하면 해당 이름으로 뷰에 값을 넘겨준다.
        // 그래서 더이상 Model model을 인자로 받아 model.addAttribute()로 모델에 데이터를 담는 코드를 작성하지 않아도 된다.
        itemRepository.save(item);

        return "basic/item";
    }

    @PostMapping("/v3/add")
    public String saveV3(@ModelAttribute Item item) { // @ModelAttribute()은 Model을 자동으로 생성해주고 이름을 지정하면 해당 이름으로 뷰에 값을 넘겨준다.
        // @ModelAttribute()에 이름을 지정하지 않아도 동일하게 동작하는데, 이름을 지정하지 않으면 @ModelAttribute로 받은 클래스 이름의 첫 글자만 소문자로 바꾼 이름으로 동일하게 값을 넘겨준다. (여기에서는 Item -> item)
        itemRepository.save(item);

        return "basic/item";
    }

    @PostMapping("/v4/add")
    public String saveV4(Item item) { // @ModelAttribute()은 Model을 자동으로 생성해주고 이름을 지정하면 해당 이름으로 뷰에 값을 넘겨준다.
        // 파라미터 값의 타입이 원시값인 경우 @RequestParam()이 자동으로 적용되고 클래스인 경우 @ModelAttribute()가 자동으로 적용된다.
        itemRepository.save(item);

        return "basic/item";
    }

    @PostMapping("/v5/add")
    public String saveV5(Item item) {
        itemRepository.save(item);

        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String saveV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);

        // RedirectAttributes는 리다이렉트 할 때, 넘겨줄 값 등을 념겨주는 것이다.
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}"; // {itemId}는 RedirectAttributes에 저장한 itemId가 들어가고 나머지 값들은 ?status=true 처럼 쿼리 파라미터로 붙여서 해당 URL로 리다이렉션 시킨다.
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);

        model.addAttribute("item", item);

        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable("itemId") Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 처음 스프링 실행에 의한 스프링 빈 생성자 호출 시, 테스트용 데이터 추가
     */
    @PostConstruct // 스프링 실행될 때, 해당 스프링 빈 호출 시 실행될 함수 애노테이션
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
