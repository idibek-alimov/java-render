package maryam.models.picture;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.inventory.Inventory;
import maryam.models.product.Article;
import maryam.models.product.Product;
import maryam.serializer.PictureSerializer;
import maryam.serializer.ProductSerializer;

import javax.persistence.*;

@Entity
@Table(name = "picture")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using= PictureSerializer.class)
public class Picture {
    @Id
    @GeneratedValue(generator = "picture_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "picture_id_generator", sequenceName = "Picture_id_generator",allocationSize=1)
    private Long id;

    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    public Picture(String name){
        this.name = name;
    }
}
