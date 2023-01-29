package maryam.models.role;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.user.User;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(generator = "role_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "role_id_generator", sequenceName = "Role_id_generator",allocationSize=1)
    private Long id;
    private String name;

    @JsonBackReference(value = "user-role")
    @ManyToOne
    private User user;

}
