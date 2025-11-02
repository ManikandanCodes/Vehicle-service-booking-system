package com.vehicle.vehicle_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehicle.vehicle_service.model.User;
import com.vehicle.vehicle_service.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

  
    public User registerUser(User user) {
        
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already registered!");
        }
        return userRepository.save(user);
    }


    public User loginUser(String email, String password) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null && existingUser.getPassword().equals(password)) {
            return existingUser;
        }
        return null;
    }
}
