package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach // 하나의 유닛 테스트가 끝나면 수행할 함수를 지정하는 애노테이션
    void afterEach() { // 하나의 유닛 테스트가 끝나면 수행되는 메서드
        itemRepository.clearStore();
    }

    @Test
    void save() {
        // given -> item을 생성해서 item이 주어졌다.
        Item item = new Item("itemA", 10000, 10);

        // when -> 주어진 아이템을 저장했을 때,
        Item savedItem = itemRepository.save(item);

        // then -> 저장한 아이템을 조회해서 저장한 아이템과 조회한 아이템이 같은 지 테스트한다.
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem); // 저장한 아이템과 조회한 아이템이 같아야 save()가 잘 동작한 것이므로 테스트에 통과하도록 했다.
    }

    @Test
    void findAll() {
        // given
        Item item1 = new Item("item1", 10000, 10);
        Item item2 = new Item("item2", 20000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        // when
        List<Item> result = itemRepository.findAll();

        // then
        assertThat(result.size()).isEqualTo(2); // result의 size가 2라면 통과
        assertThat(result).contains(item1, item2); // result 배열에 item1, item2를 포함하고 있다면 통과
    }

    @Test
    void updateItem() {
        // given
        Item item = new Item("item1", 10000, 10);

        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        // when
        Item updateParam = new Item("item2", 20000, 20);
        itemRepository.update(itemId, updateParam);

        Item findItem = itemRepository.findById(itemId);

        // then
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());
    }
}