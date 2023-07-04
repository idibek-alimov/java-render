package maryam.dto.inventory;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class InventoryQuantityDTO {
    private Long id;
    private Integer quantity;
}
