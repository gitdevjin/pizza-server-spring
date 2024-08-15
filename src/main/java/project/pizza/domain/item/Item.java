package project.pizza.domain.item;

import lombok.Data;

import java.util.List;

@Data
public class Item {

    private Long id;

    private String itemName;
    private String category;
    private String description;
    private List<ItemPrice> prices;
    private ImageFile imgFile;

    public Item() {}

    public Item(String itemName, String category, String description, List<ItemPrice> prices) {
        this.itemName = itemName;
        this.category = category;
        this.description = description;
        this.prices = prices;
    }

    public Item(String itemName, String category, String description, List<ItemPrice> prices, ImageFile imgFile) {
        this.itemName = itemName;
        this.category = category;
        this.description = description;
        this.prices = prices;
        this.imgFile = imgFile;
    }
}
