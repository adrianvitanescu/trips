package com.sci.myTrips.controller;

import com.sci.myTrips.entity.User;
import com.sci.myTrips.payload.JwtAuthenticationResponse;
import com.sci.myTrips.payload.LoginRequest;
import com.sci.myTrips.security.CustomUserDetailService;
import com.sci.myTrips.security.JwtTokenProvider;
import com.sci.myTrips.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final AuthService authService;
    private final CustomUserDetailService customUserDetailService;


    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, AuthService authService, CustomUserDetailService customUserDetailService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.authService = authService;
        this.customUserDetailService = customUserDetailService;
    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword(), Collections.singleton(new SimpleGrantedAuthority("user"))));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user) {
        return authService.registerUser(user);
    }
}
