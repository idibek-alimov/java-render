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

}
