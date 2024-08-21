package project.pizza.service.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public Item addItem(Item item, MultipartFile imgFile) {

        storeItemImage(item, imgFile);

        return itemRepository.save(item);
    }

    @Transactional
    public Item updateItem(Item item, MultipartFile imgFile, String storedImage) {

        if (storedImage != null) {
            try {
                itemImageManager.deleteFile(storedImage);
            } catch (Exception e) {
                log.info("[ItemService][delete][ERROR] - Failed to Delete StoredImageFile");
                throw new RuntimeException(e.getMessage());
            }
        }

        storeItemImage(item, imgFile);

        log.info("Multipart Check in Update: {}", imgFile.getOriginalFilename());
        log.info("This is Image Before Update: {}", storedImage);
        log.info("Image[update]: {}", item.getImgFile().getStoreFileName());

        return itemRepository.update(item);
    }

    @Transactional
    public String deleteItem(Long itemId, String imageFile) {
        itemImageManager.deleteFile(imageFile);
        return itemRepository.delete(itemId);
    }

    public Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElse(null);
    }

    public List<Item> getItems(String category) {
        return itemRepository.findAll(category);
    }

    public String getImageFullPath(String fileName) {
        return itemImageManager.getFullPath(fileName);
    }

    private void storeItemImage(Item item, MultipartFile imgFile) {
        ImageFile image;

        try {
            if (!imgFile.isEmpty()) {
                image = itemImageManager.storeFile(imgFile);
                log.info("Saving Image {}", image.getStoreFileName());
            } else {
                image = null;
            }
        } catch (IOException e) {
            log.info("[ItemService][save][ERROR] - Failed to Save ImageFile");
            throw new RuntimeException(e.getMessage());
        }

        item.setImgFile(image);
    }

}
