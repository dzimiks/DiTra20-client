package com.example.si_broker.services;

import com.example.si_broker.api.v1.models.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO addUser(UserDTO user);
    UserDTO updateUser(String id, UserDTO user);
    boolean deleteUserByID(String id);
    UserDTO getUserByID(String id);
}
