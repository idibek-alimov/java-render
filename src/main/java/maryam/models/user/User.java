package maryam.models.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//import maryam.models.order.Order;
import maryam.models.role.Role;
import maryam.serializer.UserSerializer;

import javax.persistence.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Table(name="user_list")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonSerialize(using = UserSerializer.class)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User {
    public  enum Gender {Male,Female};
    @Id
    @GeneratedValue(generator = "user_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_id_generator", sequenceName = "User_id_generator",allocationSize=1)
    @Column(unique=true, nullable=false)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String name;

    @Column(unique = true)
    private String email;



    private Boolean active = false;
    private Gender gender;
    private String profilePic;

    private String phoneNumber;
    private Integer age;

    //private String last_name;


    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    //Not relevant
//    @JsonBackReference
//    //@Column(name="pictures")
//    @OneToMany(
//            mappedBy = "user",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<Order> orderList = new ArrayList<>();
}
