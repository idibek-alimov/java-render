package maryam.models.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductExtraInfo {
    @Id
    @GeneratedValue(generator = "product_extra_info_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "product_extra_info_id_generator",initialValue = 1, allocationSize = 1,sequenceName = "Product_extra_info_id_generator")
    private Long id;
    private String name;
    private String value;
}
