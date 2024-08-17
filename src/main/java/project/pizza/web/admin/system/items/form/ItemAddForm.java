package project.pizza.web.admin.system.items.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import project.pizza.domain.item.ImageFile;
import project.pizza.domain.item.ItemPrice;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemAddForm {

    @NotEmpty
    private String itemName;

    @NotEmpty
    private String category;

    @NotEmpty
    private String description;

    @NotEmpty
    private List<ItemPriceAddForm> prices = new ArrayList<>();

    private MultipartFile itemImage;
}
