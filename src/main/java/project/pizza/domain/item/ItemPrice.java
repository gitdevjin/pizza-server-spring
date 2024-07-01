package project.pizza.domain.item;

import lombok.Data;

@Data
public class ItemPrice {
    private Long id;

    private Long itemId;
    private String size;
    private Double price;

}
