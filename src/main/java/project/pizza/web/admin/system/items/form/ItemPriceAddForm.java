package project.pizza.web.admin.system.items.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ItemPriceAddForm {

    private String size;

    private Double price;

}
