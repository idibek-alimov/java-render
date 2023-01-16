package maryam.data.visit;

import maryam.models.product.Product;
import maryam.models.user.User;
import maryam.models.uservisit.Visit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface VisitRepository extends CrudRepository<Visit,Long>{//,CustomVisitRepository {

    public List<Visit> findAllByUserOrderByCreatedAt(User user);
    public Optional<Visit> findByProductAndUser(Product product, User user);
    //public List<Visit> getVisitsGroupByUserAndOrderByCreatedAt(User user);
}
