package project.pizza.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import project.pizza.domain.item.ImageFile;
import project.pizza.domain.item.Item;
import project.pizza.domain.item.manager.ItemImageManager;
import project.pizza.repository.ItemRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageManager itemImageManager;

    public Item addItem(Item item, MultipartFile imgFile) {

        ImageFile image;

        try {
            if (!imgFile.isEmpty()) {
                image = itemImageManager.storeFile(imgFile);
            } else {
                image = null;
            }
        } catch (IOException e) {
            log.info("[ItemService][save][ERROR] - Failed to Save ImageFile");
            throw new RuntimeException(e.getMessage());
        }

        item.setImgFile(image);

        return itemRepository.save(item);
    }

    public String deleteItem(Long itemId) {
        return itemRepository.delete(itemId);
    }

    public List<Item> getItems(String category) {
        return itemRepository.findAll(category);
    }

    public String getImageFullPath(String fileName) {
        return itemImageManager.getFullPath(fileName);
    }

}
