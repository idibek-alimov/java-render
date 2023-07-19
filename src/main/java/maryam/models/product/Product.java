package maryam.models.product;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import maryam.models.category.Category;
import maryam.models.tag.Tag;
import maryam.models.user.User;
import maryam.serializer.ProductSerializer;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using= ProductSerializer.class)
@Builder
public class Product implements Comparable<Product>{
    @Id
    @GeneratedValue(generator = "product_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "product_id_generator",initialValue = 1, allocationSize = 1,sequenceName = "Product_id_generator")
    private Long id;
    @ManyToOne
    private Category category;

    private String name;
//    private String brand;
    @ManyToOne
    private Brand brand;

    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    @OneToOne(mappedBy = "product")
    private Dimensions dimensions;

    @ManyToOne
    private ProductGender productGender;

    private String description;

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
    @JsonManagedReference
    @Column
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProductExtraInfo> productExtraInfoList = new ArrayList<>();

    @ManyToMany(mappedBy = "products")
    private List<Tag> tags = new ArrayList<>();
    public Product(String name){
        this.name = name;
    }
    public Product(String name,String description,User user) {
        this.name = name;
        this.description = description;
        this.user = user;
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
