package maryam.models.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.serializer.ItemSerializer;
import maryam.serializer.SellerPropertiesSerializer;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonSerialize(using = SellerPropertiesSerializer.class)
public class SellerProperties {
    @Id
    @GeneratedValue(generator = "seller_properties_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seller_properties_id_generator", sequenceName = "Seller_properties_id_generator",allocationSize=1)
    @Column(unique=true, nullable=false)
    private Long id;

    @OneToOne
    private User user;

    private Double balance = 0.0;
    private Integer productsInStorage = 0;
    private Integer productsSold = 0;
    private Integer productsReturned = 0;

    public SellerProperties(User user){
        this.user = user;
    }
}
