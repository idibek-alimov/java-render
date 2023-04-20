package maryam.data.product;


import maryam.models.product.ProductGender;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductGenderRepository extends CrudRepository<ProductGender,Long> {
    Optional<ProductGender> findByName(String name);
}
