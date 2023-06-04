package maryam.models.category;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.serializer.CategorySerializer;
import maryam.serializer.InventorySizeSerializer;

import jakarta.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(using= CategorySerializer.class)
@Builder
public class Category {
    @Id
    @GeneratedValue(generator = "category_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "category_id_generator", sequenceName = "Category_id_generator", allocationSize = 1)
    private Long id;
    private String name;
    private String nameTJ;
    private String nameRU;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @OneToOne(mappedBy = "category")
    private CategoryProperties categoryProperties;

    public Category(String name){
        this.name = name;
    }

}