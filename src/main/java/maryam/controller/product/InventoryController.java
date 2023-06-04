package maryam.controller.product;

import lombok.RequiredArgsConstructor;
import maryam.dto.inventory.InventoryDTO;
import maryam.dto.inventory.InventoryIdAndQuantity;
//import maryam.dto.inventory.SellerItemDTO;
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

//    @GetMapping(path="/get/seller/shipping")
//    public List<SellerItemDTO> getSellerInventoryShipping(){
//        return inventoryService.getSellerItemsList(1);
//    }
//    @GetMapping(path="/get/seller/arrived")
//    public List<SellerItemDTO> getSellerInventoryArrived(){
//        return inventoryService.getSellerItemsList(2);
//    }
//    @GetMapping(path="/get/seller/delivered")
//    public List<SellerItemDTO> getSellerInventoryDelivered(){
//        return inventoryService.getSellerItemsList(3);
//    }
//    @GetMapping(path="/get/seller/all")
//    public List<SellerItemDTO> getSellerInventoryAll(){
//        return inventoryService.getSellerItemsListAll();
//    }
//    private SellerItemDTO convertToDto(Inventory inventory) {
//        System.out.println(1);
//        SellerItemDTO sellerItemDTO = modelMapper.map(inventory, SellerItemDTO.class);
//        System.out.println(2);
//        sellerItemDTO.setSellerArticle(inventory.getArticle().getSellerArticle());
//        System.out.println(3);
//        sellerItemDTO.setBarcode(inventory.getCargoBarcode().getBarcode());
//        System.out.println(4);
//        sellerItemDTO.setName(inventory.getArticle().getProduct().getName());
//        System.out.println(5);
//        if(inventory.getArticle().getPictures()!=null && inventory.getArticle().getPictures().size()!=0 && inventory.getArticle().getPictures().get(0)!=null ){
//            sellerItemDTO.setPicture(inventory.getArticle().getPictures().get(0).getName());
//        }
//       // if(inventory.getArticle().getPictures().size()!=0 || inventory.getArticle().getPictures()!=null)
//        // sellerItemDTO.setPicture(inventory.getArticle().getPictures().get(0).getName());
//        System.out.println(6);
//        sellerItemDTO.setBrand(inventory.getArticle().getProduct().getBrand());
//        System.out.println(7);
//        if(inventory.getInventorySize()!=null)
//        sellerItemDTO.setSize(inventory.getInventorySize().getSize());
//        System.out.println(8);
//        return sellerItemDTO;
//    }
}
