package project.pizza.repository;

import org.springframework.web.multipart.MultipartFile;
import project.pizza.domain.item.ImageFile;

import java.io.IOException;

public interface ImageRepository {

    public String getFullPath(String filename);
    public ImageFile storeFile(MultipartFile multipartFile) throws IOException;
    public boolean deleteFile(String filename);
}
