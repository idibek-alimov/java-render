package maryam.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.product.Article;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryIdAndQuantity {
    private Long id;
    private Integer quantity = 1;
    //private Long cargoBarcode;
}
