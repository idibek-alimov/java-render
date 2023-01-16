package maryam.controller.product;

import lombok.Data;
import maryam.models.inventory.Inventory;
import maryam.models.product.Color;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CreateArticleHolder {
//    List<MultipartFile> pictures;
    List<Inventory> inventories;
    Color color;
}
