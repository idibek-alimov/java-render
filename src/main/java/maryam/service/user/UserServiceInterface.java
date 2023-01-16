package maryam.service.user;

import maryam.models.role.Role;
import maryam.models.user.User;

import java.util.List;

public interface UserServiceInterface {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String username,String name);
    User getUser(String username);
    List<User> getUsers();
}
