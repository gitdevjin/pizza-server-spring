package project.pizza.web.admin.system.items;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.pizza.domain.item.Item;
import project.pizza.domain.item.ItemPrice;
import project.pizza.web.admin.system.items.form.ItemAddForm;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/system")
public class ItemController {

    @GetMapping("/items/add")
    public String newItem(@ModelAttribute("item") ItemAddForm form){
        return "admin/item/addItemForm";
    }

    @PostMapping("/items/add")
    public String saveItem(@Valid @ModelAttribute ItemAddForm form) {
        log.info("ItemName: {}", form.getItemName());
        log.info("Category: {}", form.getCategory());
        log.info("Desc: {}", form.getDescription());
        List<ItemPrice> prices = form.getPrices();

        for (ItemPrice price : prices) {
            if (!price.getSize().isEmpty() && price.getPrice() != null) {
                log.info("Size: {}", price.getSize());
                log.info("Price: {}", price.getPrice());
            }
        }

        return "admin/item/queryItems";
    }
}
