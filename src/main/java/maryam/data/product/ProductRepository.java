package maryam.data.product;

import maryam.models.product.Product;
import maryam.models.tag.Tag;
import maryam.models.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product,Long>{

    @Query(value = "SELECT * FROM product product WHERE ?1 % ANY(STRING_TO_ARRAY(product.name,' ')) or ?1 % ANY(STRING_TO_ARRAY(product.description,' '))", nativeQuery = true)
    List<Product> findByNameSimilar(String name,Pageable pageable);

    List<Product> getByUser(User user,Pageable pageable);

    @Query(value = "SELECT * FROM product WHERE id IN " +
            "(SELECT product_id FROM tag_product WHERE tag_id in (?1) AND " +
            "product_id NOT IN (?2)) ORDER BY created_at DESC",nativeQuery = true)
    List<Product> getByTags(Set<Long> tagSet,Set<Long> productSet);

    @Query(value = "SELECT * FROM product product" +
            " WHERE ?1 % ANY(STRING_TO_ARRAY(product.name,' '))" +
            " or ?1 % ANY(STRING_TO_ARRAY(product.description,' '))", nativeQuery = true)
    List<Product> findProductByname(String name,Pageable pageable);

    @Query(value = "SELECT * FROM product WHERE id IN " +
            "(SELECT product_id FROM tag_product WHERE tag_id in (?1))",nativeQuery = true)
   List<Product> getBySimilarTags(Set<Long> tagIds,Pageable pageable);

    @Query(value="SELECT * FROM product WHERE id IN " +
            "(SELECT product_id" +
            " FROM visit GROUP BY product_id ORDER BY COUNT(product_id) DESC LIMIT (?1) )",nativeQuery = true)
    List<Product> getMostVisited(Integer n,Pageable pageable);
    @Query(value = "SELECT * FROM product WHERE id IN " +
            "(SELECT product_id FROM visit WHERE user_id = (?1)) ORDER BY created_at DESC",nativeQuery = true)
    List<Product> getByResentVisit(Long id,Pageable pageable);



}
