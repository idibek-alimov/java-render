package maryam.data.inventory;

import maryam.dto.inventory.SellerItemDTO;
import maryam.models.inventory.Inventory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InventoryRepository extends CrudRepository<Inventory,Long> {

//    @Query(value = "SELECT inventory.* FROM product INNER JOIN article on product.id = article.product_id " +
//            "INNER JOIN inventory ON article.id = inventory.article_id  WHERE product.user_id=?1",nativeQuery = true)
//    List<Inventory> getInventoryByUser(Long id);
    @Query(value = "SELECT item.created_at,inventory.inventory_size_id,article.seller_article," +
            "product.name,product.brand,item.original_price,item.status " +
            "FROM item INNER JOIN inventory ON inventory.id=item.inventory_id " +
            "INNER JOIN article ON article.id = inventory.article_id " +
            "INNER JOIN product ON product.id = article.product_id " +
            "WHERE item.owner_id=?1 AND item.status=?2",nativeQuery = true)
    List<SellerItemDTO> getSellerItem(Long id,Integer status);
    @Query(value = "SELECT item.created_at,inventory.inventory_size_id,article.seller_article," +
            "product.name,product.brand,item.original_price,item.status " +
            "FROM item INNER JOIN inventory ON inventory.id=item.inventory_id " +
            "INNER JOIN article ON article.id = inventory.article_id " +
            "INNER JOIN product ON product.id = article.product_id " +
            "WHERE item.owner_id=?1",nativeQuery = true)
    List<SellerItemDTO> getSellerItemAll(Long id);
}
