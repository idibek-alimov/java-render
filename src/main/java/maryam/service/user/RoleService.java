package maryam.service.user;

import lombok.RequiredArgsConstructor;
import maryam.data.role.RoleRepository;
import maryam.models.role.Role;
import maryam.models.user.User;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {
    private final RoleRepository roleRepository;

    public Role createRole(String name){
        return roleRepository.save(new Role(name));
    }
    public User setUserRole(User user){
        System.out.println("before");
        Set<Role> roles = user.getRoles().stream().collect(Collectors.toSet());
        System.out.println("after");
        Role role = roleRepository.findByName("USER_ROLE");
        if(role!=null){
            roles.add(role);
        }
        else{
            roles.add(roleRepository.save(new Role("USER_ROLE")));
        }
        user.setRoles(roles);
        return user;
    }
    public User setSellerRole(User user){
        Set<Role> roles = user.getRoles().stream().collect(Collectors.toSet());
        Role role = roleRepository.findByName("SELLER_ROLE");
        if(role!=null){
            roles.add(role);
        }
        else{
            roles.add(roleRepository.save(new Role("SELLER_ROLE")));
        }
        user.setRoles(roles);
        return user;
    }
}
