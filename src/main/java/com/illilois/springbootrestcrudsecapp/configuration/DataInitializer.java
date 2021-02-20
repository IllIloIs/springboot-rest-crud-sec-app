package com.illilois.springbootrestcrudsecapp.configuration;

import com.illilois.springbootrestcrudsecapp.entity.Role;
import com.illilois.springbootrestcrudsecapp.entity.User;
import com.illilois.springbootrestcrudsecapp.service.RoleService;
import com.illilois.springbootrestcrudsecapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;


@Component
public class DataInitializer {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DataInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        initRoles();
        initUsers();
    }

    private void initRoles() {
        Role role = new Role();
        role.setName("ADMIN");
        roleService.saveRole(role);
        Role role2 = new Role();
        role2.setName("USER");
        roleService.saveRole(role2);
    }

    private void initUsers() {
        User user = new User();
        user.setUsername("admin@mail.ru");
        user.setPassword("a");
        user.setFirstName("Admin");
        user.setLastName("Adminyan");
        user.setAge(30);
        user.setRoles(Set.of(roleService.getRoleByName("ADMIN"),roleService.getRoleByName("USER")));
        userService.saveUser(user);

        User user2 = new User();
        user2.setUsername("user@mail.ru");
        user2.setPassword("u");
        user2.setFirstName("Userbay");
        user2.setLastName("Userbaev");
        user2.setAge(21);
        user2.setRoles(Set.of(roleService.getRoleByName("USER")));
        userService.saveUser(user2);

    }

}