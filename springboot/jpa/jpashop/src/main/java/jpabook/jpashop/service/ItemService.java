package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemRepository.findOne(id);
    }

    /**
     * ItemService의 메서드들을 보면 ItemRepository에 있는 기능을 그대로 선언한 것 뿐이다.
     * 즉, 위 메서드들은 단지 ItemService에서 ItemRepository로 처리를 위임하는 역할만을 할 뿐이고 사실 없어도 된다.
     * 이럴 때에는 형식상 ItemService에서 메서드로 감싸 처리를 위임하도록 하지 않고 ItemController에서 바로 ItemRepository에 접근하여 처리하는 것이 오히려 구조적으로 더 괜찮은 서택일 수 있다.
     * 단, 앞(Controller)에서 뒤(Service, Repository 등..)로 접근하는 것은 괜찮지만 뒤에서 앞으로 접근하는 구조는 절대 지양해야 한다. (앞 -> 뒤 단방향 구조로 설계 및 구현해야 한다.)
     * 다른 특별한 비즈니스 처리 로직 없이 단순히 위임만 하는 메서드는 과감히 없애고 Controller에서 Repository로 바로 접근해서 처리하는 것도 좋은 선택이다.
     */
}
