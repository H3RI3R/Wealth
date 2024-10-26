package com.scriza.in.Wealth.User.User.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scriza.in.Wealth.GlobalConfig.Response;
import com.scriza.in.Wealth.User.User.Entity.User;
import com.scriza.in.Wealth.User.User.Security.JwtUtil;
import com.scriza.in.Wealth.User.User.Service.UserService;

import io.jsonwebtoken.io.IOException;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

   @PostMapping("/register")
public ResponseEntity<Map<String, Object>> registerUser(
        @RequestPart("user") String userJson,
        @RequestPart(value = "photo", required = false) MultipartFile photo) throws JsonMappingException, JsonProcessingException {
    
    ObjectMapper objectMapper = new ObjectMapper();
    User user;
    
    try {
        user = objectMapper.readValue(userJson, User.class);
    } catch (IOException e) {
        return Response.responseFailure("Invalid user data format.");
    }
    if (userService.isEmailTaken(user.getEmail())) {
        return Response.responseFailure("Email is already in use. Please use a different email.");
    }
    String userId = userService.generateUniqueUserId();
    user.setUserId(userId);
    if (photo != null && !photo.isEmpty()) {
        String photoUrl = userService.savePhoto(photo);
        user.setPhoto(photoUrl);
    }
    userService.registerUser(user);
    return Response.responseSuccess("User has been registered successfully", "user", user);
}
    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, Object>> createAuthenticationToken(@RequestBody User user) {
        try {
            User existingUser = userService.getUserByEmail(user.getEmail());
            if (existingUser == null) {
                return Response.responseFailure("User not found");
            }
            if (passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                String jwt = jwtUtil.generateToken(existingUser.getEmail()); 
                return Response.responseSuccess("Authentication successful", "jwt", jwt);
            } else {
                return Response.responseFailure("Invalid credentials. Please try again.");
            }
        } catch (Exception e) {
            return Response.responseFailure("An error occurred: " + e.getMessage());
        }
    }
}