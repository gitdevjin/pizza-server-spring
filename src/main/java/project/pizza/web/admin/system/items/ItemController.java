package project.pizza.web.admin.system.items;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import project.pizza.domain.item.ImageFile;
import project.pizza.domain.item.Item;
import project.pizza.domain.item.ItemPrice;
import project.pizza.domain.item.manager.ItemImageManager;
import project.pizza.service.admin.ItemService;
import project.pizza.web.admin.system.items.form.ItemAddForm;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/system")
public class ItemController {

    private final ItemImageManager itemImageManager;
    private final ItemService itemService;

    @GetMapping("/items/add")
    public String newItem(@ModelAttribute("item") ItemAddForm form){
        return "admin/item/addItemForm";
    }

    @PostMapping("/items/add")
    public String saveItem(@Valid @ModelAttribute("item") ItemAddForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) return "admin/item/addItemForm";

        /* Checking the values sent from user */
        log.info("ItemName: {}; Category: {}; Desc: {}; Image: {};",
                form.getItemName(), form.getCategory(), form.getDescription(), form.getItemImage());

        List<ItemPrice> prices = form.getPrices();

        for (ItemPrice price : prices) {
            if (!price.getSize().isEmpty() && price.getPrice() != null) {
                log.info("Size: {}; Price: {}", price.getSize(), price.getPrice());
            }
        }
        /* ************************************ */

        Item newItem = new Item();
        newItem.setItemName(form.getItemName());
        newItem.setCategory(form.getCategory());
        newItem.setDescription(form.getDescription());
        newItem.setPrices(prices);

        itemService.addItem(newItem, form.getItemImage());

        return "admin/item/queryItems";
    }
}
