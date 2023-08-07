package maryam.models.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import maryam.models.product.Product;
import maryam.models.user.User;
import maryam.serializer.View;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {


    public  enum Status {InQueue,Shipping,Arrived,Delivered};
    public enum DeliveryMethod{PickPoint,Courier};

    @JsonView(View.OnlyId.class)
    @Id
    @GeneratedValue(generator = "orders_id_generator", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "orders_id_generator", sequenceName = "Orders_id_generator",allocationSize=1)
    private Long id;

    private Status status = Status.InQueue;
    @JsonView(View.OnlyId.class)
    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String address;
    private String[] coordinates = new String[2];
    private DeliveryMethod deliveryMethod;

    @JsonManagedReference
    @Column(name="items")
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonView(View.OnlyId.class)
    private List<Item> items = new ArrayList<>();

    @CreationTimestamp
    @CreatedDate
    private LocalDateTime created_at;
    @UpdateTimestamp
    private LocalDateTime updated_at;
    public Order(User user1){
        this.user = user1;
    }

    @PrePersist
    public void setcreationtime(){
        this.created_at = LocalDateTime.now();
    }
    public static DeliveryMethod getDeliveryMethodByIndex(Integer index){
        if (index == 0){
            return DeliveryMethod.PickPoint;
        }
        else if(index == 1){
            return  DeliveryMethod.Courier;
        }
        else {
            return null;
        }
    }
}
