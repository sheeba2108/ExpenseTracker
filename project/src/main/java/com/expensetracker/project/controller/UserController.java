package com.expensetracker.project.controller;

import com.expensetracker.project.entity.User;
import com.expensetracker.project.service.UserService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        String result = userService.registerUser(user);
        return ResponseEntity.ok(result);
    }

    // Separate POST endpoint for Google login
    @PostMapping("/login/google")
    public ResponseEntity<String> loginWithGoogle(@RequestBody User user) {
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            Optional<User> existingUser = userService.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                return ResponseEntity.ok("Login successful with Google!");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found!");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Google login request.");
        }
    }

    // Separate POST endpoint for email/password login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        String message = userService.loginUser(user);
        if (message.equals("Login successful!")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
        }
    }
}
//http://localhost:8080/api/users/login
//http://localhost:8080/api/users/login/google
//http://localhost:8080/api/users/signup
