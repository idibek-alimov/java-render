package maryam.models.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import maryam.models.inventory.Inventory;
import maryam.serializer.ColorSerializer;
import maryam.serializer.ProductSerializer;

<<<<<<< HEAD
import javax.persistence.*;
=======
import jakarta.persistence.*;
>>>>>>> testings

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using= ColorSerializer.class)
@Builder
public class Color {
    @Id
    @GeneratedValue(generator = "color_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "color_id_generator", sequenceName = "Color_id_generator",allocationSize=1)
    private Long id;

    @Column(unique = true)
    private String name;
<<<<<<< HEAD

//    @JsonBackReference
//    @OneToMany
//    private Article article;
=======
    private String nameTJ;
    private String nameRU;
>>>>>>> testings

}
