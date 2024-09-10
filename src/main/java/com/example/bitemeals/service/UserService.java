package com.example.bitemeals.service;

import com.example.bitemeals.model.User;
import com.example.bitemeals.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepository.findByUserName(userName)
                .orElseThrow(()-> new UsernameNotFoundException("user name not found"));
    }


    public boolean registerUser(@NonNull String userName,@NonNull String email, String password) {
        log.info("Attempting to register user with email: {}", email);
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            log.warn("Registration failed, user already exists with email: {}", email);
            return false;
        }

        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(newUser);
        log.info("User registered successfully with email: {} and user name:{}", email,userName);
        return true;
    }

    public Optional<User> authenticateUser(@NonNull String email, String rawPassword) {
        log.info("Attempting to authenticate user with email: {}", email);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            log.info("User authenticated successfully with email: {}", email);
            User user = userOptional.get();
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return Optional.of(user);
            }
            return userOptional;
        } else {
            log.warn("Authentication failed for email: {}", email);
            return Optional.empty();
        }
    }
}

