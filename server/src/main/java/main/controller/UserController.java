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
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            if (userRepository.existsByUsername(user.getUsername())) {
                return new ResponseEntity<>("Username already exists.", HttpStatus.BAD_REQUEST);
            }
            userRepository.save(user);
            return new ResponseEntity<>("User registered successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            if (!userRepository.existsByUsername(user.getUsername())) {
                return new ResponseEntity<>("Username does not exist.", HttpStatus.BAD_REQUEST);
            }
            User userFromDB = userRepository.findByUsername(user.getUsername());
            if (!userFromDB.getPassword().equals(user.getPassword())) {
                return new ResponseEntity<>("Incorrect password.", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("User logged in successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}