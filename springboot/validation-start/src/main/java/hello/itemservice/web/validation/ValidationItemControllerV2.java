package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

//    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        // 파라미터에서 BindingResult의 위치는 반드시 @ModelAttribute 다음에 와야 한다.
        // BindingResult는 @ModelAttribute 파라미터에 넘어온 객체(여기에서는 Item)의 결과를 바인딩하기 때문이다.

        /**
         * 중요!!
         * BindingResult를 파라미터로 받으면 @ModelAttribute에 들어오는 객체의 필드 타입에 맞지 않는 값이 들어온 경우 해당 타입 에러를 BindingResult에 담아서 들어오고 컨트롤러는 정상 동작한다.
         * BindingResult를 파라미터로 받지 않은 상태로 @ModelAttribute에 들어오는 객체의 필드 타입에 맞지 않는 값이 들어온 경우 에러가 발생하며 컨트롤러가 실행되지 않는다.
          */

        // 검증 오류 결과를 보관
        Map<String, String> errors = new HashMap<>();

        // 검증 로직
        if (!StringUtils.hasText(item.getItemName())) { // StringUtils는 Springframework 것을 사용하고 hasText()는 null, Wite space, Empty string 등을 넣으면 False, 길이 1 이상의 문자열을 넣으면 True가 반환된다.
            errors.put("itemName", "상품 이름은 필수입니다.");
            bindingResult.addError(new FieldError("item", "itemName", "상품 이름은 필수입니다.")); // 객체의 특정 필드에 대한 검증 에러는 new FieldError() 객체를 사용해서 BindingError에 추가한다.
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."));

        }

        // 특정 필드가 아닌 복합 롤 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice);
                bindingResult.addError(new ObjectError("item", "수량은 최대 9,999 까지 허용합니다.가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
                // 객체의 특정 필드가 아닌 에러는 new ObjectError를 생성해서 BindingError에 추가한다. -> Global Error를 저장한다.
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) { // 위 검증 로직에서 addErorr()로 추가된 new FieldError()와 new ObjectError()가 존재하는 지에 대한 Boolean 값 반환
            log.info("errors = {}", errors);

//            model.addAttribute("errors", errors);
            // bindingResult에 추가된 에러들은 자동으로 Model에 Error로 담겨서 뷰에 넘어간다.

            return "validation/v2/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }
    @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        // 파라미터에서 BindingResult의 위치는 반드시 @ModelAttribute 다음에 와야 한다.
        // BindingResult는 @ModelAttribute 파라미터에 넘어온 객체(여기에서는 Item)의 결과를 바인딩하기 때문이다.

        /**
         * 중요!!
         * BindingResult를 파라미터로 받으면 @ModelAttribute에 들어오는 객체의 필드 타입에 맞지 않는 값이 들어온 경우 해당 타입 에러를 BindingResult에 new FieldError()에 담아서 들어오고 컨트롤러는 정상 동작한다.
         * BindingResult를 파라미터로 받지 않은 상태로 @ModelAttribute에 들어오는 객체의 필드 타입에 맞지 않는 값이 들어온 경우 에러가 발생하며 컨트롤러가 실행되지 않는다.
         */

        // 검증 오류 결과를 보관
        Map<String, String> errors = new HashMap<>();

        // 검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            errors.put("itemName", "상품 이름은 필수입니다.");
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null,  "상품 이름은 필수입니다."));
            // 세 번째 인자로 Rejected value를 넣을 수 있기 떄문에 에러가 발생한 이유인 사용자가 넣은 값을 넣어주면 그 값을 뷰에서 유지할 수 있다.(이 값이 없으면 에러가 발생한 인풋 부분의 값이 사라진다.)
            // bindingResult.addError(new FieldError("item", "itemName", 123, false, null, null,  "상품 이름은 필수입니다.")); -> String 타입이지만 사용자가 123을 입력해서 에러가 발생한 경우이다.
        }
        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null, "수량은 최대 9,999 까지 허용합니다."));

        }

        // 특정 필드가 아닌 복합 롤 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice);
                bindingResult.addError(new ObjectError("item", "수량은 최대 9,999 까지 허용합니다.가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
                // 객체의 특정 필드가 아닌 에러는 new ObjectError를 생성해서 BindingError에 추가한다. -> Global Error를 저장한다.
            }
        }

        // 검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) { // 위 검증 로직에서 addErorr()로 추가된 new FieldError()와 new ObjectError()가 존재하는 지에 대한 Boolean 값 반환
            log.info("errors = {}", errors);

//            model.addAttribute("errors", errors);
            // bindingResult에 추가된 에러들은 자동으로 Model에 Error로 담겨서 뷰에 넘어간다.

            return "validation/v2/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }

}

