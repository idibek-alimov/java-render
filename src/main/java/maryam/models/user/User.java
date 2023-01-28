package maryam.models.user;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.order.Order;
import maryam.models.product.Product;
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
    @Id
    @GeneratedValue(generator = "user_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_id_generator",initialValue = 1, allocationSize = 1,sequenceName = "User_id_generator")
    @Column(unique=true, nullable=false)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonManagedReference(value = "user-role")
    private Collection<Role> roles = new ArrayList<>();


//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//    @JsonBackReference(value = "user-product")
//    @JsonBackReference(value = "user-product")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Product> products = new ArrayList<>();
    //Not relevant
//    @JsonBackReference
//    //@Column(name="pictures")
//    @OneToMany(
//            mappedBy = "user",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private List<Order> orderList = new ArrayList<>();

//    @JsonBackReference("user-order")
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Order> orders = new ArrayList<>();

    public User(String username,String password){
        this.username = username;
        this.password = password;
    }
}
