package maryam.models.inventory;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.product.Product;
import maryam.serializer.InventorySerializer;
import maryam.serializer.InventorySizeSerializer;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using= InventorySizeSerializer.class)
public class InventorySize {
    @Id
    @GeneratedValue(generator = "inventory_size_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "inventory_size_id_generator",initialValue = 1, allocationSize = 1,sequenceName = "ProductGender_id_generator")
    private Long id;

//    @OneToMany(cascade = CascadeType.ALL)
//    //@JoinColumn(name = "inventory_id", referencedColumnName = "id")
//    Inventory inventory;

    @Column(unique = true)
    private String size;

    public InventorySize(String size){
        this.size = size;
    }
}
