package com.illilois.springbootrestcrudsecapp.dao;

import com.illilois.springbootrestcrudsecapp.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findUserByUsername(String username);
}

