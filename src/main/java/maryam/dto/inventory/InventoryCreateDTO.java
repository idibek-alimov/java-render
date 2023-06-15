package maryam.dto.inventory;

import lombok.*;
import maryam.models.inventory.Inventory;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class InventoryCreateDTO {
    private Long id;
    private String size;
    private Double price;
    private Integer quantity;

    public Inventory toInventory(){
        return new Inventory()
                .builder()
                .id(this.id)
                .size(this.size)
                .price(this.price)
                .quantity(this.quantity)
                .build();
    }
}
