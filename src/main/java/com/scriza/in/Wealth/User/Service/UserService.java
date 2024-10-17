package com.scriza.in.Wealth.User.Service;

import com.scriza.in.Wealth.User.Entity.User;
import com.scriza.in.Wealth.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService inMemoryUserDetailsService;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); 
        return userRepository.save(user); 
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isEmailTaken(String email) {
        return userRepository.existsByEmail(email);
    }

    public String generateUniqueUserId() {
        String userId;
        Random random = new Random();
        do {
            String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            StringBuilder charPart = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                charPart.append(characters.charAt(random.nextInt(characters.length())));
            }
            int numberPart = random.nextInt(900) + 100; 
            userId = charPart.toString() + numberPart;
        } while (userRepository.findByUserId(userId) != null);
        return userId;
    }

    public String savePhoto(MultipartFile photo) {
        // Implement file saving logic and return the file URL or path
        return "/path/to/photo";
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User dbUser = getUserByEmail(email);
        if (dbUser != null) {
            System.out.println("KJHGFDFGHJ");
            return new CustomUserDetails(dbUser);
        }else{
            System.out.println("jai siya ram");
            throw new AccessDeniedException(email);
        }
        // return inMJwemoryUserDetailsService.loadUserByUsername(email);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }
}