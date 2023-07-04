package maryam.data.order;

import maryam.dto.order.DayOrderStatisticDTO;
import maryam.dto.order.WeekOrderStatisticDTO;
import maryam.models.order.Order;
import maryam.models.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order,Long> {
    //Iterable<Order> findByCheckedIsNull();
    List<Order> findByUser(User user);

    @Query(value = "SELECT * FROM user_order WHERE status=0 AND user_id=?1",nativeQuery = true)
    List<Order> findByUserInQueue(Long id);
    @Query(value = "SELECT * FROM user_order WHERE status=1 AND user_id=?1",nativeQuery = true)
    List<Order> findByUserShipping(Long id);
    @Query(value = "SELECT * FROM user_order WHERE status=2 AND user_id=?1",nativeQuery = true)
    List<Order> findByUserArrived(Long id);
    @Query(value = "SELECT * FROM user_order WHERE status=3 AND user_id=?1",nativeQuery = true)
    List<Order> findByUserDelivered(Long id);

    @Query(value = "SELECT COUNT(id),SUM(price*quantity)as price,EXTRACT(HOUR FROM CAST(created_at as time)) as time FROM item WHERE DATE(created_at)=CURRENT_DATE AND owner_id=?1 AND status=1 GROUP BY time ORDER BY time",nativeQuery = true)
    List<DayOrderStatisticDTO> findTodayOrdersShipping(Long id);

    @Query(value = "SELECT COUNT(id),SUM(price*quantity)as price,EXTRACT(DAY FROM DATE(created_at))as day " +
            "FROM item WHERE created_at >now() - interval '1 week' AND owner_id=?1 AND status=1 GROUP BY day ORDER BY day",nativeQuery = true)
    List<WeekOrderStatisticDTO> findWeekOrdersShipping(Long id);

    @Query(value = "SELECT COUNT(id),SUM(price*quantity)as price,EXTRACT(HOUR FROM CAST(created_at as time)) as time FROM item WHERE DATE(created_at)=CURRENT_DATE AND owner_id=?1 AND status=3 GROUP BY time ORDER BY time",nativeQuery = true)
    List<DayOrderStatisticDTO> findTodayOrdersDelivered(Long id);

    @Query(value = "SELECT COUNT(id),SUM(price*quantity)as price,EXTRACT(DAY FROM DATE(created_at))as day " +
            "FROM item WHERE created_at >now() - interval '1 week' AND owner_id=?1 AND status=3 GROUP BY day ORDER BY day",nativeQuery = true)
    List<WeekOrderStatisticDTO> findWeekOrdersDelivered(Long id);

}
