package project.pizza.repository;

import project.pizza.domain.item.Item;

import java.util.List;

public interface ItemRepository {

    public Item save(Item item);
    public List<Item> findAll(String category);

    public String delete(Long itemId);
}
