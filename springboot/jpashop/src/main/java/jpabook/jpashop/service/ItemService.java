package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 이 서비스의 비즈니스 로직은 대부분이 조회 로직이기 때문에 성능의 이점을 위해 클래스 레벨에 트랜잭션을 readOnly = true로 설정했다.
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional // save()는 단순 조회가 아니기 때문에 readOnly가 아니다. 그래서 여기에는 따로 @Transactional을 달아준다.
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    // 이 서비스의 비즈니스 로직들을 보면 특별한 처리를 하지 않고 단순히 Repository로 데이터를 전달할 뿐이다.
    // 이런 경우 추후 Controller가 개발되면 해당 Controller에서 이 서비스를 거치지 않고 바로 Repository에 접근하도록 하고 이 서비스를 없애는 방법도 고려해볼 수 있다.
}
