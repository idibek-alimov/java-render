package maryam.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.product.Article;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {
    private Long id;
    private Article article;
    private  Double price;
    private Integer quantity = 1;

    private Boolean available = true;
    private String size;

    private Long cargoBarcode;

}
