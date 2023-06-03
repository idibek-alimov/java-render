package maryam.models.order;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.inventory.Inventory;
import maryam.models.product.Article;
import maryam.models.product.Product;
import maryam.models.user.User;
import maryam.serializer.ItemSerializer;
import maryam.serializer.View;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using = ItemSerializer.class)
public class Item {
    //@JsonView(View.OnlyId.class)
    public  enum Status {InQueue,Shipping,Arrived,Delivered};
    @Id
    @GeneratedValue(generator = "items_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "items_id_generator", sequenceName = "Items_id_generator",allocationSize=1)
    private Long id;


    //@JsonView(View.OnlyId.class)
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    private Inventory inventory;
    //@JsonView(View.OnlyId.class)

    private Status status = Status.InQueue;
    private Integer quantity;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Order order ;


    @CreationTimestamp
    @CreatedDate
    private LocalDateTime created_at;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private User customer;

    private Double maryamPrice;
    private Double originalPrice;
    public Item(Inventory inventory,Integer quantity,Order order,User owner){
        this.inventory = inventory;
        this.quantity = quantity;
        this.order = order;
        this.owner = owner;
    }
    public Item(Inventory inventory,Integer quantity,Order order,User owner,User customer){
        this(inventory,quantity,order,owner);
        this.customer = customer;
    }
}
