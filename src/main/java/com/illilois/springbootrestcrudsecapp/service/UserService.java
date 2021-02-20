package com.illilois.springbootrestcrudsecapp.service;

import com.illilois.springbootrestcrudsecapp.entity.User;
import com.illilois.springbootrestcrudsecapp.entity.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    void deleteUser(Long id);

    void saveUser(User user);

    public User dtoToEntity(UserDto userDTO);

    public UserDto entityToDto(User user);

}