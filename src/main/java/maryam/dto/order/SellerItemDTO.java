package maryam.dto.order;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Data
public class SellerItemDTO {
    private Long id;
    private String brand;
    private String createdAt;
    private String size;
    private String name;
    private Double price;
    private String sellerArticle;
    private List<String> pictures;
    private String status;

}
