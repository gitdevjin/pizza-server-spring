package project.pizza.web.admin.system.items;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.pizza.domain.item.Item;
import project.pizza.domain.item.ItemPrice;
import project.pizza.domain.item.manager.ItemImageManager;
import project.pizza.service.admin.ItemService;
import project.pizza.web.admin.system.items.form.ItemAddForm;
import project.pizza.web.admin.system.items.form.ItemPriceAddForm;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/system")
public class ItemController {

    // To do
    // Update
    // Delete
    private final ItemService itemService;


    @GetMapping("/items")
    public String items(Model model) {
        List<Item> items = itemService.getItems(null);
        model.addAttribute("retrievedItems", items);
        return "admin/item/queryItems";
    }

    @GetMapping("/item/{itemId}")
    public String item(@PathVariable("itemId") Long itemId, Model model) {

        log.info("Hi Getting one Item {}", itemId);
        return "admin/item/queryItems";
    }

    @PostMapping("/item/delete/{itemId}")
    public String deleteItem(@PathVariable("itemId") Long itemId, Model model) {
        String message = itemService.deleteItem(itemId);
        model.addAttribute("deleteMessage", message);
        return "redirect:/admin/system/items";
    }

    @ResponseBody
    @GetMapping("/items/images/{filename}")
    public Resource downloadImage(@PathVariable("filename") String filename) throws MalformedURLException {
        return new UrlResource("file:" + itemService.getImageFullPath(filename));
    }

    @GetMapping("/items/add")
    public String newItem(@ModelAttribute("item") ItemAddForm form){
        return "admin/item/addItemForm";
    }

    // To do
    // Redirect to query route
    @PostMapping("/items/add")
    public String saveItem(@Valid @ModelAttribute("item") ItemAddForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("Binding Failed");
            return "admin/item/addItemForm";
        } else log.info("binding not failed");

        /* Checking the values sent from user */
        log.info("ItemName: {}; Category: {}; Desc: {}; Image: {};",
                form.getItemName(), form.getCategory(), form.getDescription(), form.getItemImage());

        List<ItemPriceAddForm> addedPrices = form.getPrices();

        List<ItemPrice> prices = new ArrayList<>();

        for (ItemPriceAddForm price : addedPrices) {
            if (price.getSize() != null && price.getPrice() != null) {
                log.info("Size: {}; Price: {}", price.getSize(), price.getPrice());
                prices.add(new ItemPrice(price.getSize(), price.getPrice()));
            } else {
                bindingResult.rejectValue("prices", "NotEmpty.item.prices", "Prices must not be empty");
                return "admin/item/addItemForm";
            }
        }
        /* ************************************ */

        Item newItem = new Item(form.getItemName(), form.getCategory(), form.getDescription(), prices);

        itemService.addItem(newItem, form.getItemImage());

        return "redirect:/admin/system/items";
    }


}
