package maryam.models.inventory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import maryam.models.product.Article;
import maryam.serializer.InventorySerializer;

import jakarta.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

//@JsonSerialize(using= InventorySerializer.class)
public class Inventory {
    @Id
    @GeneratedValue(generator = "inventory_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "inventory_id_generator", sequenceName = "Inventory_id_generator",allocationSize=1)
    private Long id;

    @JsonBackReference
    @ManyToOne
    private Article article;
    private Double price;
    private Integer quantity = 0;
    private Boolean inStock = false;
    private Boolean available = true;
    private String size;

    @JsonManagedReference
    @OneToOne(mappedBy = "inventory")
    private CargoBarcode cargoBarcode;

    public Inventory(Article article,Double price){
        this.article = article;
        this.price = price;
    }
    public Inventory(Article article,Double price,Integer quantity){
        this(article,price);
        this.quantity = quantity;
    }
}
