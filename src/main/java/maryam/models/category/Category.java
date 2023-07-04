package maryam.models.category;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import maryam.serializer.CategorySerializer;
//import maryam.serializer.InventorySizeSerializer;

import jakarta.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using= CategorySerializer.class)
@Builder
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(generator = "category_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "category_id_generator", sequenceName = "Category_id_generator", allocationSize = 1)
    private Long id;
    private String name;
    private String nameTJ;
    private String nameRU;
    private Boolean size = false;
    private Boolean color = false;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

//    @OneToOne(mappedBy = "category")
//    private CategoryProperties categoryProperties;

    public Category(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public Long getId() {
        return id;
    }

//    public void setCategoryProperties(CategoryProperties categoryProperties) {
//        this.categoryProperties = categoryProperties;
//    }
}