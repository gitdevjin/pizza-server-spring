package project.pizza.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.pizza.repository.ItemRepository;
import project.pizza.repository.jdbc.JdbcItemRepository;

@Slf4j
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

}
