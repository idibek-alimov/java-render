package maryam.service.inventory;

import lombok.RequiredArgsConstructor;
import maryam.data.inventory.CargoBarcodeRepository;
import maryam.models.inventory.CargoBarcode;
import maryam.models.inventory.Inventory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CargoBarcodeService {
    private final CargoBarcodeRepository cargoBarcodeRepository;

    public CargoBarcode createBarcode(Inventory inventory, Integer i){
        return cargoBarcodeRepository.save(new CargoBarcode(System.currentTimeMillis()+i,inventory));
    }
}
