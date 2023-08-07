package maryam.models.order;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.picture.Picture;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PickPoint {
    @Id
    @GeneratedValue(generator = "pick_points_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "pick_points_id_generator", sequenceName = "Pick_points_id_generator",allocationSize=1)
    private Long id;
    private Double longitude;
    private Double latitude;
    private String address;
    private String openFrom;
    private String openTill;
    @JsonManagedReference
    @Column(name="pictures")
    @OneToMany(
            mappedBy = "pickPoint",
            cascade = CascadeType.ALL
//            orphanRemoval = true
    )
    private List<PPPicture> pictures = new ArrayList<>();
}
