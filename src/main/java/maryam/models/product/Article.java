package maryam.models.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import maryam.models.inventory.Inventory;
import maryam.models.picture.Picture;
import maryam.serializer.ArticleSerializer;
import maryam.serializer.ProductSerializer;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Jacksonized
@JsonSerialize(using= ArticleSerializer.class)
public class Article {
    public  enum Status {NoPicture,Active,Removable,Deleted};
    @Id
    @GeneratedValue(generator = "article_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "article_id_generator", sequenceName = "Article_id_generator",allocationSize=1)
    private Long id;

<<<<<<< HEAD
//    private Integer identifier;
=======
    private Status status;
>>>>>>> testings

    @JsonBackReference
    @ManyToOne
    private Product product;

    @ManyToOne
    private Color color;

<<<<<<< HEAD
=======
    private String sellerArticle;

>>>>>>> testings
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
<<<<<<< HEAD

=======
    public Article(Product product,String sellerArticle){
        this.product = product;
        this.sellerArticle = sellerArticle;
    }
    @Column
    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }
>>>>>>> testings
}
