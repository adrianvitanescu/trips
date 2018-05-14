package com.sci.myTrips.controller;

import com.sci.myTrips.entity.User;
import com.sci.myTrips.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/user")
public class UserController {

    private UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        if (userServiceImpl.fetchUser(id).isPresent()) {
            return ResponseEntity.ok(userServiceImpl.fetchUser(id).get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("User with id %d not found", id));
    }

    @GetMapping("/all")
    public List<User> getUsers() {
        return userServiceImpl.fetchUsers();
    }

    @GetMapping
    public ResponseEntity<?> getUserByName(@RequestParam String userName) {
        Optional<User> userOptional = userServiceImpl.fetchUserByName(userName);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("User with name %s not found", userName));

    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        return userServiceImpl.updateUser(user);
    }
}
