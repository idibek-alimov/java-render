package maryam.models.order;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.product.Article;
import maryam.models.product.Product;
import maryam.serializer.ItemSerializer;
import maryam.serializer.View;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = ItemSerializer.class)
public class Item {
    //@JsonView(View.OnlyId.class)
    @Id
    @GeneratedValue(generator = "items_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "items_id_generator", sequenceName = "Items_id_generator",allocationSize=1)
    private Long id;



    //@JsonView(View.OnlyId.class)
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//    @JsonIdentityReference(alwaysAsId = true)
    private Article article;
    private String size;
    //@JsonView(View.OnlyId.class)
    private Integer amount;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;
    public Item(Article article,String size,Integer amount,Order order){
        this.article = article;
        this.size = size;
        this.amount = amount;
        this.order = order;
    }
}
