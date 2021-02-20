package com.illilois.springbootrestcrudsecapp.controllers;

import com.illilois.springbootrestcrudsecapp.entity.User;
import com.illilois.springbootrestcrudsecapp.service.RoleService;
import com.illilois.springbootrestcrudsecapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SimpleController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public SimpleController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping
    public String mainPage() {
        return "index";
    }
}
