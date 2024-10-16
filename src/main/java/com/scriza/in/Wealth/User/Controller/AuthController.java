package com.scriza.in.Wealth.User.Controller;

import com.scriza.in.Wealth.User.Entity.User;
import com.scriza.in.Wealth.User.Security.JwtUtil;
import com.scriza.in.Wealth.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Ensure that this bean is correctly injected.

    // Registration Endpoint
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.registerUser(user);  // Register user using UserService
        return ResponseEntity.ok("User has been registered");
    }

    // Authentication Endpoint
    @PostMapping("/authenticate")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody User user) throws Exception {
        // Fetch existing user by username
        User existingUser = userService.getUserByUsername(user.getUserName());

        if (existingUser == null) {
            throw new Exception("User not found");
        }

        // Debug logs for better visibility
        System.out.println("User found: " + existingUser.getUserName());
        System.out.println("Password from DB: " + existingUser.getPassword());
        System.out.println("Password from Request: " + user.getPassword());

        // Check if password matches
        if (passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            System.out.println("Password matched");
            // Generate JWT Token if credentials match
            String jwt = jwtUtil.generateToken(existingUser.getUserName());
            return ResponseEntity.ok(jwt);
        } else {
            throw new Exception("Invalid credentials");
        }
    }
}