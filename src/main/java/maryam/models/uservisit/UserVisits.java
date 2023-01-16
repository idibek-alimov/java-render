package maryam.models.uservisit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.user.User;


import javax.persistence.*;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVisits {
    @Id
    @GeneratedValue(generator = "user_visit_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_visit_id_generator", sequenceName = "User_visit_id_generator",allocationSize=1)
    private Long id;

    private static int capacity = 200;

    private Integer visitcount = 0;

    @OneToOne
    private User user;

    public UserVisits(User user){
        this.user = user;
    }
}
