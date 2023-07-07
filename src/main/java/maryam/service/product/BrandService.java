package maryam.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import maryam.data.product.BrandRepository;
import maryam.models.product.Brand;
import org.springframework.stereotype.Service;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandService {
    private final BrandRepository brandRepository;
    private Brand createBrand(String name){
        return brandRepository.save(new Brand().builder().name(name).build());
    }
    public Brand findBrand(String name){
        return brandRepository.findByName(name).orElseGet(() -> createBrand(name));
    }

}
