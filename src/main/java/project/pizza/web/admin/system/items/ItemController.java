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
import project.pizza.web.admin.system.items.form.ItemAddForm;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/system")
public class ItemController {

    private final ItemImageManager itemImageManager;

    @GetMapping("/items/add")
    public String newItem(@ModelAttribute("item") ItemAddForm form){
        return "admin/item/addItemForm";
    }

    @PostMapping("/items/add")
    public String saveItem(@Valid @ModelAttribute("item") ItemAddForm form, BindingResult bindingResult) {
        // To do
        // Using Binding Result, implement validation.

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

        ImageFile image;

        try {
            if (!form.getItemImage().isEmpty()) {
                image = itemImageManager.storeFile(form.getItemImage());
            } else {
                image = null;
            }
        } catch (IOException e) {
            bindingResult.rejectValue("itemImage", "error.itemImage", "Failed to upload image");
            return "admin/item/addItemForm";
        }

        Item newItem = new Item();
        newItem.setItemName(form.getItemName());
        newItem.setCategory(form.getCategory());
        newItem.setDescription(form.getDescription());
        newItem.setImgFile(image);
        newItem.setPrices(prices);

        return "admin/item/queryItems";
    }
}
