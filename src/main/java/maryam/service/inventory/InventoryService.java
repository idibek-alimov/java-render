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
    private final InventorySizeService inventorySizeService;
    private final UserService userService;
    public Inventory addInventory(Inventory inventory,Article article){
        if (inventory.getInventorySize()!=null){
            inventory.setInventorySize(inventorySizeService.addInventorySize(inventory,inventory.getInventorySize().getSize()));
        }
        inventory = inventoryRepository.save(inventory);
        inventory.setArticle(article);
        return inventory;
    }
    @Transactional
    public List<Inventory> addInventories(List<InventoryDTO> inventories, Article article) {
        List<Inventory> inventoryList = new ArrayList<>();
        Inventory inventory;
        Integer counter = 0;
        for(InventoryDTO inventoryDTO: inventories){
            inventory = inventoryRepository.save(new Inventory(article,inventoryDTO.getPrice(),inventoryDTO.getQuantity()));
            if(inventoryDTO.getSize()!=null) {
                inventorySizeService.addInventorySize(inventory, inventoryDTO.getSize());
            }
            cargoBarcodeService.createBarcode(inventory,counter);
            inventoryList.add(inventoryRepository.save(inventory));
            counter++;
        }
        return inventoryList;
    }
    public List<Inventory> createInventories(List<Inventory> inventories,Article article){
        List<Inventory> inventoryList = new ArrayList<>();
        Inventory inventory;
        Integer counter = 0 ;
        for(Inventory itemInventory:inventories){
            inventory = inventoryRepository.save(new Inventory(article,itemInventory.getPrice(),itemInventory.getQuantity()));
            if(itemInventory.getInventorySize()!=null){
                inventorySizeService.addInventorySize(inventory,itemInventory.getInventorySize().getSize());
            }
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
    public void updateInventories(List<Inventory> inventories,Article article){
        Inventory inventoryPlaceholder;
        List<Inventory> inventoryList = new ArrayList<>();
        for(Inventory inventory:inventories){
            if (inventory.getId()!=null){
                inventoryPlaceholder = inventoryRepository.findById(inventory.getId()).get();
                if (inventoryPlaceholder.getPrice()!=inventory.getPrice())
                    inventoryPlaceholder.setPrice(inventory.getPrice());
                if (inventoryPlaceholder.getInventorySize().getSize()!=inventory.getInventorySize().getSize())
                    inventorySizeService.addInventorySize(inventoryPlaceholder,inventory.getInventorySize().getSize());
                inventoryList.add(inventoryPlaceholder);
            }
            else {
                Inventory newInventory = addInventory(inventory,article);
                article.getInventory().add(newInventory);
                inventoryList.add(newInventory);
            }
        }
    }
    public CustomerInventoryDto inventoryToCustomerDto(Inventory inventory,Integer discount){
        CustomerInventoryDto inventoryDto = new CustomerInventoryDto().builder()
                .id(inventory.getId())
                .originalPrice(inventory.getPrice())
                .quantity(inventory.getQuantity())
                .inStock(inventory.getInStock())
                .barcode(inventory.getCargoBarcode().getBarcode())
                .build();
        if(discount!=null) {
            Double discountPrice = Math.floor(inventory.getPrice() * (100 - discount)/100);
            inventoryDto.setDiscountPrice(discountPrice);
        }

        if(inventory.getInventorySize()!=null){
            inventoryDto.setSize(inventory.getInventorySize().getSize());
        }
        return inventoryDto;
    }
}
