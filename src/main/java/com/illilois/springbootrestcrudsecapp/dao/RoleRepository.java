package com.illilois.springbootrestcrudsecapp.dao;

import com.illilois.springbootrestcrudsecapp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
}
