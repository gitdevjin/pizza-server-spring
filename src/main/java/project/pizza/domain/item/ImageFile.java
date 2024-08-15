package project.pizza.domain.item;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class ImageFile {

    private String uploadFileName;
    private String storeFileName;

    public ImageFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

}
