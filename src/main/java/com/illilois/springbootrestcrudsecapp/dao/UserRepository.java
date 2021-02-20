package com.illilois.springbootrestcrudsecapp.dao;

import com.illilois.springbootrestcrudsecapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
}

