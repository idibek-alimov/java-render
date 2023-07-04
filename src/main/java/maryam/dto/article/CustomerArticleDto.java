package maryam.dto.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.dto.inventory.CustomerInventoryDto;
import maryam.models.product.Article;

import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.Resource;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerArticleDto {
    private Long id;
    @JsonProperty("product_id")
    private Long productId;
    private Boolean likes;
    private String color;
    private Integer discount;
    private List<Integer> discounts;
    private String name;
    private String brand;
    private String description;
    private String category;
    private List<String> pictures;
    private List<CustomerInventoryDto> inventories;
}
