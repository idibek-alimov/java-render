package maryam.models.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductGender {
    @Id
    @GeneratedValue(generator = "product_gender_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "product_gender_id_generator",initialValue = 1, allocationSize = 1,sequenceName = "ProductGender_id_generator")
    private Long id;
    private String name;
}
