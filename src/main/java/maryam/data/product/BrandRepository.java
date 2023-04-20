//package maryam.data.product;
//
//import maryam.models.product.Brand;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface BrandRepository extends CrudRepository<Brand,Long> {
//    Optional<Brand> findByName(String name);
//
//    @Query(value = "SELECT * FROM brand  WHERE ?1 % ANY(STRING_TO_ARRAY(brand.name,' '))",nativeQuery = true)
//    List<Brand> findBySimilarName(String name);
//
//    @Modifying
//    @Query(value = "DELETE FROM brand_product WHERE product_id=?1",nativeQuery = true)
//    void deleteByProductId(Long id);
//}
