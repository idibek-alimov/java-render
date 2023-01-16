package maryam.data.user;

import maryam.models.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    public User findByUsername(String username);
}
