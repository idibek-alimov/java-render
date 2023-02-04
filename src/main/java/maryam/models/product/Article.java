package maryam.models.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.inventory.Inventory;
import maryam.models.picture.Picture;
import maryam.serializer.ArticleSerializer;
import maryam.serializer.ProductSerializer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(using= ArticleSerializer.class)
public class Article {
    @Id
    @GeneratedValue(generator = "article_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "article_id_generator", sequenceName = "Article_id_generator",allocationSize=1)
    private Long id;

//    private Integer identifier;

    @JsonBackReference
    @ManyToOne
    private Product product;

    @ManyToOne
    private Color color;

    @JsonManagedReference
    @Column(name = "pictures")
    @OneToMany(
            mappedBy = "article",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Inventory> inventory = new ArrayList<>();

    @JsonManagedReference
    @Column(name="pictures")
    @OneToMany(
            mappedBy = "article",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Picture> pictures = new ArrayList<>();

    public Article(Product product){
        this.product = product;
    }

}
