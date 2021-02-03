package com.illilois.springbootrestcrudsecapp.service;

import com.illilois.springbootrestcrudsecapp.entity.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByName(String name);

    void saveRole(Role role);

    List<Role> getRoles();

    Role getRoleById(long id);
}
