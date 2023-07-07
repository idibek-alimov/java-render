package maryam.models.product;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Jacksonized
public class Brand {
    @Id
    @GeneratedValue(generator = "brand_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "brand_id_generator", sequenceName = "Brand_id_generator",allocationSize=1)
    private Long id;

    private String name;


}
