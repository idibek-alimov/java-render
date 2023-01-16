package maryam.service.inventory;

import lombok.RequiredArgsConstructor;
import maryam.data.inventory.InventoryRepository;
import maryam.models.inventory.Inventory;
import maryam.models.product.Article;
import maryam.models.product.Product;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor
public class InventoryService implements InventoryServiceInterface{
    private final InventoryRepository inventoryRepository;

    @Override
    public Inventory addInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override @Transactional
    public List<Inventory> addInventories(List<Inventory> inventories, Article article) {
        List<Inventory> inventoryList = new ArrayList<>();
        for(Inventory size: inventories){
            size.setArticle(article);
            inventoryList.add(inventoryRepository.save(size));
        }
        return inventoryList;
    }

    @Override
    public void removeInventory(Inventory inventory) {
        inventoryRepository.delete(inventory);
    }

    @Override
    public void removeInventories(List<Inventory> inventories) {
        inventoryRepository.deleteAll(inventories);
    }
}
