package maryam.dto.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.dto.inventory.CustomerInventoryDto;

import java.util.List;

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
    private String gender;
    private String category;
    private List<String> pictures;
    private List<CustomerInventoryDto> inventories;
}
