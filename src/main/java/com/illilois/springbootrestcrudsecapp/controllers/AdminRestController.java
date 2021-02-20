package com.illilois.springbootrestcrudsecapp.controllers;

import com.illilois.springbootrestcrudsecapp.entity.User;
import com.illilois.springbootrestcrudsecapp.entity.dto.RoleDto;
import com.illilois.springbootrestcrudsecapp.entity.dto.UserDto;
import com.illilois.springbootrestcrudsecapp.service.RoleService;
import com.illilois.springbootrestcrudsecapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/users/")
public class AdminRestController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    // Заполняет таблицу юзеров на вкладке Admin Panel - All Users
    @GetMapping
    public ResponseEntity<List<UserDto>> showAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Подтягивает на вкладку User данные юзера о самом себе
    @GetMapping("{id}")
    public ResponseEntity<UserDto> showUserInfo(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    //Создание юзера
    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody UserDto userDTO) {
        userService.saveUser(userService.dtoToEntity(userDTO));
        return ResponseEntity.ok().build();
    }

    //Изменить данные юзера
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDTO) {
        userService.saveUser(userService.dtoToEntity(userDTO));
        return ResponseEntity.ok().build();
    }

    //Удалить юзера
    @DeleteMapping("{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("current")
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal User currentUser){
        return ResponseEntity.ok(userService.entityToDto(currentUser));
    }


    @GetMapping("roles")
    public ResponseEntity<List<RoleDto>> getPossibleRoles() {
        return ResponseEntity.ok(roleService.getRoles().stream().map(role -> roleService.entityToDto(role)).collect(Collectors.toList()));
    }
}
