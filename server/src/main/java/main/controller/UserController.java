package main.controller;

import main.model.User;
import main.repository.UserRepository;

import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody User user) {
        try {
            if (userRepository.existsByUsername(user.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists.");
            }
            userRepository.save(user);
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        try {
            if (!userRepository.existsByUsername(user.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username does not exist.");
            }
            User registeredUser = userRepository.findByUsername(user.getUsername());
            if (!registeredUser.getPassword().equals(user.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong password.");
            }
            ActiveUsers.addUser(user.getUsername());
            return ResponseEntity.ok("User logged in successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}