package maryam.dto.article;

import lombok.*;
import maryam.dto.inventory.InventoryCreateDTO;
import maryam.models.inventory.Inventory;
import maryam.models.picture.Picture;
import maryam.models.product.Color;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
public class ArticleCreateDTO {
    private Long color;
    private String sellerArticle;
    private List<InventoryCreateDTO> inventory = new ArrayList<>();
    private Double discount;
}
