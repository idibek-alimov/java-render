package maryam.data.product;

import maryam.models.product.Discount;
import org.springframework.data.repository.CrudRepository;

public interface DiscountRepository extends CrudRepository<Discount,Long> {
}
