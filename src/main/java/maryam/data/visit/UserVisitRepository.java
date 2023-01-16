package maryam.data.visit;

import maryam.models.user.User;
import maryam.models.uservisit.UserVisits;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserVisitRepository extends CrudRepository<UserVisits,Long> {
    public Optional<UserVisits> findByUser(User user);

}
