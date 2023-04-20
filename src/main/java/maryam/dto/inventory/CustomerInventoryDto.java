package maryam.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInventoryDto {
    private Long id;
    private Double originalPrice;
    private Double discountPrice;
    private Integer quantity;
    private Boolean inStock;
    private String size;
    private Long barcode;
}
