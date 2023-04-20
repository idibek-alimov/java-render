package maryam.data.visit;

import maryam.models.product.Article;
import maryam.models.product.Product;
import maryam.models.user.User;
import maryam.models.uservisit.Visit;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

public interface VisitRepository extends CrudRepository<Visit,Long>{//,CustomVisitRepository {

    public List<Visit> findAllByUserOrderByCreatedAt(User user);
    public Optional<Visit> findByArticleAndUser(Article article, User user);

    @Modifying
    @Query(value = "DELETE FROM visit WHERE article_id=?1",nativeQuery = true)
    void deleteByArticleId(Long id);
    //public List<Visit> getVisitsGroupByUserAndOrderByCreatedAt(User user);
}
