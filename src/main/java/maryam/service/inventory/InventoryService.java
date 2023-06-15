package maryam.service.inventory;

import lombok.RequiredArgsConstructor;
import maryam.data.inventory.InventoryRepository;
import maryam.dto.inventory.CustomerInventoryDto;
import maryam.dto.inventory.InventoryDTO;
import maryam.dto.inventory.InventoryIdAndQuantity;
//import maryam.dto.inventory.SellerItemDTO;
import maryam.dto.inventory.SellerItemDTO;
import maryam.models.inventory.Inventory;
import maryam.models.product.Article;
import maryam.service.user.SellerPropertiesService;
import maryam.service.user.UserService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional
public class InventoryService implements InventoryServiceInterface{
    private final InventoryRepository inventoryRepository;
    private final SellerPropertiesService sellerPropertiesService;
    private final CargoBarcodeService cargoBarcodeService;
    private final UserService userService;
    public Inventory addInventory(Inventory inventory,Article article){
//        if (inventory.getInventorySize()!=null){
//            inventory.setInventorySize(inventorySizeService.addInventorySize(inventory,inventory.getInventorySize().getSize()));
//        }
        inventory.setAvailable(true);
        inventory = inventoryRepository.save(inventory);
        inventory.setArticle(article);
        return inventory;
    }
    public List<Inventory> createInventories(List<Inventory> inventories,Article article){
        List<Inventory> inventoryList = new ArrayList<>();
        Inventory inventory;
        Integer counter = 0 ;
        for(Inventory itemInventory:inventories){
            inventory = inventoryRepository.save(new Inventory()
                    .builder()
                            .article(article)
                            .size(itemInventory.getSize())
                            .quantity(itemInventory.getQuantity())
                            .price(itemInventory.getPrice())
                            .inStock(true)
                            .available(true)
                            .build());

            cargoBarcodeService.createBarcode(inventory,counter);
            inventoryList.add(inventoryRepository.save(inventory));
            counter++;
        }
        return inventoryList;
    }
    public Inventory getById(Long id){
        Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
        if(optionalInventory.isPresent()){
            return optionalInventory.get();
        }
        return null;
    }
    public Inventory changeQuantity(Inventory inventory,Integer quantity){
        if (quantity==0){
            inventory.setInStock(false);
        }
        inventory.setQuantity(quantity);
        return inventory;
    }
    public void deleteInventoriesFromArticle(List<Long> idList,Article article){
        for(Inventory inventory: article.getInventory()){
            if(!idList.contains(inventory.getId())){
                System.out.println("deleting inventory with the id of "+inventory.getId());
                inventory.setAvailable(Boolean.FALSE);
                //System.out.println(inventoryRepository.findById(inventory.getId()));
            }
        }
    }
    public void updateInventories(List<Inventory> inventories,Article article){
        Inventory inventoryPlaceholder;
        List<Inventory> inventoryList = new ArrayList<>();
        List<Long> idList = new ArrayList<>();
        for(Inventory inventory:inventories){
            System.out.println(inventory);
            if (inventory.getId()!=null){
                inventoryPlaceholder = inventoryRepository.findById(inventory.getId()).get();
                inventoryPlaceholder.setPrice(inventory.getPrice());
                inventoryPlaceholder.setQuantity(inventory.getQuantity());
                inventoryPlaceholder.setSize(inventory.getSize());
                idList.add(inventoryPlaceholder.getId());
                inventoryList.add(inventoryPlaceholder);
            }
            else {
                Inventory newInventory = addInventory(inventory,article);
                article.getInventory().add(newInventory);
                idList.add(newInventory.getId());
                inventoryList.add(newInventory);
            }
        }
//        System.out.println("after sorting inventories");
//        System.out.println(idList);
        deleteInventoriesFromArticle(idList,article);
    }
}
