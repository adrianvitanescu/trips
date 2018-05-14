package com.sci.myTrips.service;

import com.sci.myTrips.entity.User;
import com.sci.myTrips.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> registerUser(User user) {
        if (userRepository.existsByUserName(user.getUserName())) {
            return ResponseEntity.badRequest().body("User already exists");
        }

        User createdUser = new User(user.getUserName(), passwordEncoder.encode(user.getPassword()),
                user.getFirstName(), user.getLastName(), user.getCity(), user.getAddress(), user.getPhone());

        User result = userRepository.save(createdUser);

        return ResponseEntity.ok().body(result.getId());
    }
}
