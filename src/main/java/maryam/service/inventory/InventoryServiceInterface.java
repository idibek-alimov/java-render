package maryam.service.inventory;

import maryam.dto.inventory.InventoryDTO;
import maryam.models.inventory.Inventory;
import maryam.models.product.Article;
import maryam.models.product.Product;

import java.util.List;

public interface InventoryServiceInterface {
    public Inventory addInventory(Inventory inventory);
    public List<Inventory> addInventories(List<InventoryDTO> inventories, Article article);
    public void removeInventory(Inventory inventory);
    public void removeInventories(List<Inventory> inventories);
}
