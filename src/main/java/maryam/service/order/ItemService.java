package maryam.service.order;

import lombok.RequiredArgsConstructor;
import maryam.controller.order.ItemHolder;
import maryam.data.order.ItemRepository;
import maryam.data.product.ArticleRepository;
import maryam.data.product.ProductRepository;
import maryam.data.user.UserRepository;
import maryam.models.order.Item;
import maryam.models.order.Order;
import maryam.models.product.Article;
import maryam.models.product.Product;
import maryam.service.article.ArticleService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor @Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final ArticleService articleService;
    public Item addItem(Item item){
        return itemRepository.save(item);
    }
    public List<Item> addItems(List<ItemHolder> items, Order order) throws RuntimeException{
        List<Item> itemList = new ArrayList<>();
        Item newItem;
        for(ItemHolder item:items){
            Article article = articleService.getArticle(item.getArticle()).get();
            newItem = new Item(article,item.getSize(),item.getAmount(),order);
            newItem = itemRepository.save(newItem);
            itemList.add(newItem);
        }
        order.setItems(itemList);
        return itemList;
    }
}
