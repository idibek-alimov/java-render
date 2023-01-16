package maryam.models.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.inventory.Inventory;
import maryam.serializer.ColorSerializer;
import maryam.serializer.ProductSerializer;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using= ColorSerializer.class)
public class Color {
    @Id
    @GeneratedValue(generator = "color_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "color_id_generator", sequenceName = "Color_id_generator",allocationSize=1)
    private Long id;
    private String name;

//    @JsonBackReference
//    @OneToMany
//    private Article article;

}
