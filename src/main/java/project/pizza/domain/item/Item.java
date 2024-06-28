package project.pizza.domain.item;

import lombok.Data;

@Data
public class Item {

    private Long id;

    private String itemName;
    private Double price;
    private String category;
    private String description;
    private String imgSource;

}
