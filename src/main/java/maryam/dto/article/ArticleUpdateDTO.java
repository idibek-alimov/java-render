package maryam.dto.article;

import lombok.*;
import maryam.dto.inventory.InventoryCreateDTO;
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
public class ArticleUpdateDTO {
    private Long id;
    private Color color;
    private String sellerArticle;
    private List<InventoryCreateDTO> inventory = new ArrayList<>();
    private Double discount;
    private List<Picture> pictures = new ArrayList<>();
}
