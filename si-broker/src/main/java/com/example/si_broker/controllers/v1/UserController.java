package com.example.si_broker.controllers.v1;

import com.example.si_broker.api.v1.models.UserDTO;
import com.example.si_broker.services.UserService;
import com.example.si_broker.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping(Constants.USERS_BASE_URL)
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.addUser(user), HttpStatus.OK);
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH}, value = "/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody UserDTO user) {
        return new ResponseEntity<>(userService.updateUser(id, user), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Boolean> deleteUserByID(@PathVariable String id) {
        return new ResponseEntity<>(userService.deleteUserByID(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find")
    public ResponseEntity<UserDTO> getUserByID(@RequestParam(value = "id") String id) {
        return new ResponseEntity<>(userService.getUserByID(id), HttpStatus.OK);
    }
}
