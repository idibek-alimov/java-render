package maryam.data.order;

import maryam.models.order.Item;
import maryam.models.product.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemRepository extends CrudRepository<Item,Long> {
    List<Item> findByArticle(Article article);
}
