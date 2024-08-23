package project.pizza.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import project.pizza.domain.item.ImageFile;
import project.pizza.domain.item.Item;
import project.pizza.domain.item.ItemPrice;

import java.util.ArrayList;
import java.util.List;

@Transactional
@SpringBootTest
public class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @BeforeEach
    void beforeEach() {
    }

    @Test
    public void saveAndFindById() {

        // Create Test Item
        List<ItemPrice> prices = new ArrayList<>();
        prices.add(new ItemPrice("Large", 12.99));
        prices.add(new ItemPrice("Medium", 10.99));
        Item item = new Item("Pepperoni", "Pizza", "Delicious Pizza with Italian Pepperoni",
                prices, new ImageFile("TestUploadName", "TestStoredName"));

        // Save Item
        Item createdItem = itemRepository.save(item);

        // Retrieve the Item
        Item foundItem = itemRepository.findById(createdItem.getId()).orElse(null);

        // Check Item
        Assertions.assertThat(foundItem).isNotNull();
        Assertions.assertThat(foundItem.getItemName()).isEqualTo("Pepperoni");
        Assertions.assertThat(foundItem.getCategory()).isEqualTo("Pizza");
        Assertions.assertThat(foundItem.getImgFile().getUploadFileName()).isEqualTo("TestUploadName");
        Assertions.assertThat(foundItem.getImgFile().getStoreFileName()).isEqualTo("TestStoredName");

        // Check Item Prices
        Assertions.assertThat(foundItem.getPrices().size()).isEqualTo(2);
        Assertions.assertThat(foundItem.getPrices().getFirst().getSize()).isEqualTo("Large");
        Assertions.assertThat(foundItem.getPrices().getFirst().getPrice()).isEqualTo(12.99);
        Assertions.assertThat(foundItem.getPrices().get(1).getSize()).isEqualTo("Medium");
        Assertions.assertThat(foundItem.getPrices().get(1).getPrice()).isEqualTo(10.99);
    }

    @Test
    public void delete() {

        // Create Test Item
        List<ItemPrice> prices = new ArrayList<>();
        prices.add(new ItemPrice("Large", 12.99));
        prices.add(new ItemPrice("Medium", 10.99));
        Item item = new Item("Pepperoni", "Pizza", "Delicious Pizza with Italian Pepperoni",
                prices, new ImageFile("TestUploadName", "TestStoredName"));

        // Save Item
        Item createdItem = itemRepository.save(item);

        // Delete the Item
        itemRepository.delete(createdItem.getId());

        // Retrieve the Item
        Item foundItem = itemRepository.findById(createdItem.getId()).orElse(null);

        Assertions.assertThat(foundItem).isNull();

    }

    @Test
    public void update() {

        // Create Test Item
        List<ItemPrice> prices = new ArrayList<>();
        prices.add(new ItemPrice("Large", 12.99));
        prices.add(new ItemPrice("Medium", 10.99));
        Item item = new Item("Pepperoni", "Pizza", "Delicious Pizza with Italian Pepperoni",
                prices, new ImageFile("TestUploadName", "TestStoredName"));

        // Save Item
        Item createdItem = itemRepository.save(item);

        // Retrieve the Item
        Item foundItem = itemRepository.findById(createdItem.getId()).orElse(null);

        // Check Item
        Assertions.assertThat(foundItem).isNotNull();
        Assertions.assertThat(foundItem.getItemName()).isEqualTo("Pepperoni");
        Assertions.assertThat(foundItem.getCategory()).isEqualTo("Pizza");
        Assertions.assertThat(foundItem.getDescription()).isEqualTo("Delicious Pizza with Italian Pepperoni");
        Assertions.assertThat(foundItem.getImgFile().getUploadFileName()).isEqualTo("TestUploadName");
        Assertions.assertThat(foundItem.getImgFile().getStoreFileName()).isEqualTo("TestStoredName");

        // Check Item Prices
        Assertions.assertThat(foundItem.getPrices().size()).isEqualTo(2);
        Assertions.assertThat(foundItem.getPrices().getFirst().getSize()).isEqualTo("Large");
        Assertions.assertThat(foundItem.getPrices().getFirst().getPrice()).isEqualTo(12.99);
        Assertions.assertThat(foundItem.getPrices().get(1).getSize()).isEqualTo("Medium");
        Assertions.assertThat(foundItem.getPrices().get(1).getPrice()).isEqualTo(10.99);

        // New Item for Update
        List<ItemPrice> newPrices = new ArrayList<>();
        prices.add(new ItemPrice("XLarge", 14.99));
        Item newItem = new Item("Tomato Spaghetti", "Pasta", "Amazing Pasta",
                prices, new ImageFile("UpdateUploadName", "UpdateStoredName"));
        newItem.setId(createdItem.getId());

        // Update the Item
        itemRepository.update(newItem);

        // Retrieve the updated Item
        Item updatedItem = itemRepository.findById(createdItem.getId()).orElse(null);

        // Check UpdatedItem
        Assertions.assertThat(updatedItem).isNotNull();
        Assertions.assertThat(updatedItem.getItemName()).isEqualTo("Tomato Spaghetti");
        Assertions.assertThat(updatedItem.getCategory()).isEqualTo("Pasta");
        Assertions.assertThat(updatedItem.getDescription()).isEqualTo("Amazing Pasta");
        Assertions.assertThat(updatedItem.getImgFile().getUploadFileName()).isEqualTo("UpdateUploadName");
        Assertions.assertThat(updatedItem.getImgFile().getStoreFileName()).isEqualTo("UpdateStoredName");

        // Check UpdatedItem Prices
        Assertions.assertThat(updatedItem.getPrices().size()).isEqualTo(1);
        Assertions.assertThat(updatedItem.getPrices().getFirst().getSize()).isEqualTo("XLarge");
        Assertions.assertThat(updatedItem.getPrices().getFirst().getPrice()).isEqualTo(14.99);

    }

    @Test
    public void findAll() {
        // Create Test Item
        List<ItemPrice> prices = new ArrayList<>();
        prices.add(new ItemPrice("Large", 12.99));
        prices.add(new ItemPrice("Medium", 10.99));
        Item item = new Item("Pepperoni", "Pizza", "Delicious Pizza with Italian Pepperoni",
                prices, new ImageFile("TestUploadName", "TestStoredName"));

        // Create Test Item2
        List<ItemPrice> newPrices = new ArrayList<>();
        prices.add(new ItemPrice("XLarge", 14.99));
        Item item2 = new Item("Tomato Spaghetti", "Pasta", "Amazing Pasta",
                prices, new ImageFile("UpdateUploadName", "UpdateStoredName"));

        itemRepository.save(item);
        itemRepository.save(item2);

        List<Item> items = itemRepository.findAll(null);
        // Need to make a database for test;
        // Assertions.assertThat(items.size()).isEqualTo(2);

    }
}
