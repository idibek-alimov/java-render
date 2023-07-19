package maryam.controller.product;

import lombok.RequiredArgsConstructor;
import maryam.dto.inventory.InventoryDTO;
import maryam.dto.inventory.InventoryIdAndQuantity;
//import maryam.dto.inventory.SellerItemDTO;
import maryam.dto.inventory.InventoryQuantityDTO;
import maryam.dto.inventory.SellerItemDTO;
import maryam.models.inventory.Inventory;
import maryam.service.inventory.InventoryService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    @GetMapping(path="/seller/{id}")
    public Inventory getInventory(@PathVariable("id") Long id){
        return inventoryService.getById(id);
    }
    @GetMapping(path = "/seller")
    public List<Inventory> getInventories(){
        return null;
    }
    @GetMapping(path = "/seller/article/{id}")
    public List<Inventory> getInventoriesInArticle(@PathVariable("id")Long id){
        return inventoryService.getInventoryInArticle(id);
    }

    @GetMapping(path = "/allow/quantity/change/{id}/{quantity}")
    public Inventory changeQuantity(@PathVariable("id")Long id,
                                    @PathVariable("quantity") Integer quantity){
        System.out.println("Inside the change quantity");
        return inventoryService.changeQuantity(id,quantity);
    }
    @GetMapping(path = "/seller/set/{id}/{option}/{value}")
    public Inventory setQuantity(@PathVariable("id")Long id,
                                 @PathVariable("option")String option,
                                 @PathVariable("value")Boolean value){
        return inventoryService.setOption(id,option,value);
    }

}
