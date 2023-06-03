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
    @Override
    public Inventory addInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }
    public Inventory addInventory(Inventory inventory,Article article){
        if (inventory.getInventorySize()!=null){
            inventory.setInventorySize(inventorySizeService.addInventorySize(inventory,inventory.getInventorySize().getSize()));
        }
        inventory = inventoryRepository.save(inventory);
        inventory.setArticle(article);
        return inventory;
    }
    @Override @Transactional
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
        System.out.println("creating inventory");

        List<Inventory> inventoryList = new ArrayList<>();
        Inventory inventory;
        Integer counter = 0 ;
        for(Inventory itemInventory:inventories){
            //System.out.println(itemInventory.getOriginalPrice());
            inventory = inventoryRepository.save(new Inventory(article,itemInventory.getOriginalPrice(),itemInventory.getQuantity()));
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
    public List<Inventory> updateInventory(List<InventoryDTO> inventories, Article article){
        List<Inventory> inventoryList = new ArrayList<>();
        Inventory inventory;
        for(InventoryDTO inventoryDTO: inventories){
            inventory = inventoryRepository.findById(inventoryDTO.getId()).get();
            //inventory = inventoryRepository.save(new Inventory(article,inventoryDTO.getPrice(),inventoryDTO.getQuantity()));
//            System.out.println("created Inventory");
//            System.out.println(inventory.getPrice());
            if(!inventoryDTO.getSize().equals(inventory.getInventorySize().getSize())){
                inventory.getInventorySize().setSize(inventoryDTO.getSize());
            }
            inventoryList.add(inventoryRepository.save(inventory));

        }
        return inventoryList;
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
                if (inventoryPlaceholder.getOriginalPrice()!=inventory.getOriginalPrice())
                    inventoryPlaceholder.setOriginalPrice(inventory.getOriginalPrice());

                //inventoryPlaceholder.setQuantity(inventory.getQuantity());
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
//        for(Inventory inventory:article.getInventory()){
//            if (!inventoryList.contains(inventory)){
//                inventory.setAvailable(false);
//            }
//        }
    }

    @Override
    public void removeInventory(Inventory inventory) {
        inventoryRepository.delete(inventory);
    }

    @Override
    public void removeInventories(List<Inventory> inventories) {
        inventoryRepository.deleteAll(inventories);
    }

    public Inventory addInventory(InventoryIdAndQuantity inventoryItem){
        Inventory inventory = getById(inventoryItem.getId());
        if(inventory!=null){
            inventory.setQuantity(inventory.getQuantity()+inventoryItem.getQuantity());
            //sellerPropertiesService.addProductInStorage(userService.getCurrentUser(),inventoryItem.getQuantity());
            inventory.setInStock(true);
        }
        return inventory;
    }
//    public Inventory addInventoryToStorage(Long id,Integer quantity){
//        Inventory inventory = getById(id);
//        if (inventory != null) {
//            inv
//        }
//    }
    public List<SellerItemDTO> getSellerItemsList(Integer status){
        //userService.getCurrentUser().getId();
        return inventoryRepository.getSellerItem(userService.getCurrentUser().getId(),status);
    }
    public List<SellerItemDTO> getSellerItemsListAll(){
        //userService.getCurrentUser().getId();
        return inventoryRepository.getSellerItemAll(userService.getCurrentUser().getId());
    }
    public CustomerInventoryDto inventoryToCustomerDto(Inventory inventory,Integer discount){
        //System.out.println("inventory was here");
        CustomerInventoryDto inventoryDto = new CustomerInventoryDto();
        inventoryDto.setId(inventory.getId());
        inventoryDto.setOriginalPrice(inventory.getMaryamPrice());
        if(discount!=null) {
            Double discountPrice = Math.floor(inventory.getMaryamPrice() * (100 - discount)/100);
            inventoryDto.setDiscountPrice(discountPrice);
        }
        inventoryDto.setQuantity(inventory.getQuantity());
        inventoryDto.setInStock(inventory.getInStock());
        if(inventory.getInventorySize()!=null){
            inventoryDto.setSize(inventory.getInventorySize().getSize());
        }
        inventoryDto.setBarcode(inventory.getCargoBarcode().getBarcode());
        return inventoryDto;
    }
}
