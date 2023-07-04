package maryam.dto.order;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
public class ItemCreateDto {
    private Long inventoryId;
    private Integer quantity;
}
