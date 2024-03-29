package maryam.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.category.Category;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryWithProperties {
    private Long id;
    private Category category;
    private String name;
    private Boolean size;
    private Boolean color;
    private Boolean gender;
}
