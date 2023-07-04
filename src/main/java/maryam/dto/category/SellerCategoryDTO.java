package maryam.dto.category;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
public class SellerCategoryDTO {
    private Long id;
    private String name;
    private String nameTJ;
    private String nameRU;
    private Boolean size = false;
    private Boolean color = false;
    private Long category;
}
