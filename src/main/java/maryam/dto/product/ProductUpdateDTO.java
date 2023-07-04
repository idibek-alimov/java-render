package maryam.dto.product;

import lombok.*;
import maryam.models.category.Category;
import maryam.models.product.Dimensions;
import maryam.models.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProductUpdateDTO {
    private Long id;
    private String name;
    private String description;
    private String brand;
    private List<String> tags = new ArrayList<>() ;
    private Category category;
    private Dimensions dimensions;
    private User user;
}
