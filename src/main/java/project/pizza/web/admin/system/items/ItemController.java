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
import org.springframework.web.util.UriUtils;
import project.pizza.domain.item.Item;
import project.pizza.domain.item.ItemPrice;
import project.pizza.service.admin.ItemService;
import project.pizza.web.admin.system.items.form.ItemAddForm;
import project.pizza.web.admin.system.items.form.ItemPriceAddForm;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/system")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items")
    public String items(Model model) {
        List<Item> items = itemService.getItems(null);
        model.addAttribute("retrievedItems", items);
        return "admin/item/queryItems";
    }

    @GetMapping("/item/{itemId}")
    public String item(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.getItem(itemId);
        log.info("Item Retrieved: {}; {}; {}", item.getId(), item.getItemName(), item.getCategory());
        model.addAttribute("item", item);
        return "admin/item/getItem";
    }

    @GetMapping("/item/update/{itemId}")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.getItem(itemId);
        model.addAttribute("item", item);
        return "admin/item/updateItem";
    }

    // Change this for Update Logic; Currently it's just a copy of addItem Logic
    @PostMapping("/item/update/{itemId}")
    public String updateItem(@Valid @ModelAttribute("item") ItemAddForm form,
                             @PathVariable("itemId") Long id,
                             @RequestParam("currentImage") String currentImageFile,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("Binding Failed");
            return "admin/item/addItemForm";
        }

        /* Checking the values sent from user */
        log.info("Update! ID: {}", id);
        log.info("Update! StoredImage: {}", currentImageFile);
        log.info("ItemName: {}; Category: {}; Desc: {}; Image: {};",
                form.getItemName(), form.getCategory(), form.getDescription(), form.getItemImage());

        List<ItemPrice> prices = new ArrayList<>();

        for (ItemPriceAddForm price : form.getPrices()) {
            if (price.getSize() != null && price.getPrice() != null) {
                log.info("Size: {}; Price: {}", price.getSize(), price.getPrice());
                prices.add(new ItemPrice(price.getSize(), price.getPrice()));
            } else {
                bindingResult.rejectValue("prices", "NotEmpty.item.prices", "Prices must not be empty");
                return "admin/item/addItemForm";
            }
        }
        /* ************************************ */

        Item newItem = new Item(id, form.getItemName(), form.getCategory(), form.getDescription(), prices);
        itemService.updateItem(newItem, form.getItemImage(), currentImageFile);

        return "redirect:/admin/system/items";
    }

    @PostMapping("/item/delete/{itemId}")
    public String deleteItem(@PathVariable("itemId") Long itemId,
                             @RequestParam("storedImage") String storedImage,
                             Model model) {
        // Think about what to return for delete;
        // Think about what to do with image file when delete item in database failed
        String message = itemService.deleteItem(itemId, storedImage);
        model.addAttribute("deleteMessage", message);
        return "redirect:/admin/system/items?deleteMessage=" + UriUtils.encode(message, StandardCharsets.UTF_8);
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

    @PostMapping("/items/add")
    public String saveItem(@Valid @ModelAttribute("item") ItemAddForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("Binding Failed");
            return "admin/item/addItemForm";
        }

        List<ItemPrice> prices = new ArrayList<>();

        for (ItemPriceAddForm price : form.getPrices()) {
            if (price.getSize() != null && price.getPrice() != null) {
                prices.add(new ItemPrice(price.getSize(), price.getPrice()));
            } else {
                bindingResult.rejectValue("prices", "NotEmpty.item.prices", "Prices must not be empty");
                return "admin/item/addItemForm";
            }
        }

        itemService.addItem(new Item(form.getItemName(), form.getCategory(), form.getDescription(), prices),
                form.getItemImage());

        return "redirect:/admin/system/items";
    }

}
