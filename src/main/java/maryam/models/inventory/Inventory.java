package maryam.models.inventory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.picture.Picture;
import maryam.models.product.Article;
import maryam.models.product.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(generator = "inventory_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "inventory_id_generator", sequenceName = "Inventory_id_generator",allocationSize=1)
    private Long id;

    @JsonBackReference
    @ManyToOne
    private Article article;

    private String product_size;
    private Double price;
    private Integer quantity;


//    @JsonManagedReference
//    @Column(name="pictures")
//    @OneToMany(
//            mappedBy = "product",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//
//    private List<Picture> pictures = new ArrayList<>();


}
