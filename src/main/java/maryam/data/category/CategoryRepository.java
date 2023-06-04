package maryam.data.category;

import maryam.models.category.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Long> {
    Optional<Category> findByName(String name);
    @Query(value = "SELECT * FROM category  WHERE ?1 % ANY(STRING_TO_ARRAY(category.name,' ')) or" +
            " ?1 % ANY(STRING_TO_ARRAY(category.nameru,' ')) or" +
            " ?1 % ANY(STRING_TO_ARRAY(category.nametj,' '))",nativeQuery = true)
    List<Category> findBySimilarName(String name);

//    @Query(value="SELECT id,name,category_id FROM (SELECT category.id as id,category.name as name,category.category_id as category_id FROM category " +
//            "INNER JOIN product ON category.id = product.category_id GROUP BY category.id ORDER BY COUNT(product.category_id) DESC" +
//            ") as category LIMIT ?1",nativeQuery = true)
    @Query(value = "SELECT * FROM category",nativeQuery = true)
    List<Category> findMostPopular(Integer limit);
}
