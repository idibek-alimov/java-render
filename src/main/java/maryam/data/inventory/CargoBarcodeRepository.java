package maryam.data.inventory;

import maryam.models.inventory.CargoBarcode;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CargoBarcodeRepository extends CrudRepository<CargoBarcode,Long> {
    Optional<CargoBarcode> findByBarcode(Long barcode);
}
