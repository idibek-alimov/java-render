package maryam.models.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(generator = "category_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "category_id_generator", sequenceName = "Category_id_generator", allocationSize = 1)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;


}