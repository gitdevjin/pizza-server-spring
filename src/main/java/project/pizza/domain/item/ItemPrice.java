package project.pizza.domain.item;

import lombok.Data;

@Data
public class ItemPrice {
    public ItemPrice() {
    }

    public ItemPrice(Long id, Long itemId, String size, Double price) {
        this.id = id;
        this.itemId = itemId;
        this.size = size;
        this.price = price;
    }

    public ItemPrice(Long itemId, String size, Double price) {
        this.itemId = itemId;
        this.size = size;
        this.price = price;
    }

    public ItemPrice(String size, Double price) {
        this.size = size;
        this.price = price;
    }

    private Long id;

    private Long itemId;
    private String size;
    private Double price;

}
