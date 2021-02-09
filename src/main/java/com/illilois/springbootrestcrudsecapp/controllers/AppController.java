package com.illilois.springbootrestcrudsecapp.controllers;

import com.illilois.springbootrestcrudsecapp.entity.User;
import com.illilois.springbootrestcrudsecapp.service.RoleService;
import com.illilois.springbootrestcrudsecapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AppController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    public String showRoleAdmin(Model model){
        model.addAttribute("usersToShow", userService.getAllUsers());
        model.addAttribute("currentUser", (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("possibleRoles", roleService.getRoles());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @PostMapping("/admin")
    public String createUser(@RequestParam("rolesSelected") Long[] roleIds, @ModelAttribute("newUser") User user) {
        for(Long idGiven: roleIds){
            user.setRole(roleService.getRoleById(idGiven));
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }
    @PatchMapping("/admin/user/{id}")
    public String updateUser(@ModelAttribute("userToUpdate") User user, @PathVariable("id") long id,  @RequestParam("rolesSelected") Long[] roleIds) {
        for(Long idGiven: roleIds){
            user.setRole(roleService.getRoleById(idGiven));
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/user/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String showRoleUser(Model model) {
        model.addAttribute("currentUser", (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "user";
    }

}
