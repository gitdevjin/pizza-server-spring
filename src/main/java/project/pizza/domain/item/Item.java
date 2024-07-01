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

}
