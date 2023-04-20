package maryam.service.inventory;

import lombok.RequiredArgsConstructor;
import maryam.data.inventory.InventorySizeRepository;
import maryam.models.inventory.Inventory;
import maryam.models.inventory.InventorySize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class InventorySizeService {

    private final InventorySizeRepository inventorySizeRepository;

    public InventorySize addInventorySize(Inventory inventory,String size){
        if(size!=null) {
            Optional<InventorySize> optionalInventorySize = inventorySizeRepository.findBySize(size);
            if (optionalInventorySize.isPresent()) {
                inventory.setInventorySize(optionalInventorySize.get());
                return optionalInventorySize.get();
            } else {
                InventorySize inventorySize = inventorySizeRepository.save(new InventorySize(size));
                inventory.setInventorySize(inventorySize);
                return inventorySize;
            }
        }
        else {return null;}
    }
}
