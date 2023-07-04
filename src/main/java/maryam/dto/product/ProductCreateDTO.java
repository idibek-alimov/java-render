package maryam.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.product.Dimensions;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductCreateDTO {
    private String brand;
    private Long category;
    private String description;
    private String name;
    private DimensionsCreateDTO dimensions;
    private List<String> tags = new ArrayList<>();
}
