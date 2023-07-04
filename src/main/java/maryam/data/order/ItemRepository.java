package maryam.data.order;

import maryam.dto.order.DayOrderStatisticDTO;
import maryam.models.order.Item;
import maryam.models.order.Order;
import maryam.models.product.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item,Long> {
//    List<Item> findByArticle(Article article);


    @Query(value = "SELECT * FROM item WHERE status=?2 AND owner_id=?1",nativeQuery = true)
    List<Item> findByOwnerAndStatus(Long id,Integer status);
    @Query(value = "SELECT * FROM item WHERE status=?2 AND owner_id=?1",nativeQuery = true)
    List<Item> findByUserAndStatus(Long id,Integer status);

    @Query(value = "SELECT * FROM item WHERE status!=3",nativeQuery = true)
    List<Item> getDeliveries();
    @Query(value = "SELECT * FROM item WHERE status=?2 AND owner_id=?1 AND created_at > now() - interval '?3 day'",nativeQuery = true)
    List<Item> findByOwnerAndStatusAndDay(Long id,Integer status,Integer timeIndex);
    @Query(value = "SELECT * FROM item WHERE status=?2 AND owner_id=?1 AND created_at > now() - interval '?3 week'",nativeQuery = true)
    List<Item> findByOwnerAndStatusAndWeek(Long id,Integer status,Integer timeIndex);
    @Query(value = "SELECT * FROM item WHERE status=?2 AND owner_id=?1 AND created_at > now() - interval '?3 month'",nativeQuery = true)
    List<Item> findByOwnerAndStatusAndMonth(Long id,Integer status,Integer timeIndex);
    @Query(value = "SELECT * FROM item WHERE status=?2 AND owner_id=?1 AND created_at > now() - interval '?3 year'",nativeQuery = true)
    List<Item> findByOwnerAndStatusAndYear(Long id,Integer status,Integer timeIndex);
//    @Query(value = "SELECT * FROM item WHERE status=0 AND owner_id=?1",nativeQuery = true)
//    List<Item> findByOwnerInQueue(Long id);
//    @Query(value = "SELECT * FROM item WHERE status=1 AND owner_id=?1",nativeQuery = true)
//    List<Item> findByOwnerShipping(Long id);
//    @Query(value = "SELECT * FROM item WHERE status=2 AND owner_id=?1",nativeQuery = true)
//    List<Item> findByOwnerArrived(Long id);
//    @Query(value = "SELECT * FROM item WHERE status=3 AND owner_id=?1",nativeQuery = true)
//    List<Item> findByOwnerDelivered(Long id);
//
//    @Query(value = "SELECT * FROM item WHERE status=0 AND customer_id=?1",nativeQuery = true)
//    List<Item> findByUserInQueue(Long id);
//    @Query(value = "SELECT * FROM item WHERE status=1 AND customer_id=?1",nativeQuery = true)
//    List<Item> findByUserShipping(Long id);
//    @Query(value = "SELECT * FROM item WHERE status=2 AND customer_id=?1",nativeQuery = true)
//    List<Item> findByUserArrived(Long id);
//    @Query(value = "SELECT * FROM item WHERE status=3 AND customer_id=?1",nativeQuery = true)
//    List<Item> findByUserDelivered(Long id);
    @Query(value = "SELECT COUNT(id),SUM(original_price*quantity)as price,CAST(created_at as time) as time FROM item WHERE DATE(created_at)=CURRENT_DATE AND owner_id=?1 GROUP BY time ORDER BY time",nativeQuery = true)
    List<DayOrderStatisticDTO> findTodayOrders(Long id);
}
