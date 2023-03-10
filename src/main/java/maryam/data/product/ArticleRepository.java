package maryam.data.product;

import maryam.models.product.Article;
import maryam.models.product.Product;
import maryam.models.tag.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    public List<Article> findByProductId(Long id);
    @Query(value = "SELECT * FROM article WHERE product_id IN " +
            "(SELECT product_id FROM tag_product WHERE tag_id in (?1)) AND id != (?2)",nativeQuery = true)
    public Page<Article> findSimilarArticles(List<Tag> tags,Long id, Pageable pageable);
    @Query(value = "SELECT * FROM article WHERE product_id IN (SELECT id" +
            " FROM product product WHERE ?1 % ANY(STRING_TO_ARRAY(product.name,' ')) or" +
            " ?1 % ANY(STRING_TO_ARRAY(product.description,' ')))", nativeQuery = true)
    List<Article> findBySimilarName(String name, Pageable pageable);

    @Query(value = "SELECT * FROM article WHERE product_id IN " +
            "(SELECT id FROM product WHERE user_id=?1)", nativeQuery = true)
    List<Article> getByUser(Long id);
    @Query(value = "SELECT * FROM article WHERE product_id IN " +
            "(SELECT id FROM product WHERE name=?1)", nativeQuery = true)
    List<Article> getByName(String name,Pageable pageable);
    @Query(value="SELECT * FROM article WHERE id IN" +
            "(SELECT article_id FROM liked_products WHERE user_id=?1)",nativeQuery = true)
    List<Article> getLikedArticles(Long id);

    @Query(value="SELECT * FROM article WHERE id IN" +
            "(SELECT article_id FROM visit GROUP BY article_id ORDER BY COUNT(article_id) DESC LIMIT ?1)",nativeQuery = true)
    Set<Article> getMostVisitedArticles(Integer limit);

}
