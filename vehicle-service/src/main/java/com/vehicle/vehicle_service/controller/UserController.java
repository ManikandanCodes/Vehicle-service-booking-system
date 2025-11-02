package com.vehicle.vehicle_service.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.vehicle_service.model.User;
import com.vehicle.vehicle_service.service.UserService;

@CrossOrigin(origins = "http://localhost:4200") 
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error registering user: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {
    User loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
    Map<String, String> response = new HashMap<>();

    if (loggedInUser != null) {

        if ("admin@gmail.com".equals(loggedInUser.getEmail()) && "admin123".equals(loggedInUser.getPassword())) {
            response.put("message", "Admin Login Successful");
            response.put("role", "admin");
        } else {
            response.put("message", "User Login Successful");
            response.put("role", "user");
        }
    } else {
        response.put("message", "Invalid Email or Password");
        response.put("role", "none");
    }

    return response;
}

}

