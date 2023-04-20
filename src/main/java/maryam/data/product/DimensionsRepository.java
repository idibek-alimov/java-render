package maryam.data.product;

import maryam.models.product.Dimensions;
import maryam.models.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DimensionsRepository extends JpaRepository<Dimensions,Long> {

    Optional<Dimensions> getByProduct(Product product);
}
