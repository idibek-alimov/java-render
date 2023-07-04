package maryam.service.user;

import lombok.RequiredArgsConstructor;
import maryam.data.role.RoleRepository;
import maryam.models.role.Role;
import maryam.models.user.User;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.HashSet;
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
//    public User setUserRole(User user){
//        //Set<Role> roles = user.getRoles().stream().collect(Collectors.toSet());
//        Set<Role> roles = new HashSet<>();
//        Role role = roleRepository.findByName("USER_ROLE");
//        if(role!=null){
//            roles.add(role);
//        }
//        else{
//            roles.add(roleRepository.save(new Role("USER_ROLE")));
//        }
//        user.setRoles(roles);
//        return user;
//    }
    public Role getOrCreateRole(String roleName){
        Role role = roleRepository.findByName(roleName);
        if(role!=null){
            return role;
        }
        else{
            return roleRepository.save(new Role(roleName));
        }
    }
    public User setRole(User user,String roleName){
        Collection<Role> roleCollection = user.getRoles();
        Set<Role> roles = new HashSet<>();
        if (roleCollection!=null){
            roles = roleCollection.stream().collect(Collectors.toSet());
        }
        Role role = getOrCreateRole(roleName);
        boolean contains = false;
        for (Role role1 : roles) {
            if (role.getName().equals(role1.getName())) {
                contains = true;
            }
        }
        if (!contains) {
            roles.add(role);
        }
        user.setRoles(roles);
        return user;
    }
    public User setUserRole(User user){
        return setRole(user,"ROLE_USER");
    }
    public User setSellerRole(User user){
        return setRole(user,"ROLE_SELLER");
    }
    public User setManagerRole(User user){
        return setRole(user,"ROLE_MANAGER");
    }
}
