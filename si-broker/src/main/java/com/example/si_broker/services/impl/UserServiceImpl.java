package com.example.si_broker.services.impl;

import com.example.si_broker.api.v1.mappers.UserMapper;
import com.example.si_broker.api.v1.models.UserDTO;
import com.example.si_broker.domain.User;
import com.example.si_broker.repositories.UserRepository;
import com.example.si_broker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO addUser(UserDTO userDTO) {
        return saveAndReturnDTO(userMapper.userDTOtoUser(userDTO));
    }

    @Override
    public UserDTO updateUser(String id, UserDTO userDTO) {
        User userToSave = userMapper.userDTOtoUser(userDTO);
        userToSave.setId(id);
        return saveAndReturnDTO(userToSave);
    }

    @Override
    public Boolean deleteUserByID(String id) {
        Optional<User> userToDelete = userRepository.findById(id);

        if (userToDelete.isEmpty()) {
            return false;
        }

        userRepository.deleteById(id);
        return true;
    }

    @Override
    public UserDTO getUserByID(String id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            return null;
        }

        return userMapper.userToUserDTO(user.get());
    }

    private UserDTO saveAndReturnDTO(User user) {
        userRepository.save(user);
        return userMapper.userToUserDTO(user);
    }
}
