package com.scriza.in.Wealth.User.User.Service;

import com.scriza.in.Wealth.BankDetails.Entity.Bank;
import com.scriza.in.Wealth.BankDetails.Repository.BankRepository;
import com.scriza.in.Wealth.GlobalConfig.Response;
import com.scriza.in.Wealth.User.User.Entity.User;
import com.scriza.in.Wealth.User.User.Repository.UserRepository;

import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Random;
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BankRepository paymentAccountRepository;




    private final String uploadDir = "/Users/ritiksoni/Downloads/Wealth/src/main/java/com/scriza/in/Wealth/Images";

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
            return new CustomUserDetails(dbUser);
        } else {
            throw new AccessDeniedException(email);
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }
    public User getUserByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
    public User modifyUser(String userId, User userDetails) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return null; 
        }

       
        if (userDetails.getUserName() != null) {
            user.setUserName(userDetails.getUserName());
        }
        if (userDetails.getEmail() != null) {
            user.setEmail(userDetails.getEmail());
        }
        
        return userRepository.save(user); 
    }
    public User updateProfilePicture(String userId, MultipartFile file) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return null;
        }


        Path filePath = Paths.get(uploadDir, userId + "_profile_picture.jpg");

        try {

            file.transferTo(filePath.toFile()); 
            user.setPhoto(filePath.toString()); 
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return userRepository.save(user);
    }
 
}