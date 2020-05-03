package com.example.si_broker.api.v1.models;

import com.example.si_broker.domain.Role;
import lombok.Data;
import java.util.List;

@Data
public class UserDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private List<Role> roles;
}
