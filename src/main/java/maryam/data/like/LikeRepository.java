package maryam.data.like;

import maryam.models.like.Like;
import maryam.models.product.Article;
import maryam.models.product.Product;
import maryam.models.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LikeRepository extends CrudRepository<Like,Long> {
    Optional<Like> findByArticleAndUser(Article article, User user);

}
