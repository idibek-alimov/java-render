package maryam.models.product;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.category.Category;
import maryam.models.inventory.Inventory;
import maryam.models.picture.Picture;
import maryam.models.tag.Tag;
import maryam.models.user.User;
import maryam.serializer.ProductSerializer;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonSerialize(using = NewProductSerializer.class)
@JsonSerialize(using= ProductSerializer.class)
//@JsonView(ProductSerializer.class)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product implements Comparable<Product>{

    @Id
    @GeneratedValue(generator = "product_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "product_id_generator",initialValue = 1, allocationSize = 1,sequenceName = "Product_id_generator")
    //@JsonView(View.OnlyId.class)
    private Long id;

//    @JsonView(View.Detailed.class)
    @ManyToOne
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//    @JsonIdentityReference(alwaysAsId = true)
    private Category category;

    //@JsonView(View.Detailed.class)
    private String name;

    //@JsonView(View.Detailed.class)
    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//    @JsonIdentityReference(alwaysAsId = true)
    private User user;

//    @JsonManagedReference
//    @OneToMany
//    private Article article;

    //@JsonView(View.Detailed.class)
    private String description;

    //@JsonView(View.Detailed.class)
//    private Double price;

//    @JsonManagedReference
//    @Column(name="pictures")
//    @OneToMany(
//            mappedBy = "product",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//
//    private List<Picture> pictures = new ArrayList<>();


//    @JsonManagedReference
//    @Column(name = "pictures")
//    @OneToMany(
//            mappedBy = "product",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<Inventory> inventory = new ArrayList<>();
    @Column
    private Date createdAt;

    @Column
    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }

        @JsonManagedReference
    @Column
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Article> articles = new ArrayList<>();

    @ManyToMany(mappedBy = "products")
    private List<Tag> tags = new ArrayList<>();


    public Product(String name){
        this.name = name;
    }

    public List<Tag> addTag(Tag tag){
        this.tags.add(tag);
        return this.tags;
    }

    public List<Article> addArticle(Article article){
        this.articles.add(article);
        return this.articles;
    }


    @Override
    public boolean equals(Object obj) {
        return ((Product) obj).getName().equals(getName());
    }

    @Override
    public int compareTo(Product product) {
        return this.getCreatedAt().compareTo(product.getCreatedAt());
    }
}
