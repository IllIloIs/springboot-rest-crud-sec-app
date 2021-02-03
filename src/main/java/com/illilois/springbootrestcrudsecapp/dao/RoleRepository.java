package com.illilois.springbootrestcrudsecapp.dao;

import com.illilois.springbootrestcrudsecapp.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findRoleByName(String name);
}
