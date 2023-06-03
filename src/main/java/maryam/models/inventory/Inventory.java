package maryam.models.inventory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.product.Article;
import maryam.serializer.InventorySerializer;

import jakarta.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonSerialize(using= InventorySerializer.class)
public class Inventory {
    @Id
    @GeneratedValue(generator = "inventory_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "inventory_id_generator", sequenceName = "Inventory_id_generator",allocationSize=1)
    private Long id;

    @JsonBackReference
    @ManyToOne
    private Article article;

    private Double originalPrice;
    private Double maryamPrice;
    private Integer quantity = 0;
    private Boolean inStock = false;

    @ManyToOne
    private InventorySize inventorySize;

    @JsonManagedReference
    @OneToOne(mappedBy = "inventory")
    private CargoBarcode cargoBarcode;

    public Inventory(Article article,Double price){
        this.article = article;
        this.originalPrice = price;
        this.maryamPrice = price*1.25;
    }
    public Inventory(Article article,Double price,Integer quantity){
        this(article,price);
        this.quantity = quantity;
    }
}
