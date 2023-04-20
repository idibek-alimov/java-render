package maryam.models.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Discount {
    @Id
    @GeneratedValue(generator = "discount_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "discount_id_generator", sequenceName = "Discount_id_generator",allocationSize=1)
    private Long id;
    private Integer percentage;

    @JsonBackReference
    @ManyToOne
    private Article article;

    @Column
    private Date createdAt;

    @Column
    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }

    public Discount(Article article,Integer percentage){
        this.article = article;
        this.percentage = percentage;
    }
}
