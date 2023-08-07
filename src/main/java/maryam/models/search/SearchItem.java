package maryam.models.search;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import maryam.models.user.User;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SearchItem {
    @Id
    @GeneratedValue(generator = "search_item_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "search_item_id_generator", sequenceName = "Search_item_id_generator",allocationSize=1)
    private Long id;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne
    private User user;

    private String text;
    private Date createdAt;
    private Date updatedAt;
    private Integer count;
    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
    public SearchItem updateSearchItem(){
        this.updatedAt = new Date();
        this.count ++;
        return this;
    }

}
