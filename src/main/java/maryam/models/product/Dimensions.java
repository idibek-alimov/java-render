package maryam.models.product;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dimensions {
    @Id
    @GeneratedValue(generator = "dimensions_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "dimensions_id_generator",initialValue = 1, allocationSize = 1,sequenceName = "Dimensions_id_generator")
    private Long id;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Product product;

    private Double length;
    private Double width;
    private Double height;

    @Column
    private Date createdAt;

    @Column
    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }
}
