package com.illilois.springbootrestcrudsecapp.controllers;

import com.illilois.springbootrestcrudsecapp.entity.User;
import com.illilois.springbootrestcrudsecapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public String getOneUser(@PathVariable("id") long id, Model model) {
        User currentUser = (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        if (currentUser.getId() != id && currentUser.getRoles().stream().noneMatch(role -> role.getName().contains("ADMIN"))) {
            return "redirect:/user/" + currentUser.getId();
        }
        model.addAttribute("userToShow", userService.getUserById(id));
        return "user";
    }
}
