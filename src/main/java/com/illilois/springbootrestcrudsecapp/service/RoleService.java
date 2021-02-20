package com.illilois.springbootrestcrudsecapp.service;

import com.illilois.springbootrestcrudsecapp.entity.Role;
import com.illilois.springbootrestcrudsecapp.entity.User;
import com.illilois.springbootrestcrudsecapp.entity.dto.RoleDto;
import com.illilois.springbootrestcrudsecapp.entity.dto.UserDto;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String name);

    void saveRole(Role role);

    List<Role> getRoles();

    Role getRoleById(long id);

    public RoleDto entityToDto(Role role);
    public Role dtoToEntity(RoleDto roleDto);

}
