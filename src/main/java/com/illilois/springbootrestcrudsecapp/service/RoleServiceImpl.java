package com.illilois.springbootrestcrudsecapp.service;

import com.illilois.springbootrestcrudsecapp.dao.RoleRepository;
import com.illilois.springbootrestcrudsecapp.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public List<Role> getRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public Role getRoleById(long id) {
        Optional<Role> roleFound = roleRepository.findById(id);
        if (roleFound.isPresent()) {
            return roleFound.get();
        } else {
            return null;
        }
    }
}
