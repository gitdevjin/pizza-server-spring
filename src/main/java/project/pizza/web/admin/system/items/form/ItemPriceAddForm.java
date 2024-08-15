package project.pizza.web.admin.system.items.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ItemPriceAddForm {

    private String size;

    @Pattern(regexp = "\\d+\\.\\d{2}", message = "Invalid price format")
    private Double price;

}
