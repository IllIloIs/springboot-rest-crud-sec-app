package com.illilois.springbootrestcrudsecapp.entity.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Integer age;
    private String firstName;
    private String lastName;
    private List<RoleDto> roles;

    public UserDto(Long id, String username, String password, Integer age, String firstName, String lastName, Set<RoleDto> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles.stream().collect(Collectors.toList());
    }
}
