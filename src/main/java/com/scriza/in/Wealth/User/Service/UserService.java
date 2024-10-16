package com.scriza.in.Wealth.User.Service;

import com.scriza.in.Wealth.User.Entity.User;
import com.scriza.in.Wealth.User.Repository.UserRepository;
import com.scriza.in.Wealth.User.Service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService inMemoryUserDetailsService;

    // Register a new user in the database
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode the password before saving
        return userRepository.save(user); // Save the user in the database
    }

    // Fetch user by username (for login/authentication)
    public User getUserByUsername(String username) {
        return userRepository.findByUserName(username); // Retrieve the user from the database
    }

    // Load user details from either the database or in-memory storage
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check if the user exists in the database
        User dbUser = getUserByUsername(username);

        if (dbUser != null) {
            // User found in the database, return the custom UserDetails implementation
            return new CustomUserDetails(dbUser);
        }

        // If not found in the database, fallback to in-memory storage
        try {
            return inMemoryUserDetailsService.loadUserByUsername(username); // Load user from in-memory
        } catch (UsernameNotFoundException e) {
            // If user is not found in both places, throw an exception
            throw new UsernameNotFoundException("User not found in either database or in-memory storage");
        }
    }
}