package maryam.models.inventory;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CargoBarcode {
    @Id
    @GeneratedValue(generator = "cargo_barcode_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "cargo_barcode_id_generator", sequenceName = "Inventory_id_generator",allocationSize=1)
    private Long id;

    private Long barcode;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    Inventory inventory;

    public CargoBarcode(Long barcode,Inventory inventory){
        this.barcode = barcode;
        this.inventory = inventory;
    }
}
