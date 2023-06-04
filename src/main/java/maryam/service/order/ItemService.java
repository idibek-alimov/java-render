package maryam.service.order;

import lombok.RequiredArgsConstructor;
import maryam.controller.order.ItemHolder;
import maryam.data.order.ItemRepository;
import maryam.data.product.ArticleRepository;
import maryam.data.product.ProductRepository;
import maryam.data.user.UserRepository;
import maryam.models.inventory.Inventory;
import maryam.models.order.Item;
import maryam.models.order.Order;
import maryam.models.product.Article;
import maryam.models.product.Product;
import maryam.service.article.ArticleService;
import maryam.service.inventory.InventoryService;
import maryam.service.user.SellerPropertiesService;
import maryam.service.user.UserService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor @Transactional
public class ItemService {
    private final ItemRepository itemRepository;
    private final InventoryService inventoryService;
    private final UserService userService;
    private final SellerPropertiesService sellerPropertiesService;
    public enum TimeValue{Day,Week,Month,Year};

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> addItems(List<ItemHolder> items, Order order) throws RuntimeException {
        List<Item> itemList = new ArrayList<>();
        Item newItem;
        for (ItemHolder item : items) {
            Inventory inventory = inventoryService.getById(item.getInventoryId());
            if (inventory != null && inventory.getInStock() && inventory.getQuantity() >= item.getQuantity()) {
                newItem = new Item(inventory, item.getQuantity(), order, inventory.getArticle().getProduct().getUser(), userService.getCurrentUser());
                newItem.setPrice(inventory.getPrice()* item.getQuantity());
                //newItem.setMaryamPrice(inventory.getMaryamPrice()*item.getQuantity());
                itemList.add(newItem);
                inventoryService.changeQuantity(inventory, inventory.getQuantity() - item.getQuantity());
            }
//            else {
//                throw new RuntimeException("there is no more product left to order");
//            }
        }
        itemList = (List<Item>) itemRepository.saveAll(itemList);
        order.setItems(itemList);
        return itemList;
    }
    public List<Item> byOwnerStatus(Integer status) {
        return itemRepository.findByOwnerAndStatus(userService.getCurrentUser().getId(),status);
    }
    public List<Item> byOwnerStatusAndTimeInterval(Integer status,Integer timeIndex,TimeValue timeValue) {
        switch (timeValue){
            case Day:return itemRepository.findByOwnerAndStatusAndDay(userService.getCurrentUser().getId(),status,timeIndex);
            case Week:return itemRepository.findByOwnerAndStatusAndWeek(userService.getCurrentUser().getId(),status,timeIndex);
            case Month:return itemRepository.findByOwnerAndStatusAndMonth(userService.getCurrentUser().getId(),status,timeIndex);
            case Year:return itemRepository.findByOwnerAndStatusAndYear(userService.getCurrentUser().getId(),status,timeIndex);
        }
        return null;
    }
    public List<Item> byUserStatus(Integer status) {
        return itemRepository.findByUserAndStatus(userService.getCurrentUser().getId(),status);
    }
    public Item setStatus(Long id,Item.Status status) throws Exception{
        try {
//            System.out.println(id);
            Item item = itemRepository.findById(id).get();
            item.setStatus(status);
            if(status.equals(Item.Status.Delivered)){
                System.out.println("status is delivered");
                sellerPropertiesService.sellProductHandle(userService.getCurrentUser(),item.getQuantity(),item.getPrice());
            }
            return item;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

}
