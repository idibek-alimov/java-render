package maryam.models.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.product.Article;
import maryam.serializer.PictureSerializer;

@Entity
@Table(name = "pick_point_picture")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PPPicture {
    @Id
    @GeneratedValue(generator = "pick_point_picture_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "pick_point_picture_id_generator", sequenceName = "Pppicture_id_generator",allocationSize=1)
    private Long id;

    private String name;



    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private PickPoint pickPoint;

    public PPPicture (String name){
        this.name = name;
    }
}
