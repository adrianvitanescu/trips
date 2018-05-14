package com.sci.myTrips.service;

import com.sci.myTrips.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    Optional<User> fetchUser(Long id);
}
