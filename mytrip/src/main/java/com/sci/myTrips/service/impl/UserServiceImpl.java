package com.sci.myTrips.service.impl;

import com.sci.myTrips.entity.User;
import com.sci.myTrips.repo.UserRepository;
import com.sci.myTrips.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<User> fetchUser(Long id) {
        return repository.findById(id);
    }

    public List<User> fetchUsers() {
        return repository.findAll();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public ResponseEntity<?> updateUser(User user) {
        final User foundUser = repository.findUserById(user.getId());
        if (foundUser != null) {
            return ResponseEntity.ok(repository.save(user));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("User with id %d not found", user.getId()));

    }

    public Optional<User> fetchUserByName(String userName) {
        return repository.findByUserName(userName);
    }
}