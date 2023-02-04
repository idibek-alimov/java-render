package maryam.models.uservisit;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.product.Product;
import maryam.models.user.User;
import maryam.serializer.ProductSerializer;
import maryam.serializer.VisitSerializer;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@Data
@NoArgsConstructor(access= AccessLevel.PRIVATE,force = true)
@AllArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(generator = "visit_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "visit_id_generator", sequenceName = "Visit_id_generator",allocationSize=1)
    private Long id;


    @ManyToOne()
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
//    @JsonIgnoreProperties({"category","name","user","description","price","pictures","inventory"})
    public Product product;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    public User user;

    private Date createdAt;

    public Visit(User user,Product product){
        this.user = user;
        this.product = product;
    }


    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }
}
