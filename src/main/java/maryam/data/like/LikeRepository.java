package maryam.data.like;

import maryam.models.like.Like;
import maryam.models.product.Article;
import maryam.models.product.Product;
import maryam.models.user.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends CrudRepository<Like,Long> {
    Optional<Like> findByArticleAndUser(Article article, User user);
    List<Like> findByArticle(Article article);

    @Modifying
    @Query(value = "DELETE FROM liked_products WHERE article_id=?1",nativeQuery = true)
    void deleteByArticleId(Long id);

}
