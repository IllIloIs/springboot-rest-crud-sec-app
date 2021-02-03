package com.illilois.springbootrestcrudsecapp.controllers;

import com.illilois.springbootrestcrudsecapp.entity.User;
import com.illilois.springbootrestcrudsecapp.service.RoleService;
import com.illilois.springbootrestcrudsecapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String getAllUsers(Model model) {
        model.addAttribute("usersToShow", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/user/new")
    public String newUser(Model model) {
        model.addAttribute("newUser", new User());
        model.addAttribute("possibleRoles", roleService.getRoles());
        model.addAttribute("rolesSelected", new Long[]{});
        return "new_user";
    }

    @PostMapping("")
    public String createUser(@RequestParam("rolesSelected") Long[] roleIds, @ModelAttribute("newUser") User user) {
        for(Long idGiven: roleIds){
            user.setRole(roleService.getRoleById(idGiven));
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/user/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("possibleRoles", roleService.getRoles());
        model.addAttribute("rolesSelected", userService.getUserById(id).getRoles());
        model.addAttribute("userToUpdate", userService.getUserById(id));
        return "update_user";
    }

    @PatchMapping("/user/{id}")
    public String updateUser(@ModelAttribute("userToUpdate") User user, @PathVariable("id") long id,  @RequestParam("rolesSelected") Long[] roleIds) {
        for(Long idGiven: roleIds){
            user.setRole(roleService.getRoleById(idGiven));
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}
