package maryam.models.category;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryProperties {
    @Id
    @GeneratedValue(generator = "category_properties_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "category_properties_id_generator", sequenceName = "CategoryProperties_id_generator", allocationSize = 1)
    private Long id;
    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    Category category;

    private Boolean size = false;
    private Boolean color = false;
    private Boolean gender = false;

//    public CategoryProperties(Category category){
//        this.category = category;
//    }
    public CategoryProperties(Category category, Boolean size, Boolean color, Boolean gender){
        this.category = category;
        this.size = size;
        this.color = color;
        this.gender = gender;
    }

}
