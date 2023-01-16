package maryam.data.order;

import maryam.models.order.Order;
import maryam.models.product.Product;
import maryam.models.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Long> {
    Iterable<Order> findByCheckedIsNull();
    List<Order> findByUser(User user);

    @Query(value = "SELECT * FROM user_order WHERE delivered=false AND user_id=?1",nativeQuery = true)
    List<Order> findByUserNotDelivered(Long id);

    @Query(value = "SELECT * FROM user_order WHERE delivered=true AND user_id=?1",nativeQuery = true)
    List<Order> findByUserDelivered(Long id);
}
