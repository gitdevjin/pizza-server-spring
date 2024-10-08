package project.pizza.repository.jdbc;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import project.pizza.domain.item.ImageFile;
import project.pizza.repository.ImageRepository;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class MemoryImageRepository implements ImageRepository {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    public ImageFile storeFile(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()) return null;

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);

        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new ImageFile(originalFilename, storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public boolean deleteFile(String filename) {
        File file = new File(getFullPath(filename));

        if (file.exists()) {
            return file.delete(); // Returns true if the file was successfully deleted, false otherwise
        } else {
            log.info("File Doesn't Exist");
            return false; // File does not exist
        }
    }

}
