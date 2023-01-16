package maryam.models.like;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.product.Article;
import maryam.models.product.Product;
import maryam.models.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="liked_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {
    @Id
    @GeneratedValue(generator = "like_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "like_id_generator", sequenceName = "Like_id_generator",allocationSize=1)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Article article;
    @Column
    private Date createdAt;

    @Column
    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }

    public Like(Article article,User user){
        this.article = article;
        this.user = user;
    }
}
