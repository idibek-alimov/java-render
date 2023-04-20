package maryam.service.user;

import lombok.RequiredArgsConstructor;
import maryam.data.role.RoleRepository;
import maryam.models.role.Role;
import maryam.models.user.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;

    public Role createRole(String name){
        return roleRepository.save(new Role(name));
    }
    public User setUserRole(User user){
        Set<Role> roles = (Set<Role>) user.getRoles();
        roles.add(roleRepository.findByName("USER_ROLE"));
        user.setRoles(roles);
        return user;
    }
    public User setSellerRole(User user){
        Set<Role> roles = (Set<Role>) user.getRoles();
        roles.add(roleRepository.findByName("SELLER_ROLE"));
        user.setRoles(roles);
        return user;
    }
}
