package maryam.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerInventoryDto {
    private Long id;
    private Double price;
    private Double originalPrice;
    private Integer quantity;
    private Boolean inStock;
    private String size;
    private Long barcode;
}
