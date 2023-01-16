package maryam.data.inventory;

import maryam.models.inventory.Inventory;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<Inventory,Long> {
}
