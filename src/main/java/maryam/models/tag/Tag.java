package maryam.models.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.product.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(generator = "tag_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "tag_id_generator",initialValue = 1, allocationSize = 1,sequenceName = "Tag_id_generator")
    private Long id;
    private String name;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name="Tag_Product",
            joinColumns = {@JoinColumn(name="tag_id")},
            inverseJoinColumns = {@JoinColumn(name="product_id")}
    )
    private Set<Product> products = new HashSet<>();

    public Tag(String name){
        this.name = name;
    }
    public void addProduct(Product product){
        this.products.add(product);
    }
}
