package com.example.si_broker.api.v1.models;

import lombok.Data;

import javax.management.relation.Role;

@Data
public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Role role;
}
