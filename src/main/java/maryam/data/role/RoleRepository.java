package maryam.data.role;

import maryam.models.role.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role,Long> {

    Role findByName(String name);
}
