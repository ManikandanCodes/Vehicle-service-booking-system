package com.vehicle.vehicle_service.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.vehicle_service.model.User;
import com.vehicle.vehicle_service.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class PasswordController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    
    private final Map<String, String> otpStorage = new HashMap<>();


    @PostMapping("/forgot-password")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is required!");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This email is not registered!");
        }

        
        
        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStorage.put(email, otp);

        

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("AutoCare - Password Reset OTP");
        message.setText("Dear " + user.getName() + ",\n\n"
                + "We received a request to reset your password.\n\n"
                + "Your One-Time Password (OTP) is: " + otp + "\n\n"
                + "Please use this OTP within 5 minutes to reset your password.\n\n"
                + "If you didn’t request a password reset, you can ignore this email.\n\n"
                + "Best Regards,\nAutoCare Support Team");

        try {
            mailSender.send(message);
            return ResponseEntity.ok("OTP has been sent to your registered email address.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send OTP email. Please try again later.");
        }
    }

    
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");

        if (email == null || otp == null || newPassword == null ||
                email.isEmpty() || otp.isEmpty() || newPassword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All fields are required!");
        }

        
        if (!otpStorage.containsKey(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP not found or expired!");
        }

        if (!otpStorage.get(email).equals(otp)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP!");
        }

        

        User user = userRepository.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }

        user.setPassword(newPassword); 
        userRepository.save(user);

        

        otpStorage.remove(email);

        return ResponseEntity.ok("Password reset successful! You can now log in with your new password.");
    }
}
