package maryam.models.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.inventory.Inventory;
import maryam.models.picture.Picture;
import maryam.serializer.ArticleSerializer;
import maryam.serializer.ProductSerializer;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(using= ArticleSerializer.class)
@Builder
public class Article {
    public  enum Status {NoPicture,Active,Removable,Deleted};
    @Id
    @GeneratedValue(generator = "article_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "article_id_generator", sequenceName = "Article_id_generator",allocationSize=1)
    private Long id;


    private Status status;

    @JsonBackReference
    @ManyToOne
    private Product product;

    @ManyToOne
    private Color color;

    private String sellerArticle;

    @JsonManagedReference
    @Column(name = "pictures")
    @OneToMany(
            mappedBy = "article",
            cascade = CascadeType.ALL
            //orphanRemoval = true
    )
    private List<Inventory> inventory = new ArrayList<>();

    @JsonManagedReference
    @Column(name="pictures")
    @OneToMany(
            mappedBy = "article",
            cascade = CascadeType.ALL
//            orphanRemoval = true
    )
    private List<Picture> pictures = new ArrayList<>();
    @Column
    private Date createdAt;



    @JsonManagedReference
    @Column
    @OneToMany(
            mappedBy = "article",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Discount> discounts = new ArrayList<>();
    public Article(Product product){
        this.product = product;
    }
    public Article(Product product,String sellerArticle){
        this.product = product;
        this.sellerArticle = sellerArticle;
    }
    @Column
    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }
}
