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
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User userObject = userRepository.save(user);
        return new ResponseEntity<>(userObject, HttpStatus.CREATED);
    }
}
