package com.example.bitemeals.controller;

import com.example.bitemeals.dto.UserLoginRequest;
import com.example.bitemeals.dto.UserSignupRequest;
import com.example.bitemeals.model.User;
import com.example.bitemeals.service.UserService;

import com.example.bitemeals.utility.JwtUtil;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest userloginRequest) {
        Optional<User> user = userService.authenticateUser(userloginRequest.getEmail(), userloginRequest.getPassword());
        if (user.isPresent()) {
            String token = jwtUtil.generateToken(user.get().getUserName());
            return ResponseEntity.ok().body(Map.of(
                    "message", "User logged in successfully",
                    "userName", user.get().getUserName(),
                    "email",user.get().getEmail(),
                    "token",token
                    ));
        } else {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserSignupRequest signupRequest) {
        boolean isRegistered = userService.registerUser(signupRequest.getUserName(),signupRequest.getEmail(), signupRequest.getPassword());
        if (isRegistered) {
            return ResponseEntity.ok().body(Map.of(
                    "message", "User registered successfully",
                    "userName", signupRequest.getUserName(),
                    "email",signupRequest.getEmail()
            ));
        } else {
            return ResponseEntity.status(400).body("User already exists with entered email");
        }
    }

}
